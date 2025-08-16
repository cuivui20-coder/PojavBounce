<script lang="ts">
    import {onMount} from "svelte";
    import {
        getModuleSettingsTree,
        setSettingsField
    } from "../../integration/rest";
    import type {SettingsTree, SettingsGroup, SettingsField} from "../../integration/types";
    import {slide} from "svelte/transition";
    import {quintOut} from "svelte/easing";
    import {setItem, getItem} from "../../integration/persistent_storage";

    export let moduleName: string;

    let settingsTree: SettingsTree | null = null;
    let groupStates: Record<string, boolean> = {};
    let loading = false;
    let error: string | null = null;

    onMount(async () => {
        await loadSettingsTree();
        await loadGroupStates();
    });

    async function loadSettingsTree() {
        loading = true;
        error = null;
        
        try {
            settingsTree = await getModuleSettingsTree(moduleName);
        } catch (err) {
            error = `Failed to load settings: ${err}`;
            console.error("Failed to load settings tree:", err);
        } finally {
            loading = false;
        }
    }

    async function loadGroupStates() {
        if (!settingsTree) return;
        
        for (const group of settingsTree.groups) {
            const storageKey = `settingsTree.${moduleName}.${group.groupId}`;
            const saved = await getItem(storageKey);
            groupStates[group.groupId] = saved === "true" || group.expanded;
        }
        groupStates = {...groupStates}; // Trigger reactivity
    }

    async function toggleGroup(groupId: string) {
        groupStates[groupId] = !groupStates[groupId];
        groupStates = {...groupStates}; // Trigger reactivity
        
        const storageKey = `settingsTree.${moduleName}.${groupId}`;
        await setItem(storageKey, groupStates[groupId].toString());
    }

    async function updateField(field: SettingsField, newValue: any) {
        try {
            await setSettingsField(moduleName, field.fieldId, newValue);
            // Refresh the tree to get updated values
            await loadSettingsTree();
        } catch (err) {
            console.error("Failed to update field:", err);
            error = `Failed to update ${field.fieldName}: ${err}`;
        }
    }

    function getInputType(fieldType: string): string {
        switch (fieldType) {
            case 'BOOLEAN': return 'checkbox';
            case 'NUMBER': return 'number';
            case 'RANGE': return 'range';
            case 'COLOR': return 'color';
            default: return 'text';
        }
    }

    function formatValue(value: any, fieldType: string): string {
        if (value === null || value === undefined) return '';
        
        switch (fieldType) {
            case 'RANGE':
                if (typeof value === 'object' && value.start !== undefined && value.end !== undefined) {
                    return `${value.start} - ${value.end}`;
                }
                return value.toString();
            case 'BOOLEAN':
                return value ? 'Enabled' : 'Disabled';
            default:
                return value.toString();
        }
    }
</script>

