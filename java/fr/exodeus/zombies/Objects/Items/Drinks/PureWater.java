package fr.exodeus.zombies.Objects.Items.Drinks;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Items.Effects.ItemAntibiotic;
import fr.exodeus.zombies.Objects.Items.Effects.ItemDrinks;
import fr.exodeus.zombies.Objects.Items.Food.Donut;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PureWater extends Item{
	
	public static Item pureWater;

	public static Integer maxStackSize = 4;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		pureWater = new ItemDrinks(8f,2f).setUnlocalizedName("pure_water").setMaxStackSize(maxStackSize)
				.setCreativeTab(creativeTab);
	}

	public static void register() {
		pureWater.setRegistryName(pureWater.getUnlocalizedName().substring(5));
		GameRegistry.register(pureWater);
	}

	public static void registerItemRender() {
		registerRender(pureWater);
	}

	private static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	
	
	
	
	
	
	
	

}
