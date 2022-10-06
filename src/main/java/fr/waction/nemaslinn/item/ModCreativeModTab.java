package fr.waction.nemaslinn.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab {

    public static final CreativeModeTab NEMASLINN_TAB = new CreativeModeTab("nemaslinntab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.YTTRIUM_INGOT.get());
        }
    };
}
