package elcon.mods.agecraft.core.tech;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACLog;

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
			ACLog.warning("[TechTree] Overriding existing component (" + component.name + ") with new component (" + component.name + ") on page: " + component.pageName);
			components.remove(component);
		}
		components.add(component);
		pages.put(component.pageName, components);
	}
	
	@Override
	public void postInit() {
		//init tech tree components
		paper = new TechTreeComponent(PAGE_GENERAL, "paper", 0, 0, new ItemStack(Item.paper, 1, 0));
		planks = new TechTreeComponent(PAGE_GENERAL, "planks", -1, 2, new ItemStack(Block.planks, 1, 0));
		book = new TechTreeComponent(PAGE_GENERAL, "book", 1, 2, new ItemStack(Item.book, 1, 0));
		bookShelf = new TechTreeComponent(PAGE_GENERAL, "bookShelf", 0, 4, new ItemStack(Block.bookShelf, 1, 0));
		enchantmentTable = new TechTreeComponent(PAGE_GENERAL, "enchantmentTable", 0, 6, new ItemStack(Block.enchantmentTable, 1, 0)).setSpecial();
		enchantedBook = new TechTreeComponent(PAGE_GENERAL, "enchantedBook", 0, 8, new ItemStack(Item.enchantedBook, 1, 0)).setHidden();
		expBottle = new TechTreeComponent(PAGE_GENERAL, "expBottle", 4, 4, new ItemStack(Item.expBottle, 1, 0)).setIndependent();
	
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
		ACLog.info("[TechTree] Loaded " + size + " components in " + pages.size() + " pages (Average: " + (size / pages.size()) + " components/page)");
	}
}
