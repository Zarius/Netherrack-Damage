package netherrackdamage;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import java.io.*;

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
                 } else {
                     try{
    FileInputStream fstream = new FileInputStream("ops.txt");
    // Get the object of DataInputStream
    DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String strLine;
    //Read File Line By Line
    while ((strLine = br.readLine()) != null)   {
      // Print the content on the console
      if (!event.getPlayer().getName().equalsIgnoreCase(strLine)) {
                    IsDmg = 1;
                     IsFirst = 1;
                     id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                          public void run() {
                             event.getPlayer().damage(dmgDealt);
                          }
                     }, 0L, dmgDelay);
      }
    }
    //Close the input stream
    in.close();
    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
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
    