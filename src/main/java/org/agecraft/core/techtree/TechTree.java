package org.agecraft.core.techtree;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;

import org.agecraft.ACComponent;
import org.agecraft.AgeCraft;
import org.agecraft.core.Crafting;
import org.agecraft.core.Stone;
import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.FoodRegistry.FoodStage;
import org.agecraft.prehistory.PrehistoryAge;

public class TechTree extends ACComponent {
	
	public static int minDisplayColumn;
	public static int maxDisplayColumn;
	public static int minDisplayRow;
	public static int maxDisplayRow;
	
	public static HashMap<String, ArrayList<TechTreeComponent>> pages = new HashMap<String, ArrayList<TechTreeComponent>>();
	
	public static final String PAGE_PREHISTORY = "prehistory";
	
	public static TechTreeComponent rock;
	public static TechTreeComponent rockTool;
	public static TechTreeComponent campfire;
	public static TechTreeComponent meat;
	public static TechTreeComponent workbench;
	public static TechTreeComponent cobblestone;
	public static TechTreeComponent tool;
	
	public TechTree() {
		super("techtree", false);
	}
	
	public static TechTreeComponent getComponent(String pageName, String name) {
		if(pages.containsKey(pageName)) {
			ArrayList<TechTreeComponent> components = pages.get(pageName);
			for(TechTreeComponent component : components) {
				if(component.name.equals(name)) {
					return component;
				}
			}
		}
		return null;
	}
	
	public static void registerComponent(TechTreeComponent component) {
		ArrayList<TechTreeComponent> components = new ArrayList<TechTreeComponent>();
		if(pages.containsKey(component.pageName)) {
			components = pages.get(component.pageName);
		}
		if(components.contains(component)) {
			AgeCraft.log.warn("[TechTree] Overriding existing component (" + component.name + ") with new component (" + component.name + ") on page: " + component.pageName);
			components.remove(component);
		}
		components.add(component);
		pages.put(component.pageName, components);
	}
	
	@Override
	public void postInit() {
		//init tech tree components
		rock = new TechTreeComponent(PAGE_PREHISTORY, "rock", 0, 0, new ItemStack(PrehistoryAge.rock));
		rockTool = new TechTreeComponent(PAGE_PREHISTORY, "rockTool", 0, 2, new ItemStack(PrehistoryAge.rockTool));
		campfire = new TechTreeComponent(PAGE_PREHISTORY, "campfire", -2, 4, new ItemStack(PrehistoryAge.campfire));
		meat = new TechTreeComponent(PAGE_PREHISTORY, "meat", -2, 6, ItemFood.createFood("pork", FoodStage.COOKED));
		workbench = new TechTreeComponent(PAGE_PREHISTORY, "workbench", 2, 4, new ItemStack(Crafting.workbench));
		cobblestone = new TechTreeComponent(PAGE_PREHISTORY, "cobblestone", 0, 4, new ItemStack(Stone.stoneCracked));
		tool = new TechTreeComponent(PAGE_PREHISTORY, "tool", 1, 6, ItemTool.createTool("pickaxe", 127, 15, 0)).setSpecial();
		
		rockTool.addParent(rock);
		campfire.addParent(rockTool);
		meat.addParent(campfire);
		workbench.addParent(rockTool);
		cobblestone.addParent(rockTool);
		tool.addParents(workbench, cobblestone);
		
		registerComponent(rock);
		registerComponent(rockTool);
		registerComponent(campfire);
		registerComponent(meat);
		registerComponent(workbench);
		registerComponent(cobblestone);
		registerComponent(tool);
		
		int size = 0;
		for(ArrayList<TechTreeComponent> components : pages.values()) {
			size += components.size();
		}
		AgeCraft.log.info("[TechTree] Loaded " + size + " components in " + pages.size() + " pages (Average: " + (size / pages.size()) + " components/page)");
	}
}
