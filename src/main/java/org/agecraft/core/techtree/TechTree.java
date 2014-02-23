package org.agecraft.core.techtree;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.AgeCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class TechTree extends ACComponent {
	
	public static int minDisplayColumn;
	public static int maxDisplayColumn;
	public static int minDisplayRow;
	public static int maxDisplayRow;
	
	public static HashMap<String, ArrayList<TechTreeComponent>> pages = new HashMap<String, ArrayList<TechTreeComponent>>();
	
	public static final String PAGE_GENERAL = "general";
	
	public static TechTreeComponent paper;
	public static TechTreeComponent planks;
	public static TechTreeComponent book;
	public static TechTreeComponent bookShelf;
	public static TechTreeComponent enchantmentTable;
	public static TechTreeComponent enchantedBook;
	public static TechTreeComponent expBottle;
	
	public TechTree() {
		super("techtree", true);
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
		paper = new TechTreeComponent(PAGE_GENERAL, "paper", 0, 0, new ItemStack(Items.paper, 1, 0));
		planks = new TechTreeComponent(PAGE_GENERAL, "planks", -1, 2, new ItemStack(Blocks.planks, 1, 0));
		book = new TechTreeComponent(PAGE_GENERAL, "book", 1, 2, new ItemStack(Items.book, 1, 0));
		bookShelf = new TechTreeComponent(PAGE_GENERAL, "bookShelf", 0, 4, new ItemStack(Blocks.bookshelf, 1, 0));
		enchantmentTable = new TechTreeComponent(PAGE_GENERAL, "enchantmentTable", 0, 6, new ItemStack(Blocks.enchanting_table, 1, 0)).setSpecial();
		enchantedBook = new TechTreeComponent(PAGE_GENERAL, "enchantedBook", 0, 8, new ItemStack(Items.enchanted_book, 1, 0)).setHidden();
		expBottle = new TechTreeComponent(PAGE_GENERAL, "expBottle", 4, 4, new ItemStack(Items.experience_bottle, 1, 0)).setIndependent();
	
		planks.addParent(paper);
		book.addParent(paper);
		planks.addSibling(book);
		book.addSibling(planks);
		bookShelf.addParents(planks, book);
		enchantmentTable.addParent(bookShelf);
		enchantedBook.addParent(enchantmentTable);
		
		int size = 0;
		for(ArrayList<TechTreeComponent> components : pages.values()) {
			size += components.size();
		}
		AgeCraft.log.info("[TechTree] Loaded " + size + " components in " + pages.size() + " pages (Average: " + (size / pages.size()) + " components/page)");
	}
	
	@Override
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{MessageTechTreeComponent.class, MessageTechTreeAllComponents.class};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return TechTreeClient.instance != null ? TechTreeClient.instance : new TechTreeClient(this);
	}
}
