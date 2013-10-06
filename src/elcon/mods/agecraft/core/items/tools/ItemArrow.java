package elcon.mods.agecraft.core.items.tools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArrow extends ItemTool {

	public Icon[] icons = new Icon[256];
	public Icon[][] iconsBow = new Icon[256][3];
	
	public ItemArrow(int id) {
		super(id);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		
	}
}
