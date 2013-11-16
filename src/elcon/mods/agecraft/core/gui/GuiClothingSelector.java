package elcon.mods.agecraft.core.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACPacketHandlerClient;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.clothing.Clothing;
import elcon.mods.agecraft.core.clothing.ClothingCategory;
import elcon.mods.agecraft.core.clothing.ClothingRegistry;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingTypeIndexComparator;
import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothing.ClothingPiece;
import elcon.mods.agecraft.core.clothing.PlayerClothingClient;
import elcon.mods.core.lang.LanguageManager;

@SideOnly(Side.CLIENT)
public class GuiClothingSelector extends GuiScreen {

	public int guiLeft;
	public int guiTop;
	public int xSize;
	public int ySize;

	public float rotation;
	public boolean autoRotate;

	public boolean showLeftList;
	public boolean showRightList;
	public byte rightList;

	public GuiButtonListVertical buttonsClothingType;
	public GuiButtonListVertical buttonsClothingCategories;
	public GuiButtonListVertical buttonsClothingPieces;

	public int currentClothingType;
	public int currentClothingCategory;
	public int currentClothingPiece;
	public int currentClothingColor;

	public ArrayList<ClothingType> clothingTypes = new ArrayList<ClothingType>();
	public ArrayList<ClothingCategory> clothingCategories = new ArrayList<ClothingCategory>();
	public ArrayList<Clothing> clothingPieces = new ArrayList<Clothing>();

	public HashMap<String, ClothingPiece> clothingPiecesCart = new HashMap<String, ClothingPiece>();
	public int totalPrice;

	public List<String> clothingTypesChangeable;
	public int clothingTypesChangeableCount;

	public GuiClothingSelector(List<String> clothingTypesChangable) {
		this.clothingTypesChangeable = clothingTypesChangable;
		xSize = 256;
		ySize = 166;
		allowUserInput = true;

		totalPrice = 0;
	}

