package fr.exodeus.zombies.Objects.Items.Effects;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstLogic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDrinks extends Item {

	// ===========================================

	private int thirstRegeneration;
	private float thirstSaturation;

	private boolean hasAlcohol = false;
	private float alcoholLevel = 0;

	private float poisonRisk = 0;

	private ItemStack usedItem = new ItemStack(Items.GLASS_BOTTLE,1);

	// ===========================================

	public ItemDrinks(int thirstRegeneration, float thirstSaturation) {
		this.thirstRegeneration = thirstRegeneration;
		this.thirstSaturation = thirstSaturation;
	}

	/**
	 * default set to 0f be careful to set to float
	 */

	public void setAlcoholLevel(float alcoholLevel) {
		this.alcoholLevel = alcoholLevel;
		if (alcoholLevel != 0)
			hasAlcohol = true;
	}

	public void setPoisonRisk(float poisonRisk) {
		this.poisonRisk = poisonRisk;
	}

	public void setResultItem(ItemStack itemStack) {
		this.usedItem = itemStack;
	}

	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

		if(worldIn.isRemote)
			return stack;
		
		usedItem = new ItemStack(Items.GLASS_BOTTLE,1);
		
		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			stack.shrink(1);
		}

		if (entityplayer != null && !worldIn.isRemote && !entityplayer.capabilities.isCreativeMode) {

			ThirstLogic.onPlayerDrink(entityplayer, thirstRegeneration, thirstSaturation, poisonRisk);

		}

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			if (stack.isEmpty()) {
				//entityplayer.inventory.addItemStackToInventory(usedItem);
				//entityplayer.inventory.markDirty();
				MainZombies.logString("test2");
				return usedItem;
			}else

			if (entityplayer != null) {
				entityplayer.inventory.addItemStackToInventory(usedItem);
				entityplayer.inventory.markDirty();
				MainZombies.logString("test1");
			}
		}

		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

}
