package fr.entasia.coreplugin.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.top")){
			Location loc = p.getLocation();
			int py = loc.getBlockY();
			if(py<0)py=0;

			for(int y=256; y > py; y--){
				loc.setY(y);
				if(!loc.getBlock().getType().isSolid()) {
					loc.setY(y+1);
					if (!loc.getBlock().getType().isSolid()) {
						loc.setY(y-1);
						if (loc.getBlock().getType().isSolid()) {
							loc.setY(y);
							p.teleport(loc);
							p.sendMessage("§aTu as été téléporté au sommet !");
							return true;
						}
					}
				}
			}
			p.sendMessage("§cAucun point de téléportation trouvé au dessus de toi !");
		} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}
