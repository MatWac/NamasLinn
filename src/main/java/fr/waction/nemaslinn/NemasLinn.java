package fr.waction.nemaslinn;

import com.mojang.logging.LogUtils;
import fr.waction.nemaslinn.block.ModBlocks;
import fr.waction.nemaslinn.block.entity.ModBlockEntities;
import fr.waction.nemaslinn.item.ModItems;
import fr.waction.nemaslinn.screen.ModMenuTypes;
import fr.waction.nemaslinn.screen.RawOreFoundryScreen;
import fr.waction.nemaslinn.world.feature.ModConfiguredFeatures;
import fr.waction.nemaslinn.world.feature.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NemasLinn.MODID)
public class NemasLinn
{
    public static final String MODID = "nemaslinn";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NemasLinn()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            MenuScreens.register(ModMenuTypes.RAW_ORE_FOUNDRY_MENU.get(), RawOreFoundryScreen::new);

        }
    }
}
