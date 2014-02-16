package nl.Steffion.AdminEye.Listeners;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.PlayerFile;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerFile playerFile = new PlayerFile(player.getName());

		if (playerFile.flyFlying) {
			player.setAllowFlight(true);
			player.setFlying(true);
			AdminEye.broadcastAdminEyeMessage("#", "fly", "fly", "playernames",
					player.getName() + "%N, ");
		}
	}
}
