package org.agecraft.core.gui.menu;

import java.util.Random;

import org.agecraft.AgeCraft;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiCreateWorld extends GuiScreen {

	public static final String[] illegalFolderNames = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
	
	public GuiScreen parent;
	public GuiButton buttonGameMode;
	public GuiButton buttonMoreOptions;
	public GuiButton buttonMapFeatures;
	public GuiButton buttonBonusItems;
	public GuiButton buttonMapType;
	public GuiButton buttonAllowCommands;
	public GuiButton buttonCustomizeType;
	public GuiTextField textFieldName;
	public GuiTextField textFieldSeed;
	
	public String folderName;
	public String gamemode = "survival";
	public boolean enableNapFeatures = true;
	public boolean allowCommands;
	public boolean isAllowCommandsClicked;
	public boolean enableBonusItems;
	public boolean isHardcore;
	public boolean field_146345_x;
	public boolean isOptionScreen;
	public String gamemodeInfo1;
	public String gamemodeInfo2;
	public String seed;
	public String name;
	public int terrainType;
	public String generatorOptions = "";

	public GuiCreateWorld(GuiScreen parent) {
		this.parent = parent;
		seed = "";
		name = I18n.format("selectWorld.newWorld");
	}

	@Override
	public void updateScreen() {
		textFieldName.updateCursorCounter();
		textFieldSeed.updateCursorCounter();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("selectWorld.create")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel")));
		buttonList.add(buttonGameMode = new GuiButton(2, width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode")));
		buttonList.add(buttonMoreOptions = new GuiButton(3, width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions")));
		buttonList.add(buttonMapFeatures = new GuiButton(4, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures")));
		buttonMapFeatures.visible = false;
		buttonList.add(buttonBonusItems = new GuiButton(7, width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems")));
		buttonBonusItems.visible = false;
		buttonList.add(buttonMapType = new GuiButton(5, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType")));
		buttonMapType.visible = false;
		buttonList.add(buttonAllowCommands = new GuiButton(6, width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands")));
		buttonAllowCommands.visible = false;
		buttonList.add(buttonCustomizeType = new GuiButton(8, width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType")));
		buttonCustomizeType.visible = false;

		textFieldName = new GuiTextField(fontRendererObj, width / 2 - 100, 60, 200, 20);
		textFieldName.setFocused(true);
		textFieldName.setText(name);
		textFieldSeed = new GuiTextField(fontRendererObj, width / 2 - 100, 60, 200, 20);
		textFieldSeed.setText(seed);
		setOptionScreen(isOptionScreen);
		updateFolderName();
		updateButtons();
	}

	private void updateFolderName() {
		folderName = textFieldName.getText().trim();
		char[] chars = ChatAllowedCharacters.allowedCharacters;		
		for(int j = 0; j < chars.length; ++j) {
			char c = chars[j];
			folderName = folderName.replace(c, '_');
		}
		if(MathHelper.stringNullOrLengthZero(folderName)) {
			folderName = "World";
		}
		folderName = formatFolderName(mc.getSaveLoader(), folderName);
	}

	private void updateButtons() {
		buttonGameMode.displayString = I18n.format("selectWorld.gameMode") + " " + I18n.format("selectWorld.gameMode." + gamemode);
		gamemodeInfo1 = I18n.format("selectWorld.gameMode." + gamemode + ".line1");
		gamemodeInfo2 = I18n.format("selectWorld.gameMode." + gamemode + ".line2");
		buttonMapFeatures.displayString = I18n.format("selectWorld.mapFeatures") + " ";

		if(enableNapFeatures) {
			buttonMapFeatures.displayString = buttonMapFeatures.displayString + I18n.format("options.on");
		} else {
			buttonMapFeatures.displayString = buttonMapFeatures.displayString + I18n.format("options.off");
		}

		buttonBonusItems.displayString = I18n.format("selectWorld.bonusItems") + " ";

		if(enableBonusItems && !isHardcore) {
			buttonBonusItems.displayString = buttonBonusItems.displayString + I18n.format("options.on");
		} else {
			buttonBonusItems.displayString = buttonBonusItems.displayString + I18n.format("options.off");
		}

		buttonMapType.displayString = I18n.format("selectWorld.mapType") + " " + I18n.format(WorldType.worldTypes[terrainType].getTranslateName());
		buttonAllowCommands.displayString = I18n.format("selectWorld.allowCommands") + " ";

		if(allowCommands && !isHardcore) {
			buttonAllowCommands.displayString = buttonAllowCommands.displayString + I18n.format("options.on");
		} else {
			buttonAllowCommands.displayString = buttonAllowCommands.displayString + I18n.format("options.off");
		}
	}

	public static String formatFolderName(ISaveFormat saveFormat, String folderName) {
		folderName = folderName.replaceAll("[\\./\"]", "_");
		String[] strings = illegalFolderNames;
		for(int j = 0; j < strings.length; ++j) {
			String s = strings[j];
			if(folderName.equalsIgnoreCase(s)) {
				folderName = "_" + folderName + "_";
			}
		}
		while(saveFormat.getWorldInfo(folderName) != null) {
			folderName = folderName + "-";
		}
		return folderName;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 1) {
				mc.displayGuiScreen(parent);
			} else if(button.id == 0) {
				mc.displayGuiScreen((GuiScreen) null);
				if(field_146345_x) {
					return;
				}
				field_146345_x = true;
				long i = (new Random()).nextLong();
				String s = textFieldSeed.getText();

				if(!MathHelper.stringNullOrLengthZero(s)) {
					try {
						long j = Long.parseLong(s);

						if(j != 0L) {
							i = j;
						}
					} catch(NumberFormatException numberformatexception) {
						i = (long) s.hashCode();
					}
				}

				WorldType.worldTypes[terrainType].onGUICreateWorldPress();

				WorldSettings.GameType gametype = WorldSettings.GameType.getByName(gamemode);
				WorldSettings worldsettings = new WorldSettings(i, gametype, enableNapFeatures, isHardcore, WorldType.worldTypes[terrainType]);
				worldsettings.func_82750_a(generatorOptions);

				if(enableBonusItems && !isHardcore) {
					worldsettings.enableBonusChest();
				}

				if(allowCommands && !isHardcore) {
					worldsettings.enableCommands();
				}

				AgeCraft.isNewWorld = 1;
				mc.launchIntegratedServer(folderName, textFieldName.getText().trim(), worldsettings);
			} else if(button.id == 3) {
				func_146315_i();
			} else if(button.id == 2) {
				if(gamemode.equals("survival")) {
					if(!isAllowCommandsClicked) {
						allowCommands = false;
					}
					isHardcore = false;
					gamemode = "hardcore";
					isHardcore = true;
					buttonAllowCommands.enabled = false;
					buttonBonusItems.enabled = false;
					updateButtons();
				} else if(gamemode.equals("hardcore")) {
					if(!isAllowCommandsClicked) {
						allowCommands = true;
					}
					isHardcore = false;
					gamemode = "creative";
					updateButtons();
					isHardcore = false;
					buttonAllowCommands.enabled = true;
					buttonBonusItems.enabled = true;
				} else {
					if(!isAllowCommandsClicked) {
						allowCommands = false;
					}
					gamemode = "survival";
					updateButtons();
					buttonAllowCommands.enabled = true;
					buttonBonusItems.enabled = true;
					isHardcore = false;
				}

				updateButtons();
			} else if(button.id == 4) {
				enableNapFeatures = !enableNapFeatures;
				updateButtons();
			} else if(button.id == 7) {
				enableBonusItems = !enableBonusItems;
				updateButtons();
			} else if(button.id == 5) {
				terrainType++;
				if(terrainType >= WorldType.worldTypes.length) {
					terrainType = 0;
				}
				while(WorldType.worldTypes[terrainType] == null || !WorldType.worldTypes[terrainType].getCanBeCreated()) {
					++terrainType;

					if(terrainType >= WorldType.worldTypes.length) {
						terrainType = 0;
					}
				}
				generatorOptions = "";
				updateButtons();
				setOptionScreen(isOptionScreen);
			} else if(button.id == 6) {
				isAllowCommandsClicked = true;
				allowCommands = !allowCommands;
				updateButtons();
			} else if(button.id == 8) {
				//TODO: flat world customization
				//WorldType.worldTypes[terrainType].onCustomizeButton(mc, this);
			}
		}
	}

	private void func_146315_i() {
		setOptionScreen(!isOptionScreen);
	}

	private void setOptionScreen(boolean flag) {
		isOptionScreen = flag;
		buttonGameMode.visible = !isOptionScreen;
		buttonMapFeatures.visible = isOptionScreen;
		buttonBonusItems.visible = isOptionScreen;
		buttonMapType.visible = isOptionScreen;
		buttonAllowCommands.visible = isOptionScreen;
		buttonCustomizeType.visible = isOptionScreen && WorldType.worldTypes[terrainType].isCustomizable();

		if(isOptionScreen) {
			buttonMoreOptions.displayString = I18n.format("gui.done");
		} else {
			buttonMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions");
		}
	}

	@Override
	protected void keyTyped(char c, int id) {
		if(textFieldName.isFocused() && !isOptionScreen) {
			textFieldName.textboxKeyTyped(c, id);
			name = textFieldName.getText();
		} else if(textFieldSeed.isFocused() && isOptionScreen) {
			textFieldSeed.textboxKeyTyped(c, id);
			seed = textFieldSeed.getText();
		}
		
		if(id == 28 || id == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}

		((GuiButton) buttonList.get(0)).enabled = textFieldName.getText().length() > 0;
		updateFolderName();
	}

	@Override
	protected void mouseClicked(int x, int y, int id) {
		super.mouseClicked(x, y, id);
		if(isOptionScreen) {
			textFieldSeed.mouseClicked(x, y, id);
		} else {
			textFieldName.mouseClicked(x, y, id);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialRenderTime) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, I18n.format("selectWorld.create"), width / 2, 20, -1);

		if(isOptionScreen) {
			drawString(fontRendererObj, I18n.format("selectWorld.enterSeed"), width / 2 - 100, 47, -6250336);
			drawString(fontRendererObj, I18n.format("selectWorld.seedInfo"), width / 2 - 100, 85, -6250336);
			drawString(fontRendererObj, I18n.format("selectWorld.mapFeatures.info"), width / 2 - 150, 122, -6250336);
			drawString(fontRendererObj, I18n.format("selectWorld.allowCommands.info"), width / 2 - 150, 172, -6250336);
			textFieldSeed.drawTextBox();

			if(WorldType.worldTypes[terrainType].showWorldInfoNotice()) {
				fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[terrainType].func_151359_c()), buttonMapType.xPosition + 2, buttonMapType.yPosition + 22, buttonMapType.getButtonWidth(), 10526880);
			}
		} else {
			drawString(fontRendererObj, I18n.format("selectWorld.enterName"), width / 2 - 100, 47, -6250336);
			drawString(fontRendererObj, I18n.format("selectWorld.resultFolder") + " " + folderName, width / 2 - 100, 85, -6250336);
			textFieldName.drawTextBox();
			drawString(fontRendererObj, gamemodeInfo1, width / 2 - 100, 137, -6250336);
			drawString(fontRendererObj, gamemodeInfo2, width / 2 - 100, 149, -6250336);
		}
		super.drawScreen(mouseX, mouseY, partialRenderTime);
	}

	public void func_146318_a(WorldInfo worldInfo) {
		name = I18n.format("selectWorld.newWorld.copyOf", worldInfo.getWorldName());
		seed = worldInfo.getSeed() + "";
		terrainType = worldInfo.getTerrainType().getWorldTypeID();
		generatorOptions = worldInfo.getGeneratorOptions();
		enableNapFeatures = worldInfo.isMapFeaturesEnabled();
		allowCommands = worldInfo.areCommandsAllowed();
		
		if(worldInfo.isHardcoreModeEnabled()) {
			gamemode = "hardcore";
		} else if(worldInfo.getGameType().isSurvivalOrAdventure()) {
			gamemode = "survival";
		} else if(worldInfo.getGameType().isCreative()) {
			gamemode = "creative";
		}
	}
}
