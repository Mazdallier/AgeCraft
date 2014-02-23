package org.agecraft.core.techtree;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import elcon.mods.elconqore.lang.LanguageManager;

public class TechTreeComponent {

	public String pageName;
	public String name;
	public ArrayList<TechTreeComponent> parents = new ArrayList<TechTreeComponent>();
	public ArrayList<TechTreeComponent> siblings = new ArrayList<TechTreeComponent>();

	public int displayColumn;
	public int displayRow;
	public ItemStack stack;
	public int iconIndex;

	public boolean isSpecial;
	public boolean isIndependent;
	public boolean isHidden;

	public TechTreeComponent(String pageName, String name, int displayColumn, int displayRow, ItemStack stack) {
		this(pageName, name, displayColumn, displayRow, stack, -1);
	}
	
	public TechTreeComponent(String pageName, String name, int displayColumn, int displayRow, int iconIndex) {
		this(pageName, name, displayColumn, displayRow, null, iconIndex);
	}
	
	public TechTreeComponent(String pageName, String name, int displayColumn, int displayRow, ItemStack stack, int iconIndex) {
		this.pageName = pageName;
		this.name = name;
		this.displayColumn = displayColumn;
		this.displayRow = displayRow;
		this.stack = stack;
		this.iconIndex = iconIndex;

		if(displayColumn < TechTree.minDisplayColumn) {
			TechTree.minDisplayColumn = displayColumn;
		}
		if(displayRow < TechTree.minDisplayRow) {
			TechTree.minDisplayRow = displayRow;
		}
		if(displayColumn > TechTree.maxDisplayColumn) {
			TechTree.maxDisplayColumn = displayColumn;
		}
		if(displayRow > TechTree.maxDisplayRow) {
			TechTree.maxDisplayRow = displayRow;
		}
		TechTree.registerComponent(this);
	}
	
	public String getName() {
		return LanguageManager.getLocalization("techtree." + pageName + "." + name + ".name");
	}
	
	public String getDescription() {
		return LanguageManager.getLocalization("techtree." + pageName + "." + name + ".description");
	}
	
	public TechTreeComponent setSpecial() {
		isSpecial = true;
		return this;
	}
	
	public TechTreeComponent setIndependent() {
		isIndependent = true;
		return this;
	}
	
	public TechTreeComponent setHidden() {
		isHidden = true;
		return this;
	}
	
	public void addParents(TechTreeComponent... components) {
		for(int i = 0; i < components.length; i++) {
			parents.add(components[i]);
		}
	}
	
	public void addSiblings(TechTreeComponent... components) {
		for(int i = 0; i < components.length; i++) {
			siblings.add(components[i]);
		}
	}
	
	public void addParent(TechTreeComponent component) {
		parents.add(component);
	}
	
	public void addSibling(TechTreeComponent component) {
		siblings.add(component);
	}
	
	public void removeParent(TechTreeComponent component) {
		parents.remove(component);
	}
	
	public void removeSibling(TechTreeComponent component) {
		siblings.remove(component);
	}
}
