package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class MuteCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/mute <player name> [time]")
					.build();
		} else if (args.length <= 1) {
			mutePlayer(player, playerName, args[0], "0");
		} else {
			mutePlayer(player, playerName, args[0], args[1]);
		}
		return true;
	}

	public static void mutePlayer(Player player, String playerName,
			String mutePlayerName, String time) {
		ArrayList<Player> mutePlayers = AdminEyeUtils
				.requestPlayers(mutePlayerName);

		if (mutePlayers == null && mutePlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", mutePlayerName).build();
			return;
		}

		String mutedPlayers = "";

		String[] splitter = { time };
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;

		if (!time.contains("d") && !time.contains("h") && !time.contains("m")
				&& !time.contains("s")) {
			if (AdminEyeUtils.isNumber(time)) {
				minutes = AdminEyeUtils.getNumber(time);
			} else {
				time = "0";
			}
		} else {
			if (time.contains("d")) {
				splitter = splitter[0].split("d");
				days = AdminEyeUtils.getNumber(splitter[0]);
				if (splitter.length == 2) {
					splitter[0] = splitter[1];
				}
			}
			if (time.contains("h")) {
				splitter = splitter[0].split("h");
				hours = AdminEyeUtils.getNumber(splitter[0]);
				if (splitter.length == 2) {
					splitter[0] = splitter[1];
				}
			}
			if (time.contains("m")) {
				splitter = splitter[0].split("m");
				minutes = AdminEyeUtils.getNumber(splitter[0]);
				if (splitter.length == 2) {
					splitter[0] = splitter[1];
				}
			}
			if (time.contains("s")) {
				splitter = splitter[0].split("s");
				seconds = AdminEyeUtils.getNumber(splitter[0]);
			}
		}

		String finalTime = "";
		if (days != 0) {
			finalTime += " %A" + days
					+ AdminEye.messages.getFile().getString("normal.days");
		}
		if (hours != 0) {
			finalTime += " %A" + hours
					+ AdminEye.messages.getFile().getString("normal.hours");
		}
		if (minutes != 0) {
			finalTime += " %A" + minutes
					+ AdminEye.messages.getFile().getString("normal.minutes");
		}
		if (seconds != 0) {
			finalTime += " %A" + seconds
					+ AdminEye.messages.getFile().getString("normal.seconds");
		}

		finalTime = (time.equals("0") ? AdminEye.messages.getFile().getString(
				"normal.permanently") : finalTime);

		int length = 0;
		if (!time.equals("0")) {
			length += Calendar.getInstance().getTimeInMillis()
					+ (days * 1000 * 60 * 60 * 24) + (hours * 1000 * 60 * 60)
					+ (minutes * 1000 * 60) + (seconds * 1000);
		}

		for (Player mutePlayer : mutePlayers) {
			mutedPlayers += "%A" + mutePlayer.getName() + "%N, ";

			PlayerFile playerFile = new PlayerFile(mutePlayer.getName());
			playerFile.muteMuted = true;
			playerFile.muteLength = length;
			playerFile.save();
		}

		mutedPlayers = (mutePlayerName.equals("*") ? mutedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: mutedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "muted", "mute",
				"playernames", mutedPlayers, "time", finalTime);
	}
}
