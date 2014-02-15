package org.agecraft.core.blocks;

import org.agecraft.assets.resources.Resources;
import org.agecraft.core.AgeTeleport;
import org.agecraft.core.TeleporterAC;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.lang.LanguageManager;

public class BlockAgeTeleporter extends BlockAgeTeleporterBlock {

	private Icon icon;
	private Icon iconFront;
	
	public BlockAgeTeleporter(int i) {
		super(i);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.ageTeleporter.name";
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float xx, float yy, float zz, int meta) {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xx, float yy, float zz) {
		if(!world.isRemote) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			if(playerMP != null) {
				if(playerMP.dimension < 10) {
					if(world.getBlockMetadata(x, y, z) == 0) {
						AgeTeleport.teleportList.put(player.username, AgeTeleport.create(world, x - 3, y - 3, z - 6));
					}
					playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, 10, new TeleporterAC(playerMP.mcServer.worldServerForDimension(10), 10, true));
				} else {
					playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, 0, new TeleporterAC(playerMP.mcServer.worldServerForDimension(0), 0, true));
				}
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(side == 2) {
			return iconFront;
		}
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ageTeleporterBlock");
		iconFront = iconRegister.registerIcon("agecraft:ageTeleporter");
		
		Resources.instance.registerBlockIconsCall(iconRegister);
	}
}