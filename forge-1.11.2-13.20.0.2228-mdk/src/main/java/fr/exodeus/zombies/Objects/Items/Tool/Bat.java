package fr.exodeus.zombies.Objects.Items.Tool;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Bat extends ItemSword{

	public Bat(ToolMaterial material) {
		super(ToolMaterial.WOOD);
	}
	
	public static Item bat;

	public static Integer maxStackSize = 1;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		bat = new ItemSword(ToolMaterial.WOOD).setUnlocalizedName("bat").setMaxStackSize(maxStackSize)
				.setCreativeTab(creativeTab);

	}

	public static void register() {
		bat.setRegistryName(bat.getUnlocalizedName().substring(5));
		GameRegistry.register(bat);
	}

	public static void registeerRednders() {
		registerRender(bat);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

}
