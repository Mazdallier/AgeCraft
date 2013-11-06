package elcon.mods.agecraft.core.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.clothing.Clothing;
import elcon.mods.agecraft.core.clothing.ClothingCategory;
import elcon.mods.agecraft.core.clothing.ClothingRegistry;
import elcon.mods.agecraft.core.clothing.PlayerClothing;
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
	
	public int currentClothingType;
	public int currentClothingCategory;
	public int currentClothingPiece;
	
	public ArrayList<ClothingCategory> clothingCategories = new ArrayList<ClothingCategory>();
	public ArrayList<Clothing> clothingPieces = new ArrayList<Clothing>();

	public GuiClothingSelector() {
		xSize = 256;
		ySize = 166;
		allowUserInput = true;
	}

	@Override
	public void initGui() {
		for(int i = 0; i < ClothingRegistry.categories.length; i++) {
			if(ClothingRegistry.categories[i] != null) {
				clothingCategories.add(ClothingRegistry.categories[i]);
			}
		}
		currentClothingType = 0;
		currentClothingCategory = 0;
		currentClothingPiece = 0;
		updateClothingList();
		
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username).copy();
		clothing.player = mc.thePlayer.username + "-Temp";
		PlayerClothingClient.addPlayerClothing(clothing);
		clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username).copy();
		clothing.player = mc.thePlayer.username + "-TempPiece";
		clothing.clothingPieceExclusive = currentClothingType;
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

		buttonList.add(new GuiButtonDefault(3, guiLeft + 9, guiTop + 6, 70, 8, "/\\"));
		buttonList.add(new GuiButtonDefault(4, guiLeft + 9, guiTop + 146, 70, 8, "\\/"));
		buttonsClothingType = new GuiButtonListVertical(guiTop + 16, 16, (GuiButtonDefault) buttonList.get(3), (GuiButtonDefault) buttonList.get(4), 8);
		int j = 0;
		for(int i = 0; i < ClothingRegistry.types.length; i++) {
			if(ClothingRegistry.types[i] != null) {
				buttonsClothingType.addButton(new GuiButtonClothingType(5 + j, guiLeft + 10, guiTop + 16 + i * 16, 0, 166, 68, 16, ResourcesCore.guiClothingSelector, LanguageManager.getLocalization("clothing.type." + ClothingRegistry.types[i].name), i));
				j++;
			}
		}
		buttonList.addAll(buttonsClothingType.buttons);
		((GuiToggleButton) buttonList.get(5)).toggled = true;
		
		buttonList.add(new GuiButton(13, guiLeft + 181, guiTop + 15, 0, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButtonDefault(14, guiLeft + 199, guiTop + 15, 32, 16, "List"));
		buttonList.add(new GuiButton(15, guiLeft + 233, guiTop + 15, 16, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButton(16, guiLeft + 181, guiTop + 129, 0, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButtonDefault(14, guiLeft + 199, guiTop + 129, 32, 16, "List"));
		buttonList.add(new GuiButton(18, guiLeft + 233, guiTop + 129, 16, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
	}

	public void updateClothingList() {
		Clothing[] clothingPiecesArray = clothingCategories.get(currentClothingCategory).clothing.get(ClothingRegistry.types[currentClothingType]);
		clothingPieces.clear();
		for(int i = 0; i < clothingPiecesArray.length; i++) {
			if(clothingPiecesArray[i] != null) {
				clothingPieces.add(clothingPiecesArray[i]);
			}
		}
	}
	
	public void updateTempClothing() {
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(mc.thePlayer.username + "-TempPiece");
		clothing.clothingPieceExclusive = currentClothingType;
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
		} else if(button.id == 13) {
			currentClothingCategory--;
			if(currentClothingCategory < 0) {
				currentClothingCategory = 0;
			}
			updateClothingList();
			updateTempClothing();
		} else if(button.id == 14) {
			if(rightList == 0) {
				showRightList = !showRightList;
			} else {
				rightList = 0;
				showRightList = true;
			}
		} else if(button.id == 15) {
			currentClothingCategory++;
			if(currentClothingCategory >= clothingCategories.size()) {
				currentClothingCategory = clothingCategories.size() - 1;
			}
			updateClothingList();
			updateTempClothing();
		} else if(button.id == 16) {
			currentClothingPiece--;
			if(currentClothingPiece < 0) {
				currentClothingPiece = 0;
			}
			updateTempClothing();
		} else if(button.id == 17) {
			if(rightList == 1) {
				showRightList = !showRightList;
			} else {
				rightList = 1;
				showRightList = true;
			}
		} else if(button.id == 18) {
			currentClothingPiece++;
			if(currentClothingPiece >= clothingPieces.size()) {
				currentClothingPiece = clothingPieces.size() - 1;
			}
			updateTempClothing();
		} else {
			for(int i = 5; i < 13; i++) {
				((GuiToggleButton) buttonList.get(i)).toggled = false;
			}
			((GuiToggleButton) button).toggled = true;
			currentClothingType = button.id - 5;
			updateClothingList();
			updateTempClothing();
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
		
		String stringToDraw = LanguageManager.getLocalization("clothing.category." + clothingCategories.get(currentClothingCategory).name);
		mc.fontRenderer.drawString(stringToDraw, guiLeft + 216 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 6, 0x404040);
		
		stringToDraw = LanguageManager.getLocalization("clothing." + clothingPieces.get(currentClothingPiece).category.name + "." + clothingPieces.get(currentClothingPiece).type.name +  "." + clothingPieces.get(currentClothingPiece).name);
		mc.fontRenderer.drawString(stringToDraw, guiLeft + 216 - mc.fontRenderer.getStringWidth(stringToDraw) / 2, guiTop + 120, 0x404040);
		
		if(Mouse.isButtonDown(0)) {
			if(((GuiButton) buttonList.get(0)).mousePressed(mc, mouseX, mouseY)) {
				rotation -= 1.0F;
			} else if(((GuiButton) buttonList.get(2)).mousePressed(mc, mouseX, mouseY)) {
				rotation += 1.0F;
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
}
