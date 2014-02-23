package org.agecraft.core.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.clothing.ClothingRegistry;
import org.agecraft.core.clothing.MessageClothingSelectorOpen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockClothingSelectorTest extends Block {

	public BlockClothingSelectorTest() {
		super(Material.iron);
		setCreativeTab(ACCreativeTabs.ageCraft);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			ArrayList<String> changeable = new ArrayList<String>();
			changeable.addAll(ClothingRegistry.types.keySet());
			AgeCraftCore.instance.packetHandler.sendToPlayer(player, new MessageClothingSelectorOpen(changeable));
		}
		return true;
	}
	
	@Override
	public String getLocalizedName() {
		return "Clothing Selector - TEMP";
	}
	
	@Override
	public String getUnlocalizedName() {
		return getLocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("agecraft:clothingSelector");
	}
}