<div class="settings-tree">
    {#if loading}
        <div class="loading">Loading settings...</div>
    {:else if error}
        <div class="error">{error}</div>
    {:else if settingsTree}
        <div class="module-header">
            <h3>{settingsTree.moduleName}</h3>
            <p class="description">{settingsTree.moduleDescription}</p>
        </div>

        {#each settingsTree.groups as group (group.stableId)}
            {#if group.visible}
                <div class="settings-group" class:expanded={groupStates[group.groupId]}>
                    <!-- svelte-ignore a11y-click-events-have-key-events -->
                    <!-- svelte-ignore a11y-no-static-element-interactions -->
                    <div 
                        class="group-header" 
                        class:enabled={group.enabled}
                        class:toggleable={group.groupType === 'TOGGLEABLE'}
                        class:choice={group.groupType === 'CHOICE'}
                        on:click={() => toggleGroup(group.groupId)}
                    >
                        <span class="group-name">{group.groupName}</span>
                        <span class="group-type">{group.groupType}</span>
                        <svg class="expand-icon" class:expanded={groupStates[group.groupId]}>
                            <path d="M6 9l6 6 6-6"/>
                        </svg>
                    </div>

                    {#if groupStates[group.groupId]}
                        <div class="group-content" transition:slide={{ duration: 300, easing: quintOut }}>
                            {#each group.fields as field (field.stableId)}
                                {#if field.visible}
                                    <div class="field" class:enabled={field.enabled}>
                                        <div class="field-header">
                                            <label class="field-name">{field.fieldName}</label>
                                            <span class="field-type">{field.fieldType}</span>
                                        </div>
                                        
                                        <div class="field-input">
                                            {#if field.fieldType === 'BOOLEAN'}
                                                <input 
                                                    type="checkbox" 
                                                    checked={field.currentValue}
                                                    disabled={!field.enabled}
                                                    on:change={(e) => updateField(field, e.target.checked)}
                                                />
                                            {:else if field.fieldType === 'CHOICE'}
                                                <select 
                                                    value={field.currentValue}
                                                    disabled={!field.enabled}
                                                    on:change={(e) => updateField(field, e.target.value)}
                                                >
                                                    {#each field.metadata.choices || [] as choice}
                                                        <option value={choice}>{choice}</option>
                                                    {/each}
                                                </select>
                                            {:else if field.fieldType === 'RANGE'}
                                                <div class="range-input">
                                                    <input 
                                                        type="range"
                                                        min={field.metadata.min || 0}
                                                        max={field.metadata.max || 100}
                                                        value={field.currentValue}
                                                        disabled={!field.enabled}
                                                        on:input={(e) => updateField(field, parseFloat(e.target.value))}
                                                    />
                                                    <span class="range-value">{formatValue(field.currentValue, field.fieldType)}</span>
                                                </div>
                                            {:else}
                                                <input 
                                                    type={getInputType(field.fieldType)}
                                                    value={field.currentValue}
                                                    disabled={!field.enabled}
                                                    on:change={(e) => updateField(field, e.target.value)}
                                                />
                                            {/if}
                                        </div>

                                        <div class="field-value">
                                            Current: {formatValue(field.currentValue, field.fieldType)}
                                        </div>
                                    </div>
                                {/if}
                            {/each}

                            <!-- Render sub-groups recursively -->
                            {#each group.subGroups as subGroup (subGroup.stableId)}
                                {#if subGroup.visible}
                                    <div class="sub-group">
                                        <svelte:self 
                                            moduleName={moduleName} 
                                            settingsTree={{
                                                moduleId: settingsTree.moduleId,
                                                moduleName: settingsTree.moduleName,
                                                moduleDescription: settingsTree.moduleDescription,
                                                groups: [subGroup],
                                                stableId: settingsTree.stableId
                                            }}
                                        />
                                    </div>
                                {/if}
                            {/each}
                        </div>
                    {/if}
                </div>
            {/if}
        {/each}
    {:else}
        <div class="no-settings">No settings available</div>
    {/if}
</div>

<style lang="scss">
  @use "../../colors.scss" as *;

  .settings-tree {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  }

  .loading, .error, .no-settings {
    padding: 16px;
    text-align: center;
    color: $clickgui-text-dimmed-color;
  }

  .error {
    color: #ff6b6b;
    background-color: rgba(255, 107, 107, 0.1);
    border-radius: 4px;
  }

  .module-header {
    padding: 16px;
    border-bottom: 1px solid rgba($clickgui-text-color, 0.1);
    
    h3 {
      margin: 0 0 8px 0;
      color: $accent-color;
      font-size: 18px;
      font-weight: 600;
    }
    
    .description {
      margin: 0;
      color: $clickgui-text-dimmed-color;
      font-size: 14px;
      line-height: 1.4;
    }
  }

  .settings-group {
    border: 1px solid rgba($clickgui-text-color, 0.1);
    border-radius: 8px;
    margin: 8px 0;
    overflow: hidden;
    background-color: rgba($clickgui-base-color, 0.3);

    &.expanded {
      border-color: $accent-color;
    }
  }

  .group-header {
    padding: 12px 16px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: rgba($clickgui-base-color, 0.5);
    transition: background-color 0.2s ease;

    &:hover {
      background-color: rgba($clickgui-base-color, 0.8);
    }

    &.toggleable {
      border-left: 4px solid #4CAF50;
    }

    &.choice {
      border-left: 4px solid #FF9800;
    }

    .group-name {
      font-weight: 600;
      color: $clickgui-text-color;
    }

    .group-type {
      font-size: 12px;
      color: $clickgui-text-dimmed-color;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .expand-icon {
      width: 16px;
      height: 16px;
      stroke: $clickgui-text-dimmed-color;
      stroke-width: 2;
      fill: none;
      transition: transform 0.3s ease;

      &.expanded {
        transform: rotate(180deg);
      }
    }
  }

  .group-content {
    padding: 16px;
    background-color: rgba($clickgui-base-color, 0.2);
  }

  .field {
    margin: 12px 0;
    padding: 12px;
    border-radius: 6px;
    background-color: rgba(255, 255, 255, 0.02);
    border: 1px solid rgba($clickgui-text-color, 0.05);

    &:not(.enabled) {
      opacity: 0.5;
      pointer-events: none;
    }

    .field-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 8px;

      .field-name {
        font-weight: 500;
        color: $clickgui-text-color;
      }

      .field-type {
        font-size: 11px;
        color: $clickgui-text-dimmed-color;
        text-transform: uppercase;
        letter-spacing: 0.5px;
      }
    }

    .field-input {
      margin: 8px 0;

      input, select {
        width: 100%;
        padding: 6px 8px;
        border: 1px solid rgba($clickgui-text-color, 0.2);
        border-radius: 4px;
        background-color: rgba($clickgui-base-color, 0.8);
        color: $clickgui-text-color;
        font-size: 14px;

        &:focus {
          outline: none;
          border-color: $accent-color;
        }

        &:disabled {
          opacity: 0.5;
          cursor: not-allowed;
        }
      }

      input[type="checkbox"] {
        width: auto;
        transform: scale(1.2);
      }

      .range-input {
        display: flex;
        align-items: center;
        gap: 12px;

        input[type="range"] {
          flex: 1;
        }

        .range-value {
          min-width: 60px;
          text-align: right;
          font-size: 12px;
          color: $clickgui-text-dimmed-color;
        }
      }
    }

    .field-value {
      font-size: 12px;
      color: $clickgui-text-dimmed-color;
      margin-top: 4px;
    }
  }

  .sub-group {
    margin: 16px 0;
    padding-left: 16px;
    border-left: 2px solid rgba($accent-color, 0.3);
  }
</style>