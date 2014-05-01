package org.agecraft.core.gui.menu;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSelectWorld extends net.minecraft.client.gui.GuiSelectWorld {

	public Field super_field_146643_x;
	public Field super_field_146639_s;
	public Field super_field_146640_r;

	public GuiSelectWorld(GuiScreen parent) {
		super(parent);
		try {
			super_field_146643_x = getClass().getSuperclass().getDeclaredField("field_146643_x");
			super_field_146643_x.setAccessible(true);

			super_field_146639_s = getClass().getSuperclass().getDeclaredField("field_146639_s");
			super_field_146639_s.setAccessible(true);

			super_field_146640_r = getClass().getSuperclass().getDeclaredField("field_146640_r");
			super_field_146640_r.setAccessible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		try {
			loadSaveList();
		} catch(Exception e) {
			e.printStackTrace();
			mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", e.getMessage()));
			return;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 3) {
			mc.displayGuiScreen(new GuiCreateWorld(this));
		} else if(button.id == 7) {
			try {
				GuiCreateWorld gui = new GuiCreateWorld(this);
				ISaveHandler saveHandler = mc.getSaveLoader().getSaveLoader(func_146621_a(super_field_146640_r.getInt(this)), false);
				WorldInfo worldInfo = saveHandler.loadWorldInfo();
				saveHandler.flush();
				gui.func_146318_a(worldInfo);
				mc.displayGuiScreen(gui);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			super.actionPerformed(button);
		}
	}

	@Override
	public void confirmClicked(boolean par1, int par2) {
		try {
			if(super_field_146643_x.getBoolean(this)) {
				super_field_146643_x.setBoolean(this, false);
				if(par1) {
					ISaveFormat saveFormat = mc.getSaveLoader();
					saveFormat.flushCache();
					saveFormat.deleteWorldDirectory(func_146621_a(par2));
					try {
						loadSaveList();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				mc.displayGuiScreen(this);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void loadSaveList() throws AnvilConverterException, IllegalAccessException {
		ISaveFormat saveFormat = mc.getSaveLoader();
		java.util.List list = saveFormat.getSaveList();
		ArrayList newList = new ArrayList();
		for(Object obj : list) {
			if(obj instanceof SaveFormatComparator) {
				//SaveFormatComparator comparator = (SaveFormatComparator) obj;
				//if(saveFormat.getWorldInfo(comparator.getFileName()).getNBTTagCompound().hasKey("AgeCraft")) {
					newList.add(obj);
				//}
			}
		}
		Collections.sort(newList);
		super_field_146639_s.set(this, newList);
		super_field_146640_r.setInt(this, -1);
	}
}
