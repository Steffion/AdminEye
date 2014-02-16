package nl.Steffion.AdminEye.Listeners;

import nl.Steffion.AdminEye.PlayerFile;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMoveEvent implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		PlayerFile playerFile = new PlayerFile(player.getName());
		if (playerFile.frozenFroze) {
			Location lastLoc = event.getFrom();
			Location toLoc = event.getTo();

			double x = Math.floor(lastLoc.getX());
			double y = Math.floor(lastLoc.getY());
			double z = Math.floor(lastLoc.getZ());
			
			if (Math.floor(toLoc.getX()) != x || Math.floor(toLoc.getY()) != y
					|| Math.floor(toLoc.getZ()) != z) {
				x += .5;
				y += .5;
				z += .5;
				
				player.teleport(new Location(lastLoc.getWorld(), x, y, z,
						lastLoc.getYaw(), lastLoc.getPitch()));
			}
		}
	}

}
