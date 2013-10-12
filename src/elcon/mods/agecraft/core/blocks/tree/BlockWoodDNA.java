package elcon.mods.agecraft.core.blocks.tree;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;

public class BlockWoodDNA extends BlockContainer {

	public BlockWoodDNA(int id) {
		super(id, Material.wood);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDNATree();
	}
}
