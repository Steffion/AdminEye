package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class BasicCommands {
	public class UnknownCommand extends ExecutedCommand {
		@Override
		public boolean execute(Player player, String playerName, Command cmd,
				String label, String[] args) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.commandNotFound", AdminEye.messages)
					.build();
			return true;
		}
	}

	public class InfoCommand extends ExecutedCommand {
		@Override
		public boolean execute(Player player, String playerName, Command cmd,
				String label, String[] args) {
			StefsAPI.MessageHandler
					.buildMessage()
					.addSender(playerName)
					.setMessage("chat.header_high", AdminEye.config)
					.changeVariable("title", AdminEye.pdfFile.getName())
					.build()
					.setMessage("%A%name%N made by %A%authors%N.")
					.changeVariable("name", AdminEye.pdfFile.getName())
					.changeVariable(
							"authors",
							AdminEye.pdfFile.getAuthors().get(0) + " %Nand %A"
									+ AdminEye.pdfFile.getAuthors().get(1))
					.build()
					.setMessage("%NVersion: %A%version%N.")
					.changeVariable("version", AdminEye.pdfFile.getVersion())
					.build()
					.setMessage(
							"%NType %A/" + AdminEye.pdfFile.getName()
									+ " <help/h> [page number]%N for help.")
					.build().setMessage("chat.header_high", AdminEye.config)
					.changeVariable("title", "&oInfo Page").build();
			return true;
		}
	}

	public class HelpCommand extends ExecutedCommand {
		@Override
		public boolean execute(Player player, String playerName, Command cmd,
				String label, String[] args) {
			int amountCommands = 0;
			for (nl.Steffion.AdminEye.StefsAPI.Command command : StefsAPI.commands) {
				if (command.usage != null) {
					amountCommands = amountCommands + 1;
				}
			}

			int maxPages = Math.round(amountCommands / 3);
			if (maxPages <= 0) {
				maxPages = 1;
			}

			int page;
			int i = 1;

			if (args.length == 1) {
				page = 1;
				StefsAPI.MessageHandler
						.buildMessage()
						.addSender(playerName)
						.setMessage("chat.header_high", AdminEye.config)
						.changeVariable(
								"title",
								AdminEye.pdfFile.getName() + " %Nhelp page %A"
										+ page + "%N/%A" + maxPages).build();

				for (nl.Steffion.AdminEye.StefsAPI.Command command : StefsAPI.commands) {
					if (i <= 4) {
						if (command.usage != null) {
							if (StefsAPI.PermissionHandler.hasPermission(
									player, command.name,
									command.typePermission, false)) {
								StefsAPI.MessageHandler
										.buildMessage()
										.addSender(playerName)
										.setMessage(
												"%A/"
														+ command.usage
														+ "%N - "
														+ AdminEye.messages
																.getFile()
																.get("help."
																		+ command.name))
										.build();
							} else {
								StefsAPI.MessageHandler
										.buildMessage()
										.addSender(playerName)
										.setMessage(
												"%E/"
														+ command.usage
														+ "%N - "
														+ AdminEye.messages
																.getFile()
																.get("help."
																		+ command.name))
										.build();
							}

							i = i + 1;
						}
					}
				}

				StefsAPI.MessageHandler.buildMessage().addSender(playerName)
						.setMessage("chat.header_high", AdminEye.config)
						.changeVariable("title", "&oHelp Page").build();
			} else {
				try {
					page = Integer.valueOf(args[1]);
				} catch (NumberFormatException e) {
					page = 1;
				}

				if (maxPages < page) {
					maxPages = page;
				}

				StefsAPI.MessageHandler
						.buildMessage()
						.addSender(playerName)
						.setMessage("chat.header_high", AdminEye.config)
						.changeVariable(
								"title",
								AdminEye.pdfFile.getName() + " %Nhelp page %A"
										+ page + "%N/%A" + maxPages).build();

				for (nl.Steffion.AdminEye.StefsAPI.Command command : StefsAPI.commands) {
					if (i <= (page * 4) + 4) {
						if (command.usage != null) {
							if (i >= ((page - 1) * 4) + 1
									&& i <= ((page - 1) * 4) + 4) {
								if (StefsAPI.PermissionHandler.hasPermission(
										player, command.name,
										command.typePermission, false)) {
									StefsAPI.MessageHandler
											.buildMessage()
											.addSender(playerName)
											.setMessage(
													"%A/"
															+ command.usage
															+ "%N - "
															+ AdminEye.messages
																	.getFile()
																	.get("help."
																			+ command.name))
											.build();
								} else {
									StefsAPI.MessageHandler
											.buildMessage()
											.addSender(playerName)
											.setMessage(
													"%E/"
															+ command.usage
															+ "%N - "
															+ AdminEye.messages
																	.getFile()
																	.get("help."
																			+ command.name))
											.build();
								}
							}

							i = i + 1;
						}
					}
				}

				StefsAPI.MessageHandler.buildMessage().addSender(playerName)
						.setMessage("chat.header_high", AdminEye.config)
						.changeVariable("title", "&oHelp Page").build();
			}

			return true;
		}
	}

	public class ReloadCommand extends ExecutedCommand {
		@Override
		public boolean execute(Player player, String playerName, Command cmd,
				String label, String[] args) {
			StefsAPI.ConfigHandler.displayNewFiles();
			AdminEye.config.load();
			AdminEye.messages.load();
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("normal.reloadedConfigs", AdminEye.messages)
					.build();
			return true;
		}
	}
}
