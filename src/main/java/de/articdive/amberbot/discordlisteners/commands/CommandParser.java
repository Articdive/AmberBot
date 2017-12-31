package de.articdive.amberbot.discordlisteners.commands;

import de.articdive.amberbot.AmberBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

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

public class CommandParser {

	private AmberBot main;

	public CommandParser(AmberBot main) {
		this.main = main;
	}


	public CommandContainer parser(String raw, MessageReceivedEvent event) {

		String commandless = raw.replaceFirst(main.getCommandprefix(), "");
		String[] splitcommandless = commandless.split(" ");
		String invoke = splitcommandless[0];
		ArrayList<String> split = new ArrayList<>(Arrays.asList(splitcommandless));
		String[] args = new String[split.size() - 1];
		split.subList(1, split.size()).toArray(args);

		return new CommandContainer(raw, commandless, splitcommandless, invoke, args, event);

	}

	public class CommandContainer {
		public String rawcommand;
		public String commandless;
		public String[] splitcommandless;
		public String invoke;
		public String[] args;
		public MessageReceivedEvent event;

		CommandContainer(String rawcommand, String commandless, String[] splitcommandless, String invoke, String[] args, MessageReceivedEvent event) {
			this.rawcommand = rawcommand;
			this.commandless = commandless;
			this.splitcommandless = splitcommandless;
			this.invoke = invoke;
			this.args = args;
			this.event = event;
		}
	}
}
