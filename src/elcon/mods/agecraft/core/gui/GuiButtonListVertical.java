package elcon.mods.agecraft.core.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.Gui;

public class GuiButtonListVertical extends Gui {

	public int y;
	public int height;
	
	public GuiButton buttonUp;
	public GuiButton buttonDown;
	public ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	public int size;
	public int index;
	
	public GuiButtonListVertical(int y, int height, GuiButton buttonUp, GuiButton buttonDown, int size) {
		this.y = y;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;
		this.size = size;
		index = 0;
	}
	
	public GuiButtonListVertical(int y, int height, GuiButton buttonUp, GuiButton buttonDown, int size, int index) {
		this(y, height, buttonUp, buttonDown, size);
		this.index = index;
	}
	
	public GuiButtonListVertical(int y, int height, GuiButton buttonUp, GuiButton buttonDown, int size, GuiButton... buttons) {
		this(y, height, buttonUp, buttonDown, size);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] != null) {
				addButton(buttons[i]);
			}
		}
	}
	
	public GuiButtonListVertical(int y, int height, GuiButton buttonUp, GuiButton buttonDown, int size, int index, GuiButton... buttons) {
		this(y, height, buttonUp, buttonDown, size, buttons);
		this.index = index;
	}
	
	public void addButton(GuiButton button) {
		buttons.add(button);
		if(buttons.size() <= size) {
			buttonUp.enabled = false;
			buttonDown.enabled = false;
			buttonUp.drawButton = false;
			buttonDown.drawButton = false;
		} else {
			buttonUp.enabled = true;
			buttonDown.enabled = true;
			buttonUp.drawButton = true;
			buttonDown.drawButton = true;
		}
	}
	
	public void actionPerformed(GuiButton button) {
		if(button.id == buttonUp.id) {
			index--;
			updateList();
		} else if(button.id == buttonDown.id) {
			index++;
			updateList();
		}
	}

	public void updateList() {
		for(int i = 0; i < buttons.size(); i++) {
			if((i < index) || (i >= (index + size))) {
				buttons.get(i).enabled = false;
				buttons.get(i).drawButton = false;
			} else {
				buttons.get(i).enabled = true;
				buttons.get(i).drawButton = true;
				buttons.get(i).yPosition = y + height * (i - index);
			}
		}
	}
}
