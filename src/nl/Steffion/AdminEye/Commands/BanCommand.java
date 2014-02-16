package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;
import java.util.Calendar;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class BanCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 1) {
			StefsAPI.MessageHandler
					.buildMessage()
					.addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax",
							"/ban <player name> <time> [reason]").build();
		} else {
			banPlayer(player, playerName, args[0], args[1],
					AdminEyeUtils.stringBuilder(args, 2));

		}
		return true;
	}

	public static void banPlayer(Player player, String playerName,
			String banPlayerName, String time, String reason) {
		ArrayList<Player> banPlayers = AdminEyeUtils
				.requestPlayers(banPlayerName);

		if (banPlayers == null && banPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", banPlayerName).build();
			return;
		}

		String finalReason = AdminEye.messages.getFile().getString(
				"normal.banreason")
				+ (reason == null ? AdminEye.messages.getFile().getString(
						"normal.noReasonGiven")
						+ "%N." : reason + "%N.");
		String bannedPlayers = "";

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

		finalReason += "&u"
				+ AdminEye.messages.getFile().getString("normal.banLength")
				+ (time.equals("0") ? AdminEye.messages.getFile().getString(
						"normal.permanently")
						+ "%N." : "%A" + finalTime + "%N.");

		finalTime = (time.equals("0") ? AdminEye.messages.getFile().getString(
				"normal.permanently") : finalTime);

		int lenght = 0;
		if (!time.equals("0")) {
			lenght += Calendar.getInstance().getTimeInMillis()
					+ (days * 1000 * 60 * 60 * 24) + (hours * 1000 * 60 * 60)
					+ (minutes * 1000 * 60) + (seconds * 1000);
		}

		for (Player banPlayer : banPlayers) {
			banPlayer.kickPlayer(StefsAPI.MessageHandler
					.replaceColours(StefsAPI.MessageHandler
							.replacePrefixes(finalReason)));
			bannedPlayers += "%A" + banPlayer.getName() + "%N, ";
			PlayerFile playerFile = new PlayerFile(banPlayer.getName());
			playerFile.banBanned = true;
			playerFile.banLength = lenght;
			playerFile.banReason = (reason == null ? AdminEye.messages
					.getFile().getString("normal.noReasonGiven") + "%N."
					: reason + "%N.");
			playerFile.save();
		}

		bannedPlayers = (banPlayerName.equals("*") ? bannedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: bannedPlayers);

		AdminEye.broadcastAdminEyeMessage(
				playerName,
				"banned",
				"ban",
				"playernames",
				bannedPlayers,
				"time",
				finalTime,
				"reason",
				(reason == null ? AdminEye.messages.getFile().getString(
						"normal.noReasonGiven") : reason.replaceAll("&u", " ")));
	}
}
