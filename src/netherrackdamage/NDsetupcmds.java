package netherrackdamage;

import org.bukkit.event.Event.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.*;
import org.bukkit.plugin.Plugin;

public class NDsetupcmds {
    private final NetherrackDamage plugin;
    private NDcommands cmdHandle;
    private NDprops props;
    Player player;
    Plugin permissionsPlugin;
    
    public NDsetupcmds(NetherrackDamage instance, NDcommands cmdHandle, NDprops props) {
    plugin = instance;
    this.cmdHandle = cmdHandle;
    this.props = props;
}
    
    public void setupCommands() {
        PluginCommand dmgdealt = plugin.getCommand("dmgdealt");
        PluginCommand dmgdelay = plugin.getCommand("dmgdelay");
        PluginCommand bootmod = plugin.getCommand("bootmod");
        permissionsPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");
        CommandExecutor commandExecutor = new CommandExecutor() {
            public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
                if (sender instanceof Player) {
                    player = (Player)sender;
                }
                if (permissionsPlugin != null) {
                 if ((plugin).permissionHandler.has(player, "nd.admin")) {
                        if (label.equals("dmgdealt")) {
                            cmdHandle.chngDmgDealt(sender, args[0]);
                        }
                        if (label.equals("dmgdelay")) {
                            cmdHandle.chngDmgDelay(sender, args[0]);
                        }
                        if (label.equals("bootmod")) {
                            cmdHandle.chngBootMod(sender, args[0]);
                        }
                 }
                    } else {
                     if (player.isOp()) {
                         if (label.equals("dmgdealt")) {
                            cmdHandle.chngDmgDealt(sender, args[0]);
                        }
                        if (label.equals("dmgdelay")) {
                            cmdHandle.chngDmgDelay(sender, args[0]);
                        }
                        if (label.equals("bootmod")) {
                            cmdHandle.chngBootMod(sender, args[0]);
                        }
                     }
                 }
                  
                return true;
                    }
            };
        if (dmgdealt != null) {
            dmgdealt.setExecutor(commandExecutor);
        }
        if (dmgdelay != null) {
            dmgdelay.setExecutor(commandExecutor);
        }
        if (bootmod != null) {
            bootmod.setExecutor(commandExecutor);
        }
        }
}
