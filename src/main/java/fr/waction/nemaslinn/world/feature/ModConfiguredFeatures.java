package fr.waction.nemaslinn.world.feature;

import com.google.common.base.Suppliers;
import fr.waction.nemaslinn.NemasLinn;
import fr.waction.nemaslinn.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, NemasLinn.MODID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_TITANIUM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TITANIUM_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_IRIDIUM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.IRIDIUM_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_PLATINIUM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PLATINIUM_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?,?>> TITANIUM_ORE = CONFIGURED_FEATURES.register("titanium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_TITANIUM_ORES.get(), 4)));
    public static final RegistryObject<ConfiguredFeature<?,?>> PLATINIUM_ORE = CONFIGURED_FEATURES.register("platinium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_PLATINIUM_ORES.get(), 3)));
    public static final RegistryObject<ConfiguredFeature<?,?>> IRIDIUM_ORE = CONFIGURED_FEATURES.register("iridium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_IRIDIUM_ORES.get(), 1)));





    public static void register(IEventBus eventBus) {

        CONFIGURED_FEATURES.register(eventBus);

    }
}
