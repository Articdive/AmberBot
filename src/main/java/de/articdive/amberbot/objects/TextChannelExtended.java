package de.articdive.amberbot.objects;

import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.configuration.file.FileConfiguration;

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

public class TextChannelExtended {
	private TextChannel textChannel;
	private String name;
	private FileConfiguration configuration;

	public TextChannelExtended(TextChannel channel, String name, FileConfiguration configuration) {
		this.textChannel = channel;
		this.name = name;
		this.configuration = configuration;
	}

	public boolean areCommandsEnabled() {
		return configuration.getBoolean("channels.text." + name + ".discordcommands");
	}

	public boolean isDiscordToMinecraftChatEnabled() {
		return configuration.getBoolean("channels.text." + name + ".discordchattomc");
	}

	public boolean shouldSendStartServerMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.startserver");
	}

	public boolean shouldSendStopServerMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.stopserver");
	}

	public boolean shouldSendPlayerKickMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.kickplayer");
	}

	public boolean shouldSendPlayerBanMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.banplayer");
	}

	public boolean shouldSendPlayerJoinMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.playerjoin");
	}

	public boolean shouldSendPlayerLeaveMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.playerleave");
	}

	public boolean shouldSendPlayerAdvancementMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.advancement");
	}

	public boolean shouldSendPlayerChatMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.playerchat");
	}

	public boolean shouldSendPlayerDeathMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.playerdeath");
	}

	public boolean shouldSendPlayerCommandMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.playercommand");
	}

	public boolean shouldSendConsoleCommandMessage() {
		return configuration.getBoolean("channels.text." + name + ".eventmessages.consolecommand");
	}

	public TextChannel getTextChannel() {
		return textChannel;
	}

	public FileConfiguration getConfiguration() {
		return configuration;
	}

	public String getName() {
		return name;
	}
}
