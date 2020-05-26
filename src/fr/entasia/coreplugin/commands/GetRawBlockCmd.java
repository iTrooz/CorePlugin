package fr.entasia.coreplugin.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetRawBlockCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if(sender.hasPermission("build.getrawblock")){
			Block b = ((Player)sender).getTargetBlock(null, 40);
			if(b==null)sender.sendMessage("§cAucun block en visuel !");
			else{
				sender.sendMessage("§6Informations du block :");
				sender.sendMessage("§eNom : "+b.getType().toString());
				sender.sendMessage("§eID : "+b.getTypeId());
				sender.sendMessage("§eMeta : "+b.getData());
			}
		}else sender.sendMessage("§cTu n'as pas accès à cette commande !");
	return true;
	}
}
