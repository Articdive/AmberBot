package de.articdive.amberbot.discordlisteners.commands;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

import java.util.List;

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

public class ClearCommand implements Command {

	private AmberBot main;

	public ClearCommand(AmberBot main) {
		this.main = main;
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) { // Permissions e.t.c
		if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
			if (args.length > 0) {
				for (TextChannelExtended channelExtended : main.getChannelHandler().getValidTextChannels()) {
					if (channelExtended.getTextChannel().equals(event.getTextChannel())) {
						if (channelExtended.areCommandsEnabled()) {
							return true;
						}
					}
				}
			} else {
				event.getTextChannel().sendMessage(MessageHandler.message("Please enter valid subcommand do " + main.getCommandprefix() + "help for more information")).queue();
				return false;
			}
		} else {
			event.getTextChannel().sendMessage(MessageHandler.noPermissionsMessage(main)).queue();
			return false;
		}
		return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) { //Actual Action
		MessageHistory mh = event.getTextChannel().getHistory();
		event.getMessage().delete().queue();
		int amountofmessages;
		try {
			amountofmessages = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			event.getTextChannel().sendMessage(args[0] + "is not a valid Integer, or use all to clear the entire channel").queue();
			return;
		}
		if (amountofmessages > 100) {
			amountofmessages = 100;
		}
		if (amountofmessages < 2) { // No messages xD. ok fine.
			return;
		}
		List<Message> messages = mh.retrievePast(amountofmessages).complete();
		event.getTextChannel().deleteMessages(messages).queue();

	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) { // If successful run this!
		if (success) {
			if (main.enableExtraLogging()) {
				Bukkit.getLogger().info("Discord Command 'clear' was used");
			}
		}
	}

	@Override
	public String help() {
		return null;
	}
}
