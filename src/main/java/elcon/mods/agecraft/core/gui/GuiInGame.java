package elcon.mods.agecraft.core.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.ForgeHooks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;

@SideOnly(Side.CLIENT)
public class GuiInGame extends GuiIngameForge {

	public int offsetY;
	public int offsetYTarget;

	public GuiInGame(Minecraft mc) {
		super(mc);
		renderExperiance = false;
	}

	@Override
	public void renderGameOverlay(float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
		offsetYTarget = renderJumpBar ? 0 : 6;
		if(offsetY != offsetYTarget) {
			offsetY -= (int) Math.round((offsetY - offsetYTarget) * partialTicks);
		}
		super.renderGameOverlay(partialTicks, hasScreen, mouseX, mouseY);
	}

	@Override
	protected void renderArmor(int width, int height) {
		mc.mcProfiler.startSection("armor");

		int left = width / 2 - 91;
		int top = height - left_height + offsetY;
		int level = ForgeHooks.getTotalArmorValue(mc.thePlayer);
		for(int i = 1; level > 0 && i < 20; i += 2) {
			if(i < level) {
				drawTexturedModalRect(left, top, 34, 9, 9, 9);
			} else if(i == level) {
				drawTexturedModalRect(left, top, 25, 9, 9, 9);
			} else if(i > level) {
				drawTexturedModalRect(left, top, 16, 9, 9, 9);
			}
			left += 8;
		}
		left_height += 10;

		mc.mcProfiler.endSection();
	}

	public void renderHealthBar(int width, int height) {
		mc.getTextureManager().bindTexture(ResourcesCore.guiIcons);
		mc.mcProfiler.startSection("health");

		int health = MathHelper.ceiling_float_int(mc.thePlayer.getHealth());
		int healthMax = MathHelper.ceiling_float_int(mc.thePlayer.getMaxHealth());

		int x = width / 2 - 91;
		int y = height - left_height + offsetY - 1;

		drawTexturedModalRect(x, y, 0, 0, 100, 10);
		int barType = 1;
		if(mc.thePlayer.isPotionActive(Potion.poison)) {
			barType = 3;
		} else if(mc.thePlayer.isPotionActive(Potion.wither)) {
			barType = 4;
		}
		drawTexturedModalRect(x, y, 0, barType * 10, health, 10);
		left_height += 11;

		mc.mcProfiler.endSection();
		mc.getTextureManager().bindTexture(icons);
	}

	@Override
	public void renderHealth(int width, int height) {
		mc.getTextureManager().bindTexture(icons);
		mc.mcProfiler.startSection("health");

		boolean highlight = mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
		if(mc.thePlayer.hurtResistantTime < 10) {
			highlight = false;
		}
		AttributeInstance attrMaxHealth = mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		int health = MathHelper.ceiling_float_int(mc.thePlayer.getHealth() / 5);
		int healthLast = MathHelper.ceiling_float_int(mc.thePlayer.prevHealth / 5);
		float healthMax = (float) (attrMaxHealth.getAttributeValue() / 5);
		float absorb = mc.thePlayer.getAbsorptionAmount();
		int healthRows = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F / 10.0F);
		int rowHeight = Math.max(10 - (healthRows - 2), 3);
		rand.setSeed((long) (updateCounter * 312871));

		int left = width / 2 - 91;
		int top = height - left_height + offsetY;
		left_height += (healthRows * rowHeight);
		if(rowHeight != 10) {
			left_height += 10 - rowHeight;
		}

		int regen = -1;
		if(mc.thePlayer.isPotionActive(Potion.regeneration)) {
			regen = updateCounter % 25;
		}

		final int TOP = 9 * (mc.theWorld.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
		final int BACKGROUND = (highlight ? 25 : 16);
		int MARGIN = 16;
		if(mc.thePlayer.isPotionActive(Potion.poison)) {
			MARGIN += 36;
		} else if(mc.thePlayer.isPotionActive(Potion.wither)) {
			MARGIN += 72;
		}
		float absorbRemaining = absorb;

		for(int i = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
			int b0 = (highlight ? 1 : 0);
			int row = MathHelper.ceiling_float_int((float) (i + 1) / 10.0F) - 1;
			int x = left + i % 10 * 8;
			int y = top - row * rowHeight;

			if(health <= 4) {
				y += rand.nextInt(2);
			}
			if(i == regen) {
				y -= 2;
			}

			drawTexturedModalRect(x, y, BACKGROUND, TOP, 9, 9);
			if(highlight) {
				if(i * 2 + 1 < healthLast) {
					drawTexturedModalRect(x, y, MARGIN + 54, TOP, 9, 9); // 6
				} else if(i * 2 + 1 == healthLast) {
					drawTexturedModalRect(x, y, MARGIN + 63, TOP, 9, 9); // 7
				}
			}
			if(absorbRemaining > 0.0F) {
				if(absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
					drawTexturedModalRect(x, y, MARGIN + 153, TOP, 9, 9); // 17
				} else {
					drawTexturedModalRect(x, y, MARGIN + 144, TOP, 9, 9); // 16
				}
				absorbRemaining -= 2.0F;
			} else {
				if(i * 2 + 1 < health) {
					drawTexturedModalRect(x, y, MARGIN + 36, TOP, 9, 9); // 4
				} else if(i * 2 + 1 == health) {
					drawTexturedModalRect(x, y, MARGIN + 45, TOP, 9, 9); // 5
				}
			}
		}
		mc.mcProfiler.endSection();
	}

