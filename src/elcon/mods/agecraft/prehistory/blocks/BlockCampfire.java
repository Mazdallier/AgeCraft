package elcon.mods.agecraft.prehistory.blocks;

import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.core.lang.LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCampfire extends BlockContainer {
	
	public BlockCampfire(int id) {
		super(id, Material.wood);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.campfire.name";
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCampfire();
	}
	
	@Override
	public int getLightValue(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntityCampfire tile = (TileEntityCampfire) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCampfire();
		}
		return tile.isBurning() ? 15 : 0;
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCampfire();
			world.setBlockTileEntity(x, y, z, tile);
		}
		entity.setFire(8);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCampfire();
			world.setBlockTileEntity(x, y, z, tile);
		}
		return tile.onBlockActivated(side, player.getCurrentEquippedItem());
	}
}
