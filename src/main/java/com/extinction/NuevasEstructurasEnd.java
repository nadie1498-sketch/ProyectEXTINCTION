package com.extinction;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import java.util.Random;

public class NuevasEstructurasEnd extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        // Hacemos que haya un 5% de probabilidad de que aparezca nuestra estructura en este chunk
        if (random.nextInt(100) < 5) {
            
            // Elegimos una coordenada X y Z al azar dentro de este pedazo de mundo (chunk)
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            
            // Buscamos el bloque más alto en esas coordenadas para no construir flotando
            int y = world.getHighestBlockYAt(chunk.getX() * 16 + x, chunk.getZ() * 16 + z);
            
            // Obtenemos el bloque exacto en esa posición
            Block bloqueBase = chunk.getBlock(x, y, z);
            
            // Verificamos que estamos construyendo sobre la piedra del End
            if (bloqueBase.getType() == Material.END_STONE) {
                
                // ¡Construimos una estructura muy simple! (Ejemplo: Un pilar de obsidiana)
                chunk.getBlock(x, y + 1, z).setType(Material.OBSIDIAN);
                chunk.getBlock(x, y + 2, z).setType(Material.OBSIDIAN);
                chunk.getBlock(x, y + 3, z).setType(Material.CRYING_OBSIDIAN);
            }
        }
    }
}
