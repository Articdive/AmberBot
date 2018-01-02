

package de.articdive.amberbot.commands;


import de.articdive.amberbot.AmberBot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 *
 *       AmberBot - Minecraft Disord Bot on Spigot Platform
 *       Copyright (C) 2018 Articdive (Lukas Mansour)
 *       Contact: articdive@gmail.com
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       any later version.
 *
 *       You should have received a copy of the GNU General Public License,
 *       along with this program.
 *       This is available at Lisence.MD in the root file of this program.
 *       If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

public class AmberBotCommandHandler implements CommandExecutor {

	private AmberBot main;

	public AmberBotCommandHandler(AmberBot main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("amberbot") || command.getName().equalsIgnoreCase("amberbot")) {
			if (args.length == 0) {
				cs.sendMessage(getHelpMenu(label));
			}

			if (cs instanceof Player) {
				return onPlayerCommand((Player) cs, label, args);
			} else {
				return onConsoleCommand(cs, label, args);
			}
		} else {
			return false;
		}
	}

	private boolean onConsoleCommand(CommandSender cs, String label, String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (cs.hasPermission("amberbot.help")) {
					cs.sendMessage(getHelpMenu(label));
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (cs.hasPermission("amberbot.reload")) {
					cs.sendMessage(ChatColor.GREEN + "AmberBot is reloading...");
					main.reload();
					cs.sendMessage(ChatColor.GREEN + "AmberBot reloaded!");
					return true;
				}
			}
		}
		return false;
	}

	private boolean onPlayerCommand(Player player, String label, String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("amberbot.help")) {
					player.sendMessage(getHelpMenu(label));
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (player.hasPermission("amberbot.reload")) {
					player.sendMessage(ChatColor.GREEN + "AmberBot is reloading...");
					main.reload();
					player.sendMessage(ChatColor.GREEN + "AmberBot reloaded!");
					return true;
				}

			}
		}

		return false;
	}

	private String[] getHelpMenu(String label) {
		List<String> menu = new ArrayList<>();
		menu.add(ChatColor.GOLD + "===" + ChatColor.RED + " AmberBot " + ChatColor.GOLD + "===");
		menu.add(ChatColor.YELLOW + "/" + label + " help - Shows this menu");
		menu.add(ChatColor.YELLOW + "/" + label + " reload - Reloads AmberBot");
		return menu.toArray(new String[0]);

	}
}
