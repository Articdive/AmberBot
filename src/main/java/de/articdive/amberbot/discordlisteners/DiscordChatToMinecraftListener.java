package de.articdive.amberbot.discordlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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

public class DiscordChatToMinecraftListener extends ListenerAdapter {

	private AmberBot main;

	public DiscordChatToMinecraftListener(AmberBot main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		ChannelHandler ch = main.getChannelHandler();
		if (event.getAuthor() == main.getJDA().getSelfUser()) {
			return;
		}
		if (!event.getMessage().getContentDisplay().startsWith(main.getCommandprefix())) {
			for (TextChannelExtended channelExtended : ch.getValidTextChannels()) {
				if (channelExtended.getTextChannel().equals(event.getChannel())) {
					if (channelExtended.isDiscordToMinecraftChatEnabled()) {
						String sender = event.getAuthor().getName();
						String message = event.getMessage().getContentDisplay();
						String role = getRole(event.getMember());
						Bukkit.broadcastMessage(role);
						String formattedmessage = main.getMessagesconfig().getFileConfig().getString("minecraft.discordchattomc")
								.replace("{username}", sender)
								.replace("{role}", role)
								.replace("{message}", message);
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', formattedmessage));
					}
				}
			}
		}
	}

	private String getRole(Member member) {
		if (member.getRoles().size() == 0 || member.getRoles() == null || member.getRoles().isEmpty()) {
			return main.getMessagesconfig().getFileConfig().getString("minecraft.discordchattomcnorole");
		}
		if (member.getRoles().get(0) == null) {
			return main.getMessagesconfig().getFileConfig().getString("minecraft.discordchattomcnorole");
		}
		return member.getRoles().get(0).getName();
	}
}
