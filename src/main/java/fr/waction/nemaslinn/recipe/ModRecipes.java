package fr.waction.nemaslinn.recipe;

import fr.waction.nemaslinn.NemasLinn;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NemasLinn.MODID);

    public static final RegistryObject<RecipeSerializer<RawOreFoundryRecipe>> RAW_ORE_MELTING_SERIALIZER =
            SERIALIZERS.register("raw_ore_melting", () -> RawOreFoundryRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}