package org.agecraft.core.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import org.agecraft.core.techtree.TechTree;
import org.agecraft.core.techtree.TechTreeComponent;
import org.agecraft.core.techtree.TechTreeServer;

import elcon.mods.elconqore.lang.LanguageManager;

public class CommandTechTree extends CommandBase {

	@Override
	public String getCommandName() {
		return "techtree";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return LanguageManager.getLocalization("commands.techtree.usage");
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 1 || args.length == 2) {
			if(args[0].equalsIgnoreCase("pages")) {
				StringBuilder sb = new StringBuilder();
				for(String page : TechTree.pages.keySet()) {
					sb.append(page);
					sb.append(", ");
				}
				sender.addChatMessage(new ChatComponentText(LanguageManager.getLocalization("commands.techtree.pages") + " " + sb.toString().substring(0, sb.length() - 2)));
			} else if(args[0].equalsIgnoreCase("components")) {
				if(args.length == 2) {
					if(TechTree.pages.containsKey(args[1])) {
						StringBuilder sb = new StringBuilder();
						for(TechTreeComponent component : TechTree.pages.get(args[1])) {
							sb.append(component.name);
							sb.append(", ");
						}
						sender.addChatMessage(new ChatComponentText(LanguageManager.getLocalization("commands.techtree.components") + " " + sb.toString().substring(0, sb.length() - 2)));
					} else {
						throw new WrongUsageException(LanguageManager.getLocalization("commands.techtree.unknownPage"));
					}
				} else {
					throw new WrongUsageException(LanguageManager.getLocalization("commands.techtree.components.usage"));
				}
			} else if(args[0].equalsIgnoreCase("unlock")) {
				throw new WrongUsageException(LanguageManager.getLocalization("commands.techtree.unlock.usage"));
			} else if(args[0].equalsIgnoreCase("lock")) {
				throw new WrongUsageException(LanguageManager.getLocalization("commands.techtree.lock.usage"));
			} else {
				throw new WrongUsageException(getCommandUsage(sender));
			}
		} else if(args.length >= 3) {
			if(!TechTree.pages.containsKey(args[1]) && !args[1].equals("*")) {
				throw new WrongUsageException(LanguageManager.getLocalization("commands.techtree.unknownPage"), args[1]);
			}
			if(TechTree.getComponent(args[1], args[2]) == null && !args[2].equals("*")) {
				throw new WrongUsageException(LanguageManager.getLocalization("commands.techtree.unknownComponent"), args[2]);
			}
			EntityPlayerMP player;
			if(args.length >= 4) {
				player = getPlayer(sender, args[3]);
			} else {
				player = getCommandSenderAsPlayer(sender);
			}
			if(args[0].equalsIgnoreCase("unlock")) {
				if(args[1].equals("*")) {
					for(String pageName : TechTree.pages.keySet()) {
						ArrayList<TechTreeComponent> page = TechTree.pages.get(pageName);
						for(TechTreeComponent c : page) {
							TechTreeServer.unlockComponent(player.getCommandSenderName(), c.pageName, c.name);
						}
						notifyAdmins(sender, LanguageManager.getLocalization("commands.techtree.unlock.success.all"), pageName, player.getCommandSenderName());
					}
				} else {
					if(args[2].equals("*")) {
						ArrayList<TechTreeComponent> page = TechTree.pages.get(args[1]);
						for(TechTreeComponent c : page) {
							TechTreeServer.unlockComponent(player.getCommandSenderName(), c.pageName, c.name);
						}
						notifyAdmins(sender, LanguageManager.getLocalization("commands.techtree.unlock.success.all"), args[1], player.getCommandSenderName());
					} else {
						ArrayList<TechTreeComponent> parents = TechTree.getComponent(args[1], args[2]).parents;
						for(TechTreeComponent c : parents) {
							TechTreeServer.unlockComponent(player.getCommandSenderName(), c.pageName, c.name);
						}
						TechTreeServer.unlockComponent(player.getCommandSenderName(), args[1], args[2]);
						notifyAdmins(sender, LanguageManager.getLocalization("commands.techtree.unlock.success.one"), args[2], player.getCommandSenderName());
					}
				}
			} else if(args[0].equalsIgnoreCase("lock")) {
				if(args[1].equals("*")) {
					for(String pageName : TechTree.pages.keySet()) {
						ArrayList<TechTreeComponent> page = TechTree.pages.get(pageName);
						for(TechTreeComponent c : page) {
							TechTreeServer.lockComponent(player.getCommandSenderName(), c.pageName, c.name);
						}
						notifyAdmins(sender, LanguageManager.getLocalization("commands.techtree.lock.success.all"), pageName, player.getCommandSenderName());
					}
				} else {
					if(args[2].equals("*")) {
						ArrayList<TechTreeComponent> page = TechTree.pages.get(args[1]);
						for(TechTreeComponent c : page) {
							TechTreeServer.lockComponent(player.getCommandSenderName(), c.pageName, c.name);
						}
						notifyAdmins(sender, LanguageManager.getLocalization("commands.techtree.lock.success.all"), args[1], player.getCommandSenderName());
					} else {
						TechTreeServer.lockComponent(player.getCommandSenderName(), args[1], args[2]);
						notifyAdmins(sender, LanguageManager.getLocalization("commands.techtree.lock.success.one"), args[2], player.getCommandSenderName());
					}
				}
			} else {
				throw new WrongUsageException(getCommandUsage(sender));
			}
		} else {
			throw new WrongUsageException(getCommandUsage(sender));
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(args.length == 1) {
			return getListOfStringsMatchingLastWord(args, new String[]{"pages", "components", "unlock", "lock"});
		} else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("pages")) {
				return null;
			}
			return getListOfStringsFromIterableMatchingLastWord(args, TechTree.pages.keySet());
		} else if(args.length == 3) {
			if(args[1].equals("*")) {
				return Arrays.asList(" *");
			}
			return getListOfStringsFromIterableMatchingLastWord(args, getComponentNameList(TechTree.pages.get(args[1])));
		} else if(args.length == 4) {
			return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return null;
	}

	public List<String> getComponentNameList(List<TechTreeComponent> list) {
		ArrayList<String> nameList = new ArrayList<String>();
		for(TechTreeComponent component : list) {
			nameList.add(component.name);
		}
		return nameList;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 3;
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
