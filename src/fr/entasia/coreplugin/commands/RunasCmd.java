package fr.entasia.coreplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RunasCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (sender.hasPermission("restricted.runas")) {
			if(args.length>1){
				Player p = Bukkit.getPlayer(args[0]);
				if(p==null)sender.sendMessage("§cCe joueur n'existe pas !");
				else{
					String msg = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
					p.chat(msg);
					sender.sendMessage("§6Execution faite avec succès !");
				}
			}else sender.sendMessage("§c/runas <Joueur/Console> <Message> ( pas pas oublier le / pour une commande )");

		} else sender.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}