package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.Argument;
import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.database.DBFunc;
import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.Permissions;
import com.github.intellectualcrafters.plotsquared.plot.util.UUIDHandler;
import com.github.intellectualcrafters.plotsquared.plot.util.WorldUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@CommandDeclaration(command = "kick",
        aliases = {"k"},
        description = "Kick a player from your plot",
        permission = "plots.kick",
        usage = "<player>",
        category = CommandCategory.TELEPORT,
        requiredType = RequiredType.NONE)
public class Kick extends SubCommand {

    public Kick() {
        super( Argument.PlayerName);
    }

    @Override
    public boolean onCommand(PlotPlayer player, String[] args) {
        Location location = player.getLocation();
        Plot plot = location.getPlot();
        if (plot == null) {
            return !sendMessage(player, C.NOT_IN_PLOT);
        }
        if ((!plot.hasOwner() || !plot.isOwner(player.getUUID())) && !Permissions.hasPermission(player, C.PERMISSION_ADMIN_COMMAND_KICK)) {
            MainUtil.sendMessage(player, C.NO_PLOT_PERMS);
            return false;
        }
        Set<UUID> uuids = MainUtil.getUUIDsFromString(args[0]);
        if (uuids.isEmpty()) {
            MainUtil.sendMessage(player, C.INVALID_PLAYER, args[0]);
            return false;
        }
        Set<PlotPlayer> players = new HashSet<>();
        for (UUID uuid : uuids) {
            if (uuid == DBFunc.everyone) {
                for (PlotPlayer pp : plot.getPlayersInPlot()) {
                    if (pp == player || Permissions.hasPermission(pp, C.PERMISSION_ADMIN_ENTRY_DENIED)) {
                        continue;
                    }
                    players.add(pp);
                }
                continue;
            }
            PlotPlayer pp = UUIDHandler.getPlayer(uuid);
            if (pp != null) {
                players.add(pp);
            }
        }
        players.remove(player); // Don't ever kick the calling player
        if (players.isEmpty()) {
            MainUtil.sendMessage(player, C.INVALID_PLAYER, args[0]);
            return false;
        }
        for (PlotPlayer player2 : players) {
            if (!plot.equals(player2.getCurrentPlot())) {
                MainUtil.sendMessage(player, C.INVALID_PLAYER, args[0]);
                return false;
            }
            if (player2.hasPermission("plots.admin.entry.denied")) {
                C.CANNOT_KICK_PLAYER.send(player, player2.getName());
                return false;
            }
            Location spawn = WorldUtil.IMP.getSpawn(location.getWorld());
            C.YOU_GOT_KICKED.send(player2);
            if (plot.equals(spawn.getPlot())) {
                Location newSpawn = WorldUtil.IMP.getSpawn(PS.get().getPlotAreaManager().getAllWorlds()[0]);
                if (plot.equals(newSpawn.getPlot())) {
                    // Kick from server if you can't be teleported to spawn
                    player2.kick(C.YOU_GOT_KICKED.s());
                } else {
                    player2.teleport(newSpawn);
                }
            } else {
                player2.teleport(spawn);
            }
        }
        return true;
    }
}
