package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.gamemode")) {
			if (args.length == 0) p.sendMessage("§cErreur §8» §c Mauvaise utilisation de la commande ! Utilisation : /gamemode <gamemode>");
			else {
				GameMode gm;
				switch (args[0]) {
					case "0":
					case "s":
					case "survie":
					case "survival":
						gm = GameMode.SURVIVAL;
						break;
					case "1":
					case "c":
					case "crea":
					case "creatif":
					case "creative":
						gm = GameMode.CREATIVE;
						break;
					case "2":
					case "a":
					case "aventure":
					case "adventure":
						gm = GameMode.ADVENTURE;
						break;
					case "3":
					case "sp":
					case "spec":
					case "spectateur":
					case "spectator":
						gm = GameMode.SPECTATOR;
						break;
					default:
						p.sendMessage("§cErreur §8» §c Le mode de jeu " + args[0] + " n'existe pas !");
						return true;
				}
				if (args.length == 1) {
					p.setGameMode(gm);
					switch (gm) {
						case SURVIVAL:
							p.sendMessage("§aTon mode de jeu a été mis en Survie !");
							break;
						case CREATIVE:
							p.sendMessage("§aTon mode de jeu a été mis en Créatif !");
							break;
						case ADVENTURE:
							p.sendMessage("§aTon mode de jeu a été mis en Aventure !");
							break;
						case SPECTATOR:
							p.sendMessage("§aTon mode de jeu a été mis en Spectateur !");
							break;
					}
				} else {
					if (p.hasPermission("entasia.gamemode.others")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target == null) p.sendMessage("§cCe joueur n'existe pas !");
						else {
							target.setGameMode(gm);
							switch (gm) {
								case SURVIVAL:
									p.sendMessage("§aLe mode de jeu de " + Utils.formatPlayerSuffix(target) + "§a a été mis en Survie !");
									break;
								case CREATIVE:
									p.sendMessage("§aLe mode de jeu de " + Utils.formatPlayerSuffix(target) + "§a a été mis en Créatif !");
									break;
								case ADVENTURE:
									p.sendMessage("§aLe mode de jeu de " + Utils.formatPlayerSuffix(target) + "§a a été mis en Aventure !");
									break;
								case SPECTATOR:
									p.sendMessage("§aLe mode de jeu de " + Utils.formatPlayerSuffix(target) + "§a a été mis en Spectateur !");
									break;
							}
						}
					} else p.sendMessage("§cTu n'as pas accès à changer le mode de jeu des autres joueurs !");
				}
			}
		} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return false;
	}

}
