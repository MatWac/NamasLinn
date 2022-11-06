package fr.waction.nemaslinn.fluid;

import fr.waction.nemaslinn.NemasLinn;
import fr.waction.nemaslinn.block.ModBlocks;
import fr.waction.nemaslinn.item.ModItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, NemasLinn.MODID);

    public static final RegistryObject<FlowingFluid> MOLTEN_OSMIUM = FLUIDS.register("molten_osmium_fluid",() -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_OSMIUM_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_OSMIUM = FLUIDS.register("flowing_molten_osmium",() -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_OSMIUM_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MOLTEN_YTTRIUM = FLUIDS.register("molten_yttrium_fluid",() -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_YTTRIUM_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_YTTRIUM = FLUIDS.register("flowing_molten_yttrium",() -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_YTTRIUM_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MOLTEN_THORIUM = FLUIDS.register("molten_thorium_fluid",() -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_THORIUM_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_THORIUM = FLUIDS.register("flowing_molten_thorium",() -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_THORIUM_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MOLTEN_OSMIUM_PROPERTIES =
            new ForgeFlowingFluid.Properties(ModFluidTypes.MOLTEN_OSMIUM_FLUID_TYPE, MOLTEN_OSMIUM, FLOWING_MOLTEN_OSMIUM).bucket(ModItems.MOLTEN_OSMIUM_BUCKET).block(ModBlocks.MOLTEN_OSMIUM_BLOCK);

    public static final ForgeFlowingFluid.Properties MOLTEN_YTTRIUM_PROPERTIES =
            new ForgeFlowingFluid.Properties(ModFluidTypes.MOLTEN_YTTRIUM_FLUID_TYPE, MOLTEN_YTTRIUM, FLOWING_MOLTEN_YTTRIUM).bucket(ModItems.MOLTEN_YTTRIUM_BUCKET).block(ModBlocks.MOLTEN_YTTRIUM_BLOCK);

    public static final ForgeFlowingFluid.Properties MOLTEN_THORIUM_PROPERTIES =
            new ForgeFlowingFluid.Properties(ModFluidTypes.MOLTEN_THORIUM_FLUID_TYPE, MOLTEN_THORIUM, FLOWING_MOLTEN_THORIUM).bucket(ModItems.MOLTEN_THORIUM_BUCKET).block(ModBlocks.MOLTEN_THORIUM_BLOCK);

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }
}
