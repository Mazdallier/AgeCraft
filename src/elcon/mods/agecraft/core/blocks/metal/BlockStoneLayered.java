package elcon.mods.agecraft.core.blocks.metal;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.items.tool.ItemTool;

public class BlockStoneLayered extends BlockStone {

	public static final int LAYER_SIZE = 16;

	public BlockStoneLayered(int i) {
		super(i);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			blockIcon = Block.stone.getIcon(0, 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		switch(meta) {
		case 1:
			return 0x878787;
		case 2:
			return 0xA7A7A7;
		case 3:
			return 0xC7C7C7;
		case 4:
			return 0xE7E7E7;
		default:
			return 0xFFFFFF;
		}
	}

	public void updateHeight(World world, int x, int y, int z, Random random) {
		if(y < 8) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 0);
		} else {
			int i;
			for(i = 0; y > ((i * LAYER_SIZE) + (-2 + random.nextInt(5))); i++) {
			}
			world.setBlockMetadataWithNotify(x, y, z, i, 0);
		}
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		if(player.getCurrentEquippedItem() == null) {
			return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
		}
		return player.getCurrentEquippedItem().getItem().getStrVsBlock(player.getCurrentEquippedItem(), this, world.getBlockMetadata(x, y, z)) / getBlockHardness(world, x, y, z) / 30F;
	}
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return true;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[blockID], 1);
		player.addExhaustion(0.025F);
		if(canSilkHarvest(world, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player)) {
			ItemStack itemstack = createStackedBlock(meta);
			if(itemstack != null) {
				dropBlockAsItem_do(world, x, y, z, itemstack);
			}
		} else {
			boolean shouldDropItems = false;
			if(player.getCurrentEquippedItem() != null) {
				if(player.getCurrentEquippedItem().getItem() instanceof ItemTool) {
					shouldDropItems = ((ItemTool) player.getCurrentEquippedItem().getItem()).canHarvestBlock(player.getCurrentEquippedItem(), this, meta);
				}
			}
			if(shouldDropItems) {
				dropBlockAsItem(world, x, y, z, meta, EnchantmentHelper.getFortuneModifier(player));
			}
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Block.cobblestone.blockID;
	}
}
