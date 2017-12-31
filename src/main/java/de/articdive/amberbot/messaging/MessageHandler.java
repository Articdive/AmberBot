package de.articdive.amberbot.messaging;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

public class MessageHandler {

	public static Message message(String msg) { // For Standard Messages
		MessageBuilder builder = new MessageBuilder();
		builder.setContent(msg);
		return builder.build();
	}

	public static Message headmessage(Player player, String msg) {
		UUID uuid = player.getUniqueId();
		MessageBuilder builder = new MessageBuilder();
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setAuthor(msg.replaceAll("\\*", "").replaceAll("~~", "").replaceAll("__", ""), null, "https://visage.surgeplay.com/head/512/" + uuid.toString());
		// Bold is not yet supporte in Author Names.
		builder.setEmbed(embedBuilder.build());
		return builder.build();
	}

	public static Message listmessage(Collection<? extends Player> players, String title, String description, List<String> listformat) {
		MessageBuilder builder = new MessageBuilder();
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle(title);
		embedBuilder.setDescription(description);
		for (String lineformat : listformat) {
			if (lineformat.contains("|")) {
				String[] split = lineformat.split("\\|");
				embedBuilder.addField(split[0], split[1].replace("{players}", getPlayerNames(players)), true);
			} else {
				embedBuilder.addField(lineformat.replace("{players}", getPlayerNames(players)), null, true);
			}
		}
		builder.setEmbed(embedBuilder.build());
		return builder.build();
	}

	public static String getPlayerNames(Collection<? extends Player> players) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Player player : players) {
			stringBuilder.append(player.getName()).append(", ");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 2);
	}
}
