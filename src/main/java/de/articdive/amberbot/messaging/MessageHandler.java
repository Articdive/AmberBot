package de.articdive.amberbot.messaging;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
		builder.setContent(StringEscapeUtils.unescapeJava(msg));
		return builder.build();
	}

	public static Message headmessage(Player player, String msg) {
		UUID uuid = player.getUniqueId();
		MessageBuilder builder = new MessageBuilder();
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setAuthor(StringEscapeUtils.unescapeJava(msg.replaceAll("\\*", "").replaceAll("~~", "").replaceAll("__", "")), null, "https://visage.surgeplay.com/head/512/" + uuid.toString());
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
				embedBuilder.addField(split[0], StringEscapeUtils.unescapeJava(split[1].replace("{players}", getPlayerNames(players))), true);
			}
		}
		builder.setEmbed(embedBuilder.build());
		return builder.build();
	}

	public static MessageEmbed statusmessage(String title, String description, List<String> listformat) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle(title);
		embedBuilder.setDescription(description);
		for (String lineformat : listformat) {
			if (lineformat.contains("|")) {
				String[] split = lineformat.split("\\|");
				embedBuilder.addField(split[0],
						StringEscapeUtils.unescapeJava(split[1].replace("{entities}", String.valueOf(getEntities()))
								.replace("{tps}", getTPS())
								.replace("{maxram}", String.valueOf(Runtime.getRuntime().maxMemory() / 1024L / 1024L))
								.replace("{version}", Bukkit.getVersion())
								.replace("{freeram}", String.valueOf(Runtime.getRuntime().freeMemory() / 1024L / 1024L))
								.replace("{usedram}", String.valueOf((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1024L / 1024L))), true);
			}
		}
		return embedBuilder.build();
	}

	private static String getPlayerNames(Collection<? extends Player> players) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Player player : players) {
			stringBuilder.append(player.getName()).append(", ");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 2);
	}

	private static int getEntities() {
		int i = 0;
		for (final World world : Bukkit.getServer().getWorlds()) {
			i = world.getEntities().size();
		}
		return i;
	}

	private static String getTPS() {
		StringBuilder sb = new StringBuilder();
		for (double tps : MinecraftServer.getServer().recentTps) {
			sb.append(format(tps));
			sb.append(", ");
		}
		return sb.substring(0, sb.length() - 2);
	}

	private static String format(double tps) {
		return (tps > 20.0D ? "*" : "") + Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D);
	}
}
