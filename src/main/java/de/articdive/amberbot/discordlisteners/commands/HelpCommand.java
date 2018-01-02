package de.articdive.amberbot.discordlisteners.commands;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

import java.util.List;

/*
 *     AmberBot - Minecraft Disord Bot on Spigot Platform
 *     Copyright (C) 2018 Articdive (Lukas Mansour)
 *     Contact: articdive@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     You should have received a copy of the GNU General Public License,
 *     along with this program.
 *     This is available at Lisence.MD in the root file of this program.
 *     If not, see <http://www.gnu.org/licenses/>.
 *
 */

public class HelpCommand implements Command {

	private AmberBot main;

	public HelpCommand(AmberBot main) {
		this.main = main;
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) { // Permissions e.t.c
		if (event.getMember().hasPermission(Permission.MESSAGE_WRITE)) {
			for (TextChannelExtended channelExtended : main.getChannelHandler().getValidTextChannels()) {
				if (channelExtended.getTextChannel().equals(event.getTextChannel())) {
					if (channelExtended.areCommandsEnabled()) {
						return true;
					}
				}
			}
		} else {
			event.getTextChannel().sendMessage(MessageHandler.noPermissionsMessage(main)).queue();
			return false;
		}
		return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) { //Actual Action
		String title = main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.help.header");
		String description = main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.help.subheader");
		List<String> listdescription = main.getMessagesconfig().getFileConfig().getStringList("discord.commandmessages.help.list");
		event.getTextChannel().sendMessage(MessageHandler.helpmessage(title, description, listdescription, main.getCommandprefix())).queue();

	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) { // If successful run this!
		if (success) {
			if (main.enableExtraLogging()) {
				Bukkit.getLogger().info("Discord Command 'help' was used");
			}
		}
	}

	@Override
	public String help() {
		return null;
	}
}
