package fr.exodeus.zombies.Objects.Recipe;

import fr.exodeus.zombies.Objects.Items.CleanChain;
import fr.exodeus.zombies.Objects.Items.IronNugget;
import fr.exodeus.zombies.Objects.Items.RustyChain;
import fr.exodeus.zombies.Objects.Items.SandDust;
import fr.exodeus.zombies.Objects.Items.Drinks.PureWater;
import fr.exodeus.zombies.Objects.Items.Food.CookedFlesh;
import fr.exodeus.zombies.Objects.Items.Food.PurifiedFlesh;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {

	public static void loadRecipes() {
		// Recipe

		// -----GameRegistry.addRecipe(new ItemStack(Item.ingotIron) , new
		// Object[]{"xyx","yxy","xyx", 'y' , new ItemStack(ironNugget)});
		GameRegistry.addRecipe(new ItemStack(Items.IRON_INGOT),
				new Object[] { "xxx", "xxx", 'x', new ItemStack(IronNugget.iron_nugget) });

		GameRegistry.addRecipe(new ItemStack(IronNugget.iron_nugget, 6),
				new Object[] { "x", 'x', new ItemStack(Items.IRON_INGOT) });

		GameRegistry.addRecipe(new ItemStack(Items.CHAINMAIL_HELMET),
				new Object[] { "yyy", "yxy", 'y', new ItemStack(CleanChain.clean_chain) });

		GameRegistry.addRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE),
				new Object[] { "yxy", "yyy", "yyy", 'y', new ItemStack(CleanChain.clean_chain) });

		GameRegistry.addRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS),
				new Object[] { "yyy", "yxy", "yxy", 'y', new ItemStack(CleanChain.clean_chain) });

		GameRegistry.addRecipe(new ItemStack(Items.CHAINMAIL_BOOTS),
				new Object[] { "yxy", "yxy", 'y', new ItemStack(CleanChain.clean_chain) });

		//GameRegistry.addRecipe(new ItemStack(bat), new Object[] { "xxx", "yzz", "xxx", 'y', new ItemStack(Items.STICK),
		//		'z', new ItemStack(Blocks.PLANKS) });

		// Recipe
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.CLAY),
				new Object[] { new ItemStack(Blocks.SAND), new ItemStack(Items.WATER_BUCKET) });

		GameRegistry.addShapelessRecipe(new ItemStack(PurifiedFlesh.purified_flesh),
				new Object[] { new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(Blocks.SAND), new Object[] { "yy", "yy", 'y', new ItemStack(SandDust.sand_dust) });

		GameRegistry.addShapelessRecipe(new ItemStack(SandDust.sand_dust, 4), new Object[] { new ItemStack(Blocks.SAND) });

		// Smelting

		GameRegistry.addSmelting(PurifiedFlesh.purified_flesh, new ItemStack(CookedFlesh.cooked_flesh), 0.1f);
		GameRegistry.addSmelting(RustyChain.rusty_chain, new ItemStack(CleanChain.clean_chain), 0.1f);
		
		GameRegistry.addSmelting(Items.POTIONITEM, new ItemStack(PureWater.pureWater), 0.1f);

	}

}
