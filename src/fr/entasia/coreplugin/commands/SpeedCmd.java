package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.speed")) {
			if (args.length>0) {
				float value;
				try {
					value = Float.parseFloat(args[0]);
					if (value<0||value>10)throw new NumberFormatException();
				} catch (NumberFormatException e) {
					p.sendMessage("§c Valeur " + args[0] + " incorrecte ! La valeur doit être comprise entre 1 et 10 !");
					return true;
				}
				Player target;
				if(args.length>1){
					if (p.hasPermission("entasia.speed.others")) {
						target = Bukkit.getPlayer(args[1]);
						if(target==null)p.sendMessage("§cCe joueur n'est pas connecté !");
						else{
							if (target.isFlying()) {
								target.setFlySpeed(value / 10f);
								p.sendMessage(Utils.formatPlayerSuffix(target) + "§a a maintenant la vitesse de vol " + value + " !");
							} else {
								float speed = (value - 1) / 9f; // CODE DE ESSENTIALS, A COMPRENDRE
								target.setWalkSpeed((speed * 0.8f) + 0.2f); // CODE DE ESSENTIALS, A COMPRENDRE
								p.sendMessage(Utils.formatPlayerSuffix(target) + "§a a maintenant la vitesse de marche " + value + " !");
							}
						}
					} else p.sendMessage("§cTu n'as pas la permission de changer la vitesse des autres joueurs !");
				}else{
					if (p.isFlying()) {
						p.setFlySpeed(value / 10f);
						p.sendMessage("§aTu as maintenant la vitesse de vol " + value + " !");
					} else {
						float speed = (value - 1) / 9f; // CODE DE ESSENTIALS, A COMPRENDRE
						p.setWalkSpeed((speed * 0.8f) + 0.2f); // CODE DE ESSENTIALS, A COMPRENDRE
						p.sendMessage("§aTu as maintenant la vitesse de marche " + value + " !");
					}
				}
			}else p.sendMessage("§cSyntaxe : /speed <vitesse> [joueur]");
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
	
}
