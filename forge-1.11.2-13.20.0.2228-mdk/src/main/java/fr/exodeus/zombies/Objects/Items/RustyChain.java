package fr.exodeus.zombies.Objects.Items;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RustyChain {
	public static Item rusty_chain;

	public static Integer maxStackSize = 8;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		rusty_chain = new Item().setUnlocalizedName("rusty_chain").setMaxStackSize(maxStackSize)
				.setCreativeTab(creativeTab);

	}

	public static void register() {
		rusty_chain.setRegistryName(rusty_chain.getUnlocalizedName().substring(5));
		GameRegistry.register(rusty_chain);
	}

	public static void registerItemRender() {
		registerRender(rusty_chain);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
