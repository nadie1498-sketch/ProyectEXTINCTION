package com.proyectoextinction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        // Registrar eventos y comandos
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("extinctionmenu").setExecutor(this);
        getLogger().info("ProyectEXTINCTION ha sido activado correctamente.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ProyectEXTINCTION desactivado.");
    }

    // --- COMANDO PARA ABRIR EL INVENTARIO PERSONALIZADO ---
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

        // Añadir elementos al menú
        menu.setItem(10, getExtinctionHelmet());
        menu.setItem(11, getExtinctionChestplate());
        menu.setItem(12, getExtinctionLeggings());
        menu.setItem(13, getExtinctionBoots());
        
        // Indicador de Mobs personalizados
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

    // Prevenir que roben los items del menú creativo personalizado al hacer clic
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

    // --- CREACIÓN DE LA ARMADURA EXTINTA CON SUPER ENCAHTS ---
    public ItemStack getExtinctionHelmet() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Casco Extinto");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExtinctionChestplate() {
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Pechera Extinta");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.THORNS, 5, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExtinctionLeggings() {
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Pantalones Extintos");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExtinctionBoots() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Botas Extintas");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        item.setItemMeta(meta);
        return item;
    }

    // --- CONFIGURACIÓN DE MOBS: DAÑO 30 Y RESISTENCIA 50 ---
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();
            if (!zombie.getCustomNameSafe().contains("Extinto")) {
                zombie.setCustomName(ChatColor.DARK_RED + "Zombie Extinto");
                zombie.setCustomNameVisible(true);
                
                // Configurar Daño de Ataque (30)
                AttributeInstance attackAttr = zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if (attackAttr != null) attackAttr.setBaseValue(30.0);

                // Configurar Resistencia / Vida Máxima (50)
                AttributeInstance healthAttr = zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (healthAttr != null) {
                    healthAttr.setBaseValue(50.0);
                    zombie.setHealth(50.0);
                }
            }
        } else if (event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            if (!creeper.getCustomNameSafe().contains("Extinto")) {
                creeper.setCustomName(ChatColor.DARK_RED + "Creeper Extinto");
                creeper.setCustomNameVisible(true);
                creeper.setPowered(true); // Se vuelve un creeper cargado para más poder

                // Configurar Resistencia / Vida Máxima (50)
                AttributeInstance healthAttr = creeper.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (healthAttr != null) {
                    healthAttr.setBaseValue(50.0);
                    creeper.setHealth(50.0);
                }
            }
        }
    }
}
