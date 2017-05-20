package fr.exodeus.zombies.Core;

import java.util.ArrayList;

import fr.exodeus.zombies.Objects.Potion.Bonebreak;
import fr.exodeus.zombies.Objects.Potion.Infection;
import net.minecraft.init.Biomes;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class Reference {

	public static final String MOD_ID = "zombies";
	public static final String MOD_NAME = "Zombies Plus";
	public static final String VERSION = "1.0.0";
	public static final String CLIENT_PROXY_CLASS = "fr.exodeus.zombies.Proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "fr.exodeus.zombies.Proxy.CommonProxy";

	public static final Biome[] allBiomes = { Biomes.OCEAN, Biomes.DEFAULT, Biomes.PLAINS, Biomes.DESERT,
			Biomes.EXTREME_HILLS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMPLAND, Biomes.RIVER, Biomes.BEACH,
			Biomes.DESERT_HILLS, Biomes.FOREST_HILLS, Biomes.TAIGA_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.JUNGLE,
			Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_BEACH, Biomes.COLD_BEACH,
			Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.ROOFED_FOREST, Biomes.COLD_TAIGA,
			Biomes.COLD_TAIGA_HILLS, Biomes.REDWOOD_TAIGA, Biomes.REDWOOD_TAIGA_HILLS, Biomes.EXTREME_HILLS_WITH_TREES,
			Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.MESA, Biomes.MESA_ROCK, Biomes.MESA_CLEAR_ROCK };


	public static final int MAX_THIRST_LEVEL = 20;
	public static final int MAX_THIRST_SATURATION = 6;
	
	public static final int INITIAL_SATURATION = 4;

	public static ArrayList<Potion> potionTypes = new ArrayList<Potion>();

	public static void registerAllPotionEffects() {
		potionTypes.add(Potion.getPotionById(15)); // 1.6.4Potion.Blindness
		potionTypes.add(Potion.getPotionById(9)); // 1.6.4Confusion
		potionTypes.add(Potion.getPotionById(17)); // 1.6.4Hunger
		potionTypes.add(Potion.getPotionById(18)); // 1.6.4Weaknees
		potionTypes.add(Potion.getPotionById(2)); // 1.6.4MoveSlowdown
		potionTypes.add(Potion.getPotionById(4)); // 1.6.4DigSlowdown
		potionTypes.add(Infection.infection);
		potionTypes.add(Bonebreak.bonebreak);

	}
}
