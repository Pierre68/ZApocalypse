package fr.exodeus.zombies.Objects.Items;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class IronNugget {
	public static Item iron_nugget;

	public static Integer maxStackSize = 64;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		iron_nugget = new Item().setUnlocalizedName("iron_nugget").setMaxStackSize(maxStackSize)
				.setCreativeTab(creativeTab);

	}

	public static void register() {
		iron_nugget.setRegistryName(iron_nugget.getUnlocalizedName().substring(5));
		GameRegistry.register(iron_nugget);
	}

	public static void registerItemRender() {
		registerRender(iron_nugget);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
