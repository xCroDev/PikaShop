package me.xCro.Shop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandListener
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("crystals"))
    {
      if (args.length == 1)
      {
        if (args[0].equalsIgnoreCase("reload"))
        {
          if ((sender instanceof Player))
          {
            Player player = (Player)sender;
            if ((player.hasPermission("mcrystals.reload")) || (player.isOp()))
            {
              Main.plugin.reloadConfig();
              player.sendMessage(Main.colorize(Main.prefix + "&aReloaded!"));
              Main.print("Reloaded");
            }
            else
            {
              player.sendMessage(Main.colorize(Main.prefix + Main.plugin.getConfig().getString("message.noReloadPerm")));
            }
          }
          else
          {
            Main.plugin.reloadConfig();
            Main.print("Reloaded");
          }
        }
        else if ((args[0].equalsIgnoreCase("shop")) && ((sender instanceof Player)))
        {
          Player player = (Player)sender;
          if ((player.hasPermission("mcrystals.open")) || (player.isOp())) {
            Menu.openInventory(player);
          } else {
            player.sendMessage(Main.colorize(Main.prefix + Main.plugin.getConfig().getString("message.noOpenPerm")));
          }
        }
        else if ((sender instanceof Player))
        {
          Player player = (Player)sender;
          player.sendMessage(Main.colorize(Main.prefix + "&c(PIKA) Try that again!"));
        }
        else
        {
          Main.print("Try that again!");
        }
      }
      else
      {
        if (args.length == 3)
        {
          if (!args[0].equalsIgnoreCase("give")) {
            break label782;
          }
          if ((sender instanceof Player))
          {
            Player player = (Player)sender;
            if ((player.hasPermission("mcrystals.give")) || (player.isOp())) {
              try
              {
                try
                {
                  Player targetPlayer = Bukkit.getPlayer(args[1]);
                  targetPlayer.getInventory().addItem(new ItemStack[] { CrystalHandler.createCrystal(Integer.parseInt(args[2])) });
                  player.sendMessage(Main.colorize(Main.prefix + Main.plugin.getConfig().getString("message.giveItem").replace("[PLAYER]", args[1]).replace("[AMOUNT]", args[2])));
                  Main.print("Giving " + args[1] + " " + args[2] + " gems!");
                }
                catch (NullPointerException npe)
                {
                  player.sendMessage(Main.colorize(Main.prefix + "&c(PIKA) Player not found!"));
                }
                player.sendMessage(Main.colorize(Main.prefix + Main.plugin.getConfig().getString("message.noGivePerm")));
              }
              catch (NumberFormatException e)
              {
                player.sendMessage(Main.colorize(Main.prefix + "&c(PIKA) Invalid Amount!"));
              }
            }
            break label782;
          }
          try
          {
            try
            {
              Player targetPlayer = Bukkit.getPlayer(args[1]);
              targetPlayer.getInventory().addItem(new ItemStack[] { CrystalHandler.createCrystal(Integer.parseInt(args[2])) });
              Main.print("Giving " + args[1] + " " + args[2] + " gems!");
            }
            catch (NullPointerException npe)
            {
              Main.print("Player not found");
            }
            if (!(sender instanceof Player)) {
              break label777;
            }
          }
          catch (NumberFormatException e)
          {
            Main.print("Invalid Amount!");
          }
        }
        else
        {
          Player player = (Player)sender;
          player.sendMessage(Main.colorize(Main.prefix + "&c(PIKA) Try that again!"));
          break label782;
        }
        label777:
        Main.print("Try that again!");
      }
      label782:
      return true;
    }
    return false;
  }
}
