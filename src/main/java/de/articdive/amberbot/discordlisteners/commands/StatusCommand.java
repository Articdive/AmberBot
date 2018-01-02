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

public class StatusCommand implements Command {

	private AmberBot main;

	public StatusCommand(AmberBot main) {
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
		String title = main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.status.header");
		String subtitle = main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.status.subheader").replace("{maxplayers}", Integer.toString(Bukkit.getMaxPlayers()))
				.replace("{onlineplayers}", Integer.toString(Bukkit.getOnlinePlayers().size()));
		List<String> listdescription = main.getMessagesconfig().getFileConfig().getStringList("discord.commandmessages.status.list");
		event.getTextChannel().sendMessage(MessageHandler.statusmessage(title, subtitle, listdescription)).queue();

	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) { // If successful run this!
		if (success) {
			if (main.enableExtraLogging()) {
				Bukkit.getLogger().info("Discord Command 'status' was used");
			}
		}
	}

	@Override
	public String help() {
		return null;
	}
}
