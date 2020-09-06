package fr.entasia.coreplugin.commands;

import fr.entasia.apis.other.ChatComponent;
import fr.entasia.coreplugin.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WhoIsCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.whois")){
			if(args.length == 0) p.sendMessage("§cErreur : Syntaxe : /whois <joueur>");
			else {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null){
					p.sendMessage("§e» §6Informations Spigot : §a" + target.getName());
					p.sendMessage("§bVie : §7" + target.getHealth() + "§c❤ §b/" + target.getMaxHealth());
					p.sendMessage("§bNourriture : §7" + target.getFoodLevel() + "§b/20");
					p.sendMessage("§bXP : §7" + target.getLevel() + " niveaux");
					ChatComponent location = new ChatComponent("§7Coordonnées : §7X : §b" + target.getLocation().getBlockX() + "§7, Y : §b" + target.getLocation().getBlockY() + "§7, Z : §b" + target.getLocation().getBlockZ() + "§7, Monde §b" + target.getWorld().getName());
					location.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponent.create("§aCliquez pour vous téléporter au joueur !")));
					location.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + target.getName()));
					p.spigot().sendMessage(location.create());
					p.sendMessage("§bMode de jeu : §7" + getGmName(target.getGameMode()));
					p.sendMessage("§bMode Dieu : §7" + getGod(target.getUniqueId()));
					p.sendMessage("§bVol : §7" + getFly(target) + "§b " + getInFly(target));
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

	private String getFly(Player p) {
		if(p.getAllowFlight())return "§aOui";
		else return "§cNon";
	}
	
	private String getInFly(Player p) {
		if(!p.getAllowFlight())return "";
		if(p.isFlying())return "(En vol)";
		else return "(Sur terre)";
		
	}

	private String getGod(UUID uuid) {
		if(Main.gods.contains(uuid))return "§aOui";
		else return "§cNon";
		
	}

	private String getGmName(GameMode gm) {
		switch(gm){
			case SPECTATOR:
				return "Spectateur (3)";
			case ADVENTURE:
				return "Aventure (2)";
			case CREATIVE:
				return "Créatif (1)";
			case SURVIVAL:
				return "Survie (0)";
		}
		return "Erreur";
	}

}
