package elcon.mods.agecraft.core.tech.gui;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.tech.TechTree;
import elcon.mods.agecraft.core.tech.TechTreeClient;
import elcon.mods.agecraft.core.tech.TechTreeComponent;
import elcon.mods.core.lang.LanguageManager;

@SideOnly(Side.CLIENT)
public class GuiTechTree extends GuiScreen {

	private static int guiMapTop = AchievementList.minDisplayColumn * 24 - 112;
	private static int guiMapLeft = AchievementList.minDisplayRow * 24 - 112;
	private static int guiMapBottom = AchievementList.maxDisplayColumn * 24 - 77;
	private static int guiMapRight = AchievementList.maxDisplayRow * 24 - 77;
	
	protected int paneWidth = 256;
	protected int paneHeight = 202;
	protected double field_74117_m;
	protected double field_74115_n;
	protected double guiMapX;
	protected double guiMapY;
	protected double field_74124_q;
	protected double field_74123_r;
	protected int mouseX;
	protected int mouseY;
	private int isMouseButtonDown;
	private GuiSmallButton pageButton;
	
	private ArrayList<String> pages = new ArrayList<String>();
	private int pageIndex;
	private String pageName = TechTree.PAGE_GENERAL;
	private ArrayList<TechTreeComponent> components = new ArrayList<TechTreeComponent>();

