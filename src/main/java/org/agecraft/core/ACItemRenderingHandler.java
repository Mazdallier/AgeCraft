package org.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ACItemRenderingHandler implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		switch(type) {
		case ENTITY:
			return true;
		case EQUIPPED:
			return true;
		case EQUIPPED_FIRST_PERSON:
			return true;
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if(stack.getItem() instanceof ItemBlock) {
			switch(type) {
			case ENTITY:
				renderItemBlock((RenderBlocks) data[0], stack, -0.5F, -0.5F, -0.5F);
				break;
			case EQUIPPED:
				renderItemBlock((RenderBlocks) data[0], stack, -0.5F, -0.5F, -0.5F);
				break;
			case EQUIPPED_FIRST_PERSON:
				renderItemBlock((RenderBlocks) data[0], stack, -0.5F, -0.5F, -0.5F);
				break;
			case INVENTORY:
				renderItemBlock((RenderBlocks) data[0], stack, -0.5F, -0.5f, -0.5F);
				break;
			default:
			}
		}
	}

	private void renderItemBlock(RenderBlocks renderer, ItemStack stack, float translateX, float translateY, float translateZ) {
		Block block = Block.blocksList[((ItemBlock) stack.getItem()).getBlockID()];
		Tessellator tessellator = Tessellator.instance;

		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(stack.getItemDamage());
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}

        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F); 
        GL11.glTranslatef(translateX, translateY, translateZ);
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, stack.getItemDamage()));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, stack.getItemDamage()));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, stack.getItemDamage()));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, stack.getItemDamage()));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, stack.getItemDamage()));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, stack.getItemDamage()));
        tessellator.draw();
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
