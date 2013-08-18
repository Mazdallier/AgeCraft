package elcon.mods.agecraft.overlay;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.core.blocks.BlockOverlay;

public class ItemBlockOverlayRenderer implements IItemRenderer {

	private void renderOverlayBlock(RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
		Tessellator tessellator = Tessellator.instance;
		BlockOverlay block = (BlockOverlay) Block.blocksList[item.itemID];
		Icon overlay = null;

		block.setBlockBoundsForItemRender();
		render.setRenderBoundsFromBlock(block);

		GL11.glTranslatef(translateX, translateY, translateZ);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		render.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, item.getItemDamage()));
		overlay = block.getBlockOverlayTexture(0, item.getItemDamage());
		if(overlay != null) {
			render.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		render.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, item.getItemDamage()));
		overlay = block.getBlockOverlayTexture(1, item.getItemDamage());
		if(overlay != null) {
			render.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		render.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, item.getItemDamage()));
		overlay = block.getBlockOverlayTexture(2, item.getItemDamage());
		if(overlay != null) {
			render.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		render.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, item.getItemDamage()));
		overlay = block.getBlockOverlayTexture(3, item.getItemDamage());
		if(overlay != null) {
			render.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		render.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, item.getItemDamage()));
		overlay = block.getBlockOverlayTexture(4, item.getItemDamage());
		if(overlay != null) {
			render.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		render.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, item.getItemDamage()));
		overlay = block.getBlockOverlayTexture(5, item.getItemDamage());
		if(overlay != null) {
			render.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type.ordinal()) {
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type.ordinal()) {
		case 1:
			renderOverlayBlock((RenderBlocks) data[0], item, -0.5F, -0.5F, -0.5F);
			break;
		case 2:
			renderOverlayBlock((RenderBlocks) data[0], item, 0.0F, 0.0F, 0.0F);
			break;
		case 3:
			renderOverlayBlock((RenderBlocks) data[0], item, -0.5F, -0.5F, -0.5F);
			break;
		}
	}
}
