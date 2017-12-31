package de.articdive.amberbot.discordlisteners.commands;

import de.articdive.amberbot.AmberBot;
import de.articdive.amberbot.messaging.MessageHandler;

import java.util.HashMap;

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

public class CommandHandler {

	public CommandParser cmdparser;
	public HashMap<String, Command> cmds = new HashMap<>();
	private AmberBot main;

	public CommandHandler(AmberBot main) {
		this.main = main;
		cmdparser = new CommandParser(main);
	}

	public void handleCommand(CommandParser.CommandContainer cmd) {

		if (cmds.containsKey(cmd.invoke)) {
			boolean success = cmds.get(cmd.invoke).called(cmd.args, cmd.event);
			if (success) {
				cmds.get(cmd.invoke).action(cmd.args, cmd.event);
				cmds.get(cmd.invoke).executed(success, cmd.event);
			} else {
				cmds.get(cmd.invoke).executed(success, cmd.event);
			}
		} else {
			cmd.event.getTextChannel().sendMessage(MessageHandler.message("This command does not exist!")).queue();
		}
	}

	public CommandParser getCmdparser() {
		return cmdparser;
	}
}
