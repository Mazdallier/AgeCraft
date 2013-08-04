package elcon.mods.agecraft.prehistory.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.AgeCraft;

public class ItemFakeStone extends Item {
	
	private Icon icon;
	
	public ItemFakeStone(int i) {
		super(i);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/fake_stone");
		
		AgeCraft.instance.registerItemIcons(iconRegister);
	}
}
