package fr.exodeus.zombies.Objects.Items.Effects;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Potion.Infection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemAntibiotic extends Item {

	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player,
			EnumHand hand) {

		
		ItemStack itemOut = new ItemStack(this);
		itemOut.setCount(player.inventory.getCurrentItem().getCount() - 1);


		if (player.isPotionActive(Infection.infection))
			if (player.world.isRemote)
				player.sendMessage(new TextComponentString("§cInfection healed"));

		player.setHealth(player.getHealth() + 1);
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 400, 1)); // regeneration
		player.removePotionEffect(Potion.getPotionById(17)); // hunger
		player.removePotionEffect(Potion.getPotionById(9)); // confusion
		player.removePotionEffect(Potion.getPotionById(15)); // blindness
		player.removePotionEffect(Potion.getPotionById(9)); // poison
		player.removePotionEffect(Potion.getPotionById(4)); // dig slow down
		player.removePotionEffect(Potion.getPotionById(16)); // night vision

		player.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, 1F, 1F);

		player.removePotionEffect(Infection.infection);

		return new ActionResult(EnumActionResult.SUCCESS, itemOut);

	}
}
