package com.ldtteam.domumornamentum.recipe.architectscutter;

import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Creates the native recipe serializer with the cutter's persistent and network codecs.
 */
public final class ArchitectsCutterRecipeSerializer
{
    private ArchitectsCutterRecipeSerializer()
    {
    }

    public static RecipeSerializer<ArchitectsCutterRecipe> create()
    {
        return new RecipeSerializer<>(ArchitectsCutterRecipe.CODEC, ArchitectsCutterRecipe.STREAM_CODEC);
    }
}
