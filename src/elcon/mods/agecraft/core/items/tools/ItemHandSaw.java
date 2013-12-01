package elcon.mods.agecraft.core.items.tools;

import codechicken.microblock.Saw;

public class ItemHandSaw extends ItemTool implements Saw {

	public ItemHandSaw(int id) {
		super(id);
	}

	@Override
	public int getCuttingStrength() {
		return 0;
	}
}
