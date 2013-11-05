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

import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.clothing.Clothing;
import elcon.mods.agecraft.core.clothing.ClothingCategory;
import elcon.mods.agecraft.core.clothing.ClothingRegistry;
import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothingClient;
import elcon.mods.core.lang.LanguageManager;

public class GuiClothingSelector extends GuiScreen {

	public int guiLeft;
	public int guiTop;
	public int xSize;
	public int ySize;

	public float rotation;
	public boolean autoRotate;

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

		rotation = 0.0F;
		autoRotate = true;
	}

	@Override
	public void initGui() {
		for(int i = 0; i < ClothingRegistry.categories.length; i++) {
			clothingCategories.add(ClothingRegistry.categories[i]);
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
		
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;

		buttonList.clear();
		buttonList.add(new GuiButton(0, guiLeft + 100, guiTop + 146, 0, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButton(1, guiLeft + 120, guiTop + 146, 32, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButton(2, guiLeft + 140, guiTop + 146, 16, 182, 16, 16, ResourcesCore.guiClothingSelector, ""));

		buttonList.add(new GuiButton(3, guiLeft + 9, guiTop + 7, 68, 182, 70, 8, ResourcesCore.guiClothingSelector, ""));
		buttonList.add(new GuiButton(4, guiLeft + 9, guiTop + 145, 68, 182, 70, 8, ResourcesCore.guiClothingSelector, ""));
		buttonsClothingType = new GuiButtonListVertical(guiTop + 16, 16, (GuiButton) buttonList.get(3), (GuiButton) buttonList.get(4), 8);
		int j = 0;
		for(int i = 0; i < ClothingRegistry.types.length; i++) {
			if(ClothingRegistry.types[i] != null) {
				buttonsClothingType.addButton(new GuiButtonClothingType(5 + j, guiLeft + 10, guiTop + 16 + i * 16, 0, 166, 68, 16, ResourcesCore.guiClothingSelector, LanguageManager.getLocalization("clothing.type." + ClothingRegistry.types[i].name), i));
				j++;
			}
		}
		buttonList.addAll(buttonsClothingType.buttons);

		((GuiToggleButton) buttonList.get(5)).toggled = true;
	}

	public void updateClothingList() {
		Clothing[] clothingPiecesArray = clothingCategories.get(currentClothingCategory).clothing.get(ClothingRegistry.types[currentClothingType]);
		clothingPieces.clear();
		for(int i = 0; i < clothingPiecesArray.length; i++) {
			clothingPieces.add(clothingPiecesArray[i]);
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
			buttonsClothingType.actionPerformed((GuiButton) button);
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
		drawTexturedModalRect(guiLeft - 100, guiTop, 0, 0, 96, ySize);
		drawTexturedModalRect(guiLeft + xSize + 4, guiTop, 160, 0, 96, ySize);

		PlayerClothingClient.renderTempClothing = true;
		renderPlayer(guiLeft + 128, guiTop + 136, 60, 0.0F, 0.0F, mc.thePlayer);
		PlayerClothingClient.renderTempClothing = false;
		
		PlayerClothingClient.renderTempClothingPiece = true;
		renderPlayer(guiLeft + 216, guiTop + 124, 48, 0.0F, 0.0F, mc.thePlayer);
		PlayerClothingClient.renderTempClothingPiece = false;
		
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
