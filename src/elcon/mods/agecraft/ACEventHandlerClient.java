package elcon.mods.agecraft;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.render.TexturePartial;

@SideOnly(Side.CLIENT)
public class ACEventHandlerClient {

	@ForgeSubscribe
	public void onTextureStichtPre(TextureStitchEvent.Pre event) {
		if(event.map.textureType == 0) {
			for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
				if(fluid != null && fluid.getStillIcon() != null) {
					TexturePartial texture = new TexturePartial(fluid.getStillIcon().getIconName(), 3, 3, 13, 12);
					ResourcesCore.registerPartialFluidIcon(fluid.getName(), 0, texture);
					event.map.setTextureEntry(fluid.getStillIcon().getIconName(), texture);
				}
			}
		}
	}
}
