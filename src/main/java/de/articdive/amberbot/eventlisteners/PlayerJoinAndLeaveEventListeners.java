package de.articdive.amberbot.eventlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

public class PlayerJoinAndLeaveEventListeners implements Listener {
	private AmberBot main;

	public PlayerJoinAndLeaveEventListeners(AmberBot main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerJoinMessage()) {
				String name = event.getPlayer().getName();
				String displayname = event.getPlayer().getDisplayName();
				String formatedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.playerjoin")
						.replace("{player}", name)
						.replace("{displayname}", displayname);
				if (formatedmessage.contains("{playerhead}")) {
					channelExtended.getTextChannel().sendMessage(MessageHandler.headmessage(event.getPlayer(), formatedmessage.replace("{playerhead}", ""))).queue();
				} else {
					channelExtended.getTextChannel().sendMessage(MessageHandler.message(formatedmessage)).queue();
				}
			}
		}

	}

	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event) {
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerLeaveMessage()) {
				String name = event.getPlayer().getName();
				String displayname = event.getPlayer().getDisplayName();
				String formatedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.playerleave")
						.replace("{player}", name)
						.replace("{displayname}", displayname);
				if (formatedmessage.contains("{playerhead}")) {
					channelExtended.getTextChannel().sendMessage(MessageHandler.headmessage(event.getPlayer(), formatedmessage.replace("{playerhead}", ""))).queue();
				} else {
					channelExtended.getTextChannel().sendMessage(MessageHandler.message(formatedmessage)).queue();
				}
			}
		}
	}
}
