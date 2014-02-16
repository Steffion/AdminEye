package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class UnfreezeCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/unfreeze <player name>")
					.build();
		} else {
			unfreezePlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void unfreezePlayer(Player player, String playerName,
			String unfreezePlayerName) {
		ArrayList<Player> unfreezePlayers = AdminEyeUtils
				.requestPlayers(unfreezePlayerName);
		if (unfreezePlayers == null && unfreezePlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", unfreezePlayerName).build();
			return;
		}

		String unfrozenPlayers = "";

		for (Player unfreezePlayer : unfreezePlayers) {
			PlayerFile playerFile = new PlayerFile(unfreezePlayer.getName());
			playerFile.frozenFroze = false;
			playerFile.save();

			unfrozenPlayers += "%A" + unfreezePlayer.getName() + "%N, ";
		}

		unfrozenPlayers = (unfreezePlayerName.equals("*") ? unfrozenPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: unfrozenPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "unfreeze", "unfreeze",
				"playernames", unfrozenPlayers);
	}

}
