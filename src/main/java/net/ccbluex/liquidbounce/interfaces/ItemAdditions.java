package net.ccbluex.liquidbounce.interfaces;

import net.ccbluex.liquidbounce.utils.item.ItemClass;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

public interface ItemAdditions {
    @NotNull
    ItemClass liquidBounce$getItemClass();

    static ItemClass getItemClass(Item item) {
        return ((ItemAdditions) item).liquidBounce$getItemClass();
    }
}
