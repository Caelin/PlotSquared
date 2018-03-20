package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.database.DBFunc;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;

@CommandDeclaration(
    command = "debugloadtest",
    permission = "plots.debugloadtest",
    description = "This debug command will force the reload of all plots in the DB",
    usage = "/plot debugloadtest",
    category = CommandCategory.DEBUG,
    requiredType = RequiredType.CONSOLE)
public class DebugLoadTest extends SubCommand {

  @Override
  public boolean onCommand(PlotPlayer player, String[] args) {
    PS.get().plots_tmp = DBFunc.getPlots();
    return true;
  }
}
