package com.extinction;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("¡ProyectEXTINCTION activado correctamente!");
        if (getCommand("extinction") != null) {
            getCommand("extinction").setExecutor(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ProyectEXTINCTION desactivado.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("extinction")) {
            // --- Crear Espada Extinta ---
            ItemStack extintSword = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta swordMeta = extintSword.getItemMeta();
            if (swordMeta != null) {
                swordMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Espada Extinta");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Una espada legendaria de tiempos olvidados.");
                swordMeta.setLore(lore);
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
                swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
                swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                extintSword.setItemMeta(swordMeta);
            }

            // --- Crear Armadura Extinta ---
            ItemStack extintChestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemMeta chestMeta = extintChestplate.getItemMeta();
            if (chestMeta != null) {
                chestMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Armadura Extinta");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Forjada con restos de un mundo destruido.");
                chestMeta.setLore(lore);
                chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
                chestMeta.addEnchant(Enchantment.DURABILITY, 3, true);
                chestMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                extintChestplate.setItemMeta(chestMeta);
            }

            // Entregar al jugador
            player.getInventory().addItem(extintSword);
            player.getInventory().addItem(extintChestplate);
            player.sendMessage(ChatColor.GREEN + "¡Has recibido los ítems de ProyectEXTINCTION!");
            return true;
        }
        return false;
    }
}
