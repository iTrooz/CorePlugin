package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SkullCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		if(sender.hasPermission("entasia.skull")){
			if(args.length==0)sender.sendMessage("§cMet un argument !");
			else if(args.length==1){
				sender.sendMessage("§6Génération de la tête en cours...");
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						meta.setOwner(args[0]);
						item.setItemMeta(meta);
						((Player) sender).getInventory().addItem(item);
						sender.sendMessage("§aFini !");

					}
				}.runTaskAsynchronously(Main.main);

			}else sender.sendMessage("§cSyntaxe : "+cmd.getName()+" <pseudo>");
		}else sender.sendMessage("§cTu n'as pas la permission d'éxecuter cette commande !");

		return false;
	}

}
