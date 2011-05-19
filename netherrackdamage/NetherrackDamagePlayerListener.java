package netherrackdamage;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import java.lang.String;

/**
 * Handle events for all Player related events
 * @author Dinnerbone
 */
public class NetherrackDamagePlayerListener extends PlayerListener {
    private final NetherrackDamage plugin;

    public NetherrackDamagePlayerListener(NetherrackDamage instance) {
        plugin = instance;
    }
  
    int id = 0;
    int IsFirst = 0;
    int IsDmg = 0;
    String pla = "0";
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location target = event.getTo();
        final World world = target.getWorld();
        pla = event.getPlayer().getName();
        final Block block = world.getBlockAt(new Location(world, target.getX(), target.getY() - 1, target.getZ()));
        if (block.getType() == Material.NETHERRACK) {
            if (IsFirst == 0) {
                 IsDmg = 1;
                 IsFirst = 1;
                 id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                     public void run() {
                        System.out.println("The Netherrack is hurting!");
                        event.getPlayer().damage(1);
                     }
                 }, 0L, 200L);
            }
        } else {
            if (IsDmg == 1) {
                Bukkit.getServer().getScheduler().cancelTask(id);
                IsDmg = 0;
                IsFirst = 0;
                System.out.println("The Netherrack has stopped hurting!");
            }
        }
    }
}
    