package fr.exodeus.zombies.Objects.Potion;

import java.util.HashMap;
import java.util.Random;

import fr.exodeus.zombies.Core.MainZombies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bonebreak extends Potion {
	public static Potion bonebreak;

	public Bonebreak(int par1, boolean par2, int par3) {
		super(par2, par3);
		setPotionName("potion.bonebreak");
		setIconIndex(1, 0);

	}

	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	public static void init() {
		bonebreak = (new Bonebreak(32, true, 0));

		REGISTRY.register(51, new ResourceLocation("zombies", "bonebreak"), bonebreak);
	}

	public static ResourceLocation textureResource = new ResourceLocation("zombies", "textures/gui/status_gui.png");

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
		return true;
	}

	public static void effects(EntityPlayer player) {

		Random r = new Random();
		int lvl = player.getActivePotionEffect(bonebreak).getAmplifier() + 1;

		switch (r.nextInt(15000 / lvl)) {
		case 1:
			player.attackEntityFrom(DamageSource.GENERIC, 2);
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 100 + r.nextInt(50) * lvl, 0)); // confusion
			break;
		case 2:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 300 + r.nextInt(500), r.nextInt(3))); // dig
																													// slow
																													// down
			break;
		case 3:
			player.addPotionEffect(
					new PotionEffect(Potion.getPotionById(18), 300 + r.nextInt(500), r.nextInt(2 + lvl))); // weakness
			break;
		case 4:
			if (r.nextInt(30) == 0) {
				player.addPotionEffect(
						new PotionEffect(Potion.getPotionById(2), 300 + r.nextInt(500), r.nextInt(2 + lvl))); // move
																												// slow
																												// down
				//Infection.infectPlayer(player, 0);
				Infection.addPlayerInfectionLevel(player, 1);
			}
			break;

		}
	}

	public static void playerFall(LivingHurtEvent event) {

		if (event.getAmount() >= event.getEntityLiving().getHealth())
			return; // not longer useful

		Random r = new Random();

		if (event.getAmount() < 3)
			return;

		if (event.getEntityLiving().isPotionActive(Bonebreak.bonebreak)) {

			int lvl = event.getEntityLiving().getActivePotionEffect(Bonebreak.bonebreak).getAmplifier();

			// breakLegPlayer(event.getEntityLiving(), lvl + 1);
			if (event.getAmount() < 10)
				breakPlayerLeg(event.getEntityLiving(), lvl + 1);
			else
				breakPlayerLeg(event.getEntityLiving(), lvl + 2);

		} else {

			// breakLegPlayer(event.getEntityLiving(), 0);
			breakPlayerLeg(event.getEntityLiving(), 0);
		}

	}

	public static void breakPlayerLeg(EntityLivingBase playerIn, Integer lvl) {

		EntityPlayer player = (EntityPlayer) playerIn;

		if (player.getHealth() == 0)
			return;

		if (lvl == 0)
			player.sendMessage(new TextComponentString("§7You broke your leg use a bandage"));

		if (lvl != 0)
			player.sendMessage(new TextComponentString("§7Be careful with your leg"));

		playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 5000 * (lvl + 1), lvl));
		playerIn.addPotionEffect(new PotionEffect(Bonebreak.bonebreak, 7000 * (lvl + 1), lvl));

	}
}
