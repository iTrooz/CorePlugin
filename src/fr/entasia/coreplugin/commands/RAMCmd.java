package fr.entasia.coreplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RAMCmd implements CommandExecutor {

	public static float format(long a){
		float b = a/1073741824f; // 1024**3
		return Math.round(b*100)/100f;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (sender.hasPermission("admin.ram")) {
			Runtime runtime = Runtime.getRuntime();
			sender.sendMessage("§bMémoire maximale : §7"+format(runtime.maxMemory())+" §bGo");
//			sender.sendMessage("§bMémoire allouée : §7"+format(runtime.totalMemory())+" §bGo");
			sender.sendMessage("§bMémoire libre : §7"+format(runtime.freeMemory())+" §bGo");

		} else sender.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}