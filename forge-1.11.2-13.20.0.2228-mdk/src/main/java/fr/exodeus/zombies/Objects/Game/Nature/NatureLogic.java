package fr.exodeus.zombies.Objects.Game.Nature;

import java.util.ArrayList;
import java.util.List;

import fr.exodeus.zombies.Core.MainZombies;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class NatureLogic {

	public static void onPlayerBlockBreak(BreakEvent event) {
		/*
		 * if (event.getState().getMaterial() ==
		 * Blocks.LOG.getBlockState().getBaseState().getMaterial() ||
		 * event.getState().getMaterial() ==
		 * Blocks.LOG2.getBlockState().getBaseState().getMaterial()) {
		 */

		BlockPos pos = new BlockPos(event.getPos()).add(0, 1, 0);
		World w = event.getWorld();

		Material log1 = Blocks.LOG.getBlockState().getBaseState().getMaterial();
		Material log2 = Blocks.LOG2.getBlockState().getBaseState().getMaterial();

		if (w.getBlockState(pos).getMaterial() != log1 && w.getBlockState(pos).getMaterial() != log2)
			return;

		List<BlockPos> blockScaned = new ArrayList<BlockPos>();
		List<BlockPos> blockLeft = new ArrayList<BlockPos>();

		blockLeft.add(pos);

		while (!blockLeft.isEmpty()) {

			BlockPos scanPos = blockLeft.get(0);
			blockLeft.remove(0);
			blockScaned.add(scanPos);

			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					for (int z = -1; z < 2; z++) {

						BlockPos newPos = new BlockPos(scanPos.getX() + x, scanPos.getY() + y, scanPos.getZ() + z);

						if (event.getPos().equals(newPos))
							continue;

						if (w.getBlockState(newPos).getMaterial() == log1
								|| w.getBlockState(newPos).getMaterial() == log2) {

							if (!blockLeft.contains(newPos) && !blockScaned.contains(newPos))
								blockLeft.add(newPos);

						} else if (w.getBlockState(newPos).getMaterial() == Material.AIR
								|| w.getBlockState(newPos).getMaterial() == Material.LEAVES
								|| w.getBlockState(newPos).getMaterial() == Material.FIRE
								|| w.getBlockState(newPos).getMaterial() == Material.PLANTS
								|| w.getBlockState(newPos).getMaterial() == Material.VINE
								|| w.getBlockState(newPos).getMaterial() == Blocks.SAPLING.getDefaultState()
										.getMaterial()
								|| w.getBlockState(newPos).getMaterial() == Blocks.TORCH.getDefaultState()
										.getMaterial()) {
							continue;
						} else {
							return;
						}
					}
				}
			}
		}

		for (BlockPos blockPos : blockScaned) {

			boolean veryReal = false; //TODO option

			if (veryReal) {
				w.destroyBlock(blockPos, true);

				EntityFallingBlock block = new EntityFallingBlock(w, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
						w.getBlockState(pos));
				block.addVelocity((w.rand.nextFloat() / 4) - 0.125f, (w.rand.nextFloat() / 4) - 0.125f,
						(w.rand.nextFloat() / 4) - 0.125f);

				w.spawnEntity(block);

			} else {
				w.destroyBlock(blockPos, true);
			}

			for (int x = -2; x < 3; x++) {
				for (int y = 0; y < 4; y++) {
					for (int z = -2; z < 3; z++) {

						BlockPos newPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);

						if (veryReal) {
							if (w.getBlockState(newPos).getMaterial() == Material.LEAVES) {

								EntityFallingBlock blockLeef = new EntityFallingBlock(w, newPos.getX(), newPos.getY(),
										newPos.getZ(), w.getBlockState(newPos));
								blockLeef.addVelocity((w.rand.nextFloat() / 4) - 0.125f,
										(w.rand.nextFloat() / 4) - 0.125f, (w.rand.nextFloat() / 4) - 0.125f);
								blockLeef.setDropItemsWhenDead(true);

								w.spawnEntity(blockLeef);

							}
						}

					}
				}
			}

		}

	}
	// }

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
