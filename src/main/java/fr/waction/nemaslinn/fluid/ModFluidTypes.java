package fr.waction.nemaslinn.fluid;

import fr.waction.nemaslinn.NemasLinn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {

    public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
    public static final ResourceLocation LAVA_FLOWING_RL = new ResourceLocation("block/lava_flow");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, NemasLinn.MODID);

    public static final RegistryObject<FluidType> MOLTEN_OSMIUM_FLUID_TYPE = register("molten_osmium_fluid",
            FluidType.Properties.create());

    public static final RegistryObject<FluidType> MOLTEN_THORIUM_FLUID_TYPE = register("molten_thorium_fluid",
            FluidType.Properties.create());

    public static final RegistryObject<FluidType> MOLTEN_YTTRIUM_FLUID_TYPE = register("molten_yttrium_fluid",
            FluidType.Properties.create());

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(LAVA_STILL_RL, LAVA_FLOWING_RL, properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
