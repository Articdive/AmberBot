package de.articdive.amberbot.discordlisteners;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.discordlisteners.commands.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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

public class CommandListener extends ListenerAdapter {

	private AmberBot main;
	private CommandHandler handler;

	public CommandListener(AmberBot main) {
		this.main = main;
		handler = main.getCommandHandler();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		if (event.getMessage().getContentDisplay().startsWith(main.getCommandprefix()) && !(event.getAuthor().getId().equals(main.getJDA().getSelfUser().getId()))) {
			handler.handleCommand(handler.cmdparser.parser(event.getMessage().getContentDisplay(), event));
		}
	}

}
