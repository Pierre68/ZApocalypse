package fr.exodeus.zombies.Objects.Game.Nature;

import fr.exodeus.zombies.Core.MainZombies;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class NatureLogic {

	public static void onPlayerBlockBreak(BreakEvent event) {
		if (event.getState().getMaterial() == Blocks.LOG.getBlockState().getBaseState().getMaterial()
				|| event.getState().getMaterial() == Blocks.LOG2.getBlockState().getBaseState().getMaterial()) {
			MainZombies.logString("true");
		}

	}

	public static void onItemDespawn(ItemExpireEvent event) {

		if (event.getEntityItem().getEntityItem().getItem() == new ItemStack(Blocks.SAPLING).getItem()) {

			World world = event.getEntity().getEntityWorld();

			BlockPos pos = new BlockPos(event.getEntity());

			if (world.getBlockState(pos).getBlock() == Blocks.AIR
					|| world.getBlockState(pos).getBlock() == Blocks.TALLGRASS
					|| world.getBlockState(pos).getBlock() == Blocks.DOUBLE_PLANT
					|| world.getBlockState(pos).getBlock() == Blocks.RED_FLOWER
					|| world.getBlockState(pos).getBlock() == Blocks.YELLOW_FLOWER) {

				BlockPos posUnder = new BlockPos(event.getEntity()).add(0, -1, 0);

				if (world.getBlockState(posUnder).getBlock() == Blocks.GRASS
						|| world.getBlockState(posUnder).getBlock() == Blocks.DIRT) {

					int damage = event.getEntityItem().getEntityItem().serializeNBT().getInteger("Damage");
					IBlockState state = Blocks.SAPLING.getStateFromMeta(damage);

					world.setBlockState(pos, state);

				}
			}

		}

	}

}
