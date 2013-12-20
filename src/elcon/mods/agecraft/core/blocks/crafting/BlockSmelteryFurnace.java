package elcon.mods.agecraft.core.blocks.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.Crafting;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import elcon.mods.core.blocks.BlockStructure;

public class BlockSmelteryFurnace extends BlockStructure {

	public BlockSmelteryFurnace(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.crafting);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySmelteryFurnace(getStructureName(), blockID);
	}

	@Override
	public String getStructureName() {
		return Crafting.structureSmeltery.name;
	}
}
