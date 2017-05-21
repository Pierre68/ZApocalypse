package fr.exodeus.zombies.Objects.Entity.Entities;

import java.util.Random;

import fr.exodeus.zombies.Objects.Entity.Render.ZombieMinerRender;
import fr.exodeus.zombies.Objects.Items.IronNugget;
import fr.exodeus.zombies.Objects.Items.SandDust;
import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import fr.exodeus.zombies.Objects.Items.Usable.Bandage;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZombieMiner extends EntityMob {

	public ZombieMiner(World par1World) {
		super(par1World);

		// Zombie Task
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.9D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);

	}

	@SideOnly(Side.CLIENT)
	public static void registerRender() {
		RenderingRegistry.registerEntityRenderingHandler(ZombieMiner.class, new ZombieMinerRender());
	}

	protected void dropFewItems(boolean par1, int par2) {

		Random r = new Random();

		switch (this.rand.nextInt(3)) {

		case 0:
			this.dropItem(Bandage.bandage, 1);
			this.dropItem(Items.ROTTEN_FLESH, r.nextInt(3));
			if (r.nextBoolean() == true) {
				this.dropItem(SandDust.sand_dust, r.nextInt(8) + 4);
			} else {
				this.dropItem(IronNugget.iron_nugget, r.nextInt(15) + 4);

				this.dropItem(Items.GOLD_NUGGET, r.nextInt(3));
				this.dropItem(Items.IRON_INGOT, r.nextInt(3));
			}
			break;

		case 1:
			this.dropItem(IronNugget.iron_nugget, r.nextInt(15) + 5);
			this.dropItem(SandDust.sand_dust, r.nextInt(20) + 5);
			this.dropItem(Items.GOLD_NUGGET, r.nextInt(4));
			break;

		case 2:
			this.dropItem(IronNugget.iron_nugget, r.nextInt(20) + 7);
			this.dropItem(Items.ROTTEN_FLESH, r.nextInt(2));

			if (r.nextBoolean() == true) {
				this.dropItem(SandDust.sand_dust, r.nextInt(10) + 6);

				this.dropItem(Antibiotic.antibiotic, r.nextInt(2));
				this.dropItem(Items.GUNPOWDER, r.nextInt(4));

			}
			break;
		}
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_ZOMBIE_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_DEATH;
	}

	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
	}

}
