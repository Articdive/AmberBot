package de.articdive.amberbot.objects;

import net.dv8tion.jda.core.entities.VoiceChannel;
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

public class VoiceChannelExtended {
	private VoiceChannel voiceChannel;
	private String name;
	private FileConfiguration configuration;

	public VoiceChannelExtended(VoiceChannel channel, String name, FileConfiguration configuration) {
		this.configuration = configuration;
		this.voiceChannel = channel;
		this.name = name;
	}

	public FileConfiguration getConfiguration() {
		return configuration;
	}

	public String getName() {
		return name;
	}

	public VoiceChannel getVoiceChannel() {
		return voiceChannel;
	}
}
