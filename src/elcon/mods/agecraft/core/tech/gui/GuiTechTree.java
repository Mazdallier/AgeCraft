package elcon.mods.agecraft.core.tech.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.registry.LanguageRegistry;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.tech.TechTree;
import elcon.mods.agecraft.core.tech.TechTreeClient;
import elcon.mods.agecraft.core.tech.TechTreeComponent;

public class GuiTechTree extends GuiScreen {

	private static final int guiMapTop = TechTree.minDisplayColumn * 24 - 112;
	private static final int guiMapLeft = TechTree.minDisplayRow * 24 - 112;
	private static final int guiMapBottom = TechTree.maxDisplayColumn * 24 - 77;
	private static final int guiMapRight = TechTree.maxDisplayRow * 24 - 77;
	protected int achievementsPaneWidth = 256;
	protected int achievementsPaneHeight = 202;

	protected int mouseX = 0;
	protected int mouseY = 0;

	protected double field_74117_m;
	protected double field_74115_n;

	protected double guiMapX;
	protected double guiMapY;

	protected double field_74124_q;
	protected double field_74123_r;

	private int isMouseButtonDown = 0;

	public static int lastX = 0;
	public static int lastY = 0;
	private LinkedList<TechTreeComponent> techTreeComponents = new LinkedList<TechTreeComponent>();
	public static List unlockedTechTreeComponents = new ArrayList();
	private FontRenderer galFontRenderer;
	private TechTreeComponent currentHighlight = null;

	public GuiTechTree() {
		short var2 = 141;
		short var3 = 141;
		field_74117_m = guiMapX = field_74124_q = (double) (lastX * 24 - var2 / 2 - 12);
		field_74115_n = guiMapY = field_74123_r = (double) (lastY * 24 - var3 / 2);
		techTreeComponents.clear();
		Collection col = TechTree.techTreeComponents.values();
		for(Iterator i = col.iterator(); i.hasNext();) {
			Object t = i.next();
			techTreeComponents.add((TechTreeComponent) t);
		}
		for(String s : TechTreeClient.unlockedTechComponents) {
			unlockedTechTreeComponents.add(s);
		}
		galFontRenderer = Minecraft.getMinecraft().fontRenderer;
	}

	public GuiTechTree(double x, double y) {
		field_74117_m = guiMapX = field_74124_q = x;
		field_74115_n = guiMapY = field_74123_r = y;
		techTreeComponents.clear();
		Collection col = TechTree.techTreeComponents.values();
		for(Iterator i = col.iterator(); i.hasNext();) {
			Object res = i.next();
			techTreeComponents.add((TechTreeComponent) res);
		}
		for(String s : TechTreeClient.unlockedTechComponents) {
			unlockedTechTreeComponents.add(s);
		}
		galFontRenderer = Minecraft.getMinecraft().fontRenderer;
	}

