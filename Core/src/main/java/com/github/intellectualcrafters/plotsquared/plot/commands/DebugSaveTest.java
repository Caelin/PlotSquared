package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.database.DBFunc;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;

import java.util.ArrayList;

@CommandDeclaration(
        command = "debugsavetest",
        permission = "plots.debugsavetest",
        category = CommandCategory.DEBUG,
        requiredType = RequiredType.CONSOLE,
        usage = "/plot debugsavetest",
        description = "This command will force the recreation of all plots in the DB")
public class DebugSaveTest extends SubCommand {

    @Override
    public boolean onCommand(final PlotPlayer player, String[] args) {
        ArrayList<Plot> plots = new ArrayList<Plot>();
        plots.addAll(PS.get().getPlots());
        MainUtil.sendMessage(player, "&6Starting `DEBUGSAVETEST`");
        DBFunc.createPlotsAndData(plots, new Runnable() {
            @Override
            public void run() {
                MainUtil.sendMessage(player, "&6Database sync finished!");
            }
        });
        return true;
    }
}
