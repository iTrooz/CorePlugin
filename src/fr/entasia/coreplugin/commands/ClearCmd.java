package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.clear")){
			if(args.length == 0){
				int items = 0;
				for(ItemStack it : p.getInventory().getContents())if(it != null) items += it.getAmount();
				p.getInventory().clear();
				if(items == 1) p.sendMessage("§aTon inventaire a été supprimé ! (§b1§a item supprimé)");
				else p.sendMessage("§aTon inventaire a été supprimé ! (§b"+items+"§a items supprimés)");

			}else{
				if(p.hasPermission("entasia.clear.others")){
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) p.sendMessage("§cErreur : Ce joueur n'est pas connecté ou n'existe pas !");
					else{
						int items = 0;
						for(ItemStack it : target.getInventory().getContents()) if(it != null) items += it.getAmount();
						target.getInventory().clear();
						if(items == 1)
							p.sendMessage("§aL'inventaire de "+ Utils.formatPlayerSuffix(target)+"§a a été supprimé ! (§b1§a item supprimé)");
						else
							p.sendMessage("§aL'inventaire de "+ Utils.formatPlayerSuffix(target)+"§a a été supprimé ! (§b"+items+"§a items supprimés)");
					}
				}
			}
		} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return false;
	}
}