	@Override
	public void onGuiClosed() {
		short var2 = 141;
		short var3 = 141;
		lastX = (int) ((guiMapX + var2 / 2 + 12.0D) / 24.0D);
		lastY = (int) ((guiMapY + var3 / 2) / 24.0D);
		super.onGuiClosed();
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiSmallButton(1, width / 2 + 24, height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton.id == 1) {
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		}
		super.actionPerformed(par1GuiButton);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if(par2 == mc.gameSettings.keyBindInventory.keyCode) {
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		} else {
			super.keyTyped(par1, par2);
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(Mouse.isButtonDown(0)) {
			int var4 = (width - achievementsPaneWidth) / 2;
			int var5 = (height - achievementsPaneHeight) / 2;
			int var6 = var4 + 8;
			int var7 = var5 + 17;

			if((isMouseButtonDown == 0 || isMouseButtonDown == 1) && par1 >= var6 && par1 < var6 + 224 && par2 >= var7 && par2 < var7 + 155) {
				if(isMouseButtonDown == 0) {
					isMouseButtonDown = 1;
				} else {
					guiMapX -= (double) (par1 - mouseX);
					guiMapY -= (double) (par2 - mouseY);
					field_74124_q = field_74117_m = guiMapX;
					field_74123_r = field_74115_n = guiMapY;
				}

				mouseX = par1;
				mouseY = par2;
			}

			if(field_74124_q < (double) guiMapTop) {
				field_74124_q = (double) guiMapTop;
			}

			if(field_74123_r < (double) guiMapLeft) {
				field_74123_r = (double) guiMapLeft;
			}

			if(field_74124_q >= (double) guiMapBottom) {
				field_74124_q = (double) (guiMapBottom - 1);
			}

			if(field_74123_r >= (double) guiMapRight) {
				field_74123_r = (double) (guiMapRight - 1);
			}
		} else {
			isMouseButtonDown = 0;
		}

		drawDefaultBackground();
		genBackground(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		drawTitle();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public void updateScreen() {
		field_74117_m = guiMapX;
		field_74115_n = guiMapY;
		double var1 = field_74124_q - guiMapX;
		double var3 = field_74123_r - guiMapY;

		if(var1 * var1 + var3 * var3 < 4.0D) {
			guiMapX += var1;
			guiMapY += var3;
		} else {
			guiMapX += var1 * 0.85D;
			guiMapY += var3 * 0.85D;
		}
	}

	protected void drawTitle() {
		int var1 = (width - achievementsPaneWidth) / 2;
		int var2 = (height - achievementsPaneHeight) / 2;
		galFontRenderer.drawString(LanguageRegistry.instance().getStringLocalization("agecraft.techtree.name"), var1 + 15, var2 + 5, 4210752);
	}

	protected void genBackground(int par1, int par2, float par3) {
		int k = MathHelper.floor_double(field_74117_m + (guiMapX - field_74117_m) * (double) par3);
		int l = MathHelper.floor_double(field_74115_n + (guiMapY - field_74115_n) * (double) par3);

		if(k < guiMapTop) {
			k = guiMapTop;
		}

		if(l < guiMapLeft) {
			l = guiMapLeft;
		}

		if(k >= guiMapBottom) {
			k = guiMapBottom - 1;
		}

		if(l >= guiMapRight) {
			l = guiMapRight - 1;
		}

		int i1 = (this.width - this.achievementsPaneWidth) / 2;
		int j1 = (this.height - this.achievementsPaneHeight) / 2;
		int k1 = i1 + 16;
		int l1 = j1 + 17;
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int i2 = k + 288 >> 4;
		int j2 = l + 288 >> 4;
		int k2 = (k + 288) % 16;
		int l2 = (l + 288) % 16;
		Random random = new Random();
		int i3;
		int j3;
		int k3;

		for(i3 = 0; i3 * 16 - l2 < 155; ++i3) {
			float f1 = 0.6F - (float) (j2 + i3) / 25.0F * 0.3F;
			GL11.glColor4f(f1, f1, f1, 1.0F);

			for(k3 = 0; k3 * 16 - k2 < 224; ++k3) {
				random.setSeed((long) (1234 + i2 + k3));
				random.nextInt();
				j3 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
				Icon icon = Block.sand.getIcon(0, 0);

				if(j3 <= 37 && j2 + i3 != 35) {
					if(j3 == 22) {
						if(random.nextInt(2) == 0) {
							icon = Block.oreDiamond.getIcon(0, 0);
						} else {
							icon = Block.oreRedstone.getIcon(0, 0);
						}
					} else if(j3 == 10) {
						icon = Block.oreIron.getIcon(0, 0);
					} else if(j3 == 8) {
						icon = Block.oreCoal.getIcon(0, 0);
					} else if(j3 > 4) {
						icon = Block.stone.getIcon(0, 0);
					} else if(j3 > 0) {
						icon = Block.dirt.getIcon(0, 0);
					}
				} else {
					icon = Block.bedrock.getIcon(0, 0);
				}

				mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				drawTexturedModelRectFromIcon(k1 + k3 * 16 - k2, l1 + i3 * 16 - l2, icon, 16, 16);
			}
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int var24;
		int var25;
		int var26;
		int var27;

		for(int var22 = 0; var22 < techTreeComponents.size(); var22++) {
			TechTreeComponent var33 = (TechTreeComponent) techTreeComponents.get(var22);

			if((var33.parents != null) && (var33.parents.length > 0)) {
				for(int a = 0; a < var33.parents.length; a++) {
					if((var33.parents[a] != null) && (techTreeComponents.contains(var33.parents[a]))) {
						var24 = var33.displayColumn * 24 - k + 11 + k1;
						var25 = var33.displayRow * 24 - l + 11 + l1;
						var26 = var33.parents[a].displayColumn * 24 - k + 11 + k1;
						var27 = var33.parents[a].displayRow * 24 - l + 11 + l1;

						boolean var28 = unlockedTechTreeComponents.contains(var33.key);
						boolean var29 = unlockedTechTreeComponents.contains(var33.parents[a].key);

						int var30 = Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) > 0.6D ? 255 : 130;
						int var31 = -16777216;

						if(var28) {
							var31 = -9408400;
						} else {
							if(var33.getHidden())
								continue;
							if(var29) {
								var31 = 65280 + (var30 << 24);
							}
						}

						drawHorizontalLine(var24, var26, var25, var31);
						drawVerticalLine(var26, var25, var27, var31);
					}
				}
			}
			if((var33.siblings != null) && (var33.siblings.length > 0)) {
				for(int a = 0; a < var33.siblings.length; a++) {
					if(((var33.siblings[a] != null) && (techTreeComponents.contains(var33.siblings[a])) && (var33.siblings[a].parents == null)) || ((var33.siblings[a].parents != null) && (!Arrays.asList(var33.siblings[a].parents).contains(var33)))) {
						var24 = var33.displayColumn * 24 - k + 11 + k1;
						var25 = var33.displayRow * 24 - l + 11 + l1;
						var26 = var33.siblings[a].displayColumn * 24 - k + 11 + k1;
						var27 = var33.siblings[a].displayRow * 24 - l + 11 + l1;

						boolean var28 = unlockedTechTreeComponents.contains(var33.key);
						boolean var29 = unlockedTechTreeComponents.contains(var33.siblings[a].key);

						int var30 = Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) > 0.6D ? 255 : 130;
						int var31 = -16777216;

						if(var28) {
							var31 = -9408400;
						} else {
							if(var33.getHidden())
								continue;
							if(var29) {
								var31 = 65280 + (var30 << 24);
							}
						}

						drawHorizontalLine(var24, var26, var25, var31);
						drawVerticalLine(var26, var25, var27, var31);
					}
				}
			}
		}
		currentHighlight = null;
		RenderItem var37 = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);

		for(var24 = 0; var24 < techTreeComponents.size(); var24++) {
			TechTreeComponent var35 = (TechTreeComponent) techTreeComponents.get(var24);
			var26 = var35.displayColumn * 24 - k;
			var27 = var35.displayRow * 24 - l;

			if((var26 >= -24) && (var27 >= -24) && (var26 <= 224) && (var27 <= 196)) {
				if(unlockedTechTreeComponents.contains(var35.key)) {
					float var38 = 1.0F;
					GL11.glColor4f(var38, var38, var38, 1.0F);
				} else {
					if(var35.getHidden())
						continue;
					if(canUnlockResearch(var35)) {
						float var38 = Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) < 0.6D ? 0.6F : 0.8F;
						GL11.glColor4f(var38, var38, var38, 1.0F);
					} else {
						float var38 = 0.3F;
						GL11.glColor4f(var38, var38, var38, 1.0F);
					}
				}

				mc.renderEngine.bindTexture(ResourcesCore.guiTechTree);
				int var42 = k1 + var26;
				int var41 = l1 + var27;

				if(var35.getStub()) {
					drawTexturedModalRect(var42 - 2, var41 - 2, 54, 202, 26, 26);
				} else {
					drawTexturedModalRect(var42 - 2, var41 - 2, 0, 202, 26, 26);
				}
				if(var35.getSpecial()) {
					drawTexturedModalRect(var42 - 2, var41 - 2, 26, 202, 26, 26);
				}

				if(!canUnlockResearch(var35)) {
					float var40 = 0.1F;
					GL11.glColor4f(var40, var40, var40, 1.0F);
					var37.renderWithColor = false;
				}

				if(var35.itemStack != null) {
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_CULL_FACE);
					var37.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, var35.itemStack, var42 + 3, var41 + 3);
					GL11.glDisable(GL11.GL_LIGHTING);
				} else {
					mc.renderEngine.bindTexture(ResourcesCore.guiTechTreeIcons);
					drawTexturedModalRect(var42 + 3, var41 + 3, var35.iconIndex % 16 * 16, var35.iconIndex / 16 * 16, 16, 16);
				}

				if(!canUnlockResearch(var35)) {
					var37.renderWithColor = true;
				}

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				if((par1 >= k1) && (par2 >= l1) && (par1 < k1 + 224) && (par2 < l1 + 196) && (par1 >= var42) && (par1 <= var42 + 22) && (par2 >= var41) && (par2 <= var41 + 22)) {
					this.currentHighlight = var35;
				}
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(ResourcesCore.guiTechTree);
		drawTexturedModalRect(i1, j1, 0, 0, achievementsPaneWidth, achievementsPaneHeight);
		GL11.glPopMatrix();
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		super.drawScreen(par1, par2, par3);

		if(this.currentHighlight != null) {
			String var34 = currentHighlight.getName();
			String var36 = currentHighlight.getDescription();
			var26 = par1 + 12;
			var27 = par2 - 4;
			FontRenderer fr = fontRenderer;
			if(!unlockedTechTreeComponents.contains(currentHighlight.key))
				fr = galFontRenderer;
			if(canUnlockResearch(currentHighlight)) {
				int var42 = Math.max(fr.getStringWidth(var34), 120);
				int var41 = fr.splitStringWidth(var36, var42);

				if(unlockedTechTreeComponents.contains(currentHighlight.key)) {
					var41 += 12;
				}

				drawGradientRect(var26 - 3, var27 - 3, var26 + var42 + 3, var27 + var41 + 3 + 12, -1073741824, -1073741824);
				fr.drawSplitString(var36, var26, var27 + 12, var42, -6250336);

				if(unlockedTechTreeComponents.contains(currentHighlight.key)) {
					String s = LanguageRegistry.instance().getStringLocalization("agecraft.techtree.unlocked");
					String st = s != "" ? s : LanguageRegistry.instance().getStringLocalization("agecraft.techtree.unlocked", "en_US");
					fontRenderer.drawStringWithShadow(st, var26, var27 + var41 + 4, -7302913);
				}
			} else {
				int var42 = Math.max(fr.getStringWidth(var36), 120);
				String s = LanguageRegistry.instance().getStringLocalization("agecraft.techtree.locked");
				String var39 = s != "" ? s : LanguageRegistry.instance().getStringLocalization("agecraft.techtree.locked", "en_US");
				int var30 = fr.splitStringWidth(var39, var42);
				drawGradientRect(var26 - 3, var27 - 3, var26 + var42 + 3, var27 + var30 + 12 + 3, -1073741824, -1073741824);
				fontRenderer.drawSplitString(var39, var26, var27 + 12, var42, -9416624);
			}

			fr.drawStringWithShadow(var34, var26, var27, currentHighlight.getSpecial() ? -8355776 : canUnlockResearch(currentHighlight) ? -1 : currentHighlight.getSpecial() ? -128 : -8355712);
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.disableStandardItemLighting();
	}

	private boolean canUnlockResearch(TechTreeComponent res) {
		if((res.parents != null) && (res.parents.length > 0)) {
			for(TechTreeComponent parent : res.parents) {
				if((parent != null) && (!unlockedTechTreeComponents.contains(parent.key))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
