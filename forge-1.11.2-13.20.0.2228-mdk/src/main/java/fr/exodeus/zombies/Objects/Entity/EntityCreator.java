package fr.exodeus.zombies.Objects.Entity;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieButcher;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCivil;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCrawler;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieHerobrine;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieMiner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieGeneral;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePolice;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePrisoner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSoldier;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSurgeon;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemEgg;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityCreator {

	private static int UniqueID = 12000;

	public static final void createEntity(Class entityClass, String entityName, EnumCreatureType typeOfCreature,
			int probability, int minSpawn, int maxSpawn, Biome[] biomes, int eggPrimary, int eggSecondary,
			boolean hasSpawnEgg) {

		int trackingRange = 64; // pas changer
		int updateFrequency = 1; // pas changer
		boolean sendsVelocityUpdates = true; // pas changer
		
		ResourceLocation ressource = new ResourceLocation(Reference.MOD_ID, "textures/entity/" + entityName + ".png");

		EntityRegistry.registerModEntity(ressource, entityClass, entityName, UniqueID, MainZombies.instance, trackingRange,
				updateFrequency, sendsVelocityUpdates);
		
		EntityRegistry.addSpawn(entityClass, probability, minSpawn, maxSpawn, typeOfCreature, biomes);
		
		

		if (hasSpawnEgg) {
			EntityRegistry.registerEgg(ressource, eggPrimary, eggSecondary);
			//ItemEgg.getItemById(383).setCreativeTab(MainZombies.tabZombies); bouge tous les oeufs :/
		}

		++UniqueID;

	}

	public static final void registerEntities() {

		Biome[] biomes = Reference.allBiomes;

		createEntity(ZombieButcher.class, "zombie_butcher", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);

		createEntity(ZombieCivil.class, "zombie_civil", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);

		createEntity(ZombieCrawler.class, "zombie_crawler", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);

		createEntity(ZombieHerobrine.class, "zombie_herobrine", EnumCreatureType.MONSTER, 100, 10, 100, biomes,
				0x4D5246, 0x510000, true);
		createEntity(ZombieMiner.class, "zombie_miner", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);
		createEntity(ZombieGeneral.class, "zombie_general", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);
		createEntity(ZombiePolice.class, "zombie_police", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);
		createEntity(ZombiePrisoner.class, "zombie_prisoner", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);
		createEntity(ZombieSoldier.class, "zombie_soldier", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);
		createEntity(ZombieSurgeon.class, "zombie_surgeon", EnumCreatureType.MONSTER, 100, 10, 100, biomes, 0x4D5246,
				0x510000, true);

	}

}
