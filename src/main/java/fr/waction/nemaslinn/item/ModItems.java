package fr.waction.nemaslinn.item;

import fr.waction.nemaslinn.NemasLinn;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NemasLinn.MODID);

    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> IRIDIUM_INGOT = ITEMS.register("iridium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> PLATINIUM_INGOT = ITEMS.register("platinium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));

    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> RAW_IRIDIUM = ITEMS.register("raw_iridium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> RAW_PLATINIUM = ITEMS.register("raw_platinium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));


    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);
    }

}
