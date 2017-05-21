package fr.exodeus.zombies.Objects.Items.Usable;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Items.Effects.ItemBandage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Bandage {
	public static Item bandage;

	public static Integer maxStackSize = 4;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		bandage = new ItemBandage().setUnlocalizedName("bandage").setMaxStackSize(maxStackSize)
				.setCreativeTab(creativeTab);

	}

	public static void register() {
		bandage.setRegistryName(bandage.getUnlocalizedName().substring(5));
		GameRegistry.register(bandage);
	}

	public static void registerItemRender() {
		registerRender(bandage);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
