package de.articdive.amberbot.discordlisteners.commands;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;

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

public class TPSCommand implements Command {

	private AmberBot main;

	public TPSCommand(AmberBot main) {
		this.main = main;
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) { // Permissions e.t.c
		for (TextChannelExtended channelExtended : main.getChannelHandler().getValidTextChannels()) {
			if (channelExtended.getTextChannel().equals(event.getTextChannel())) {
				if (channelExtended.areCommandsEnabled()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) { //Actual Action
		event.getTextChannel().sendMessage(MessageHandler.message(main.getMessagesconfig().getFileConfig().getString("discord.commandmessages.tps").replace("{tps}", getTPS()))).queue();

	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) { // If successful run this!
		if (success) {
			if (main.enableExtraLogging()) {
				Bukkit.getLogger().info("Discord Command 'tps' was used");
			}
		}
	}

	@Override
	public String help() {
		return null;
	}

	private String getTPS() {
		StringBuilder sb = new StringBuilder();
		for (double tps : MinecraftServer.getServer().recentTps) {
			sb.append(format(tps));
			sb.append(", ");
		}
		return sb.substring(0, sb.length() - 2);
	}

	private String format(double tps) {
		return (tps > 20.0D ? "*" : "") + Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D);
	}
}
