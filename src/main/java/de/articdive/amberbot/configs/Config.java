package de.articdive.amberbot.configs;

import de.articdive.amberbot.AmberBot;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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

public class Config {
	private final AmberBot main;
	private final String filename;
	private File file;
	private FileConfiguration fileconfig;

	public Config(AmberBot main, String filename) {
		this.main = main;
		this.filename = filename + ".yml";
	}

	public void initialize() {
		file = new File(main.getDataFolder(), filename);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			main.saveResource(filename, false);
		}
		fileconfig = new YamlConfiguration();
		try {
			fileconfig.load(file);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public File getFile() {
		return file;
	}

	public FileConfiguration getFileConfig() {
		return fileconfig;
	}

	public String getFileName() {
		return filename;
	}
}
