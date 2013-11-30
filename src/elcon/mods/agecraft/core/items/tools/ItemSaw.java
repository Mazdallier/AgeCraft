package elcon.mods.agecraft.core.items.tools;

import codechicken.microblock.Saw;

public class ItemSaw extends ItemTool implements Saw {

	public ItemSaw(int id) {
		super(id);
	}

	@Override
	public int getCuttingStrength() {
		return 0;
	}
}
