package org.agecraft.core.clothing;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingUnlocks extends EQMessage {

	public PlayerClothing clothing;
	
	public MessageClothingUnlocks() {
		
	}
	
	public MessageClothingUnlocks(PlayerClothing clothing) {
		this.clothing = clothing;
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeInt(clothing.unlockedCategories != null ? clothing.unlockedCategories.size() : 0);
		for(String category : clothing.unlockedCategories) {
			writeString(target, category);
			target.writeInt(clothing.unlockedClothing.get(category) != null ? clothing.unlockedClothing.get(category).size() : 0);
			if(clothing.unlockedClothing.get(category) != null) { 
				for(String piece : clothing.unlockedClothing.get(category)) {
					writeString(target, piece);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void decodeFrom(ByteBuf source) {
		clothing = PlayerClothingClient.getPlayerClothing(EQUtilClient.getPlayer().getCommandSenderName());
		clothing.unlockedCategories.clear();
		clothing.unlockedClothing.clear();
		int categories = source.readInt();
		for(int i = 0; i < categories; i++) {
			String category = readString(source);
			clothing.unlockCategory(category);
			int pieces = source.readInt();
			for(int j = 0; j < pieces; j++) {
				clothing.unlockClothing(category, readString(source));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		
	}
}
