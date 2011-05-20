package netherrackdamage;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
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
    String newdmg;
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        Location target = event.getTo();
        World world = target.getWorld();
        final Block block = world.getBlockAt(new Location(world, target.getX(), target.getY() - 1, target.getZ()));
        dmgDealt = props.damageDealt * 2;
        dmgDelay = props.damageDelay * 20;
        newdmg = String.valueOf(dmgDelay);
        if (block.getType() == Material.NETHERRACK) {
            if (IsFirst == 0) {
                 IsDmg = 1;
                 IsFirst = 1;
                 id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                     public void run() {
                        System.out.println("The Netherrack is hurting!");
                        // TODO: Check if player is wearing boots. If so, reduce damage dealt.
                        event.getPlayer().damage(dmgDealt);
                     }
                 }, 0L, dmgDelay);
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
    