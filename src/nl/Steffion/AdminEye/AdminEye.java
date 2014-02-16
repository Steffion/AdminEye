package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.Config;
import nl.Steffion.AdminEye.StefsAPI.PermissionType;
import nl.Steffion.AdminEye.Commands.BanCommand;
import nl.Steffion.AdminEye.Commands.BringCommand;
import nl.Steffion.AdminEye.Commands.DeopCommand;
import nl.Steffion.AdminEye.Commands.FlyCommand;
import nl.Steffion.AdminEye.Commands.FreezeCommand;
import nl.Steffion.AdminEye.Commands.GamemodeCommand;
import nl.Steffion.AdminEye.Commands.GotoCommand;
import nl.Steffion.AdminEye.Commands.HPCommand;
import nl.Steffion.AdminEye.Commands.HungerCommand;
import nl.Steffion.AdminEye.Commands.KickCommand;
import nl.Steffion.AdminEye.Commands.MuteCommand;
import nl.Steffion.AdminEye.Commands.OpCommand;
import nl.Steffion.AdminEye.Commands.SlapCommand;
import nl.Steffion.AdminEye.Commands.SlayCommand;
import nl.Steffion.AdminEye.Commands.UnbanCommand;
import nl.Steffion.AdminEye.Commands.UnflyCommand;
import nl.Steffion.AdminEye.Commands.UnfreezeCommand;
import nl.Steffion.AdminEye.Commands.UnmuteCommand;
import nl.Steffion.AdminEye.Commands.VotekickCommand;
import nl.Steffion.AdminEye.Commands.VotenoCommand;
import nl.Steffion.AdminEye.Commands.VoteyesCommand;
import nl.Steffion.AdminEye.Listeners.OnAsyncPlayerChatEvent;
import nl.Steffion.AdminEye.Listeners.OnPlayerJoinEvent;
import nl.Steffion.AdminEye.Listeners.OnPlayerLoginEvent;
import nl.Steffion.AdminEye.Listeners.OnPlayerMoveEvent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminEye extends JavaPlugin implements Listener {

	public static AdminEye plugin;
	public static PluginDescriptionFile pdfFile;
	public static Config config;
	public static Config messages;

	public static String mainPermission;

	public void onEnable() {
		plugin = this;
		pdfFile = getDescription();

		mainPermission = pdfFile.getName().toLowerCase() + ".";

		config = StefsAPI.ConfigHandler.createConfig("config");
		messages = StefsAPI.ConfigHandler.createConfig("messages");

		getServer().getPluginManager().registerEvents(
				new OnAsyncPlayerChatEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerMoveEvent(),
				this);
		getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(),
				this);
		getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(),
				this);

		StefsAPI.ConfigHandler.addDefault(config, "chat.tag",
				"[" + pdfFile.getName() + "] ");
		StefsAPI.ConfigHandler.addDefault(config, "chat.normal", "&b");
		StefsAPI.ConfigHandler.addDefault(config, "chat.warning", "&6");
		StefsAPI.ConfigHandler.addDefault(config, "chat.error", "&c");
		StefsAPI.ConfigHandler.addDefault(config, "chat.argument", "&e");
		StefsAPI.ConfigHandler.addDefault(config, "chat.header", "&9");
		StefsAPI.ConfigHandler.addDefault(config, "chat.header_high",
				"%H_______.[ %A%title%H ]._______");
		StefsAPI.ConfigHandler
				.addDefault(config, "chat.someone", "&3(Someone)");
		StefsAPI.ConfigHandler
				.addDefault(config, "chat.console", "&7(Console)");
		StefsAPI.ConfigHandler.addDefault(config, "chat.system", "&7(System)");
		StefsAPI.ConfigHandler
				.addDefault(config, "chat.everyone", "&3Everyone");

		StefsAPI.ConfigHandler.addDefault(config, "metrics.enabled", true);

		// AdminEye default commands.
		StefsAPI.CommandHandler.registerCommand(pdfFile.getName(), null, null,
				"info", "Displays the plugin's info.", PermissionType.ALL,
				new BasicCommands().new InfoCommand(), null);
		StefsAPI.CommandHandler.registerCommand(pdfFile.getName(),
				new String[] { "info" }, new String[] { "i" }, "info",
				"Displays the plugin's info.", PermissionType.ALL,
				new BasicCommands().new InfoCommand(), pdfFile.getName()
						+ " [info/i]");
		StefsAPI.CommandHandler.registerCommand(pdfFile.getName(),
				new String[] { "help" }, new String[] { "h" }, "help",
				"Shows a list of commands.", PermissionType.ALL,
				new BasicCommands().new HelpCommand(), pdfFile.getName()
						+ " <help/h> [page number]");
		StefsAPI.CommandHandler.registerCommand(pdfFile.getName(),
				new String[] { "reload" }, new String[] { "r" }, "reload",
				"Reloads all configs.", PermissionType.MODERATOR,
				new BasicCommands().new ReloadCommand(), pdfFile.getName()
						+ " <reload/r>");

		// AdminEye commands.
		StefsAPI.CommandHandler.registerCommand("ban", new String[] { "*" },
				new String[] { "*" }, "ban", "Bans a player.",
				PermissionType.ADMIN, new BanCommand(),
				"ban <player name> <time> [reason]");
		StefsAPI.CommandHandler.registerCommand("bring", new String[] { "*" },
				new String[] { "*" }, "bring",
				"Teleports a player or players to you.",
				PermissionType.MODERATOR, new BringCommand(),
				"bring <player name>");
		StefsAPI.CommandHandler.registerCommand("deop", new String[] { "*" },
				new String[] { "*" }, "deop", "DeOPs a player.",
				PermissionType.OP, new DeopCommand(), "deop <player name>");
		StefsAPI.CommandHandler.registerCommand("hunger", new String[] { "*" },
				new String[] { "*" }, "hunger", "Sets a players hunger.",
				PermissionType.MODERATOR, new HungerCommand(),
				"hunger <player name> <amount>");
		StefsAPI.CommandHandler
				.registerCommand("fly", new String[] { "*" },
						new String[] { "*" }, "fly",
						"Makes a player able to fly.",
						PermissionType.MODERATOR, new FlyCommand(),
						"fly <player name>");
		StefsAPI.CommandHandler.registerCommand("freeze", new String[] { "*" },
				new String[] { "*" }, "freeze",
				"Prevents a player from moving.", PermissionType.MODERATOR,
				new FreezeCommand(), "freeze <player name>");
		StefsAPI.CommandHandler.registerCommand("gamemode",
				new String[] { "*" }, new String[] { "*" }, "gamemode",
				"Sets a players gamemode.", PermissionType.MODERATOR,
				new GamemodeCommand(), "gamemode <player name> <gamemode>");
		StefsAPI.CommandHandler.registerCommand("goto", new String[] { "*" },
				new String[] { "*" }, "goto",
				"Teleports the sender to a player.", PermissionType.MODERATOR,
				new GotoCommand(), "goto <player name>");
		StefsAPI.CommandHandler.registerCommand("hp", new String[] { "*" },
				new String[] { "*" }, "hp", "Sets a players health.",
				PermissionType.MODERATOR, new HPCommand(),
				"hp <player name> [amount of hp]");
		StefsAPI.CommandHandler.registerCommand("kick", new String[] { "*" },
				new String[] { "*" }, "kick", "Kicks a player.",
				PermissionType.MODERATOR, new KickCommand(),
				"kick <player name> [reason]");
		StefsAPI.CommandHandler.registerCommand("mute", new String[] { "*" },
				new String[] { "*" }, "mute",
				"Prevents a player from chatting.", PermissionType.MODERATOR,
				new MuteCommand(), "mute <player name> [time]");
		StefsAPI.CommandHandler.registerCommand("op", new String[] { "*" },
				new String[] { "*" }, "op", "OPs a player.", PermissionType.OP,
				new OpCommand(), "op <player name>");
		StefsAPI.CommandHandler.registerCommand("slap", new String[] { "*" },
				new String[] { "*" }, "slap", "Slaps a player into the air.",
				PermissionType.MODERATOR, new SlapCommand(),
				"slap <player name>");
		StefsAPI.CommandHandler.registerCommand("slay", new String[] { "*" },
				new String[] { "*" }, "slay", "Kills a player.",
				PermissionType.MODERATOR, new SlayCommand(),
				"slay <player name>");
		StefsAPI.CommandHandler.registerCommand("unfreeze",
				new String[] { "*" }, new String[] { "*" }, "unfreeze",
				"Lets a player move again.", PermissionType.MODERATOR,
				new UnfreezeCommand(), "unfreeze <player name>");
		StefsAPI.CommandHandler.registerCommand("unmute", new String[] { "*" },
				new String[] { "*" }, "unmute", "Unmutes a player.",
				PermissionType.MODERATOR, new UnmuteCommand(),
				"unmute <player name>");
		StefsAPI.CommandHandler.registerCommand("unfly", new String[] { "*" },
				new String[] { "*" }, "unfly", "Stops a player from flying.",
				PermissionType.MODERATOR, new UnflyCommand(),
				"unfly <player name>");
		StefsAPI.CommandHandler.registerCommand("unban", new String[] { "*" },
				new String[] { "*" }, "unban", "Unbans a player.",
				PermissionType.MODERATOR, new UnbanCommand(),
				"unban <player name>");
		StefsAPI.CommandHandler.registerCommand("votekick",
				new String[] { "*" }, new String[] { "*" }, "votekick",
				"Votekicks a player.", PermissionType.MODERATOR,
				new VotekickCommand(), "votekick <player name> [reason]");
		StefsAPI.CommandHandler.registerCommand("vote_no", null, null,
				"vote_no", "Vote no on a vote.", PermissionType.PLAYER,
				new VotenoCommand(), null);
		StefsAPI.CommandHandler.registerCommand("vote_yes", null, null,
				"vote_yes", "Vote yes on a vote.", PermissionType.PLAYER,
				new VoteyesCommand(), null);

		// AdminEye's broadcast enabled.
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.ban", true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.bring",
				true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.deop", true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.feed", true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.fly", true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.freeze",
				true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.gamemode",
				true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.goto", true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.hp", true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.kick", true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.mute", true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.op", true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.slap", true);
		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.slay", true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.unfly",
				true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.unfreeze",
				true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.unmuted",
				true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.unban",
				true);
		StefsAPI.ConfigHandler.addDefault(config, "broadcastEnabled.votekick",
				true);

		// Normal qualified messages.
		StefsAPI.ConfigHandler.addDefault(messages, "normal.reloadedConfigs",
				"%TAG&aReloaded configs!");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.broadcast",
				"%TAG%someone%N %message%N.");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.noReasonGiven",
				"%ANo reason given");
		StefsAPI.ConfigHandler
				.addDefault(messages, "normal.banned",
						"banned %A%playernames%Nwith the reason: %A%reason%N. Ban length:%A%time");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.banreason",
				"%TAG\n%NYou've been banned! Reason: \n%A");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.banLength",
				"%NBan length:");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.days", " %Nday(s)");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.hours",
				" %Nhour(s)");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.minutes",
				" %Nminute(s)");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.seconds",
				" %Nsecond(s)");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.permanently",
				" %E&lPermanently");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.bring",
				"has brought %A%playernames%Nto them");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.deop",
				"made %A%playernames%Nde-opped");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.fed",
				"set the food of %A%playernames%Nto %A%amount");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.fly",
				"made %A%playernames%Nable to fly");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.freeze",
				"has made %A%playernames%Nfrozen");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.setgamemode",
				"set the gamemode of %A%playernames%Nto %A%gamemode");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.went",
				"went to %A%playernames");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.sethealth",
				"set the health of %A%playernames%Nto %A%amount");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.kicked",
				"kicked %A%playernames%Nwith the reason: %A%reason");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.kickreason",
				"%TAG\n%NYou've been kicked! Reason: \n%A");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.muted",
				"made %A%playernames%Nmuted. Mute length:%A%time");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.op",
				"made %A%playernames%Nan operator");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.slapped",
				"slapped %A%playernames%Nin their face");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.slayed",
				"slayed %A%playernames%Nto death");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.unfly",
				"made %A%playernames%Nunable to fly");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.unfreeze",
				"made %A%playernames%Nunfrozen");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.unmuted",
				"made %A%playernames%Nunmuted");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.unbanned",
				"made %A%playernames%Nunbanned");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.votekicked",
				"votekicked %A%playernames%Nwith the reason: %A%reason");
		StefsAPI.ConfigHandler
				.addDefault(
						messages,
						"normal.votekick.layout1",
						"%A%playerName %Ncalled a votekick&uon %A%votekickedPlayers%Nwith the reason:&u%A%reason%N&uvote by clicking on a button:");
		StefsAPI.ConfigHandler
				.addDefault(
						messages,
						"normal.votekick.layout2",
						"&a[Yes]**/vote_yes**__%NYou will vote &ayes%N.__; &c[No]**/vote_no**__%NYou will vote &cno%N.__");
		StefsAPI.ConfigHandler.addDefault(messages,
				"normal.votekick.notEnoughVoted",
				"       %E&lX %Nvote failed!&uNot enough players voted.");
		StefsAPI.ConfigHandler.addDefault(messages,
				"normal.votekick.yesVotesMustExtend",
				"       %E&lX %Nvote failed!&uYes votes must extend no votes.");
		StefsAPI.ConfigHandler.addDefault(messages,
				"normal.votekick.votePassed",
				"       &a&lV %Nvote passed!&uUser(s) will be kicked!");

		// Error qualified messages.
		StefsAPI.ConfigHandler.addDefault(messages, "error.noPermission",
				"%TAG%EYou don't have the permissions to do that!");
		StefsAPI.ConfigHandler
				.addDefault(
						messages,
						"error.commandNotEnabled",
						"%TAG%EThis command has been disabled! Ask your administrator if you belive this is an error.");
		StefsAPI.ConfigHandler.addDefault(messages, "error.onlyIngame",
				"%TAG%EThis is an only in-game command!");
		StefsAPI.ConfigHandler.addDefault(messages, "error.commandNotFound",
				"%TAG%ECouldn't find the command. Try %A/" + pdfFile.getName()
						+ " <help/h> [page number] %Efor more info.");
		StefsAPI.ConfigHandler.addDefault(messages, "error.notEnoughArguments",
				"%TAG%EYou're missing arguments, correct syntax: %A%syntax");
		StefsAPI.ConfigHandler.addDefault(messages, "error.playerNotFound",
				"%TAG%ENo player found with the name '%A%playername%E'!");
		StefsAPI.ConfigHandler.addDefault(messages, "error.notANumber",
				"%TAG%E'%A%number%E' is not a number!");
		StefsAPI.ConfigHandler.addDefault(messages, "error.notAGamemode",
				"%TAG%E'%A%gamemode%E' is not a gamemode type!");
		StefsAPI.ConfigHandler.addDefault(messages, "error.muted",
				"%TAG%EYou're muted! You can't talk for:%time%E.");
		StefsAPI.ConfigHandler.addDefault(messages,
				"error.voteNoVoteAvailable",
				"%TAG%EThere is currently no vote available!");
		StefsAPI.ConfigHandler.addDefault(messages, "error.voteAlreadyVoted",
				"%TAG%EYou've already voted! You can only vote once.");
		StefsAPI.ConfigHandler.addDefault(messages, "error.voteAlreadyAVote",
				"%TAG%EThere is already a vote going on. Please wait.");

		// Logging.
		StefsAPI.ConfigHandler
				.addDefault(messages, "log.enabledPlugin",
						"%TAG%name&a&k + %N%version is now Enabled. Made by %A%authors%N.");
		StefsAPI.ConfigHandler
				.addDefault(messages, "log.disabledPlugin",
						"%TAG%name&c&k - %N%version is now Disabled. Made by %A%authors%N.");

		StefsAPI.ConfigHandler.displayNewFiles();
		AdminEye.checkPlayerFiles();

		StefsAPI.enableAPI();

		if (config.getFile().getBoolean("metrics.enabled")) {
			try {
				Metrics metrics = new Metrics(plugin);
				metrics.start();
				StefsAPI.MessageHandler.buildMessage().addSender("$")
						.setMessage("%TAGSending %AMCStats%N to their server!")
						.build();
			} catch (Exception e) {
				StefsAPI.MessageHandler
						.buildMessage()
						.addSender("$")
						.setMessage(
								"%TAG%EUnable to send %AMCStats %Eto their server. Something went wrong ;(!")
						.build();
			}
		} else {
			StefsAPI.MessageHandler
					.buildMessage()
					.addSender("$")
					.setMessage(
							"%TAG%EUnable to send %AMCStats %Eto their server. Is it disabled?")
					.build();
		}

		StefsAPI.MessageHandler
				.buildMessage()
				.addSender("$")
				.setMessage("log.enabledPlugin", messages)
				.changeVariable("name", pdfFile.getName())
				.changeVariable("version", "v" + pdfFile.getVersion())
				.changeVariable(
						"authors",
						pdfFile.getAuthors().get(0) + " %Nand %A"
								+ pdfFile.getAuthors().get(1)).build();

	}

	public void onDisable() {
		StefsAPI.MessageHandler
				.buildMessage()
				.addSender("$")
				.setMessage("log.disabledPlugin", messages)
				.changeVariable("name", pdfFile.getName())
				.changeVariable("version", "v" + pdfFile.getVersion())
				.changeVariable(
						"authors",
						pdfFile.getAuthors().get(0) + " %Nand %A"
								+ pdfFile.getAuthors().get(1)).build();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		String playerName = "$";
		if (sender instanceof Player) {
			player = (Player) sender;
			playerName = player.getName();
		}

		for (nl.Steffion.AdminEye.StefsAPI.Command command : StefsAPI.commands) {
			String[] arguments = command.arguments;
			String[] aliases = command.aliases;

			if (cmd.getName().equalsIgnoreCase(command.label)) {
				int i = 0;
				boolean equals = true;

				if (arguments == null) {
					if (args.length != 0) {
						equals = false;
					}
				} else {
					if (!arguments[0].equals("*")) {
						if (args.length >= arguments.length) {
							for (String argument : arguments) {
								for (String alias : aliases) {
									if (!argument.equalsIgnoreCase(args[i])
											&& !alias.equalsIgnoreCase(args[i])) {
										equals = false;
									}

									i = i + 1;
								}
							}
						} else {
							equals = false;
						}
					}
				}

				if (equals) {
					if (StefsAPI.PermissionHandler.hasPermission(player,
							command.name, command.typePermission, true)) {
						if (config.getFile().getBoolean(
								"commandEnabled." + command.name)) {
							command.command.execute(player, playerName, cmd,
									label, args);
						} else {
							StefsAPI.MessageHandler
									.buildMessage()
									.addSender(playerName)
									.setMessage("error.commandNotEnabled",
											messages).build();
						}
					}

					return true;
				}
			}
		}

		new BasicCommands().new UnknownCommand().execute(player, playerName,
				cmd, label, args);
		return true;
	}

	public static void broadcastAdminEyeMessage(String issuer, String message,
			String enabled, String... variables) {
		String someone;
		message = AdminEye.messages.getFile().getString("normal." + message);
		for (int i = variables.length; i >= 2; i--) {
			message = message.replaceAll("%" + variables[i - 2],
					variables[i - 1]);
		}

		if (AdminEye.config.getFile().getBoolean("broadcastEnabled." + enabled)) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (issuer == "$" || issuer == null) {
					someone = AdminEye.config.getFile().getString(
							"chat.console");
				} else if (issuer == "#") {
					someone = AdminEye.config.getFile()
							.getString("chat.system");
				} else if (StefsAPI.PermissionHandler.hasPermission(player,
						"someone", PermissionType.MODERATOR, false)) {
					someone = "%A" + issuer;
				} else {
					someone = AdminEye.config.getFile().getString(
							"chat.someone");
				}

				StefsAPI.MessageHandler.buildMessage()
						.addSender(player.getName())
						.setMessage("normal.broadcast", AdminEye.messages)
						.changeVariable("someone", someone)
						.changeVariable("message", message).build();
			}
		}

		if (issuer == "$" || issuer == null) {
			someone = AdminEye.config.getFile().getString("chat.console");
		} else if (issuer == "#") {
			someone = AdminEye.config.getFile().getString("chat.system");
		} else {
			someone = "%A" + issuer;
		}

		StefsAPI.MessageHandler.buildMessage().addSender("$")
				.setMessage("normal.broadcast", AdminEye.messages)
				.changeVariable("someone", someone)
				.changeVariable("message", message).build();
	}

	public static void checkPlayerFiles() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			new PlayerFile(player.getName());
		}
	}
}