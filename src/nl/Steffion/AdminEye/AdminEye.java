package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.Config;
import nl.Steffion.AdminEye.StefsAPI.PermissionType;
import nl.Steffion.AdminEye.Commands.KickCommand;

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

		config = StefsAPI.ConfigHandler.createConfig("config", "");
		messages = StefsAPI.ConfigHandler.createConfig("messages", "");

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

		StefsAPI.CommandHandler.registerCommand(pdfFile.getName(), null, null,
				"info", "Displays the plugin's info.", PermissionType.ALL,
				new BasicCommands().new InfoCommand(), pdfFile.getName()
						+ " [info/i]");
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
		StefsAPI.CommandHandler.registerCommand("kick", new String[] { "*" },
				new String[] { "*" }, "kick", "Kicks a player.",
				PermissionType.MODERATOR, new KickCommand(),
				"kick <playername> [reason]");

		StefsAPI.ConfigHandler
				.addDefault(config, "broadcastEnabled.kick", true);

		StefsAPI.ConfigHandler.addDefault(messages, "normal.reloadedConfigs",
				"%TAG&aReloaded configs!");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.broadcast",
				"%TAG%someone%N %message%N.");
		StefsAPI.ConfigHandler.addDefault(messages, "normal.kicked",
				"kicked player %A%playername%N with the reason: %A%reason");

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

		StefsAPI.ConfigHandler
				.addDefault(messages, "log.enabledPlugin",
						"%TAG%name&a&k + %N%version is now Enabled. Made by %A%authors%N.");
		StefsAPI.ConfigHandler
				.addDefault(messages, "log.disabledPlugin",
						"%TAG%name&c&k - %N%version is now Disabled. Made by %A%authors%N.");

		StefsAPI.ConfigHandler.displayNewFiles();

		StefsAPI.enableAPI();

		StefsAPI.MessageHandler.buildMessage().addSender("$")
				.setMessage("log.enabledPlugin", messages)
				.changeVariable("name", pdfFile.getName())
				.changeVariable("version", "v" + pdfFile.getVersion())
				.changeVariable("authors", pdfFile.getAuthors().get(0)).build();

	}

	public void onDisable() {
		StefsAPI.MessageHandler.buildMessage().addSender("$")
				.setMessage("log.disabledPlugin", messages)
				.changeVariable("name", pdfFile.getName())
				.changeVariable("version", "v" + pdfFile.getVersion())
				.changeVariable("authors", pdfFile.getAuthors().get(0)).build();
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

	public static void kickPlayer(Player player, String playerName,
			String kickPlayerName, String reason2) {
		Player kickPlayer = Bukkit.getPlayer(kickPlayerName);

		if (kickPlayer == null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", kickPlayerName).build();
			return;
		}

		String reason = "%TAG\n%NYou've been kicked! Reason: \n%A";

		reason = reason
				+ (reason2 == null ? "No reason given%N." : reason2 + "%N.");

		AdminEye.broadcastAdminEyeMessage(playerName, "kicked", "kick",
				"playername", kickPlayer.getName(), "reason",
				(reason2 == null ? "No reason given" : reason2));

		kickPlayer
				.kickPlayer(StefsAPI.MessageHandler
						.replaceColours(StefsAPI.MessageHandler
								.replacePrefixes(reason)));
	}
}