	@Override
	public void initGui() {
		currentClothingType = 0;
		currentClothingCategory = 0;
		currentClothingPiece = 0;
		currentClothingColor = 0;

		clothingTypes.clear();
		clothingTypes.addAll(ClothingRegistry.types.values());
		Collections.sort(clothingTypes, new ClothingTypeIndexComparator());

		int index = 0;
		clothingCategories.clear();
		for(ClothingCategory category : ClothingRegistry.categories.values()) {
			if(category.enabled) {
				clothingCategories.add(category);
				if(category.name.equalsIgnoreCase("general")) {
					currentClothingCategory = index;
				}
				index++;
			}
		}

		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username).copy();
		clothing.player = mc.thePlayer.username + "-Temp";
		PlayerClothingClient.addPlayerClothing(clothing);
		clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username).copy();
		clothing.player = mc.thePlayer.username + "-TempPiece";
		clothing.clothingTypeExclusive = clothingTypes.get(currentClothingType).name;
		clothing.clothingColorExclusive = currentClothingColor;
		PlayerClothingClient.addPlayerClothing(clothing);

		showLeftList = true;
		showRightList = false;
		rightList = 0;

		rotation = 0.0F;
		autoRotate = true;

		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;

		buttonList.clear();
		buttonList.add(new GuiButton(0, guiLeft + 100, guiTop + 146, 0, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButton(1, guiLeft + 120, guiTop + 146, 32, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButton(2, guiLeft + 140, guiTop + 146, 16, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));

		buttonList.add(new GuiButtonDefault(3, guiLeft + 9, guiTop + 6, 70, 8, LanguageManager.getLocalization("gui.up.sign")));
		buttonList.add(new GuiButtonDefault(4, guiLeft + 9, guiTop + 146, 70, 8, LanguageManager.getLocalization("gui.down.sign")));
		buttonsClothingType = new GuiButtonListVertical(guiTop + 16, 16, (GuiButtonDefault) buttonList.get(3), (GuiButtonDefault) buttonList.get(4), 8);
		index = 0;
		clothingTypesChangeableCount = 0;
		for(ClothingType type : clothingTypes) {
			if(type != null && clothingTypesChangeable.contains(type.name)) {
				buttonsClothingType.addButton(new GuiButtonClothingType(5 + index, guiLeft + 10, guiTop + 16 + index * 16, 0, 166, 68, 16, ResourcesCore.guiClothingSelector, LanguageManager.getLocalization("clothing.type." + type.name), type.name, index));
				index++;
				clothingTypesChangeableCount++;
			}
		}
		buttonList.addAll(buttonsClothingType.buttons);
		((GuiToggleButton) buttonList.get(5)).toggled = true;

		buttonList.add(new GuiButtonDefault(21, guiLeft + 181, guiTop + 15, 16, 16, LanguageManager.getLocalization("gui.prev.sign")));
		buttonList.add(new GuiButtonDefault(22, guiLeft + 199, guiTop + 15, 32, 16, LanguageManager.getLocalization("gui.list")));
		buttonList.add(new GuiButtonDefault(23, guiLeft + 233, guiTop + 15, 16, 16, LanguageManager.getLocalization("gui.next.sign")));
		buttonList.add(new GuiButtonDefault(24, guiLeft + 181, guiTop + 129, 16, 16, LanguageManager.getLocalization("gui.prev.sign")));
		buttonList.add(new GuiButtonDefault(25, guiLeft + 199, guiTop + 129, 32, 16, LanguageManager.getLocalization("gui.list")));
		buttonList.add(new GuiButtonDefault(26, guiLeft + 233, guiTop + 129, 16, 16, LanguageManager.getLocalization("gui.next.sign")));

		for(int j = 0; j < 2; j++) {
			for(int i = 0; i < 8; i++) {
				buttonList.add(new GuiButtonClothingColor(27 + i + j * 8, guiLeft + 183 + i * 8, guiTop + 146 + j * 8, 68 + i * 8, 166 + j * 8, 8, 8, ResourcesCore.guiClothingSelector, ""));
			}
		}

		buttonList.add(new GuiButtonDefault(43, guiLeft + 266, guiTop + 5, 84, 10, LanguageManager.getLocalization("gui.up.sign")));
		buttonList.add(new GuiButtonDefault(44, guiLeft + 266, guiTop + 150, 84, 10, LanguageManager.getLocalization("gui.down.sign")));

		buttonList.add(new GuiButtonDefault(45, guiLeft + 80, guiTop + 169, 96, 16, LanguageManager.getLocalization("gui.addToCart")));
		if(showLeftList) {
			buttonList.add(new GuiButtonDefault(46, guiLeft - 100, guiTop + 169, 40, 16, LanguageManager.getLocalization("gui.cancel")));
			buttonList.add(new GuiButtonDefault(47, guiLeft - 44, guiTop + 169, 40, 16, LanguageManager.getLocalization("gui.pay")));
		}

		buttonsClothingCategories = new GuiButtonListVertical(guiTop + 19, 16, (GuiButtonDefault) buttonList.get(27 + clothingTypesChangeableCount), (GuiButtonDefault) buttonList.get(28 + clothingTypesChangeableCount), 8);
		buttonsClothingPieces = new GuiButtonListVertical(guiTop + 19, 16, (GuiButtonDefault) buttonList.get(27 + clothingTypesChangeableCount), (GuiButtonDefault) buttonList.get(28 + clothingTypesChangeableCount), 8);

		buttonsClothingCategories.buttons.clear();
		for(int i = 0; i < clothingCategories.size(); i++) {
			buttonsClothingCategories.addButton(new GuiToggleButton(100 + i, guiLeft + 267, guiTop + 19 + i * 16, 140, 166, 82, 16, ResourcesCore.guiClothingSelector, LanguageManager.getLocalization("clothing.category." + clothingCategories.get(i).name), true));
		}
		updateClothingList();
		updateTempPieceClothing();

		((GuiToggleButton) buttonsClothingCategories.buttons.get(currentClothingCategory)).toggled = true;

		buttonsClothingCategories.hide();
		buttonsClothingPieces.hide();
	}

	public void updateClothingList() {
		Collection<Clothing> clothingPiecesList = clothingCategories.get(currentClothingCategory).clothing.get(ClothingRegistry.types.get(clothingTypes.get(currentClothingType).name)).values();
		clothingPieces.clear();
		clothingPieces.addAll(clothingPiecesList);
		buttonsClothingPieces.buttons.clear();
		if(clothingPiecesList != null) {
			int index = 0;
			for(Clothing clothing : clothingPiecesList) {
				if(clothing != null) {
					buttonsClothingPieces.addButton(new GuiToggleButton(1000 + index, guiLeft + 267, guiTop + 19 + index * 16, 140, 166, 82, 16, ResourcesCore.guiClothingSelector, LanguageManager.getLocalization("clothing." + clothing.category.name + "." + clothing.type.name + "." + clothing.name), true));
					index++;
				}
			}
		}
		((GuiToggleButton) buttonsClothingPieces.buttons.get(currentClothingPiece)).toggled = true;
	}

	public void updateTempClothing() {
		totalPrice = 0;
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username).copy();
		clothing.player = mc.thePlayer.username + "-Temp";
		clothing.glTextureID = -1;
		for(ClothingPiece piece : clothingPiecesCart.values()) {
			clothing.clothingPiecesWorn.put(piece.typeID, piece);
			clothing.clothingPiecesWornColor.put(piece.typeID, piece.getActiveColor());
			totalPrice += ClothingRegistry.categories.get(piece.categoryID).getClothing(ClothingRegistry.types.get(piece.typeID), piece.clothingID).price;
		}
		PlayerClothingClient.addPlayerClothing(clothing);
	}

	public void updateTempPieceClothing() {
		int firstColor = -1;
		for(int i = 0; i < 16; i++) {
			boolean flag = clothingPieces.get(currentClothingPiece).colors[i];
			((GuiToggleButton) buttonList.get(11 + clothingTypesChangeableCount + i)).enabled = flag;
			((GuiToggleButton) buttonList.get(11 + clothingTypesChangeableCount + i)).drawButton = flag;
			((GuiToggleButton) buttonList.get(11 + clothingTypesChangeableCount + i)).toggled = false;
			if(flag && firstColor == -1) {
				firstColor = i;
			}
		}
		if(currentClothingColor == -1) {
			currentClothingColor = firstColor;
		}
		((GuiToggleButton) buttonList.get(11 + clothingTypesChangeableCount + currentClothingColor)).toggled = true;

		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username + "-TempPiece");
		clothing.addClothingPieceAndWear(new ClothingPiece(clothingTypes.get(currentClothingType).name, clothingCategories.get(currentClothingCategory).name, clothingPieces.get(currentClothingPiece).name, currentClothingColor), currentClothingColor);
		clothing.clothingTypeExclusive = clothingTypes.get(currentClothingType).name;
		clothing.clothingColorExclusive = currentClothingColor;
		PlayerClothingClient.updatePlayerClothing(mc.thePlayer.username + "-TempPiece");
	}

	@Override
	public void updateScreen() {
		if(autoRotate) {
			rotation += 2.0F;
		}
	}

	@Override
	protected void actionPerformed(net.minecraft.client.gui.GuiButton button) {
		if(button.id == 0) {
			rotation -= 1.0F;
		} else if(button.id == 1) {
			autoRotate = !autoRotate;
		} else if(button.id == 2) {
			rotation += 1.0F;
		} else if(button.id == 3 || button.id == 4) {
			buttonsClothingType.actionPerformed((GuiButtonDefault) button);
		} else if(button.id >= 5 && button.id < 21) {
			for(int i = 5; i < 5 + clothingTypesChangeableCount; i++) {
				((GuiToggleButton) buttonList.get(i)).toggled = false;
			}
			((GuiToggleButton) button).toggled = true;
			currentClothingType = ((GuiButtonClothingType) button).clothingTypeIndex;
			currentClothingPiece = 0;
			currentClothingColor = -1;
			updateClothingList();
			updateTempPieceClothing();
		} else if(button.id == 21) {
			currentClothingCategory--;
			if(currentClothingCategory < 0) {
				currentClothingCategory = 0;
			}
			currentClothingPiece = 0;
			currentClothingColor = -1;
			updateClothingList();
			updateTempPieceClothing();
		} else if(button.id == 22) {
			if(rightList == 0) {
				showRightList = !showRightList;
				buttonsClothingPieces.hide();
				buttonsClothingCategories.setHidden(!showRightList);
			} else {
				rightList = 0;
				showRightList = true;
				buttonsClothingPieces.hide();
				buttonsClothingCategories.show();
			}
		} else if(button.id == 23) {
			currentClothingCategory++;
			if(currentClothingCategory >= clothingCategories.size()) {
				currentClothingCategory = clothingCategories.size() - 1;
			}
			currentClothingPiece = 0;
			currentClothingColor = -1;
			updateClothingList();
			updateTempPieceClothing();
		} else if(button.id == 24) {
			currentClothingPiece--;
			if(currentClothingPiece < 0) {
				currentClothingPiece = 0;
			}
			currentClothingColor = -1;
			updateTempPieceClothing();
		} else if(button.id == 25) {
			if(rightList == 1) {
				showRightList = !showRightList;
				buttonsClothingCategories.hide();
				buttonsClothingPieces.setHidden(!showRightList);
			} else {
				rightList = 1;
				showRightList = true;
				buttonsClothingCategories.hide();
				buttonsClothingPieces.show();
			}
		} else if(button.id == 26) {
			currentClothingPiece++;
			if(currentClothingPiece >= clothingPieces.size()) {
				currentClothingPiece = clothingPieces.size() - 1;
			}
			currentClothingColor = -1;
			updateTempPieceClothing();
		} else if(button.id >= 27 && button.id < 43) {
			for(int i = 0; i < 16; i++) {
				((GuiToggleButton) buttonList.get(11 + clothingTypesChangeableCount + i)).toggled = false;
			}
			((GuiToggleButton) button).toggled = true;
			currentClothingColor = button.id - 27;
			updateTempPieceClothing();
		} else if(button.id == 43 || button.id == 44) {
			if(rightList == 0) {
				buttonsClothingCategories.actionPerformed((GuiButtonDefault) button);
			} else if(rightList == 1) {
				buttonsClothingPieces.actionPerformed((GuiButtonDefault) button);
			}
		} else if(button.id == 45) {
			if(!clothingPiecesCart.containsKey(currentClothingType) && clothingPiecesCart.size() < 8) {
				ClothingPiece piece = new ClothingPiece(clothingTypes.get(currentClothingType).name, clothingCategories.get(currentClothingCategory).name, clothingPieces.get(currentClothingPiece).name, currentClothingColor);
				clothingPiecesCart.put(clothingTypes.get(currentClothingType).name, piece);
				currentClothingColor = -1;
				updateTempClothing();
			}
		} else if(button.id == 46) {
			mc.thePlayer.closeScreen();
		} else if(button.id == 47) {
			PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getClothingSelectorPacket(mc.thePlayer.username, clothingPiecesCart));
			mc.thePlayer.closeScreen();
		} else if(button.id >= 100 && button.id <= 1000 && rightList == 0) {
			for(net.minecraft.client.gui.GuiButton b : buttonsClothingCategories.buttons) {
				((GuiToggleButton) b).toggled = false;
			}
			((GuiToggleButton) button).toggled = true;
			currentClothingCategory = button.id - 100;
			currentClothingPiece = 0;
			currentClothingColor = -1;
			updateClothingList();
			updateTempPieceClothing();
		} else if(button.id >= 1000 && rightList == 1) {
			for(net.minecraft.client.gui.GuiButton b : buttonsClothingPieces.buttons) {
				((GuiToggleButton) b).toggled = false;
			}
			((GuiToggleButton) button).toggled = true;
			currentClothingPiece = button.id - 1000;
			currentClothingColor = -1;
			updateTempPieceClothing();
		}
	}

	@Override
	protected void keyTyped(char c, int keyCode) {
		super.keyTyped(c, keyCode);
		if(keyCode == mc.gameSettings.keyBindInventory.keyCode) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int id) {
		super.mouseClicked(x, y, id);
		if(rightList == 0) {
			for(net.minecraft.client.gui.GuiButton button : buttonsClothingCategories.buttons) {
				if(button.mousePressed(mc, x, y)) {
					selectedButton = button;
					mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					actionPerformed(button);
				}
			}
		} else if(rightList == 1) {
			for(net.minecraft.client.gui.GuiButton button : buttonsClothingPieces.buttons) {
				if(button.mousePressed(mc, x, y)) {
					selectedButton = button;
					mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					actionPerformed(button);
				}
			}
		}
		if(showLeftList) {
			if(x >= guiLeft - 96 && x < guiLeft - 8 && y >= guiTop + 4 && y < guiTop + 132) {
				int cartItemIndex = (int) Math.floor((y - guiTop - 4) / 16);
				if(cartItemIndex < clothingPiecesCart.size()) {
					clothingPiecesCart.remove(clothingPiecesCart.keySet().toArray(new String[clothingPiecesCart.size()])[cartItemIndex]);
					updateTempClothing();
				}
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTickTime) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ResourcesCore.guiClothingSelector);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		mc.getTextureManager().bindTexture(ResourcesCore.guiClothingSelectorSideBar);
		if(showLeftList) {
			drawTexturedModalRect(guiLeft - 100, guiTop, 0, 0, 96, ySize);
		}
		if(showRightList) {
			drawTexturedModalRect(guiLeft + xSize + 4, guiTop, 160, 0, 96, ySize);
		}

		PlayerClothingClient.renderTempClothing = true;
		renderPlayer(guiLeft + 128, guiTop + 136, 60, 0.0F, 0.0F, mc.thePlayer);
		PlayerClothingClient.renderTempClothing = false;

		PlayerClothingClient.renderTempClothingPiece = true;
		renderPlayer(guiLeft + 216, guiTop + 118, 44, 0.0F, 0.0F, mc.thePlayer);
		PlayerClothingClient.renderTempClothingPiece = false;

		String stringToDraw = mc.thePlayer.getDisplayName();
		mc.fontRenderer.drawString(stringToDraw, guiLeft + 128 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 6, 0x404040);

		stringToDraw = LanguageManager.getLocalization("clothing.category." + clothingCategories.get(currentClothingCategory).name);
		mc.fontRenderer.drawString(stringToDraw, guiLeft + 216 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 6, 0x404040);

		stringToDraw = LanguageManager.getLocalization("clothing." + clothingPieces.get(currentClothingPiece).category.name + "." + clothingPieces.get(currentClothingPiece).type.name + "." + clothingPieces.get(currentClothingPiece).name);
		mc.fontRenderer.drawString(stringToDraw, guiLeft + 216 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 120, 0x404040);

		if(Mouse.isButtonDown(0)) {
			if(((GuiButton) buttonList.get(0)).mousePressed(mc, mouseX, mouseY)) {
				rotation -= 1.0F;
			} else if(((GuiButton) buttonList.get(2)).mousePressed(mc, mouseX, mouseY)) {
				rotation += 1.0F;
			}
		}
		if(showLeftList) {
			int i = 0;
			for(String type : clothingPiecesCart.keySet()) {
				ClothingPiece piece = clothingPiecesCart.get(type);
				stringToDraw = LanguageManager.getLocalization("clothing." + piece.categoryID + "." + piece.typeID + "." + piece.clothingID);
				mc.fontRenderer.drawString(stringToDraw, guiLeft - 94, guiTop + 6 + 16 * i, 0x404040);
				stringToDraw = Integer.toString(ClothingRegistry.categories.get(piece.categoryID).getClothing(ClothingRegistry.types.get(piece.typeID), piece.clothingID).price);
				mc.fontRenderer.drawString(stringToDraw, guiLeft - 10 - mc.fontRenderer.getStringWidth(stringToDraw), guiTop + 6 + 16 * i, 0x404040);
				i++;
			}

			mc.fontRenderer.drawString("+", guiLeft - 16, guiTop + 138, 0x404040);
			mc.fontRenderer.drawString("--------------", guiLeft - 94, guiTop + 144, 0x404040);

			stringToDraw = Integer.toString(totalPrice);
			mc.fontRenderer.drawString(stringToDraw, guiLeft - 10 - mc.fontRenderer.getStringWidth(stringToDraw), guiTop + 152, 0x404040);
		}
		if(showRightList) {
			if(rightList == 0) {
				if(buttonsClothingCategories.buttons.size() <= buttonsClothingCategories.size) {
					stringToDraw = LanguageManager.getLocalization("clothing.categories");
					mc.fontRenderer.drawString(stringToDraw, guiLeft + 308 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 6, 0x404040);
				}
				for(net.minecraft.client.gui.GuiButton button : buttonsClothingCategories.buttons) {
					button.drawButton(mc, mouseX, mouseY);
				}
			} else if(rightList == 1) {
				if(buttonsClothingPieces.buttons.size() <= buttonsClothingPieces.size) {
					stringToDraw = LanguageManager.getLocalization("clothing.pieces");
					mc.fontRenderer.drawString(stringToDraw, guiLeft + 308 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 6, 0x404040);
				}
				for(net.minecraft.client.gui.GuiButton button : buttonsClothingPieces.buttons) {
					button.drawButton(mc, mouseX, mouseY);
				}
			}
		}
		super.drawScreen(mouseX, mouseY, partialTickTime);
	}

	public void renderPlayer(int x, int y, int size, float lookX, float lookY, EntityLivingBase entity) {
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, 50.0F);
		GL11.glScalef((float) (-size), (float) size, (float) size);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float renderYawOffset = entity.renderYawOffset;
		float rotationYaw = entity.rotationYaw;
		float rotationPitch = entity.rotationPitch;
		float prevRotationYawHead = entity.prevRotationYawHead;
		float rotationYawHead = entity.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		entity.renderYawOffset = rotation;
		entity.rotationYaw = rotation;
		entity.rotationPitch = 0.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entity.renderYawOffset = renderYawOffset;
		entity.rotationYaw = rotationYaw;
		entity.rotationPitch = rotationPitch;
		entity.prevRotationYawHead = prevRotationYawHead;
		entity.rotationYawHead = rotationYawHead;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
