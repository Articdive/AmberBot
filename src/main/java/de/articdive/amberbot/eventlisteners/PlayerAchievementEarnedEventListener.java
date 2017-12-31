package de.articdive.amberbot.eventlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import org.bukkit.Achievement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

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

public class PlayerAchievementEarnedEventListener implements Listener {

	private AmberBot main;

	public PlayerAchievementEarnedEventListener(AmberBot main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerEarnAcheivement(PlayerAchievementAwardedEvent event) { // I hate deprication.
		ChannelHandler ch = main.getChannelHandler();
		for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
			if (channelExtended.shouldSendPlayerAdvancementMessage()) {
				String name = event.getPlayer().getName();
				String achievementname = getAchievementName(event.getAchievement());
				String formattedmessage = main.getMessagesconfig().getFileConfig().getString("discord.eventformats.advancement")
						.replace("{player}", name)
						.replace("{advancement}", achievementname);
				channelExtended.getTextChannel().sendMessage(MessageHandler.message(formattedmessage)).queue();
			}
		}
	}

	private String getAchievementName(Achievement achievement) {
		if (achievement == null) {
			return "";
		}
		return achievement.name();
	}
}
