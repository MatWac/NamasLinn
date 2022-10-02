package fr.waction.nemaslinn.world.feature;

import fr.waction.nemaslinn.NemasLinn;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, NemasLinn.MODID);

    public static final RegistryObject<PlacedFeature> TITANIUM_ORE_PLACED = PLACED_FEATURE.register("titanium_ore_placed",
        () -> new PlacedFeature(ModConfiguredFeatures.TITANIUM_ORE.getHolder().get(),
                commonOrePlacement(5,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));


    public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2){
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int i,PlacementModifier modifier){
        return orePlacement(CountPlacement.of(i), modifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int i,PlacementModifier modifier){
        return orePlacement(RarityFilter.onAverageOnceEvery(i), modifier);
    }


    public static void register(IEventBus eventBus) {
        PLACED_FEATURE.register(eventBus);

    }
}
