package fr.entasia.coreplugin.listeners;

import fr.entasia.apis.other.Signer;
import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.utils.Utils;
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

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e){
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
				e.getPlayer().sendMessage(" \n§6Lockdown §8» §c§lTu as rejoint un serveur en maintenance !\n ");
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
			if(e.getClickedBlock().getType() == Material.SIGN_POST||e.getClickedBlock().getType()==Material.SIGN) {
				e.setCancelled(true);
				if(Signer.activate) {
					Signer.open(e.getPlayer(), e.getClickedBlock().getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ(), (String[] lines) -> {
						Sign sign = (Sign) e.getClickedBlock().getState();
						for (int i = 0; i < 4; i++) sign.setLine(i, lines[i]);
					});
				}
			}
		}
	}
	
	@EventHandler
	public void noUproot(PlayerInteractEvent e) {
		if(e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL){
			e.setCancelled(true);
			e.getClickedBlock().getState().update(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e){
		e.setDeathMessage("");
	}
}
