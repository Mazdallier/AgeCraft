package elcon.mods.agecraft.core.tech;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Deprecated
public class TechTreeComponent {

	public final String key;
	public String name;
	public String description;
	public TechTreeComponent[] parents = null;
	public TechTreeComponent[] siblings = null;
	public final int displayColumn;
	public final int displayRow;
	public final ItemStack itemStack;
	public final int iconIndex;
	private boolean isSpecial;
	private boolean isStub;
	private boolean isHidden;

	public TechTreeComponent(String key, String dispName, String desc, int column, int row, int icon) {
		this(key, dispName, desc, column, row, (ItemStack) null, icon);
	}

	public TechTreeComponent(String key, String dispName, String desc, int column, int row, ItemStack itemStack) {
		this(key, dispName, desc, column, row, itemStack, -1);
	}

	public TechTreeComponent(String key, String dispName, String desc, int column, int row, Item item) {
		this(key, dispName, desc, column, row, new ItemStack(item), -1);
	}

	public TechTreeComponent(String key, String dispName, String desc, int column, int row, Block block) {
		this(key, dispName, desc, column, row, new ItemStack(block), -1);
	}

	public TechTreeComponent(String key, String dispName, String desc, int column, int row, ItemStack itemStack, int icon) {
		this.key = key;
		this.name = "agecraft.techtree.component." + key + ".name";
		this.description = "agecraft.techtree.component." + key + ".description";
		this.itemStack = itemStack;
		this.iconIndex = icon;
		this.displayColumn = column;
		this.displayRow = row;
		
		LanguageRegistry.instance().addStringLocalization(name, "en_US", dispName);
		LanguageRegistry.instance().addStringLocalization(description, "en_US", desc);

		if (column < TechTree.minDisplayColumn) {
			TechTree.minDisplayColumn = column;
		}

		if (row < TechTree.minDisplayRow) {
			TechTree.minDisplayRow = row;
		}

		if (column > TechTree.maxDisplayColumn) {
			TechTree.maxDisplayColumn = column;
		}

		if (row > TechTree.maxDisplayRow) {
			TechTree.maxDisplayRow = row;
		}
	}

	public TechTreeComponent setSpecial() {
		this.isSpecial = true;
		return this;
	}

	public TechTreeComponent setStub() {
		this.isStub = true;
		return this;
	}

	public TechTreeComponent setHidden() {
		this.isHidden = true;
		return this;
	}

	public TechTreeComponent setParents(TechTreeComponent [] par) {
		this.parents = par;
		return this;
	}

	public TechTreeComponent setSiblings(TechTreeComponent [] sib) {
		this.siblings = sib;
		return this;
	}

	public TechTreeComponent registerTechTreeComponent() {
		TechTree.techTreeComponents.put(key, this);
		return this;
	}

	public String getName() {
		String s = LanguageRegistry.instance().getStringLocalization(name);
		return s != "" ? s : LanguageRegistry.instance().getStringLocalization(name, "en_US");
	}
	
	public String getDescription() {
		String s = LanguageRegistry.instance().getStringLocalization(description);
		return s != "" ? s : LanguageRegistry.instance().getStringLocalization(description, "en_US");
	}

	public boolean getSpecial() {
		return isSpecial;
	}

	public boolean getStub() {
		return isStub;
	}

	public boolean getHidden() {
		return isHidden;
	}
}