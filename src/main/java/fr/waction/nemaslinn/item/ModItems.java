package fr.waction.nemaslinn.item;

import fr.waction.nemaslinn.NemasLinn;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NemasLinn.MODID);

    public static final RegistryObject<Item> OSMIUM_INGOT = ITEMS.register("osmium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> YTTRIUM_INGOT = ITEMS.register("yttrium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> THORIUM_INGOT = ITEMS.register("thorium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));

    public static final RegistryObject<Item> RAW_OSMIUM = ITEMS.register("raw_osmium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> RAW_YTTRIUM = ITEMS.register("raw_yttrium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> RAW_THORIUM = ITEMS.register("raw_thorium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));


    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);
    }

}
