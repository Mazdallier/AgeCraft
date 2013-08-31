package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolRodMaterial;
import elcon.mods.agecraft.core.items.tool.ItemTool;

public class Tools extends ACComponent {

	public static ItemTool sword;
	public static ItemTool pickaxe;
	public static ItemTool axe;
	public static ItemTool shovel;
	public static ItemTool hoe;
	
	@Override
	public void preInit() {
		sword = (ItemTool) new ItemTool(12520).setUnlocalizedName("tools_sword");
	}
	
	@Override
	public void init() {
		//register tools
		ToolRegistry.registerTool(new Tool(0, "sword", sword, 2, 1, true, true, true, new Block[]{
			Block.web
		}));
		ToolRegistry.registerTool(new Tool(1, "pickaxe", pickaxe, 1, 2, true, true, true, new Block[]{
			Block.stone, Metals.ore, Metals.block, Metals.fence, Metals.fenceGate, Metals.door, Metals.trapdoor, Metals.ladder
		}));
		
		//register tool materials
		ToolRegistry.registerToolMaterial(new ToolMaterial(0, "woodOak", new ItemStack(Trees.planks, 1, 0), 39, 1.5F, 0));
		
		//register tool rod materials
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(0, "woodOak", new ItemStack(Trees.stick, 1, 0), 20, 0.5F, 0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemIcons(IconRegister iconRegister) {
		for(int i = 0; i < ToolRegistry.tools.length; i++) {
			Tool tool = ToolRegistry.tools[i];
			if(tool != null) {
				if(tool.hasHead) {
					for(int j = 0; j < ToolRegistry.toolMaterials.length; j++) {
						ToolMaterial toolMaterial = ToolRegistry.toolMaterials[j];
						if(toolMaterial != null) {
							toolMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/" + tool.name + "/" + tool.name + ACUtil.firstUpperCase(toolMaterial.name));
						}
					}
				}
				if(tool.hasRod) {
					for(int j = 0; j < ToolRegistry.toolRodMaterials.length; j++) {
						ToolRodMaterial toolRodMaterial = ToolRegistry.toolRodMaterials[j];
						if(toolRodMaterial != null) {
							toolRodMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/sticks/" + tool.name + "/" + tool.name + ACUtil.firstUpperCase(toolRodMaterial.name));
						}
					}
				}
				if(tool.hasEnhancements) {
					//TODO: add tool enhancements
				}
			}
		}
	}
}
