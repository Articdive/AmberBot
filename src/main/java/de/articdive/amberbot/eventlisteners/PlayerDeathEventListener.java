package de.articdive.amberbot.eventlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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

public class PlayerDeathEventListener implements Listener {

	private AmberBot main;

	public PlayerDeathEventListener(AmberBot main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerDeathMessage()) {
				if (event.getEntity() instanceof Player) {
					String message = event.getDeathMessage();
					String formatedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.playerdeath").replace("{message}", message);
					channelExtended.getTextChannel().sendMessage(MessageHandler.message(formatedmessage)).queue();
				}
			}
		}
	}
}