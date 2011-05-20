package netherrackdamage;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import java.lang.String;
import java.util.*;

/**
 * Handle events for all Player related events
 * @author Dinnerbone
 */
public class NetherrackDamagePlayerListener extends PlayerListener {
    private final NetherrackDamage plugin;
    private NDprops props;

    public NetherrackDamagePlayerListener(NetherrackDamage instance, NDprops props) {
        plugin = instance;
        this.props = props;
    }
  
    int id = 0;
    int IsFirst = 0;
    int IsDmg = 0;
    int dmgDelay;
    int dmgDealt;
    ItemStack air;
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        Location target = event.getTo();
        World world = target.getWorld();
        final Block block = world.getBlockAt(new Location(world, target.getX(), target.getY() - 1, target.getZ()));
        dmgDealt = props.damageDealt * 2;
        dmgDelay = props.damageDelay * 20;
        air = new ItemStack(Material.AIR);
        if (block.getType() == Material.NETHERRACK) {
            if (IsFirst == 0) {
                 if (!event.getPlayer().getInventory().getBoots().equals(air)) {
                     dmgDealt = dmgDealt / 2;
                 }
                 if (props.perm.equals("Yes")) {
                 if (!(plugin).permissionHandler.has(event.getPlayer(), "nd.protection")) {
                     IsDmg = 1;
                     IsFirst = 1;
                     id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                          public void run() {
                             event.getPlayer().damage(dmgDealt);
                          }
                     }, 0L, dmgDelay);
                 }
                 }
            }
        } else {
            if (IsDmg == 1) {
                Bukkit.getServer().getScheduler().cancelTask(id);
                IsDmg = 0;
                IsFirst = 0;
            }
        }
    }
}
    