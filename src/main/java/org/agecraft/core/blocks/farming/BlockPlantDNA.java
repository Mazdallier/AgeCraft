package org.agecraft.core.blocks.farming;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.agecraft.core.tileentities.TileEntityDNAPlant;

import elcon.mods.elconqore.blocks.BlockExtendedContainer;

public class BlockPlantDNA extends BlockExtendedContainer {

	public BlockPlantDNA(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDNAPlant();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDNAPlant.class;
	}
}
