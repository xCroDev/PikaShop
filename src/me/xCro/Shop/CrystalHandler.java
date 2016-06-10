package me.xCro.Shop;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class CrystalHandler
{
  static ItemStack createCrystal(int amount)
  {
    String name = "INK_SACK";
    if (Main.plugin.getConfig().getString("crystal.item") != null) {
      name = Main.plugin.getConfig().getString("crystal.item");
    }
    short durability = 4;
    if (Main.plugin.getConfig().getString("crystal.durability") != null) {
      durability = (short)Main.plugin.getConfig().getInt("crystal.durability");
    }
    ItemStack crystal = new ItemStack(Material.getMaterial(name), amount);
    crystal.setDurability(durability);
    
    crystal = setName(crystal, "crystal.name");
    crystal = setLore(crystal, "crystal.lores");
    if ((Main.plugin.getConfig().getString("crystal.glow") != null) && 
      (Main.plugin.getConfig().getBoolean("crystal.glow"))) {
      crystal.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
    }
    return crystal;
  }
  
  static ItemStack setName(ItemStack item, String path)
  {
    String name = null;
    if (Main.plugin.getConfig().getString(path) != null) {
      name = Main.plugin.getConfig().getString(path);
    }
    if (name == null) {
      return item;
    }
    ItemMeta itemMeta = item.getItemMeta();
    itemMeta.setDisplayName(Main.colorize(name));
    item.setItemMeta(itemMeta);
    return item;
  }
  
  static ItemStack setLore(ItemStack item, String path)
  {
    if (Main.plugin.getConfig().getString(path) == null) {
      return item;
    }
    ItemMeta itemMeta = item.getItemMeta();
    List<String> lore = new ArrayList();
    for (String s : Main.plugin.getConfig().getStringList(path)) {
      lore.add(Main.colorize(s));
    }
    itemMeta.setLore(lore);
    item.setItemMeta(itemMeta);
    return item;
  }
  
  static int checkCrystalBalance(Player player)
  {
    int balance = 0;
    ItemStack[] playerInv = player.getInventory().getContents();
    ItemStack[] arrayOfItemStack1;
    int j = (arrayOfItemStack1 = playerInv).length;
    for (int i = 0; i < j; i++)
    {
      ItemStack item = arrayOfItemStack1[i];
      if ((item != null) && 
        (item.getType() != Material.AIR) && 
        (item.equals(createCrystal(item.getAmount())))) {
        balance += item.getAmount();
      }
    }
    return balance;
  }
  
  static ItemStack DisplayItem(int slot)
  {
    if (StrConf("items." + slot) != null)
    {
      int setAmount = IntConf("items." + slot + ".display.amount");
      int price = IntConf("items." + slot + ".display.price");
      
      int setItem = IntConf("items." + slot + ".display.item");
      int durability = IntConf("items." + slot + ".display.durability");
      
      ItemStack stack = new ItemStack(setItem, setAmount);
      stack.setDurability((short)durability);
      
      int itemsSold = IntConf("extra.itemsSold");
      int crystalsMade = IntConf("extra.crystalsMade");
      
      ItemMeta meta = stack.getItemMeta();
      meta.setDisplayName(Main.colorize(StrConf("items." + slot + ".display.name")));
      
      ArrayList<String> lore = new ArrayList();
      for (int i = 1; i <= 6; i++) {
        if (StrConf("items." + slot + ".display.lore." + i) != null) {
          if (StrConf("items." + slot + ".display.lore." + i).length() != 0)
          {
            String newLore = StrConf("items." + slot + ".display.lore." + i);
            newLore = newLore.replace("[PRICE]", Integer.toString(price));
            newLore = newLore.replace("[AMOUNT]", Integer.toString(setAmount));
            newLore = newLore.replace("[ITEMSSOLD]", Integer.toString(itemsSold));
            newLore = newLore.replace("[CRYSTALSMADE]", Integer.toString(crystalsMade));
            lore.add(Main.colorize(newLore));
          }
        }
      }
      meta.setLore(lore);
      stack.setItemMeta(meta);
      for (int i = 1; i <= 8; i++) {
        if (StrConf("items." + slot + ".display.enchantments." + i + ".enchantment") != null)
        {
          String enchantment = StrConf("items." + slot + ".display.enchantments." + i + ".enchantment");
          int level = IntConf("items." + slot + ".display.enchantments." + i + ".level");
          
          stack.addUnsafeEnchantment(Enchantment.getByName(enchantment), level);
        }
      }
      return stack;
    }
    return null;
  }
  
  static ItemStack GiveItem(int slot)
  {
    if (StrConf("items." + slot) != null)
    {
      int setItem = IntConf("items." + slot + ".item.item");
      int setAmount = IntConf("items." + slot + ".item.amount");
      int durability = IntConf("items." + slot + ".item.durability");
      
      ItemStack stack = new ItemStack(setItem, setAmount);
      stack.setDurability((short)durability);
      
      ItemMeta meta = stack.getItemMeta();
      meta.setDisplayName(Main.colorize(StrConf("items." + slot + ".item.name")));
      
      ArrayList<String> lore = new ArrayList();
      for (int i = 1; i <= 6; i++) {
        if (StrConf("items." + slot + ".item.lore." + i) != null) {
          if (StrConf("items." + slot + ".item.lore." + i).length() != 0) {
            lore.add(Main.colorize(StrConf("items." + slot + ".item.lore." + i)));
          }
        }
      }
      meta.setLore(lore);
      stack.setItemMeta(meta);
      for (int i = 1; i <= 8; i++) {
        if (StrConf("items." + slot + ".item.enchantments." + i + ".enchantment") != null)
        {
          String enchantment = StrConf("items." + slot + ".item.enchantments." + i + ".enchantment");
          int level = IntConf("items." + slot + ".item.enchantments." + i + ".level");
          if (setItem == 403)
          {
            EnchantmentStorageMeta enMeta = (EnchantmentStorageMeta)stack.getItemMeta();
            enMeta.addStoredEnchant(Enchantment.getByName(enchantment), level, true);
            stack.setItemMeta(enMeta);
          }
          else
          {
            stack.addUnsafeEnchantment(Enchantment.getByName(enchantment), level);
          }
        }
      }
      return stack;
    }
    return null;
  }
  
  static String StrConf(String param)
  {
    return Main.plugin.getConfig().getString(param);
  }
  
  static int IntConf(String param)
  {
    return Main.plugin.getConfig().getInt(param);
  }
}
