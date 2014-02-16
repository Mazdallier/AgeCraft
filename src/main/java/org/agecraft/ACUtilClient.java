package org.agecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings.Options;

public class ACUtilClient {

	public static void changeGuiScale(Minecraft mc) {
		mc.gameSettings.setOptionValue(Options.GUI_SCALE, 1);
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		mc.currentScreen.setWorldAndResolution(mc, sr.getScaledWidth(), sr.getScaledHeight());
	}
}
