package netherrackdamage;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class NetherrackDamage extends JavaPlugin {
    private final NDprops props = new NDprops(this);
    private final NetherrackDamagePlayerListener playerListener = new NetherrackDamagePlayerListener(this, props);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public static PermissionHandler permissionHandler;
    String permiss;
    
    private void setupPermissions() {
      Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

      if (this.permissionHandler == null) {
          if (permissionsPlugin != null) {
              this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
              System.out.println("[Netherrack-Damage] hooked into Permissions.");
              permiss = "Yes";
          } else {
              // TODO: read ops.txt file if Permissions isn't found.
              System.out.println("[Netherrack-Damage] Permissions not found! Using ops.txt file.");
              permiss = "No";
          }
      }
    }

    public void onDisable() {
        System.out.println("[Netherrack-Damage] has been safely disabled.");
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        setupPermissions();
        props.doConfig(permiss);
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( "[Netherrack-Damage] version v" + pdfFile.getVersion() + " is enabled." );
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
