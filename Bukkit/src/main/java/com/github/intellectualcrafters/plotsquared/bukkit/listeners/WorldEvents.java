package com.github.intellectualcrafters.plotsquared.bukkit.listeners;

import com.github.intellectualcrafters.plotsquared.bukkit.generator.BukkitPlotGenerator;
import com.github.intellectualcrafters.plotsquared.plot.PS;
import com.github.intellectualcrafters.plotsquared.plot.generator.GeneratorWrapper;
import com.github.intellectualcrafters.plotsquared.plot.object.worlds.PlotAreaManager;
import com.github.intellectualcrafters.plotsquared.plot.object.worlds.SinglePlotAreaManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.ChunkGenerator;

public class WorldEvents implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onWorldInit(WorldInitEvent event) {
    World world = event.getWorld();
    String name = world.getName();
    PlotAreaManager manager = PS.get().getPlotAreaManager();
    if (manager instanceof SinglePlotAreaManager) {
      SinglePlotAreaManager single = (SinglePlotAreaManager) manager;
      if (single.isWorld(name)) {
        world.setKeepSpawnInMemory(false);
        return;
      }
    }
    ChunkGenerator gen = world.getGenerator();
    if (gen instanceof GeneratorWrapper) {
      PS.get().loadWorld(name, (GeneratorWrapper<?>) gen);
    } else {
      PS.get().loadWorld(name, new BukkitPlotGenerator(name, gen));
    }
  }
}
