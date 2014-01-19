package nl.Steffion.AdminEye.Commands;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class KickCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/kick <player name> [reason]")
					.build();
		} else {
			AdminEye.kickPlayer(player, playerName, args[0],
					AdminEyeUtils.stringBuilder(args, 1));

		}
		return true;
	}
}