	@Override
	public void renderFood(int width, int height) {
		mc.mcProfiler.startSection("food");

		int left = width / 2 + 91;
		int top = height - right_height + offsetY;
		right_height += 10;
		boolean unused = false;

		FoodStats stats = mc.thePlayer.getFoodStats();
		int level = stats.getFoodLevel();
		int levelLast = stats.getPrevFoodLevel();

		for(int i = 0; i < 10; ++i) {
			int idx = i * 2 + 1;
			int x = left - i * 8 - 9;
			int y = top;
			int icon = 16;
			byte backgound = 0;

			if(mc.thePlayer.isPotionActive(Potion.hunger)) {
				icon += 36;
				backgound = 13;
			}
			if(unused) {
				backgound = 1;
			}
			if(mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && updateCounter % (level * 3 + 1) == 0) {
				y = top + (rand.nextInt(3) - 1);
			}

			drawTexturedModalRect(x, y, 16 + backgound * 9, 27, 9, 9);

			if(unused) {
				if(idx < levelLast) {
					drawTexturedModalRect(x, y, icon + 54, 27, 9, 9);
				} else if(idx == levelLast) {
					drawTexturedModalRect(x, y, icon + 63, 27, 9, 9);
				}
			}

			if(idx < level) {
				drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
			} else if(idx == level) {
				drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
			}
		}
		mc.mcProfiler.endSection();
	}

	@Override
	protected void renderAir(int width, int height) {
		mc.mcProfiler.startSection("air");
		int left = width / 2 + 91;
		int top = height - right_height + offsetY;

		if(mc.thePlayer.isInsideOfMaterial(Material.water)) {
			int air = mc.thePlayer.getAir();
			int full = MathHelper.ceiling_double_int((double) (air - 2) * 10.0D / 300.0D);
			int partial = MathHelper.ceiling_double_int((double) air * 10.0D / 300.0D) - full;

			for(int i = 0; i < full + partial; ++i) {
				drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
			}
			right_height += 10;
		}
		mc.mcProfiler.endSection();
	}

	@Override
	protected void renderJumpBar(int width, int height) {
		mc.getTextureManager().bindTexture(icons);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.mcProfiler.startSection("jumpBar");
		float charge = mc.thePlayer.getHorseJumpPower();
		final int barWidth = 182;
		int x = (width / 2) - (barWidth / 2);
		int filled = (int) (charge * (float) (barWidth + 1));
		int top = height - 32 + 3;

		drawTexturedModalRect(x, top, 0, 84, barWidth, 5);
		if(filled > 0) {
			drawTexturedModalRect(x, top, 0, 89, filled, 5);
		}

		mc.mcProfiler.endSection();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	protected void renderHealthMount(int width, int height) {
		Entity tmp = mc.thePlayer.ridingEntity;
		if(!(tmp instanceof EntityLivingBase)) {
			return;
		}
		mc.getTextureManager().bindTexture(icons);

		boolean unused = false;
		int left_align = width / 2 + 91;

		mc.mcProfiler.endStartSection("mountHealth");
		EntityLivingBase mount = (EntityLivingBase) tmp;
		int health = (int) Math.ceil((double) mount.getHealth());
		float healthMax = mount.getMaxHealth();
		int hearts = (int) (healthMax + 0.5F) / 2;

		if(hearts > 30)
			hearts = 30;

		final int MARGIN = 52;
		final int BACKGROUND = MARGIN + (unused ? 1 : 0);
		final int HALF = MARGIN + 45;
		final int FULL = MARGIN + 36;

		for(int heart = 0; hearts > 0; heart += 20) {
			int top = height - right_height + offsetY;

			int rowCount = Math.min(hearts, 10);
			hearts -= rowCount;

			for(int i = 0; i < rowCount; ++i) {
				int x = left_align - i * 8 - 9;
				drawTexturedModalRect(x, top, BACKGROUND, 9, 9, 9);

				if(i * 2 + 1 + heart < health) {
					drawTexturedModalRect(x, top, FULL, 9, 9, 9);
				} else if(i * 2 + 1 + heart == health) {
					drawTexturedModalRect(x, top, HALF, 9, 9, 9);
				}
			}
			right_height += 10;
		}
	}
}
