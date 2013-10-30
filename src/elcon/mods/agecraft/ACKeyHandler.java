package elcon.mods.agecraft;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import elcon.mods.agecraft.core.tech.gui.GuiTechTree;

public class ACKeyHandler extends KeyHandler {

	public static KeyBinding techTree = new KeyBinding("Technology Tree", Keyboard.KEY_F);
	public static KeyBinding trade = new KeyBinding("Trade", Keyboard.KEY_G);
	
	public ACKeyHandler() {
		super(new KeyBinding[]{techTree, trade}, new boolean[]{false, false});
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.currentScreen == null) {
			if(kb.keyCode == techTree.keyCode) {
				mc.displayGuiScreen(new GuiTechTree());
			}
		}
		if(mc.inGameHasFocus) {
			if(kb.keyCode == trade.keyCode) {
				if(mc.pointedEntityLiving != null && mc.pointedEntityLiving instanceof EntityPlayer) {
					PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getTradePacket(mc.thePlayer.dimension, mc.thePlayer.username, ((EntityPlayer) mc.pointedEntityLiving).username));
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public String getLabel() {
		return "AgeCraftKeyHandler";
	}
}
