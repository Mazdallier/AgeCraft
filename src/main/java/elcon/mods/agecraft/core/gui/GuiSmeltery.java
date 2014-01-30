package elcon.mods.agecraft.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACPacketHandlerClient;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;

@SideOnly(Side.CLIENT)
public class GuiSmeltery extends GuiContainer {

	public ContainerSmeltery container;
	public TileEntitySmelteryFurnace tile;

	public int lastMouseX;
	public int lastMouseY;

	public float oreScrollbar;
	public float fuelScrollbar;
	
	public boolean isScrollingOre;
	public boolean isScrollingFuel;
	public boolean wasClicking;

	public byte oreIndex;
	public byte fuelIndex;

	public boolean slotOverTop;
	public int slotOverID;

	public GuiSmeltery(ContainerSmeltery container) {
		super(container);
		this.container = container;
		tile = container.tile;
		xSize = 176;
		ySize = 185;
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int scroll = Mouse.getEventDWheel();
		if(scroll != 0) {
			if(lastMouseY < guiTop + 57) {
				if(tile.ores != null && tile.ores.length > 10) {
					int scrollPerRow = tile.ores.length / 5 - 1;
					if(scroll > 0) {
						scroll = 1;
					} else if(scroll < 0) {
						scroll = -1;
					}
					oreScrollbar = (float) ((double) oreScrollbar - (double) scroll / (double) scrollPerRow);
					if(oreScrollbar < 0.0F) {
						oreScrollbar = 0.0F;
					} else if(oreScrollbar > 1.0F) {
						oreScrollbar = 1.0F;
					}
					oreIndex = (byte) ((oreScrollbar * (float) scrollPerRow) + 0.5F);
				}
			} else {
				if(tile.fuel != null && tile.fuel.length > 10) {
					int scrollPerRow = tile.fuel.length / 5 - 1;
					if(scroll > 0) {
						scroll = 1;
					} else if(scroll < 0) {
						scroll = -1;
					}
					fuelScrollbar = (float) ((double) fuelScrollbar - (double) scroll / (double) scrollPerRow);
					if(fuelScrollbar < 0.0F) {
						fuelScrollbar = 0.0F;
					} else if(fuelScrollbar > 1.0F) {
						fuelScrollbar = 1.0F;
					}
					fuelIndex = (byte) ((fuelScrollbar * (float) scrollPerRow) + 0.5F);
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int id) {
		super.mouseClicked(mouseX, mouseY, id);
		int x = mouseX - guiLeft;
		int y = mouseY - guiTop;
		boolean pressedShift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
		if(x >= 62 && x < 152) {
			if(y >= 9 && y < 45) {
				PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getSmelterySlotClickPacket(tile.worldObj.provider.dimensionId, mc.thePlayer.username, true, oreIndex, (byte) ((x - 62) / 18), (byte) ((y - 9) / 18), Mouse.isButtonDown(1), pressedShift));
			} else if(y >= 64 && y < 100) {
				PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getSmelterySlotClickPacket(tile.worldObj.provider.dimensionId, mc.thePlayer.username, false, fuelIndex, (byte) ((x - 62) / 18), (byte) ((y - 64) / 18), Mouse.isButtonDown(1), pressedShift));
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(tile.temperature + " \u00b0C", 32, 64, 0x404040);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_LIGHTING);

		slotOverID = -1;
		if(tile.ores != null) {
			for(int j = 0; j < 2; j++) {
				for(int i = 0; i < 5; i++) {
					if(oreIndex * 5 + (i + j * 5) < tile.ores.length) {
						int xx = 62 + i * 18;
						int yy = 9 + j * 18;
						drawItemStack(xx, yy, tile.ores[oreIndex * 5 + (i + j * 5)]);
						if(isPointInRegion(xx, yy, 16, 16, mouseX, mouseY)) {
							slotOverTop = true;
							slotOverID = oreIndex * 5 + (i + j * 5);
							GL11.glDisable(GL11.GL_LIGHTING);
							GL11.glDisable(GL11.GL_DEPTH_TEST);
							drawGradientRect(xx, yy, xx + 16, yy + 16, -2130706433, -2130706433);
							GL11.glEnable(GL11.GL_LIGHTING);
							GL11.glEnable(GL11.GL_DEPTH_TEST);
						}
					}
				}
			}
		}
		if(tile.ores != null) {
			for(int j = 0; j < 2; j++) {
				for(int i = 0; i < 5; i++) {
					if(fuelIndex * 5 + (i + j * 5) < tile.fuel.length) {
						int xx = 62 + i * 18;
						int yy = 64 + j * 18;
						drawItemStack(xx, yy, tile.fuel[fuelIndex * 5 + (i + j * 5)]);
						if(isPointInRegion(xx, yy, 16, 16, mouseX, mouseY)) {
							slotOverTop = false;
							slotOverID = fuelIndex * 5 + (i + j * 5);
							GL11.glDisable(GL11.GL_LIGHTING);
							GL11.glDisable(GL11.GL_DEPTH_TEST);
							drawGradientRect(xx, yy, xx + 16, yy + 16, -2130706433, -2130706433);
							GL11.glEnable(GL11.GL_LIGHTING);
							GL11.glEnable(GL11.GL_DEPTH_TEST);
						}
					}
				}
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		boolean flag = Mouse.isButtonDown(0);
		int x = mouseX - guiLeft;
		int y = mouseY - guiTop;

		if(!wasClicking && flag && x >= 156 && x < 168) {
			if(y >= 9 && y <= 45) { 
				isScrollingOre = tile.ores.length > 10;
				isScrollingFuel = false;
			} else if(y >= 64 && y < 98) {
				isScrollingOre = false;
				isScrollingFuel = tile.ores.length > 10;
			}
		}
		if(!flag) {
			isScrollingOre = false;
			isScrollingFuel = false;
		}
		wasClicking = flag;

		if(isScrollingOre) {
			oreScrollbar = ((float) (y - 9) - 7.5F) / 19.0F;
			if(oreScrollbar < 0.0F) {
				oreScrollbar = 0.0F;
			}
			if(oreScrollbar > 1.0F) {
				oreScrollbar = 1.0F;
			}
			int scrollPerRow = tile.ores.length / 5 - 1;
			oreIndex = (byte) ((oreScrollbar * (float) scrollPerRow) + 0.5F);
		} else if(isScrollingFuel) {
			fuelScrollbar = ((float) (y - 64) - 7.5F) / 19.0F;
			if(fuelScrollbar < 0.0F) {
				fuelScrollbar = 0.0F;
			}
			if(fuelScrollbar > 1.0F) {
				fuelScrollbar = 1.0F;
			}
			int scrollPerRow = tile.fuel.length / 5 - 1;
			fuelIndex = (byte) ((fuelScrollbar * (float) scrollPerRow) + 0.5F);
		}

		if(slotOverID >= 0) {
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ItemStack stack = slotOverTop ? tile.ores[slotOverID] : tile.fuel[slotOverID];
			if(stack != null && mc.thePlayer.inventory.getItemStack() == null) {
				drawItemStackTooltip(stack, mouseX, mouseY);
			}
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		lastMouseX = mouseX;
		lastMouseY = mouseY;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ResourcesCore.guiSmeltery);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(tile.ores != null) {
			drawTexturedModalRect(guiLeft + 156, guiTop + 9 + (int) (oreScrollbar * 19), tile.ores.length > 10 ? 232 : 244, 0, 12, 15);
		}
		if(tile.fuel != null) {
			drawTexturedModalRect(guiLeft + 156, guiTop + 64 + (int) (fuelScrollbar * 19), tile.fuel.length > 10 ? 232 : 244, 0, 12, 15);
		}
			
		if(tile.isBurning()) {
			drawTexturedModalRect(guiLeft + 31, guiTop + 81, 232, 15, 14, 14);

			int size = (int) Math.floor(((double) tile.temperature / TileEntitySmelteryFurnace.MAX_TEMPERATURE) * 34.0D);
			drawTexturedModalRect(guiLeft + 9, guiTop + 64 + (34 - size), 249, 15 + (34 - size), 7, size);
		}

		drawTexturedModalRect(guiLeft + 10, guiTop + 9, 176, 0, 46, 47);
	}

	public void drawItemStack(int x, int y, ItemStack itemstack) {
		zLevel = 100.0F;
		itemRenderer.zLevel = 100.0F;

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), itemstack, x, y);
		itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.getTextureManager(), itemstack, x, y, null);

		itemRenderer.zLevel = 0.0F;
		zLevel = 0.0F;
	}
}
