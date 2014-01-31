package nl.Steffion.AdminEye.Listeners;

import java.util.Calendar;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class OnPlayerLoginEvent implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLoginEvent(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		PlayerFile playerFile = new PlayerFile(player.getName());

		if (playerFile.banBanned) {
			String finalTime = (playerFile.banLength.equals(0) ? AdminEye.messages
					.getFile().getString("normal.permanently") : "");
			String finalReason = AdminEye.messages.getFile().getString(
					"normal.banreason")
					+ playerFile.banReason;
			if (playerFile.banLength != 0) {
				int time = (int) Calendar.getInstance().getTimeInMillis();

				if (time >= playerFile.banLength) {
					playerFile.banBanned = false;
					playerFile.save();
					return;
				}

				int days = 0;
				int hours = 0;
				int minutes = 0;
				int seconds = 0;

				days = (playerFile.banLength - time) / 1000 / 60 / 60 / 24;
				hours = ((playerFile.banLength - time) / 1000 / 60 / 60)
						- (days * 24);
				minutes = ((playerFile.banLength - time) / 1000 / 60)
						- (hours * 60) - (days * 24 * 60);
				seconds = ((playerFile.banLength - time) / 1000)
						- (minutes * 60) - (hours * 60 * 60)
						- (days * 24 * 60 * 60);

				if (days != 0) {
					finalTime += " %A"
							+ days
							+ AdminEye.messages.getFile().getString(
									"normal.days");
				}
				if (hours != 0) {
					finalTime += " %A"
							+ hours
							+ AdminEye.messages.getFile().getString(
									"normal.hours");
				}
				if (minutes != 0) {
					finalTime += " %A"
							+ minutes
							+ AdminEye.messages.getFile().getString(
									"normal.minutes");
				}
				if (seconds != 0) {
					finalTime += " %A"
							+ seconds
							+ AdminEye.messages.getFile().getString(
									"normal.seconds");
				}
			}

			finalReason += "&u"
					+ AdminEye.messages.getFile().getString("normal.banLength")
					+ (playerFile.banLength.equals(0) ? AdminEye.messages
							.getFile().getString("normal.permanently") + "%N."
							: "%A" + finalTime + "%N.");

			event.setKickMessage(StefsAPI.MessageHandler
					.replaceColours(StefsAPI.MessageHandler
							.replacePrefixes(finalReason)));
			event.setResult(Result.KICK_OTHER);
		}
	}
}
