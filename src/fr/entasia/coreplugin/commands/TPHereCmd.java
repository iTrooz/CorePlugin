package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPHereCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.tphere")){
			if(args.length == 0)p.sendMessage("§cErreur §8» §c Utilisation : /tphere <joueur>");
			else{
				Player target = Bukkit.getPlayer(args[0]);
				if(target == null)p.sendMessage("§cErreur §8» §c Ce joueur n'est pas connecté ou n'existe pas !");
				else{
					target.teleport(p.getLocation());
					p.sendMessage("§aTu as téléporté "+ Main.formatPlayerSuffix(target)+"§a à toi !");
				}

			}

		} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
