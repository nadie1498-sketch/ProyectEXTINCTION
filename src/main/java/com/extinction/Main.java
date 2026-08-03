package com.extinction;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.CreativeModeTab;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public static ItemStack extintSword;
    public static ItemStack extintChestplate;
    public static CreativeModeTab extinctionTab;

    @Override
    public void onEnable() {
        getLogger().info("¡ProyectEXTINCTION activado correctamente!");

        // 1. Crear los ítems personalizados
        createExtinctItems();

        // 2. Crear y registrar la pestaña del modo creativo
        createExtinctionCreativeTab();
    }

    @Override
    public void onDisable() {
        getLogger().info("ProyectEXTINCTION desactivado.");
    }

    /**
     * Crea los ítems personalizados (espada y armadura).
     */
    private void createExtinctItems() {
        // --- Espada Extinta ---
        extintSword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta swordMeta = extintSword.getItemMeta();
        if (swordMeta != null) {
            swordMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Espada Extinta");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Una espada legendaria de tiempos olvidados.");
            lore.add(ChatColor.DARK_PURPLE + "Poder abrumador.");
            swordMeta.setLore(lore);
            swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
            swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
            swordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
            swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            extintSword.setItemMeta(swordMeta);
        }

        // --- Armadura Extinta ---
        extintChestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta chestMeta = extintChestplate.getItemMeta();
        if (chestMeta != null) {
            chestMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Armadura Extinta");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Forjada con restos de un mundo destruido.");
            lore.add(ChatColor.DARK_PURPLE + "Resistencia extrema.");
            chestMeta.setLore(lore);
            chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
            chestMeta.addEnchant(Enchantment.THORNS, 5, true);
            chestMeta.addEnchant(Enchantment.DURABILITY, 3, true);
            chestMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            extintChestplate.setItemMeta(chestMeta);
        }
    }

    /**
     * Crea y registra la nueva pestaña del modo creativo.
     */
    private void createExtinctionCreativeTab() {
        // Crea la nueva pestaña con un icono (la Espada Extinta) y un título
        extinctionTab = CreativeModeTab.builder(new NamespacedKey(this, "extinction_tab"))
                .icon(() -> extintSword)
                .title(ChatColor.DARK_RED + "ProyectEXTINCTION")
                .displayItems((params, output) -> {
                    // Añade los ítems personalizados a la pestaña
                    output.accept(extintSword);
                    output.accept(extintChestplate);
                    // Opcionalmente, añade otros ítems vanilla que quieras
                    output.accept(Material.NETHERITE_INGOT);
                })
                .build();
        
        // Registra la nueva pestaña
        getServer().getPluginManager().addCreativeModeTab(extinctionTab);
    }
}
