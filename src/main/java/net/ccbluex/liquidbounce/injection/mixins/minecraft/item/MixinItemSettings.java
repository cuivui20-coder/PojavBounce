package net.ccbluex.liquidbounce.injection.mixins.minecraft.item;

import net.ccbluex.liquidbounce.interfaces.ItemSettingsAddition;
import net.ccbluex.liquidbounce.utils.item.ItemClass;
import net.ccbluex.liquidbounce.utils.item.classes.ArmorItem;
import net.ccbluex.liquidbounce.utils.item.classes.SwordItem;
import net.ccbluex.liquidbounce.utils.item.classes.ToolItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Item.Settings.class)
public class MixinItemSettings implements ItemSettingsAddition {
    @Unique
    private ItemClass itemClass = null;

    private void setItemClass(ItemClass itemClass) {
        if (this.itemClass != null) {
            throw new IllegalStateException("Item can only have one class ot once!");
        }

        this.itemClass = itemClass;
    }

    private boolean fixed = false;

    @Override
    public ItemClass liquidBounce$buildItemClass() {
        if (this.fixed) {
            throw new IllegalStateException("buildItemClass can only be called once!");
        }

        this.fixed = true;

        return Objects.requireNonNullElseGet(this.itemClass, ItemClass::new);
    }

    @Inject(method = "armor", at = @At("RETURN"))
    private void injectArmorClass(ArmorMaterial material, EquipmentType type, CallbackInfoReturnable<Item.Settings> cir) {
        this.setItemClass(new ArmorItem(material, type));
    }
    @Inject(method = "sword", at = @At("RETURN"))
    private void injectSwordClass(ToolMaterial material, float attackDamage, float attackSpeed, CallbackInfoReturnable<Item.Settings> cir) {
        this.setItemClass(new SwordItem());
    }
    @Inject(method = "tool", at = @At("RETURN"))
    private void injectSwordClass(ToolMaterial material, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds, CallbackInfoReturnable<Item.Settings> cir) {
        this.setItemClass(new ToolItem(material));
    }

}
