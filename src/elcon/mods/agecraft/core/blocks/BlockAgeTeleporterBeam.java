package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterBeam;
import elcon.mods.core.lang.LanguageManager;

public class BlockAgeTeleporterBeam extends BlockContainer {

	private Icon icon;
	private Icon iconTop;
	
	public BlockAgeTeleporterBeam(int i) {
		super(i, Material.iron);
		setResistance(6000000.0F);
		setLightValue(0.5F);
		setCreativeTab(ACCreativeTabs.ageCraft);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.ageTeleporterBeam.name";
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAgeTeleporterBeam();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(side == 1) {
			return iconTop;
		}
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ageTeleporterBeamBlock");
		iconTop = iconRegister.registerIcon("agecraft:ageTeleporterBeam");
	}
}
