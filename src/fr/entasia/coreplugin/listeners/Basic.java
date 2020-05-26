package fr.entasia.coreplugin.listeners;

import fr.entasia.apis.Signer;
import fr.entasia.coreplugin.Main;
import org.bukkit.Material;
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

public class Basic implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(Main.gods.contains(p.getUniqueId())) e.setCancelled(true);
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerLoginEvent e){
		if(Main.lockdown!=null){
			if(e.getPlayer().hasPermission("entasia.lockdown.bypass")){
				e.getPlayer().sendMessage(" \n§6Lockdown §8» §c§lTu as rejoint un serveur en maintenance !\n ");
			}else{
				e.setKickMessage("§cServeur en maintenance ! Raison : "+ Main.lockdown);
				e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(Main.formatPlayerSuffix(e.getPlayer())+" §7a rejoint le serveur !");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage(Main.formatPlayerSuffix(e.getPlayer())+"§7 a quitté le serveur !");
		Main.gods.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void Interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK&&e.getClickedBlock().getType() == Material.SIGN_POST){
			if(e.isCancelled())return;
			e.setCancelled(true);
			if(Signer.activate){
				Signer.open(e.getPlayer(), e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ(), (String[] lines) -> {
					Sign sign = (Sign) e.getClickedBlock().getState();
					for(int i=0; i<4;i++) sign.setLine(i, lines[i]);
				});
			}
		}
	}
	@EventHandler
	public void noUproot(PlayerInteractEvent event) {
		if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL){
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e){
		e.setDeathMessage("");
	}
}
