package de.articdive.amberbot.discordlisteners.commands;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
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

public class ListCommand implements Command {

	private AmberBot main;

	public ListCommand(AmberBot main) {
		this.main = main;
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) { // Permissions e.t.c
		for (TextChannelExtended channelExtended : main.getChannelHandler().getValidTextChannels()) {
			if (channelExtended.getTextChannel().equals(event.getTextChannel())) {
				if (channelExtended.areCommandsEnabled()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) { //Actual Action
		if (main.getServer().getOnlinePlayers().size() == 0){
			event.getTextChannel().sendMessage(MessageHandler.message(main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.list.noplayers"))).queue();
			return;
		}
		String title = main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.list.header");
		String subtitle = main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.list.subheader").replace("{maxplayers}", Integer.toString(Bukkit.getMaxPlayers()))
				.replace("{onlineplayers}", Integer.toString(Bukkit.getOnlinePlayers().size()));
		List<String> listdescription = main.getMessagesconfig().getFileConfig().getStringList("discord.commandmessages.list.list");
		event.getTextChannel().sendMessage(MessageHandler.listmessage(Bukkit.getOnlinePlayers(),title,subtitle,listdescription)).queue();


	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) { // If successful run this!
		if (success) {
			if (main.enableExtraLogging()) {
				Bukkit.getLogger().info("Discord Command 'list' was used");
			}
		}
	}

	@Override
	public String help() {
		return null;
	}
}
