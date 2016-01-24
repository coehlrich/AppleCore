package squeek.applecore.asm.reference;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;
import squeek.applecore.asm.Hooks;

import java.util.Random;

public class BlockCactusModifications extends BlockCactus
{
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (world.isAirBlock(pos.up()))
		{
			int i;

			for (i = 1; world.getBlockState(pos.down(i)).getBlock() == this; ++i)
			{
				;
			}

			// added && ...
			if (i < 3 && Hooks.fireAllowPlantGrowthEvent(this, world, pos, state, rand) != Event.Result.DENY)
			{
				int j = (state.getValue(AGE)).intValue();

				if (j == 15)
				{
					world.setBlockState(pos.up(), this.getDefaultState());
					IBlockState iblockstate = state.withProperty(AGE, Integer.valueOf(0));
					world.setBlockState(pos, iblockstate, 4);
					this.onNeighborBlockChange(world, pos.up(), iblockstate, this);
				}
				else
				{
					world.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 4);
				}

				// added line
				Hooks.fireOnGrowthEvent(this, world, pos, state);
			}
		}
	}
}