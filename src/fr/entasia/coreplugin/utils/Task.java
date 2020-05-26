package fr.entasia.coreplugin.utils;

import fr.entasia.apis.ServerUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class Task extends BukkitRunnable {

	@Override
	public void run() {
		File a = new File("logs/latest.log");
		if(a.exists()){
			double len = a.length()/1024f/1024f;
			if(len>1) ServerUtils.permMsg("logs.logs",
					"§cLes logs font plus de §8"+Math.round(len*100)/100f+"§cMo !");

			len = (Runtime.getRuntime().freeMemory()/(float)Runtime.getRuntime().maxMemory())/1073741824f;
			if(len>0.95)ServerUtils.permMsg("logs.ram",
					"§cLa mémoire est à §8"+Math.round(len*100)/100f+"§c% !");

		}else ServerUtils.permMsg("logs.logs",
				"§cImpossible de trouver le fichiers des logs ! Redémarre le serveur pour réparer ce problème");
	}
}
