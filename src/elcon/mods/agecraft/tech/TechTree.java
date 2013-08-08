package elcon.mods.agecraft.tech;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class TechTree {

	public static int minDisplayColumn;
    public static int minDisplayRow;
    public static int maxDisplayColumn;
    public static int maxDisplayRow;
    public static Map techTreeComponents = new HashMap();
    
    public static TechTreeComponent test;
	public static TechTreeComponent test2;
	public static TechTreeComponent test3;
	public static TechTreeComponent test4;
	public static TechTreeComponent test5;
	
	public void init() {
		test = new TechTreeComponent("test", "Paper", "Paper is needed for books.", 0, 0, Item.paper).registerTechTreeComponent();
		test2 = new TechTreeComponent("test2", "Planks", "Planks are needed for the bookshelf frame.", -1, 2, Block.planks).registerTechTreeComponent();
		test3 = new TechTreeComponent("test3", "Books", "Books can be put into bookshelves.", 1, 2, Item.book).registerTechTreeComponent();
		test4 = new TechTreeComponent("test4", "Bookshelves", "Bookshelves contain create knowledge.", 0, 4, Block.bookShelf).setStub().registerTechTreeComponent();
		test5 = new TechTreeComponent("test5", "Enchantment Table", "Enchanting requires knowledge.", 0, 6, Block.enchantmentTable).setSpecial().registerTechTreeComponent();
		
		test2.setParents(new TechTreeComponent[]{test});
		test3.setParents(new TechTreeComponent[]{test});
		test4.setParents(new TechTreeComponent[]{test2, test3});
		test5.setParents(new TechTreeComponent[]{test4});
	}
	
    public static TechTreeComponent getTechTreeComponent(String key) {
    	return (TechTreeComponent)techTreeComponents.get(key);
    }
    
    public static String getTechTreeComponentName(String key) {
    	TechTreeComponent tech = (TechTreeComponent)techTreeComponents.get(key);
    	return tech.getName();
    }
    
    public static String getTechTreeComponentDescription(String key) {
    	TechTreeComponent tech = (TechTreeComponent)techTreeComponents.get(key);
    	return tech.getDescription();
    }
}
