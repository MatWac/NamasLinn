package fr.waction.nemaslinn.screen;

import fr.waction.nemaslinn.NemasLinn;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, NemasLinn.MODID);

    public static final RegistryObject<MenuType<RawOreFoundryMenu>> RAW_ORE_FOUNDRY_MENU =
            registerMenuType(RawOreFoundryMenu::new, "raw_ore_foundry_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}