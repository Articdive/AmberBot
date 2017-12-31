package de.articdive.amberbot.eventlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

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

public class PlayerKickAndBanEventListeners implements Listener {
	private AmberBot main;

	public PlayerKickAndBanEventListeners(AmberBot main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerKickOrBanEvent(PlayerKickEvent event) {
		if (event.getPlayer().isBanned()) {
			onBan(event);
			return;
		}
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerKickMessage()) {
				String name = event.getPlayer().getName();
				String displayname = event.getPlayer().getDisplayName();
				String formatedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.kickplayer")
						.replace("{player}", name)
						.replace("{displayname}", displayname)
						.replace("{reason}", event.getReason());
				if (formatedmessage.contains("{playerhead}")) {
					channelExtended.getTextChannel().sendMessage(MessageHandler.headmessage(event.getPlayer(), formatedmessage.replace("{playerhead}", ""))).queue();
				} else {
					channelExtended.getTextChannel().sendMessage(MessageHandler.message(formatedmessage)).queue();
				}
			}
		}

	}

	public void onBan(PlayerKickEvent event) {
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerBanMessage()) {
				String name = event.getPlayer().getName();
				String displayname = event.getPlayer().getDisplayName();
				String formatedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.banplayer")
						.replace("{player}", name)
						.replace("{displayname}", displayname)
						.replace("{reason}", event.getReason());
				if (formatedmessage.contains("{playerhead}")) {
					channelExtended.getTextChannel().sendMessage(MessageHandler.headmessage(event.getPlayer(), formatedmessage.replace("{playerhead}", ""))).queue();
				} else {
					channelExtended.getTextChannel().sendMessage(MessageHandler.message(formatedmessage)).queue();
				}
			}
		}
	}
}
