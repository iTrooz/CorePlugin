package fr.entasia.coreplugin.utils;

import fr.entasia.apis.ServerUtils;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class Task extends BukkitRunnable {

	@Override
	public void run() {
		File a = new File("logs/latest.log");
		if(a.exists()){
			double d = a.length()/1024f/1024f;
			if(d>1) ServerUtils.permMsg("log.logs",
					"§cLes logs font plus de §8"+Math.round(d*100)/100f+"§cMo !");

			d = (Runtime.getRuntime().freeMemory()/(float)Runtime.getRuntime().maxMemory())/1073741824f;
			if(d>0.90)ServerUtils.permMsg("log.ram",
					"§cLa mémoire est à §8"+Math.round(d*100)/100f+"§c% !");

			d = Bukkit.getServer().getTPS()[0];
			if(d<19.5)ServerUtils.permMsg("log.tps",
					"§cLes TPS sont à moins de §8"+Math.round(d*100)/100f+"§c% !");

		}else ServerUtils.permMsg("log.logs",
				"§cImpossible de trouver le fichiers des logs ! Redémarre le serveur pour réparer ce problème");
	}
}
