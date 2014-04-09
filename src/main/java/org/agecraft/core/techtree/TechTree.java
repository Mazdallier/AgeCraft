package org.agecraft.core.techtree;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.agecraft.ACComponent;
import org.agecraft.AgeCraft;
import org.agecraft.core.Crafting;
import org.agecraft.core.Stone;
import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.FoodRegistry.FoodStage;
import org.agecraft.core.registry.FoodRegistry.FoodType;
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
		rock = new TechTreeComponent(PAGE_PREHISTORY, "rock", new TechTreeGoalItem(new ItemStack(PrehistoryAge.rock)), 0, 0);
		rockTool = new TechTreeComponent(PAGE_PREHISTORY, "rockTool", new TechTreeGoalItem(new ItemStack(PrehistoryAge.rockTool, 1, OreDictionary.WILDCARD_VALUE)).disableNBT(), 0, 2);
		campfire = new TechTreeComponent(PAGE_PREHISTORY, "campfire", new TechTreeGoalItem(new ItemStack(PrehistoryAge.campfire)).disableNBT(), -2, 2);
		meat = new TechTreeComponent(PAGE_PREHISTORY, "meat", new TechTreeGoalItemFood(FoodType.MEAT), -2, 6, ItemFood.createFood("pork", FoodStage.COOKED));
		workbench = new TechTreeComponent(PAGE_PREHISTORY, "workbench", new TechTreeGoalItem(new ItemStack(Crafting.workbench, 1, OreDictionary.WILDCARD_VALUE)), 2, 2, new ItemStack(Crafting.workbench, 1, 15));
		cobblestone = new TechTreeComponent(PAGE_PREHISTORY, "cobblestone", new TechTreeGoalItem(new ItemStack(Stone.stoneCracked, 1, OreDictionary.WILDCARD_VALUE)), 0, 4);
		tool = new TechTreeComponent(PAGE_PREHISTORY, "tool", new TechTreeGoalItem(ItemTool.createTool("pickaxe", 127, 15, 0)), 2, 6).setSpecial();
		
		rockTool.addParent(rock);
		campfire.addParent(rockTool);
		meat.addParent(campfire);
		workbench.addParent(rockTool);
		cobblestone.addParent(rockTool);
		tool.addParents(workbench, cobblestone);
		
		int size = 0;
		for(ArrayList<TechTreeComponent> components : pages.values()) {
			size += components.size();
		}
		AgeCraft.log.info("[TechTree] Loaded " + size + " components in " + pages.size() + " pages (Average: " + (size / pages.size()) + " components/page)");
	}
}
