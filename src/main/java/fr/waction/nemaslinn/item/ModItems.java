package fr.waction.nemaslinn.item;

import fr.waction.nemaslinn.NemasLinn;
import fr.waction.nemaslinn.fluid.ModFluids;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NemasLinn.MODID);

    public static final RegistryObject<Item> OSMIUM_INGOT = ITEMS.register("osmium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> THORIUM_INGOT = ITEMS.register("thorium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> YTTRIUM_INGOT = ITEMS.register("yttrium_ingot", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));


    public static final RegistryObject<Item> RAW_OSMIUM = ITEMS.register("raw_osmium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> RAW_THORIUM = ITEMS.register("raw_thorium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> RAW_YTTRIUM = ITEMS.register("raw_yttrium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));

    public static final RegistryObject<Item> OSMIUM_PICKAXE = ITEMS.register("osmium_pickaxe", () -> new PickaxeItem(Tiers.OSMIUM, 2, -2.8f ,new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> THORIUM_PICKAXE  = ITEMS.register("thorium_pickaxe", () -> new PickaxeItem(Tiers.THORIUM, 1, -2.8f, new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> YTTRIUM_PICKAXE  = ITEMS.register("yttrium_pickaxe", () -> new PickaxeItem(Tiers.YTTRIUM, 0, -2.8f, new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));

    public static final RegistryObject<Item> OSMIUM_PICKAXE_HEAD = ITEMS.register("osmium_pickaxe_head", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> THORIUM_PICKAXE_HEAD  = ITEMS.register("thorium_pickaxe_head", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> YTTRIUM_PICKAXE_HEAD  = ITEMS.register("yttrium_pickaxe_head", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));

    public static final RegistryObject<Item> OSMIUM_SWORD = ITEMS.register("osmium_sword", () -> new SwordItem(Tiers.OSMIUM, 4, -2.4f ,new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> THORIUM_SWORD = ITEMS.register("thorium_sword", () -> new SwordItem(Tiers.THORIUM, 4, -2.4f ,new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> YTTRIUM_SWORD = ITEMS.register("yttrium_sword", () -> new SwordItem(Tiers.YTTRIUM, 4, -2.4f ,new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));

    public static final RegistryObject<Item> OSMIUM_SWORD_BLADE = ITEMS.register("osmium_sword_blade", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> THORIUM_SWORD_BLADE = ITEMS.register("thorium_sword_blade", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));
    public static final RegistryObject<Item> YTTRIUM_SWORD_BLADE = ITEMS.register("yttrium_sword_blade", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB)));


    public static final RegistryObject<Item> PICKAXE_MOLD = ITEMS.register("pickaxe_mold", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB).stacksTo(1)));
    public static final RegistryObject<Item> SWORD_MOLD = ITEMS.register("sword_mold", () -> new Item(new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MOLTEN_OSMIUM_BUCKET  = ITEMS.register("molten_osmium_bucket",
            () -> new BucketItem(ModFluids.MOLTEN_OSMIUM, new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MOLTEN_THORIUM_BUCKET  = ITEMS.register("molten_thorium_bucket",
            () -> new BucketItem(ModFluids.MOLTEN_THORIUM, new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MOLTEN_YTTRIUM_BUCKET  = ITEMS.register("molten_yttrium_bucket",
            () -> new BucketItem(ModFluids.MOLTEN_YTTRIUM, new Item.Properties().tab(ModCreativeModTab.NEMASLINN_TAB).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);
    }

    public static class Tiers {
        public static final Tier OSMIUM = new ForgeTier(
                3,
                851,
                9.0F,
                2.0F,
                15,
                null,
                () -> Ingredient.of(ModItems.OSMIUM_INGOT.get()));

        public static final Tier THORIUM = new ForgeTier(
                3,
                1863,
                10.0F,
                4.0F,
                15,
                null,
                () -> Ingredient.of(ModItems.THORIUM_INGOT.get()));

        public static final Tier YTTRIUM = new ForgeTier(
                4,
                3269,
                20.0F,
                5.0F,
                20,
                null,
                () -> Ingredient.of(ModItems.YTTRIUM_INGOT.get()));
    }

}
