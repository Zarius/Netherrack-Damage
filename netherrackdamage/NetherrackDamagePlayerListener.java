package netherrackdamage;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;

public class NetherrackDamagePlayerListener extends PlayerListener {
    private final NetherrackDamage plugin;
    private NDprops props;
    private NDcommands commands;
    Plugin permissionsPlugin;
    String[] protectedWorlds;

    public NetherrackDamagePlayerListener(NetherrackDamage instance, NDprops props, NDcommands commands) {
        plugin = instance;
        this.props = props;
        this.commands = commands;
    }
  
    int id = 0;
    int IsFirst = 0;
    int IsDmg = 0;
    int isPrt = 0;
    int dmgDelay;
    int dmgDealt;
    ItemStack air;
    @Override
    public void onPlayerMove(final PlayerMoveEvent event) {
        Location target = event.getTo();
        World world = target.getWorld();
            int i = 0;
            protectedWorlds = props.temp.split(",");
        for (int x = protectedWorlds.length; x>0; x--) {
            if (world.getName().equals(protectedWorlds[i])) {
                isPrt = 1;
            }
            i++;
        }
        if (isPrt == 0) {
        permissionsPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");
        final Block block = world.getBlockAt(new Location(world, target.getX(), target.getY() - 1, target.getZ()));
        final Block blockxs = world.getBlockAt(new Location(world, target.getX() - 0.4, target.getY(), target.getZ()));
        final Block blockxp = world.getBlockAt(new Location(world, target.getX() + 0.4, target.getY(), target.getZ()));
        final Block blockzs = world.getBlockAt(new Location(world, target.getX(), target.getY(), target.getZ() - 0.4));
        final Block blockzp = world.getBlockAt(new Location(world, target.getX(), target.getY(), target.getZ() + 0.4));
        dmgDealt = props.damageDealt * 2;
        dmgDelay = props.damageDelay * 20;
        air = new ItemStack(Material.AIR);
        if (block.getType() == Material.NETHERRACK || blockxs.getType() == Material.NETHERRACK || blockxp.getType() == Material.NETHERRACK || blockzs.getType() == Material.NETHERRACK || blockzp.getType() == Material.NETHERRACK) {
            if (IsFirst == 0) {
                 if (!event.getPlayer().getInventory().getBoots().equals(air) && props.bootMod.equals("Yes")) {
                     dmgDealt = dmgDealt / 2;
                 }
                 if (permissionsPlugin != null) {
                 if (!(plugin).permissionHandler.has(event.getPlayer(), "nd.protection")) {
                     IsDmg = 1;
                     IsFirst = 1;
                     id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                          public void run() {
                             event.getPlayer().damage(dmgDealt);
                          }
                     }, 0L, dmgDelay);
                 }
                 } else {
                     if (!event.getPlayer().isOp()) {
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
                Bukkit.getServer().getScheduler().cancelAllTasks();
                IsDmg = 0;
                IsFirst = 0;
            }
        }
    }
}
}
    