package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeAPI;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;
import nl.Steffion.AdminEye.VoteHandler;
import nl.Steffion.AdminEye.VoteHandler.VoteType;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class VotekickCommand extends ExecutedCommand {

	public static float pitch = 0.67f;

	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler
					.buildMessage()
					.addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax",
							"/votekick <player name> [reason]").build();
		} else {
			votekickPlayer(player, playerName, args[0],
					AdminEyeUtils.stringBuilder(args, 1));

		}
		return true;
	}

	public static void votekickPlayer(final Player player, String playerName,
			String votekickPlayerName, final String reason) {
		ArrayList<Player> votekickPlayers = AdminEyeUtils
				.requestPlayers(votekickPlayerName);

		if (votekickPlayers == null && votekickPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", votekickPlayerName).build();
			return;
		}

		if (VoteHandler.type != VoteType.NO_VOTE) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.voteAlreadyAVote", AdminEye.messages)
					.build();
			return;
		}

		String finalReason = AdminEye.messages.getFile().getString(
				"normal.kickreason");
		String votekickedPlayers = "";

		finalReason = finalReason
				+ (reason == null ? AdminEye.messages.getFile().getString(
						"normal.noReasonGiven")
						+ "%N." : reason + "%N.");

		for (Player votekickPlayer : votekickPlayers) {
			votekickedPlayers += "%A" + votekickPlayer.getName() + "%N, ";
		}

		votekickedPlayers = (votekickPlayerName.equals("*") ? votekickedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: votekickedPlayers);

		AdminEye.broadcastAdminEyeMessage(
				playerName,
				"votekicked",
				"votekick",
				"playernames",
				votekickedPlayers,
				"reason",
				(reason == null ? AdminEye.messages.getFile().getString(
						"normal.noReasonGiven") : reason.replaceAll("&u", " ")));

		// Set vote to the votekick
		VoteHandler.type = VoteType.VOTE_KICK;
		ArrayList<String> votekickPlayerNames = new ArrayList<String>();
		for (Player votekickPlayer : votekickPlayers) {
			votekickPlayerNames.add(votekickPlayer.getName());
		}
		VoteHandler.voted_players = votekickPlayerNames;

		// Display the vote buttons
		StefsAPI.MessageHandler
				.buildMessage()
				.addSender("*")
				.setMessage("chat.header_high", AdminEye.config)
				.changeVariable("title", "Votekick")
				.build()
				.setMessage("normal.votekick.layout1", AdminEye.messages)
				.changeVariable("playerName", playerName)
				.changeVariable("votekickedPlayers", votekickedPlayers)
				.changeVariable(
						"reason",
						(reason == null ? AdminEye.messages.getFile()
								.getString("normal.noReasonGiven") + "%N."
								: reason + "%N.")).build()
				.setMessage("normal.votekick.layout2", AdminEye.messages)
				.buildAsJSON().setMessage("chat.header_high", AdminEye.config)
				.changeVariable("title", "Votekick").build();

		// Music on start vote
		int delay = 0;
		int notes_passed = 0;
		while (notes_passed < 5) {
			Bukkit.getServer().getScheduler()
					.runTaskLater(AdminEye.plugin, new Runnable() {

						@Override
						public void run() {
							pitch = (pitch == 0.67f ? 0.8f : 0.67f);

							for (Player notePlayer : Bukkit.getOnlinePlayers()) {
								notePlayer.playSound(notePlayer.getPlayer()
										.getLocation(), Sound.NOTE_BASS_GUITAR,
										1, pitch);
							}
						}
					}, delay);
			delay += 5;
			notes_passed = notes_passed + 1;
		}

		pitch = 0.67f;

		// End of vote
		Bukkit.getServer().getScheduler()
				.runTaskLater(AdminEye.plugin, new Runnable() {

					@Override
					public void run() {
						// Music on end vote
						int delay = 0;
						int notes_passed = 0;
						while (notes_passed < 4) {
							Bukkit.getServer()
									.getScheduler()
									.runTaskLater(AdminEye.plugin,
											new Runnable() {

												@Override
												public void run() {
													pitch = (pitch == 0.67f ? 0.8f
															: 0.67f);

													for (Player notePlayer : Bukkit
															.getOnlinePlayers()) {
														notePlayer
																.playSound(
																		notePlayer
																				.getPlayer()
																				.getLocation(),
																		Sound.NOTE_BASS_GUITAR,
																		1,
																		pitch);
													}
												}
											}, delay);
							delay += 5;
							notes_passed = notes_passed + 1;
						}

						// Get votes and get passed or failed
						int totalplayersvoted = VoteHandler.vote_yes_players
								.size() + VoteHandler.vote_no_players.size();

						if (totalplayersvoted <= (Bukkit.getOnlinePlayers().length / 2)) {
							StefsAPI.MessageHandler
									.buildMessage()
									.addSender("*")
									.setMessage("chat.header_high",
											AdminEye.config)
									.changeVariable("title", "Votekick")
									.build()
									.setMessage(
											"normal.votekick.notEnoughVoted",
											AdminEye.messages)
									.build()
									.setMessage("chat.header_high",
											AdminEye.config)
									.changeVariable("title", "Votekick")
									.build();

							VoteHandler.type = VoteType.NO_VOTE;
							VoteHandler.voted_players = new ArrayList<String>();
							VoteHandler.vote_yes_players = new ArrayList<String>();
							VoteHandler.vote_no_players = new ArrayList<String>();

							for (Player notePlayer : Bukkit.getOnlinePlayers()) {
								notePlayer.playSound(notePlayer.getPlayer()
										.getLocation(), Sound.NOTE_BASS_GUITAR,
										1, 0.6f);
							}

							return;
						}

						if (VoteHandler.vote_yes_players.size() <= (totalplayersvoted / 2)) {
							StefsAPI.MessageHandler
									.buildMessage()
									.addSender("*")
									.setMessage("chat.header_high",
											AdminEye.config)
									.changeVariable("title", "Votekick")
									.build()
									.setMessage(
											"normal.votekick.yesVotesMustExtend",
											AdminEye.messages)
									.build()
									.setMessage("chat.header_high",
											AdminEye.config)
									.changeVariable("title", "Votekick")
									.build();

							VoteHandler.type = VoteType.NO_VOTE;
							VoteHandler.voted_players = new ArrayList<String>();
							VoteHandler.vote_yes_players = new ArrayList<String>();
							VoteHandler.vote_no_players = new ArrayList<String>();

							for (Player notePlayer : Bukkit.getOnlinePlayers()) {
								notePlayer.playSound(notePlayer.getPlayer()
										.getLocation(), Sound.NOTE_BASS_GUITAR,
										1, 0.6f);
							}

							return;
						}

						// Vote passed
						StefsAPI.MessageHandler
								.buildMessage()
								.addSender("*")
								.setMessage("chat.header_high", AdminEye.config)
								.changeVariable("title", "Votekick")
								.build()
								.setMessage("normal.votekick.votePassed",
										AdminEye.messages)
								.build()
								.setMessage("chat.header_high", AdminEye.config)
								.changeVariable("title", "Votekick").build();

						for (final String votedPlayerName : VoteHandler.voted_players) {
							Bukkit.getScheduler().runTaskLater(AdminEye.plugin,
									new Runnable() {

										@Override
										public void run() {

											AdminEyeAPI
													.kickPlayer(
															player,
															"#",
															votedPlayerName,
															(reason == null ? AdminEye.messages
																	.getFile()
																	.getString(
																			"normal.noReasonGiven")
																	: reason.replaceAll(
																			"&u",
																			" ")));

										}
									}, 40);
						}

						VoteHandler.type = VoteType.NO_VOTE;
						VoteHandler.voted_players = new ArrayList<String>();
						VoteHandler.vote_yes_players = new ArrayList<String>();
						VoteHandler.vote_no_players = new ArrayList<String>();

						for (Player notePlayer : Bukkit.getOnlinePlayers()) {
							notePlayer.playSound(notePlayer.getPlayer()
									.getLocation(), Sound.NOTE_BASS_GUITAR, 1,
									1.06f);
						}

					}
				}, 20 * 20);
	}
}
