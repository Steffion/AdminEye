package nl.Steffion.AdminEye.Commands;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;
import nl.Steffion.AdminEye.VoteHandler;
import nl.Steffion.AdminEye.VoteHandler.VoteType;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class VotenoCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		voteNo(player, playerName);
		return true;
	}

	public static void voteNo(Player player, String playerName) {
		if (player == null) {
			StefsAPI.MessageHandler.buildMessage().addSender("$")
					.setMessage("error.onlyIngame", AdminEye.messages).build();
			return;
		}

		if (VoteHandler.type == VoteType.NO_VOTE) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.voteNoVoteAvailable", AdminEye.messages)
					.build();
			return;
		}

		if (VoteHandler.vote_yes_players.contains(playerName)
				|| VoteHandler.vote_no_players.contains(playerName)) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.voteAlreadyVoted", AdminEye.messages)
					.build();
			return;
		}

		VoteHandler.vote_no_players.add(playerName);

		for (Player notePlayer : Bukkit.getOnlinePlayers()) {
			notePlayer.playSound(notePlayer.getPlayer().getLocation(),
					Sound.NOTE_BASS_GUITAR, 1, 0.6f);
		}
	}
}