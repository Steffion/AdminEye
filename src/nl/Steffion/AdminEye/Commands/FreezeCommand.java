package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class FreezeCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/freeze <player name>").build();
		} else {
			freezePlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void freezePlayer(Player player, String playerName,
			String freezePlayerName) {
		ArrayList<Player> freezePlayers = AdminEyeUtils
				.requestPlayers(freezePlayerName);
		if (freezePlayers == null && freezePlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", freezePlayerName).build();
			return;
		}

		String frozenPlayers = "";

		for (Player freezePlayer : freezePlayers) {
			PlayerFile playerFile = new PlayerFile(freezePlayer.getName());
			playerFile.frozenFroze = true;
			playerFile.save();

			frozenPlayers += "%A" + freezePlayer.getName() + "%N, ";
		}

		frozenPlayers = (freezePlayerName.equals("*") ? frozenPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: frozenPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "freeze", "freeze",
				"playernames", frozenPlayers);
	}
}
