package fr.waction.nemaslinn.block.entity;

import fr.waction.nemaslinn.NemasLinn;
import fr.waction.nemaslinn.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NemasLinn.MODID);

    public static final RegistryObject<BlockEntityType<RawOreFoundryBlockEntity>> RAW_ORE_FOUNDRY =
            BLOCK_ENTITIES.register("raw_ore_foundry", () ->
                    BlockEntityType.Builder.of(RawOreFoundryBlockEntity::new,
                            ModBlocks.RAW_ORE_FOUNDRY.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
