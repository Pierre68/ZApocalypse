package fr.exodeus.zombies.Objects.Game.Thirst;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities.IPlayerExtendedProperties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class ThirstLogic {

	private static DamageSource damagerThirst = new DamageThirst();
	private static DamageSource damagerOverhydration = new DamageOverhydration();
	// =======================================
	// Player status

	public EntityPlayer player;

	public int thirstLevel = 0;
	float thirstSaturation;

	public boolean isPoisoned;

	private float thirstExhaustion = 0;

	// =======================================

	public ThirstLogic(EntityPlayer player) {
		this.thirstLevel = Reference.MAX_THIRST_LEVEL;
		this.thirstSaturation = Reference.INITIAL_SATURATION;
		this.player = player;

		readData();
	}

	public void onTick() {

		//player.sendMessage(new TextComponentString("§2" + thirstLevel + "§a/§2" + thirstSaturation + "§a/§2" + thirstExhaustion));

		if (thirstExhaustion > 5f) {

			if (thirstLevel <= 0) {
				thirstExhaustion = 4.8f;
				player.attackEntityFrom(getDamagerThirst(), 1);
			} else {
				thirstExhaustion = 0f + (1 - thirstLevel/20);
			}

			if (thirstSaturation > 0f) {
				thirstSaturation = Math.max(thirstSaturation - 1, 0);
				if (thirstLevel == 0) {
					thirstExhaustion = 4.8f;
				}

			} else {
				thirstLevel = Math.max(thirstLevel - 1, 0);
			}
		}

		if (thirstSaturation > Reference.MAX_THIRST_SATURATION) {
			float damage = (this.thirstSaturation - Reference.MAX_THIRST_SATURATION) / 2;
			player.attackEntityFrom(getDamagerOverhydration(), damage);
		}
		if (thirstLevel > Reference.MAX_THIRST_LEVEL) {
			thirstLevel = Math.max(thirstLevel - 1, 0);
			player.attackEntityFrom(getDamagerOverhydration(), 1);
		}

		this.computeExhaustion(player);

		this.writeData();

		//MainZombies.logString("" + this.thirstLevel + " " + player.isEntityAlive());
		// player.sendMessage(new TextComponentString(thirstExhaustion + " / " +
		// thirstSaturation + " / " + thirstLevel));

	}

	public void onDeath() {
		/*
		 * this.thirstLevel = Reference.MAX_THIRST_LEVEL; this.thirstSaturation
		 * = Reference.MAX_THIRST_SATURATION; this.writeData();
		 */ // RIEN FAIRE CAR L'ENTITY CHANGE
	}

	public void onAttack() {
		addExhaustion(0.4f);
	}

	public void onHurt() {
		addExhaustion(0.5f);
	}

	public void onBlockBreak() {
		addExhaustion(0.05f);
	}

	public void onBlockPlace() {
		// TODO event
	}

	public void onHeal(LivingHealEvent e) {
		if (thirstLevel <= 3) {
			if (e.getAmount() <= 1)
				e.setCanceled(true);
		}
	}

	// =========================================================================

	public void onPlayerDrink(float thirstRegen, float thirstSaturation, float poisonRisk) {
		addWater(thirstRegen, thirstSaturation);
		// TODO poison !
	}

	// =========================================================================

	private void writeData() {

		// if(true)return;

		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return;

		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop != null) {
			prop.setThirstLevel(thirstLevel);
			prop.setThirstSaturation(thirstSaturation);
			prop.setThirstExhaustion(thirstExhaustion);

			Capabilities.syncServerDataToClient((EntityPlayerMP) player);
		}
	}

	private void readData() {

		MainZombies.logString("======= readData Start");

		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return;

		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop != null) {
			this.thirstLevel = prop.getThirstLevel();
			this.thirstSaturation = prop.getThirstSaturation();
			this.thirstExhaustion = prop.getThirstExhaustion();
			
		}

		MainZombies.logString("======= readData succes");
	}

	// =========================================================================

	private void addWater(float thirstRegen, float thirstSaturation) {
		this.thirstSaturation = this.thirstSaturation + thirstSaturation;
		this.thirstLevel = (int) (this.thirstLevel + thirstRegen);

		this.thirstExhaustion = 0;

		// TODO change r tout en float pas oublie les capabilities
		thirstExhaustion = 0;
	}

	private void addExhaustion(float f) {
		if(player.world.getDifficulty() == EnumDifficulty.PEACEFUL || player.capabilities.isCreativeMode)
			return;
		thirstExhaustion = thirstExhaustion + f;
	}

	private void computeExhaustion(EntityPlayer player) {

		float movement = player.isRiding() ? 0 : movementSpeed();
		float exhaustAmplifier = isNight() ? 0.9f : 1;
		float multiplier = getCurrentBiomeMultiplier();
		
		if (player.isInsideOfMaterial(Material.WATER)) {
			if (movement > 0) {
				addExhaustion(0.03f * movement * 0.003F * exhaustAmplifier);
			}
		} else if (player.isInWater()) {
			if (movement > 0) {
				addExhaustion(0.03f * movement * 0.003F * exhaustAmplifier);
			}
		} else if (player.onGround) {

			if (movement > 0) {
				if (player.isSprinting()) {
					addExhaustion(0.1f * movement * 0.018F * multiplier * exhaustAmplifier);
				} else {

					addExhaustion(0.1f * movement * 0.018F * multiplier * exhaustAmplifier);
				}
			}
		} else if (!player.onGround && !player.isRiding()) {
			if (player.isSprinting()) {
				addExhaustion(0.02f * 0.5f * multiplier * exhaustAmplifier);
			} else {
				addExhaustion(0.01f * 0.1f * multiplier * exhaustAmplifier);
			}
		}
	}

	// --------------------------------

	private float getCurrentBiomeMultiplier() {

		TempCategory cat = player.world.getBiome(player.getPosition()).getTempCategory();

		if (cat == TempCategory.COLD)
			return 1.5f;
		if (cat == TempCategory.MEDIUM)
			return 1f;
		if (cat == TempCategory.WARM)
			return 2f;
		if (cat == TempCategory.OCEAN)
			return 1.25f;

		return 1;

	}

	private float movementSpeed() {
		
		if(player.isSneaking()){
			return 0.6f;
		}else if(player.isSprinting()){
			return 2.2f;
		}else if(player.isInWater()){
			return 0.9f;
		}else if(player.isElytraFlying()){
			return 0.4f;
		}else{
			return 1.0f;
		}

	}

	private boolean isNight() {

		return !player.world.isDaytime();
	}

	// ==============================================================================
	// getters
	public int getThirstLevel() {
		return this.thirstLevel;
	}

	public boolean getIsPoisoned() {
		return this.isPoisoned;
	}
	
	public DamageSource getDamagerThirst(){
		if(this.damagerThirst == null)
			this.damagerThirst = new DamageThirst();
		return this.damagerThirst;
	}

	public DamageSource getDamagerOverhydration(){
		if(this.damagerOverhydration == null)
			this.damagerOverhydration = new DamageOverhydration();
		return this.damagerOverhydration;
	}
	
	// ==============================================================================

	public static class DamageThirst extends DamageSource {
		public DamageThirst() {
			super("thirst");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public TextComponentString getDeathMessage(EntityLivingBase entity) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				String deathmessage = "";
				// TODO do custom death messages lang("death.dried") ?
				deathmessage = player.getDisplayName().getFormattedText() + "'s body is now made up of 0% water!";
				deathmessage = player.getDisplayName().getFormattedText() + " dried out";

				return new TextComponentString(deathmessage);

			}
			return (TextComponentString) super.getDeathMessage(entity);
		}
	}
	
	public static class DamageOverhydration extends DamageSource {
		public DamageOverhydration() {
			super("overhydration");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public TextComponentString getDeathMessage(EntityLivingBase entity) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				String deathmessage = "";
				// TODO do custom death messages lang("death.dried") ?
				deathmessage = player.getDisplayName().getFormattedText() + "'s body exploded";
				deathmessage = player.getDisplayName().getFormattedText() + " had to much presure";

				return new TextComponentString(deathmessage);

			}
			return (TextComponentString) super.getDeathMessage(entity);
		}
	}

}
