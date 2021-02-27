package fr.entasia.coreplugin.listeners;

import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.Utils;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Basic implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void d1(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(Main.gods.contains(p.getUniqueId())){
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void d2(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(Main.gods.contains(p.getUniqueId())){
				e.setCancelled(true);
				if(e.getCause()== EntityDamageEvent.DamageCause.VOID)p.teleport(p.getWorld().getSpawnLocation());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerLoginEvent e){
		if(Main.lockdown!=null){
			if(e.getPlayer().hasPermission("staff.lockdown.bypass")){
				e.getPlayer().sendMessage(" \n§6Lockdown :§lTu as rejoint un serveur en maintenance !\n ");
			}else{
				e.setKickMessage("§cServeur en maintenance ! Raison : "+ Main.lockdown);
				e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(Utils.formatPlayerSuffix(e.getPlayer())+" §7a rejoint le serveur !");
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage(Utils.formatPlayerSuffix(e.getPlayer())+"§7 a quitté le serveur !");
		Main.gods.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void Interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(Tag.SIGNS.isTagged(e.getClickedBlock().getType())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void noUproot(PlayerInteractEvent e) {
		if(e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND){
			e.setCancelled(true);
			e.getClickedBlock().getState().update(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e){
		e.setDeathMessage("");
	}
}
