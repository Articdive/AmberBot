package de.articdive.amberbot.channels;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.configs.Config;
import de.articdive.amberbot.objects.TextChannelExtended;
import de.articdive.amberbot.objects.VoiceChannelExtended;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

public class ChannelHandler {
	private List<TextChannelExtended> textChannels;
	private List<VoiceChannelExtended> voiceChannels;
	private AmberBot main;
	private Config channelsConfig;

	public ChannelHandler(AmberBot main, Config channelsConfig) {
		this.main = main;
		this.channelsConfig = channelsConfig;
	}

	public void initialize() {
		FileConfiguration config = channelsConfig.getFileConfig();
		Set<String> textchannels = config.getConfigurationSection("channels.text").getKeys(false);
		Set<String> voicechannels = config.getConfigurationSection("channels.voice").getKeys(false);
		List<TextChannelExtended> textChannels = new ArrayList<>();
		List<VoiceChannelExtended> voiceChannels = new ArrayList<>();
		for (String textchannelname : textchannels) {
			for (TextChannel channel : main.getJDA().getTextChannelsByName(textchannelname, true)) {
				TextChannelExtended channelExtended = new TextChannelExtended(channel, channel.getName(), config);
				textChannels.add(channelExtended);
			}
		}
		if (textChannels != null && !textChannels.isEmpty()) { // If not empty then
			this.textChannels = textChannels;
		}
		for (String voicechannelname : voicechannels) {
			for (VoiceChannel channel : main.getJDA().getVoiceChannelByName(voicechannelname, true)) {
				VoiceChannelExtended channelExtended = new VoiceChannelExtended(channel, channel.getName(), config);
				voiceChannels.add(channelExtended);
			}
		}
		if (voiceChannels != null && !voiceChannels.isEmpty()) {
			this.voiceChannels = voiceChannels;
		}
	}


	public List<TextChannelExtended> getValidTextChannels() {
		return textChannels;
	}

	public List<VoiceChannelExtended> getValidVoiceChannels() {
		return voiceChannels;
	}
}
