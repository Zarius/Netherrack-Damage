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
    String[] blockIDs;
    String[] wool;
    int data;

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
    int ids;
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
        final Block blockUnder = world.getBlockAt(new Location(world, target.getX(), target.getY() - 1, target.getZ()));
        final Block blockxs = world.getBlockAt(new Location(world, target.getX() - 0.4, target.getY(), target.getZ()));
        final Block blockxp = world.getBlockAt(new Location(world, target.getX() + 0.4, target.getY(), target.getZ()));
        final Block blockzs = world.getBlockAt(new Location(world, target.getX(), target.getY(), target.getZ() - 0.4));
        final Block blockzp = world.getBlockAt(new Location(world, target.getX(), target.getY(), target.getZ() + 0.4));
        final Block blockxsHigh = world.getBlockAt(new Location(world, target.getX() - 0.4, target.getY()+1, target.getZ()));
        final Block blockxpHigh = world.getBlockAt(new Location(world, target.getX() + 0.4, target.getY()+1, target.getZ()));
        final Block blockzsHigh = world.getBlockAt(new Location(world, target.getX(), target.getY()+1, target.getZ() - 0.4));
        final Block blockzpHigh = world.getBlockAt(new Location(world, target.getX(), target.getY()+1, target.getZ() + 0.4));
        dmgDealt = props.damageDealt * 2;
        dmgDelay = props.damageDelay * 20;
        air = new ItemStack(Material.AIR);

        boolean isBlockUnder = false;
        boolean isBlockUpperSide = false;
        boolean isBlockLowerSide = false;
        boolean safeUpper = false;
        boolean safeLower = false;
        boolean safeUnder = false;
        
        int y = 0;
            blockIDs = props.blockID.split(",");
        for (int x = blockIDs.length; x>0; x--) {
            if (blockIDs[y].contains(":")) {
                wool = blockIDs[y].split("[:]");
                data = Integer.parseInt(wool[1]);
                ids = Integer.parseInt(wool[0]);
            } else {
                ids = Integer.parseInt(blockIDs[y]);
            }
            if (blockUnder.getTypeId() == ids) {
                if (ids == 6 || ids == 17 || ids == 35 || ids == 44) {
                    if (blockUnder.getData() == data) {
                    	isBlockUnder = true;
                    }
                } else {
                	isBlockUnder = true;
                }
            }
            
            if (blockxs.getTypeId() == ids || blockxp.getTypeId() == ids || blockzs.getTypeId() == ids || blockzp.getTypeId() == ids) {
                if (ids == 6 || ids == 17 || ids == 35 || ids == 44) {
                    if (blockxs.getData() == data || blockxp.getData() == data || blockzs.getData() == data || blockzp.getData() == data) {
                    	isBlockLowerSide = true;
                    }
                } else {
                	isBlockLowerSide = true;
                }
            }
            
            if (blockxsHigh.getTypeId() == ids || blockxpHigh.getTypeId() == ids || blockzsHigh.getTypeId() == ids || blockzpHigh.getTypeId() == ids) {            
                if (ids == 6 || ids == 17 || ids == 35 || ids == 44) {
                    if (blockxsHigh.getData() == data || blockxpHigh.getData() == data || blockzsHigh.getData() == data || blockzpHigh.getData() == data) {
                    	isBlockUpperSide = true;
                    }
                } else {
                	isBlockUpperSide = true;
                }
            }
            
            y++;
        }
        if (isBlockUnder || isBlockLowerSide || isBlockUpperSide) {
        	if (IsFirst == 0) {
        		if (!event.getPlayer().getInventory().getBoots().equals(air) && props.bootMod.equals("Yes")) {
        			dmgDealt = dmgDealt / 2;
        		}

        		// Armor Mod
        		if (props.armorMod.equals("Yes")) {
        			if (isBlockUpperSide) {
        				if ( !(event.getPlayer().getInventory().getChestplate().equals(air))) {
        					safeUpper = true;
        				}
        			} else {
    					safeUpper = true;        				
        			}
        			
        			if (isBlockLowerSide) {
        				if (!(event.getPlayer().getInventory().getLeggings().equals(air)) && props.armorMod.equals("Yes") && isBlockLowerSide) {
        					safeLower = true; 					
        				}
        			} else {
    					safeLower = true; 					
        			}
        			
        			if (isBlockUnder) {
        				if ( !event.getPlayer().getInventory().getBoots().equals(air)) {
        					safeUnder = true;
        				}
        			} else {
    					safeUnder = true;
        			}
        			
        			if (safeUnder && safeUpper && safeLower) {
        				dmgDealt = 0;
        			}
        		}

        		if (props.debugMessages.equals("Yes")) {
        			event.getPlayer().sendMessage("Debug: damage dealt - " + dmgDealt + ", blockunder - " + isBlockUnder + ", blockUpperside - " + isBlockUpperSide + ", blockLowerside - " + isBlockLowerSide);
        		}

        		if (dmgDealt > 0) {
        			if (permissionsPlugin != null) {

        				if (!(plugin).permissionHandler.has(event.getPlayer(), "nd.protection")) {
        					IsDmg = 1;
        					IsFirst = 1;
        					if (props.painMessages.equals("Yes")) {
        						event.getPlayer().sendMessage(props.painMessage);
        					}
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
        					if (props.painMessages.equals("Yes")) {
        						event.getPlayer().sendMessage(props.painMessage);
        					}
        					id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
        						public void run() {
        							event.getPlayer().damage(dmgDealt);
        						}
        					}, 0L, dmgDelay);
        				}
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
    