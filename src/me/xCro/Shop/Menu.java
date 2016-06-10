package me.xCro.Shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Menu
  implements InventoryHolder, Listener
{
  public static void openInventory(Player player)
  {
    String invName = Main.colorize(Main.plugin.getConfig().getString("GUI.name").replace("[BALANCE]", Integer.toString(CrystalHandler.checkCrystalBalance(player))));
    Inventory inv = Bukkit.createInventory(player, 27, invName);
    for (int i = 0; i <= 27; i++) {
      if ((CrystalHandler.StrConf("items." + i + ".display.name") != null) || (CrystalHandler.StrConf("items." + i + ".display.mythic") != null)) {
        inv.setItem(i, CrystalHandler.DisplayItem(i));
      }
    }
    player.openInventory(inv);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event)
  {
    if (event != null)
    {
      Player player = (Player)event.getWhoClicked();
      ItemStack clicked = event.getCurrentItem();
      Inventory inventory = event.getInventory();
      if (ChatColor.stripColor(inventory.getName()).contains(Main.plugin.getConfig().getString("GUI.contains")))
      {
        event.setCancelled(true);
        if ((clicked == null) || (clicked.getType() == Material.AIR)) {
          return;
        }
        if (event.getRawSlot() + 1 <= 27)
        {
          if (player.getInventory().firstEmpty() != -1)
          {
            int balance = CrystalHandler.checkCrystalBalance(player);
            int price = CrystalHandler.IntConf("items." + event.getRawSlot() + ".display.price");
            if (balance >= price)
            {
              if ((CrystalHandler.StrConf("items." + event.getRawSlot() + ".item.command") != null) && 
                (CrystalHandler.StrConf("items." + event.getRawSlot() + ".item.command").length() != 0))
              {
                String commandToRun = CrystalHandler.StrConf("items." + event.getRawSlot() + ".item.command");
                Main.print("Command1: " + commandToRun);
                commandToRun = commandToRun.replace("[PLAYER]", player.getName());
                Main.print("Command2: " + commandToRun);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToRun);
              }
              if (CrystalHandler.StrConf("items." + event.getRawSlot() + ".item.amount") != null) {
                player.getInventory().addItem(new ItemStack[] { CrystalHandler.GiveItem(event.getRawSlot()) });
              }
              player.getInventory().removeItem(new ItemStack[] { CrystalHandler.createCrystal(price) });
              player.updateInventory();
              
              Main.plugin.getConfig().set("extra.crystalsMade", Integer.valueOf(CrystalHandler.IntConf("extra.crystalsMade") + price));
              Main.plugin.getConfig().set("extra.itemsSold", Integer.valueOf(CrystalHandler.IntConf("extra.itemsSold") + 1));
              Main.plugin.saveConfig();
              if (CrystalHandler.StrConf("GUI.name").contains("[BALANCE]")) {
                openInventory(player);
              }
            }
            else
            {
              player.sendMessage(Main.colorize(Main.prefix + Main.plugin.getConfig().getString("message.noPay")));
            }
          }
          else
          {
            player.sendMessage(Main.colorize(Main.prefix + Main.plugin.getConfig().getString("message.noSpace")));
          }
        }
        else {
          return;
        }
        player.updateInventory();
      }
    }
  }
  
  static String fromConfig(String configItem)
  {
    return Main.plugin.getConfig().getString(configItem);
  }
  
  public Inventory getInventory()
  {
    return null;
  }
}
