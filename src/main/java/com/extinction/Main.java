package com.proyectoextinction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        if (getCommand("extinctionmenu") != null) {
            getCommand("extinctionmenu").setExecutor(this);
        }
        getLogger().info("ProyectEXTINCTION ha sido activado correctamente.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ProyectEXTINCTION desactivado.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;
        openExtinctionMenu(player);
        return true;
    }

    public void openExtinctionMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 27, ChatColor.DARK_RED + "Inventario ProyectEXTINCTION");

        menu.setItem(10, getExtinctionHelmet());
        menu.setItem(11, getExtinctionChestplate());
        menu.setItem(12, getExtinctionLeggings());
        menu.setItem(13, getExtinctionBoots());
        
        ItemStack mobSpawnerInfo = new ItemStack(Material.ZOMBIE_HEAD);
        ItemMeta mobMeta = mobSpawnerInfo.getItemMeta();
        mobMeta.setDisplayName(ChatColor.RED + "Mobs Extintos");
        List<String> mobLore = new ArrayList<>();
        mobLore.add(ChatColor.GRAY + "Generados automáticamente en el mundo:");
        mobLore.add(ChatColor.YELLOW + "- Zombie Extinto (Daño: 30, Resistencia: 50)");
        mobLore.add(ChatColor.YELLOW + "- Creeper Extinto (Daño: 30, Resistencia: 50)");
        mobMeta.setLore(mobLore);
        mobSpawnerInfo.setItemMeta(mobMeta);
        
        menu.setItem(16, mobSpawnerInfo);

        player.openInventory(menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_RED + "Inventario ProyectEXTINCTION")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "¡Has obtenido un objeto de ProyectEXTINCTION!");
            }
        }
    }

    public ItemStack getExtinctionHelmet() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Casco Extinto");
        meta.addEnchant(Enchantment.PROTECTION, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExtinctionChestplate() {
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Pechera Extinta");
        meta.addEnchant(Enchantment.PROTECTION, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.THORNS, 5, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExtinctionLeggings() {
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Pantalones Extintos");
        meta.addEnchant(Enchantment.PROTECTION, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExtinctionBoots() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Botas Extintas");
        meta.addEnchant(Enchantment.PROTECTION, 10, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();
            if (zombie.getCustomName() == null || !zombie.getCustomName().contains("Extinto")) {
                zombie.setCustomName(ChatColor.DARK_RED + "Zombie Extinto");
                zombie.setCustomNameVisible(true);
                
                AttributeInstance attackAttr = zombie.getAttribute(Attribute.ATTACK_DAMAGE);
                if (attackAttr != null) attackAttr.setBaseValue(30.0);

                AttributeInstance healthAttr = zombie.getAttribute(Attribute.MAX_HEALTH);
                if (healthAttr != null) {
                    healthAttr.setBaseValue(50.0);
                    zombie.setHealth(50.0);
                }
            }
        } else if (event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            if (creeper.getCustomName() == null || !creeper.getCustomName().contains("Extinto")) {
                creeper.setCustomName(ChatColor.DARK_RED + "Creeper Extinto");
                creeper.setCustomNameVisible(true);
                creeper.setPowered(true);

                AttributeInstance healthAttr = creeper.getAttribute(Attribute.MAX_HEALTH);
                if (healthAttr != null) {
                    healthAttr.setBaseValue(50.0);
                    creeper.setHealth(50.0);
                }
            }
        }
    }
}