	public GuiTechTree() {		
		field_74117_m = guiMapX = field_74124_q = (double) (AchievementList.openInventory.displayColumn * 24 - 141 / 2 - 12);
		field_74115_n = guiMapY = field_74123_r = (double) (AchievementList.openInventory.displayRow * 24 - 141 / 2);
		components.clear();
		
		pages.clear();
		System.out.println(TechTree.pages.get("planks"));
		pages.addAll(TechTree.pages.keySet());
		pageIndex = 0;
		pageName = pages.get(pageIndex);
		
		components.clear();
		components.addAll(TechTree.pages.get(pageName));
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiSmallButton(1, width / 2 + 24, height / 2 + 74, 80, 20, I18n.getString("gui.done")));
		pageButton = new GuiSmallButton(2, (width - paneWidth) / 2 + 24, height / 2 + 74, 125, 20, LanguageManager.getLocalization("techtree." + pageName + ".name"));
		buttonList.add(pageButton);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		}
		if(button.id == 2) {
			pageIndex++;
			if(pageIndex >= pages.size()) {
				pageIndex = 0;
			}
			pageName = pages.get(pageIndex);
			components.clear();
			components.addAll(TechTree.pages.get(pageName));
			pageButton.displayString = LanguageManager.getLocalization("techtree." + pageName + ".name");
		}
		super.actionPerformed(button);
	}

	@Override
	protected void keyTyped(char c, int keyCode) {
		if(keyCode == mc.gameSettings.keyBindInventory.keyCode) {
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		} else {
			super.keyTyped(c, keyCode);
		}
	}

	@Override
	public void drawScreen(int mx, int my, float par3) {
		if(Mouse.isButtonDown(0)) {
			int guiLeft = (width - paneWidth) / 2;
			int guiTop = (height - paneHeight) / 2;
			int paneX = guiLeft + 8;
			int paneY = guiTop + 17;

			if((isMouseButtonDown == 0 || isMouseButtonDown == 1) && mx >= paneX && mx < paneX + 224 && my >= paneY && my < paneY + 155) {
				if(isMouseButtonDown == 0) {
					isMouseButtonDown = 1;
				} else {
					guiMapX -= (double) (mx - mouseX);
					guiMapY -= (double) (my - mouseY);
					field_74124_q = field_74117_m = guiMapX;
					field_74123_r = field_74115_n = guiMapY;
				}
				mouseX = mx;
				mouseY = my;
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
		genAchievementBackground(mx, my, par3);
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
		double d0 = field_74124_q - guiMapX;
		double d1 = field_74123_r - guiMapY;

		if(d0 * d0 + d1 * d1 < 4.0D) {
			guiMapX += d0;
			guiMapY += d1;
		} else {
			guiMapX += d0 * 0.85D;
			guiMapY += d1 * 0.85D;
		}
	}

	protected void drawTitle() {
		int i = (width - paneWidth) / 2;
		int j = (height - paneHeight) / 2;
		fontRenderer.drawString("Achievements", i + 15, j + 5, 4210752);
	}

	protected void genAchievementBackground(int par1, int par2, float par3) {
		int paneX = MathHelper.floor_double(field_74117_m + (guiMapX - field_74117_m) * (double) par3);
		int paneY = MathHelper.floor_double(field_74115_n + (guiMapY - field_74115_n) * (double) par3);

		if(paneX < guiMapTop) {
			paneX = guiMapTop;
		}
		if(paneY < guiMapLeft) {
			paneY = guiMapLeft;
		}
		if(paneX >= guiMapBottom) {
			paneX = guiMapBottom - 1;
		}
		if(paneY >= guiMapRight) {
			paneY = guiMapRight - 1;
		}
		int i1 = (width - paneWidth) / 2;
		int j1 = (height - paneHeight) / 2;
		int k1 = i1 + 16;
		int l1 = j1 + 17;
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int i2 = paneX + 288 >> 4;
		int j2 = paneY + 288 >> 4;
		int k2 = (paneX + 288) % 16;
		int l2 = (paneY + 288) % 16;
		boolean flag = true;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
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
		int l3;
		int i4;
		int j4;

		for(TechTreeComponent component : components) {
			if(!component.parents.isEmpty()) {
				for(TechTreeComponent parentComponent : component.parents) {
					if(components.contains(parentComponent)) {
						k3 = component.displayColumn * 24 - paneX + 11 + k1;
						j3 = component.displayRow * 24 - paneY + 11 + l1;
						j4 = parentComponent.displayColumn * 24 - paneX + 11 + k1;
						l3 = parentComponent.displayRow * 24 - paneY + 11 + l1;
						boolean unlocked = TechTreeClient.hasUnlockedComponent(component.pageName, component.name);
						boolean canUnlock = TechTreeClient.canUnlockComponent(component.pageName, component.name);
						i4 = Math.sin((double) (Minecraft.getSystemTime() % 600L) / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
						int color = -16777216;
						if(unlocked) {
							color = -9408400;
						} else {
							if(component.isHidden) {
								continue;
							}
							if(canUnlock) {
								color = 65280 + (i4 << 24);
							}
						}
						drawHorizontalLine(k3, j4, j3, color);
						drawVerticalLine(j4, j3, l3, color);
					}
				}
			}
			if(!component.siblings.isEmpty()) {
				for(TechTreeComponent siblingComponent : component.siblings) {
					if(components.contains(siblingComponent)) {
						k3 = component.displayColumn * 24 - paneX + 11 + k1;
						j3 = component.displayRow * 24 - paneY + 11 + l1;
						j4 = siblingComponent.displayColumn * 24 - paneX + 11 + k1;
						l3 = siblingComponent.displayRow * 24 - paneY + 11 + l1;
						boolean unlocked = TechTreeClient.hasUnlockedComponent(component.pageName, component.name);
						boolean canUnlock = TechTreeClient.canUnlockComponent(siblingComponent.pageName, siblingComponent.name);
						
						i4 = Math.sin((double) (Minecraft.getSystemTime() % 600L) / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
						int color = -16777216;
						if(unlocked) {
							color = -9408400;
						} else {
							if(component.isHidden) {
								continue;
							}
							if(canUnlock) {
								color = 65280 + (i4 << 24);
							}							
						}
						drawHorizontalLine(k3, j4, j3, color);
						drawVerticalLine(j4, j3, l3, color);
					}
				}
			}
		}

		TechTreeComponent currentHighlight = null;
		RenderItem renderitem = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int l4;
		int i5;

		for(TechTreeComponent component : components) {
			j4 = component.displayColumn * 24 - paneX;
			l3 = component.displayRow * 24 - paneY;
			if(j4 >= -24 && l3 >= -24 && j4 <= 224 && l3 <= 155) {
				float f2;
				if(TechTreeClient.hasUnlockedComponent(component.pageName, component.name)) {
					f2 = 1.0F;
					GL11.glColor4f(f2, f2, f2, 1.0F);
				} else {
					if(component.isHidden) {
						continue;
					}
					if(TechTreeClient.canUnlockComponent(component.pageName, component.name)) {
						f2 = Math.sin((double) (Minecraft.getSystemTime() % 600L) / 600.0D * Math.PI * 2.0D) < 0.6D ? 0.6F : 0.8F;
						GL11.glColor4f(f2, f2, f2, 1.0F);
					} else {
						f2 = 0.3F;
						GL11.glColor4f(f2, f2, f2, 1.0F);
					}
				}
				mc.getTextureManager().bindTexture(ResourcesCore.guiTechTree);
				i5 = k1 + j4;
				l4 = l1 + l3;

				if(component.isSpecial) {
					drawTexturedModalRect(i5 - 2, l4 - 2, 26, 202, 26, 26);
				} else {
					drawTexturedModalRect(i5 - 2, l4 - 2, 0, 202, 26, 26);
				}
				if(!TechTreeClient.canUnlockComponent(component.pageName, component.name)) {
					float f3 = 0.1F;
					GL11.glColor4f(f3, f3, f3, 1.0F);
					renderitem.renderWithColor = false;
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);
				if(component.stack != null) {
					renderitem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), component.stack, i5 + 3, l4 + 3);
				} else {
					mc.renderEngine.bindTexture(ResourcesCore.guiTechTreeIcons);
					drawTexturedModalRect(i5 + 3, l4 + 3, component.iconIndex % 16 * 16, component.iconIndex / 16 * 16, 16, 16);
					mc.getTextureManager().bindTexture(ResourcesCore.guiTechTree);
				}
				GL11.glDisable(GL11.GL_LIGHTING);
				if(!TechTreeClient.canUnlockComponent(component.pageName, component.name)) {
					renderitem.renderWithColor = true;
				}				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if(par1 >= k1 && par2 >= l1 && par1 < k1 + 224 && par2 < l1 + 155 && par1 >= i5 && par1 <= i5 + 22 && par2 >= l4 && par2 <= l4 + 22) {
					currentHighlight = component;
				}
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ResourcesCore.guiTechTree);
		drawTexturedModalRect(i1, j1, 0, 0, paneWidth, paneHeight);
		GL11.glPopMatrix();
		zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(par1, par2, par3);

		if(currentHighlight != null) {
			String s = I18n.getString(currentHighlight.getName());
			String s1 = currentHighlight.getDescription();
			j4 = par1 + 12;
			l3 = par2 - 4;

			if(TechTreeClient.canUnlockComponent(currentHighlight.pageName, currentHighlight.name)) {
				i5 = Math.max(fontRenderer.getStringWidth(s), 120);
				l4 = fontRenderer.splitStringWidth(s1, i5);
				if(TechTreeClient.hasUnlockedComponent(currentHighlight.pageName, currentHighlight.name)) {
					l4 += 12;
				}
				drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + l4 + 3 + 12, -1073741824, -1073741824);
				fontRenderer.drawSplitString(s1, j4, l3 + 12, i5, -6250336);
				if(TechTreeClient.hasUnlockedComponent(currentHighlight.pageName, currentHighlight.name)) {
					fontRenderer.drawStringWithShadow(I18n.getString("achievement.taken"), j4, l3 + l4 + 4, -7302913);
				}
			} else {
				i5 = Math.max(fontRenderer.getStringWidth(s), 120);
				i4 = fontRenderer.splitStringWidth(LanguageManager.getLocalization("techtree.locked"), i5);
				drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + i4 + 12 + 3, -1073741824, -1073741824);
				fontRenderer.drawSplitString(LanguageManager.getLocalization("techtree.locked"), j4, l3 + 12, i5, -9416624);
			}
			fontRenderer.drawStringWithShadow(s, j4, l3, TechTreeClient.canUnlockComponent(currentHighlight.pageName, currentHighlight.name) ? (currentHighlight.isSpecial ? -128 : -1) : (currentHighlight.isSpecial ? -8355776 : -8355712));
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.disableStandardItemLighting();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
