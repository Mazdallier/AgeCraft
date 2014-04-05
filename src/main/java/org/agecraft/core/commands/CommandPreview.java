package org.agecraft.core.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings.GameType;

import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.Crafting;
import org.agecraft.core.techtree.TechTree;
import org.agecraft.core.techtree.TechTreeServer;

import elcon.mods.elconqore.lang.LanguageManager;

public class CommandPreview extends CommandBase {

	public static Random random = new Random();
	public static HashMap<String, List<List<ItemStack>>> previews = new HashMap<String, List<List<ItemStack>>>();
	
	public static List<List<ItemStack>> anvil = Arrays.asList(
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 0)),
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 4)),
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 8)),
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 12)),
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 16)),
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 20)),
			Arrays.asList(new ItemStack(Crafting.anvil, 1, 24))
	);
	public static List<List<ItemStack>> clothingSelector = Arrays.asList(Arrays.asList(new ItemStack(AgeCraftCore.clothingSelectorTest, 1, 0)));
	
	static {
		previews.put("anvil", anvil);
		previews.put("clothingSelector", clothingSelector);
	}
	
	@Override
	public String getCommandName() {
		return "preview";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return LanguageManager.getLocalization("commands.preview.usage");
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				StringBuilder sb = new StringBuilder();
				for(String topic : previews.keySet()) {
					sb.append(topic);
					sb.append(", ");
				}
				sender.addChatMessage(new ChatComponentText(LanguageManager.getLocalization("commands.preview.topics") + " " + sb.toString().substring(0, sb.length() - 2)));
			} else if(args[0].equalsIgnoreCase("creative")) {
				EntityPlayer player = getCommandSenderAsPlayer(sender);
				if(!TechTreeServer.hasUnlockedComponent(player.getCommandSenderName(), TechTree.PAGE_PREHISTORY, TechTree.tool.name)) {
					throw new WrongUsageException(LanguageManager.getLocalization("commands.preview.completeTechTree"));
				}
				if(!player.capabilities.isCreativeMode) {
					player.setGameType(GameType.CREATIVE);
				} else {
					player.setGameType(GameType.SURVIVAL);
				}
				notifyAdmins(sender, LanguageManager.getLocalization("commands.preview.success"), args[0].toLowerCase(), sender.getCommandSenderName());
			} else if(previews.containsKey(args[0].toLowerCase())) {
				EntityPlayer player = getCommandSenderAsPlayer(sender);
				if(!TechTreeServer.hasUnlockedComponent(player.getCommandSenderName(), TechTree.PAGE_PREHISTORY, TechTree.tool.name)) {
					throw new WrongUsageException(LanguageManager.getLocalization("commands.preview.completeTechTree"));
				}
				List<ItemStack> topic = previews.get(args[0]).get(random.nextInt(previews.get(args[0]).size()));
				for(ItemStack stack : topic) {
					player.dropPlayerItemWithRandomChoice(stack.copy(), false);
				}
				notifyAdmins(sender, LanguageManager.getLocalization("commands.preview.success"), args[0].toLowerCase(), sender.getCommandSenderName());
			} else {
				throw new WrongUsageException(LanguageManager.getLocalization("commands.preview.unknown"), args[0]);
			}
		} else {
			throw new WrongUsageException(getCommandUsage(sender));
		}
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(previews.keySet());
		list.add("list");
		list.add("creative");
		return getListOfStringsFromIterableMatchingLastWord(args, list);
	}
	
	@Override
	public int compareTo(Object obj) {
		return compareTo((ICommand) obj);
	}
	
	@Override
	public int compareTo(ICommand command) {
		return getCommandName().compareTo(command.getCommandName());
	}
}
