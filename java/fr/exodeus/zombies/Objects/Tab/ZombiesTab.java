package fr.exodeus.zombies.Objects.Tab;

import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ZombiesTab extends CreativeTabs{

	public ZombiesTab() {
		super("zombies_tab");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Antibiotic.antibiotic);
	}
}
