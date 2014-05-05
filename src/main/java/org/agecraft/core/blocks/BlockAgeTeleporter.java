package org.agecraft.core.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.core.AgeTeleporter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockAgeTeleporter extends BlockAgeTeleporterBlock {

	private IIcon icon;
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.ageTeleporter.name";
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			int dimensionID;
			if(world.provider.dimensionId < 10) {
				dimensionID = 10;
			} else {
				//dimensionID = world.provider.dimensionId + 1;
				return false;
			}
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, dimensionID, new AgeTeleporter(playerMP.mcServer.worldServerForDimension(dimensionID)));
			return true;
		}
		return world.provider.dimensionId < 10;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? super.getIcon(side, meta) : icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ageTeleporter");
	}
}
