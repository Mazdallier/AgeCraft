package elcon.mods.agecraft.prehistory.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.prehistory.PrehistoryAge;
import elcon.mods.core.lang.LanguageManager;

@Deprecated
public class BlockRock extends Block {

	private Icon icon;
	
	public BlockRock(int i) {
		super(i, Material.rock);
		setHardness(0.4F);
		setStepSound(Block.soundStoneFootstep);
		setBlockBounds(0.35F, 0.0F, 0.35F, 0.75F, 0.2F, 0.75F);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.rock.name";
	}
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return true;
	}
	
	@Override
	public int idDropped(int i, Random random, int j) {
		return PrehistoryAge.rock.itemID;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		int i = world.getBlockId(x, y - 1, z);
		if(i != 0 && i != blockID && world.isBlockOpaqueCube(x, y - 1, z)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem_do(world, x, y, z, new ItemStack(PrehistoryAge.rock.itemID, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem_do(world, x, y, z, new ItemStack(PrehistoryAge.rock.itemID, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/rock");
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 200;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
