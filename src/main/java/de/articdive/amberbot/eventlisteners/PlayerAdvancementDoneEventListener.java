package de.articdive.amberbot.eventlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

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

public class PlayerAdvancementDoneEventListener implements Listener {

	private AmberBot main;

	public PlayerAdvancementDoneEventListener(AmberBot main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerFinishAdvancement(PlayerAdvancementDoneEvent event) {
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerAdvancementMessage()) {
				String name = event.getPlayer().getName();
				String advancementname = getAdvancementName(event.getAdvancement());
				String formattedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.advancement")
						.replace("{player}", name)
						.replace("{advancement}", advancementname);
				channelExtended.getTextChannel().sendMessage(MessageHandler.message(formattedmessage)).queue();
			}
		}
	}

	private String getAdvancementName(Advancement advancement) {
		if (advancement == null) {
			return "";
		}
		String key = advancement.getKey().getKey();
		String[] keyparts = key.split("/");
		return keyparts[1].replaceAll("_", " ");
	}
}
