package fr.waction.nemaslinn.block;

import fr.waction.nemaslinn.NemasLinn;
import fr.waction.nemaslinn.fluid.ModFluids;
import fr.waction.nemaslinn.item.ModCreativeModTab;
import fr.waction.nemaslinn.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NemasLinn.MODID);

    public static final RegistryObject<Block> OSMIUM_BLOCK = registerBlock("osmium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> YTTRIUM_BLOCK = registerBlock("yttrium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> THORIUM_BLOCK = registerBlock("thorium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);

    public static final RegistryObject<Block> OSMIUM_ORE = registerBlock("osmium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> YTTRIUM_ORE = registerBlock("yttrium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> THORIUM_ORE = registerBlock("thorium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);

    public static final RegistryObject<Block> DEEPSLATE_OSMIUM_ORE = registerBlock("deepslate_osmium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> DEEPSLATE_YTTRIUM_ORE = registerBlock("deepslate_yttrium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> DEEPSLATE_THORIUM_ORE = registerBlock("deepslate_thorium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);

    public static final RegistryObject<Block> RAW_ORE_FOUNDRY = registerBlock("raw_ore_foundry",
            () -> new RawOreFoundryBlock(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(RawOreFoundryBlock.LIT) ? 15 : 0)), ModCreativeModTab.NEMASLINN_TAB);

    public static final RegistryObject<LiquidBlock> MOLTEN_OSMIUM_BLOCK = BLOCKS.register("molten_osmium_block",() -> new LiquidBlock(ModFluids.MOLTEN_OSMIUM, BlockBehaviour.Properties.copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOLTEN_YTTRIUM_BLOCK = BLOCKS.register("molten_yttrium_block",() -> new LiquidBlock(ModFluids.MOLTEN_YTTRIUM, BlockBehaviour.Properties.copy(Blocks.LAVA)));
    public static final RegistryObject<LiquidBlock> MOLTEN_THORIUM_BLOCK = BLOCKS.register("molten_thorium_block",() -> new LiquidBlock(ModFluids.MOLTEN_THORIUM, BlockBehaviour.Properties.copy(Blocks.LAVA)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register (IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
