package com.github.intellectualcrafters.plotsquared.plot.object.worlds;

import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.flag.Flag;
import com.github.intellectualcrafters.plotsquared.plot.object.BlockLoc;
import com.github.intellectualcrafters.plotsquared.plot.object.Location;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotArea;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotId;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.RegionWrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class SinglePlot extends Plot
{
    public SinglePlot(PlotArea area, PlotId id, UUID owner) {
        super(area, id, owner);
    }

    public SinglePlot(PlotArea area, PlotId id) {
        super(area, id);
    }

    public SinglePlot(PlotArea area, PlotId id, UUID owner, int temp) {
        super(area, id, owner, temp);
    }

    public SinglePlot(PlotId id, UUID owner, HashSet<UUID> trusted, HashSet<UUID> members, HashSet<UUID> denied, String alias, BlockLoc position, Collection<Flag> flags, PlotArea area, boolean[] merged, long timestamp, int temp) {
        super(id, owner, trusted, members, denied, alias, position, flags, area, merged, timestamp, temp);
    }

    @Override
    public String getWorldName() {
        return getId().toCommaSeparatedString();
    }

    @Override
    public SinglePlotArea getArea() {
        return (SinglePlotArea) super.getArea();
    }

    public boolean teleportPlayer(final PlotPlayer player) {
        if (isLoaded()) {
            return super.teleportPlayer(player);
        } else {
            C.NOT_LOADED.send(player);
            return false;
        }
    }

    @Override
    public Location getSide() {
        return getCenter();
    }

    @Override
    protected boolean isLoaded() {
        getArea().loadWorld(getId());
        return super.isLoaded();
    }
    private HashSet<RegionWrapper> regions;
    {
        regions = new HashSet<>();
        regions.add(new RegionWrapper(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Override
    public HashSet<RegionWrapper> getRegions() {
        return regions;
    }

    // getCenter getSide getHome getDefaultHome getBiome
}
