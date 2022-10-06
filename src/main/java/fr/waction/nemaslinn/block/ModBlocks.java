package fr.waction.nemaslinn.block;

import fr.waction.nemaslinn.NemasLinn;
import fr.waction.nemaslinn.item.ModCreativeModTab;
import fr.waction.nemaslinn.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NemasLinn.MODID);

    public static final RegistryObject<Block> OSMIUM_BLOCK = registrerBlock("osmium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> YTTRIUM_BLOCK = registrerBlock("yttrium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> THORIUM_BLOCK = registrerBlock("thorium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);

    public static final RegistryObject<Block> OSMIUM_ORE = registrerBlock("osmium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> YTTRIUM_ORE = registrerBlock("yttrium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> THORIUM_ORE = registrerBlock("thorium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);

    public static final RegistryObject<Block> DEEPSLATE_OSMIUM_ORE = registrerBlock("deepslate_osmium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> DEEPSLATE_YTTRIUM_ORE = registrerBlock("deepslate_yttrium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);
    public static final RegistryObject<Block> DEEPSLATE_THORIUM_ORE = registrerBlock("deepslate_thorium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()), ModCreativeModTab.NEMASLINN_TAB);




    private static <T extends Block>RegistryObject<T> registrerBlock(String name, Supplier<T> block, CreativeModeTab tab){

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registrerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registrerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register (IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
