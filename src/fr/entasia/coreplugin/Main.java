package fr.entasia.coreplugin;

import fr.entasia.apis.socket.SocketClient;
import fr.entasia.coreplugin.commands.*;
import fr.entasia.coreplugin.listeners.Basic;
import fr.entasia.coreplugin.listeners.Others;
import fr.entasia.coreplugin.utils.ConsoleFilter;
import fr.entasia.coreplugin.utils.Task;
import fr.entasia.coreplugin.utils.Warp;
import net.milkbowl.vault.chat.Chat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin{

	public static Main main;
	public static Chat vaultChat=null;
	public static String lockdown;
	public static List<UUID> gods = new ArrayList<>();
	public static List<String> spyers = new ArrayList<>();
	public static List<Warp> warps = new ArrayList<>();
	public static File datafile;
	public static FileConfiguration dataconfig;

	@Override
	public void onEnable() {
		try{
			main = this;
			try{
				vaultChat = getServer().getServicesManager().getRegistration(Chat.class).getProvider();
				getLogger().info("Vault chargé avec succès ! Système de préfix activé !");
			}catch(NullPointerException | NoClassDefFoundError e){
				getLogger().info("Vault non trouvé ! Système de préfix désactivé !");
			}


			datafile = new File(getDataFolder()+"/data.yml");
			if(!datafile.exists())saveResource("data.yml", false);
			dataconfig = YamlConfiguration.loadConfiguration(datafile);
			loadWarps();

			getCommand("tp").setExecutor(new TPCmd());
			getCommand("tphere").setExecutor(new TPHereCmd());
			getCommand("gamemode").setExecutor(new GamemodeCmd());
			getCommand("god").setExecutor(new GodCommand());
			getCommand("speed").setExecutor(new SpeedCmd());
			getCommand("lockdown").setExecutor(new LockCmd());
			getCommand("clear").setExecutor(new ClearCmd());
			getCommand("top").setExecutor(new TopCmd());
			getCommand("nightvision").setExecutor(new NVCmd());
			getCommand("bwarp").setExecutor(new BWarpCmd());
			getCommand("setbwarp").setExecutor(new SetBWarpCmd());
			getCommand("delbwarp").setExecutor(new DelBWarpCmd());
			getCommand("whois").setExecutor(new WhoIsCmd());
			getCommand("commandspy").setExecutor(new CmdSpyToggleCmd());
			getCommand("fly").setExecutor(new FlyCmd());
			getCommand("crashcmd").setExecutor(new CrashCmd());
			getCommand("amount").setExecutor(new AmountCmd());
			getCommand("ram").setExecutor(new RAMCmd());
			getCommand("runas").setExecutor(new RunasCmd());

			getCommand("skull").setExecutor(new SkullCmd());
			getCommand("heal").setExecutor(new HealCmd());

			getServer().getPluginManager().registerEvents(new Basic(), this);
			getServer().getPluginManager().registerEvents(new Others(), this);


			Logger logger = (Logger) LogManager.getRootLogger();
			logger.addFilter(new ConsoleFilter());

			new Task().runTaskTimerAsynchronously(this, 0, 4800); // 4 * 60 * 20 = 4800

			if(dataconfig.getStringList("spyers")!=null) spyers.addAll(dataconfig.getStringList("spyers"));
			lockdown = dataconfig.getString("lockdown");

			SocketClient.sendData("onlines 0");
			getLogger().info("Plugin activé !");
		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("LE SERVEUR VA S'ETEINDRE !");
			Bukkit.getServer().shutdown();
		}
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Plugin désactivé !");
	}
	
	private static void loadWarps() {
		warps.clear();
		ConfigurationSection config = dataconfig.getConfigurationSection("warps");
		if(config==null) main.getLogger().warning("Aucune warp détectée !");
		else{
			for (String name : config.getKeys(false)) {
				Location loc = parseLocation(config.getString(name + ".location"));
				warps.add(new Warp(loc, config.getString(name+".world"), name, config.getString(name + ".creator")));
			}
		}
	}

	private static Location parseLocation(String coos) {
		String[] args = coos.split(",");
		return new Location(null,
				Double.parseDouble(args[0])+0.5,
				Double.parseDouble(args[1]),
				Double.parseDouble(args[2])+0.5);
	}

}
