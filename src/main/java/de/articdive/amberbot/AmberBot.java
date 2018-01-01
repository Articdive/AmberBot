package de.articdive.amberbot;

import de.articdive.amberbot.channels.ChannelHandler;
import de.articdive.amberbot.configs.Config;
import de.articdive.amberbot.discordlisteners.CommandListener;
import de.articdive.amberbot.discordlisteners.DiscordChatToMinecraftListener;
import de.articdive.amberbot.discordlisteners.commands.ClearCommand;
import de.articdive.amberbot.discordlisteners.commands.CommandHandler;
import de.articdive.amberbot.discordlisteners.commands.ListCommand;
import de.articdive.amberbot.discordlisteners.commands.PingCommand;
import de.articdive.amberbot.discordlisteners.commands.StatusCommand;
import de.articdive.amberbot.discordlisteners.commands.TPSCommand;
import de.articdive.amberbot.eventlisteners.AsyncPlayerChatEventListener;
import de.articdive.amberbot.eventlisteners.CommandEventListeners;
import de.articdive.amberbot.eventlisteners.PlayerAchievementEarnedEventListener;
import de.articdive.amberbot.eventlisteners.PlayerAdvancementDoneEventListener;
import de.articdive.amberbot.eventlisteners.PlayerDeathEventListener;
import de.articdive.amberbot.eventlisteners.PlayerJoinAndLeaveEventListeners;
import de.articdive.amberbot.eventlisteners.PlayerKickAndBanEventListeners;
import de.articdive.amberbot.messaging.MessageHandler;
import de.articdive.amberbot.objects.TextChannelExtended;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

/*
 *     AmberBot - Minecraft Disord Bot on Spigot Platform
 *     Copyright (C) 2018 Articdive (Lukas Mansour)
 *     Contact: articdive@gmail.com
 *     Note: This copyright is ONLY valid for the code found under the packages: de.articdive(...)
 *     I do not have any right nor am I trying to license ANY of the used dependencies.
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

public final class AmberBot extends JavaPlugin {
	private static Plugin plugin;
	private static String commandprefix;
	private Config messagesconfig;
	private Config channelsconfig;
	private JDA jda;
	private ChannelHandler channelHandler;
	private CommandHandler commandHandler;
	private boolean extraLogging;

	public AmberBot() {
		this.messagesconfig = new Config(this, "messages");
		this.channelsconfig = new Config(this, "channels");
	}

	public static Plugin getPlugin() {
		return plugin;
	}


	@Override
	public void onEnable() {
		plugin = this;
		this.saveDefaultConfig(); //We need a config.yml straight away or else ERRORS!
		registerConfigs(messagesconfig, channelsconfig);
		setupCommands();
		registerBot(getConfig().getString("Token"), getConfig().getString("Game"));
		extraLogging = getConfig().getBoolean("log-discord-commands-to-mc-log");
		commandprefix = getConfig().getString("Command-Prefix");
		if (commandprefix.isEmpty() || commandprefix == null) {
			commandprefix = "!";
		}
		if (jda == null) {
			// I guess we should stop the code.
			return;
		}
		setupChannels(); // Channels first then Events.
		registerEvents(plugin.getServer().getVersion());

		for (TextChannelExtended channel : channelHandler.getValidTextChannels()) {
			if (channel.shouldSendStartServerMessage()) {
				String format = getMessagesconfig().getFileConfig().getString("discord.eventformats.startserver");
				channel.getTextChannel().sendMessage(MessageHandler.message(format)).queue();
			}
		}

	}

	private void setupCommands() {
		commandHandler = new CommandHandler(this);
		commandHandler.cmds.put("ping", new PingCommand(this));
		commandHandler.cmds.put("list", new ListCommand(this));
		commandHandler.cmds.put("tps", new TPSCommand(this));
		commandHandler.cmds.put("clear", new ClearCommand(this));
		commandHandler.cmds.put("status", new StatusCommand(this));

	}

	@Override
	public void onDisable() {
		if (jda != null) {
			for (TextChannelExtended channel : channelHandler.getValidTextChannels()) {
				if (channel.shouldSendStopServerMessage()) {
					String format = getMessagesconfig().getFileConfig().getString("discord.eventformats.stopserver");
					channel.getTextChannel().sendMessage(MessageHandler.message(format)).queue();
				}
			}
			jda.shutdown();
		}
		plugin = null;
	}

	private void registerConfigs(Config... config) {
		for (Config cnf : config) {
			cnf.initialize();
		}
	}

	private void registerBot(String token, String gamename) {
		try {
			this.jda = new JDABuilder(AccountType.BOT)
					.setToken(token)
					.setGame(Game.of(Game.GameType.DEFAULT, gamename))
					.setAutoReconnect(true)
					.addEventListener(new DiscordChatToMinecraftListener(this), new CommandListener(this)).buildBlocking();
		} catch (LoginException e) {
			this.getLogger().warning("The entered Token is not valid! Disabling AmberBot...");
			this.getServer().getPluginManager().disablePlugin(this);
		} catch (InterruptedException | RateLimitedException e) {
			this.getServer().getPluginManager().disablePlugin(this);
		}
	}

	private void setupChannels() {
		channelHandler = new ChannelHandler(this, channelsconfig);
		channelHandler.initialize();
	}

	private void registerEvents(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	private void registerEvents(String version) {
		registerEvents(
				new AsyncPlayerChatEventListener(this),
				new PlayerJoinAndLeaveEventListeners(this),
				new PlayerKickAndBanEventListeners(this),
				new PlayerDeathEventListener(this),
				new CommandEventListeners(this)
		);
		if (version.contains("1.12") || version.contains("1.13")) { // 1.12 and over.
			registerEvents(new PlayerAdvancementDoneEventListener(this));
		} else { // 1.11 and lower
			registerEvents(new PlayerAchievementEarnedEventListener(this));
		}
	}

	public String getCommandprefix() {
		return commandprefix;
	}

	public Config getMessagesconfig() {
		return messagesconfig;
	}

	public Config getChannelsconfig() {
		return channelsconfig;
	}

	public ChannelHandler getChannelHandler() {
		return channelHandler;
	}

	public boolean enableExtraLogging() {
		return extraLogging;
	}

	public JDA getJDA() {
		return jda;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
}
