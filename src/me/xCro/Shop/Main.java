package me.xCro.Shop;

import java.io.PrintStream;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
  extends JavaPlugin
{
  public static Main plugin;
  public static String prefix = "ERROR";
  
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(new Menu(), this);
    getCommand("crystalmerchant").setExecutor(new CommandListener());
    saveDefaultConfig();
    getConfig().addDefault("items", null);
    plugin = this;
    prefix = colorize(plugin.getConfig().getString("chat.prefix"));
    print("Enabled");
  }
  
  public void onDisable()
  {
    print("Disabled");
  }
  
  public static void print(String paramString)
  {
    System.out.println("[Crystals] " + paramString);
  }
  
  static String colorize(String string)
  {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
}
