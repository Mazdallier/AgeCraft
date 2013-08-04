package elcon.mods.agecraft.prehistory;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class PrehistoryBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		if(modelID == 200) {
			TileEntityCampfire tile = (TileEntityCampfire) blockAccess.getBlockTileEntity(x, y, z);
			if(tile.hasSpit) {
				renderer.setOverrideBlockTexture(Block.planks.getIcon(0, 0));
				if(tile.spitDirection == 0) {
					if(tile.spitStage >= 1) {
						renderer.setRenderBounds(0.0D, 0.0D, 0.4D, 0.1D, 1.0D, 0.6D);
						renderer.renderStandardBlock(block, x, y, z);
					}
					if(tile.spitStage >= 2) {
						renderer.setRenderBounds(0.9D, 0.0D, 0.4D, 1.0D, 1.0D, 0.6D);
						renderer.renderStandardBlock(block, x, y, z);
					}
					if(tile.spitStage >= 3) {
						renderer.setRenderBounds(0.1D, 0.9D, 0.45D, 0.9D, 1.0D, 0.55D);
						renderer.renderStandardBlock(block, x, y, z);
					}
				} else if(tile.spitDirection == 1) {
					if(tile.spitStage >= 1) {		
						renderer.setRenderBounds(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 0.1D);
						renderer.renderStandardBlock(block, x, y, z);
					}
					if(tile.spitStage >= 2) {
						renderer.setRenderBounds(0.4D, 0.0D, 0.9D, 0.6D, 1.0D, 1.0D);
						renderer.renderStandardBlock(block, x, y, z);
					}
					if(tile.spitStage >= 3) {		
						renderer.setRenderBounds(0.45D, 0.9D, 0.1D, 0.55D, 1.0D, 0.9D);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				renderer.clearOverrideBlockTexture();
			}
			return true;
		} else if(modelID == 201) {
			renderer.setRenderBounds(0.35D, 0.0D, 0.35D, 0.75D, 0.2D, 0.75D);
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return 200;
	}
}
