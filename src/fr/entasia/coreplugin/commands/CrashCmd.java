package fr.entasia.coreplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrashCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("restricted.crashcmd")){
			if(args.length==1){
				Player p = Bukkit.getPlayer(args[0]);
				if(p==null)sender.sendMessage("§cCe joueur n'existe pas !");
				else{
					p.spawnParticle(Particle.FLAME, p.getLocation(), 999999999);
				}
			}else sender.sendMessage("§cTu dois mettre un argument !");
		} else sender.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
