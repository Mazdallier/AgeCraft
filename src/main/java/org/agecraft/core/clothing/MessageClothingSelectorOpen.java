package org.agecraft.core.clothing;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import org.agecraft.core.gui.GuiClothingSelector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingSelectorOpen extends EQMessage {

	public ArrayList<String> changeable;
	
	public MessageClothingSelectorOpen() {
		changeable = new ArrayList<String>();
	}
	
	public MessageClothingSelectorOpen(ArrayList<String> changeable) {
		this();
		this.changeable.addAll(changeable);
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeInt(changeable.size());
		for(String change : changeable) {
			writeString(target, change);
		}
	}

	@Override
	public void decodeFrom(ByteBuf source) {
		int changeableCount = source.readInt();
		for(int i = 0; i < changeableCount; i++) {
			changeable.add(readString(source));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiClothingSelector(changeable));
	}
}
