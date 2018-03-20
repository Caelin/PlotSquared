package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.generator.HybridPlotManager;
import com.github.intellectualcrafters.plotsquared.plot.generator.HybridPlotWorld;
import com.github.intellectualcrafters.plotsquared.plot.generator.HybridUtils;
import com.github.intellectualcrafters.plotsquared.plot.object.ChunkLoc;
import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotArea;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.MathMan;

@CommandDeclaration(
    command = "debugroadregen",
    usage = "/plot debugroadregen",
    requiredType = RequiredType.NONE,
    description = "Regenerate all roads based on the road schematic",
    category = CommandCategory.DEBUG,
    permission = "plots.debugroadregen")
public class DebugRoadRegen extends SubCommand {

  @Override
  public boolean onCommand(PlotPlayer player, String[] args) {
    Location loc = player.getLocation();
    PlotArea plotArea = loc.getPlotArea();
    if (!(plotArea instanceof HybridPlotWorld)) {
      return sendMessage(player, C.NOT_IN_PLOT_WORLD);
    }
    Plot plot = player.getCurrentPlot();
    if (plot == null) {
      ChunkLoc chunk = new ChunkLoc(loc.getX() >> 4, loc.getZ() >> 4);
      int extend = 0;
      if (args.length == 1) {
        if (MathMan.isInteger(args[0])) {
          try {
            extend = Integer.parseInt(args[0]);
          } catch (NumberFormatException ignored) {
            C.NOT_VALID_NUMBER.send(player, "(0, <EXTEND HEIGHT>)");
            return false;
          }
        }
      }
      boolean result = HybridUtils.manager.regenerateRoad(plotArea, chunk, extend);
      MainUtil.sendMessage(player,
          "&6Regenerating chunk: " + chunk.x + ',' + chunk.z + "\n&6 - Result: " + (result
              ? "&aSuccess" : "&cFailed"));
      MainUtil.sendMessage(player, "&cTo regenerate all roads: /plot regenallroads");
    } else {
      HybridPlotManager manager = (HybridPlotManager) plotArea.getPlotManager();
      manager.createRoadEast(plotArea, plot);
      manager.createRoadSouth(plotArea, plot);
      manager.createRoadSouthEast(plotArea, plot);
      MainUtil.sendMessage(player,
          "&6Regenerating plot south/east roads: " + plot.getId() + "\n&6 - Result: &aSuccess");
      MainUtil.sendMessage(player, "&cTo regenerate all roads: /plot regenallroads");
    }
    return true;
  }
}
