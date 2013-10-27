package elcon.mods.agecraft.core.blocks.tree;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.blocks.BlockExtendedContainer;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;

public class BlockLeavesDNA extends BlockExtendedContainer {

	public BlockLeavesDNA(int id) {
		super(id, Material.leaves);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDNATree();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
}
