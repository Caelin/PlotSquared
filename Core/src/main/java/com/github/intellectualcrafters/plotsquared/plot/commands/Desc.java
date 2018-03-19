package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.flag.FlagManager;
import com.github.intellectualcrafters.plotsquared.plot.flag.Flags;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;

@CommandDeclaration(
        command = "setdescription",
        permission = "plots.set.desc",
        description = "Set the plot description",
        usage = "/plot desc <description>",
        aliases = {"desc", "setdesc", "setd", "description"},
        category = CommandCategory.SETTINGS,
        requiredType = RequiredType.NONE)
public class Desc extends SetCommand {

    @Override
    public boolean set(PlotPlayer player, Plot plot, String desc) {
        if (desc.isEmpty()) {
            plot.removeFlag( Flags.DESCRIPTION);
            MainUtil.sendMessage(player, C.DESC_UNSET);
            return true;
        }
        boolean result = FlagManager.addPlotFlag(plot, Flags.DESCRIPTION, desc);
        if (!result) {
            MainUtil.sendMessage(player, C.FLAG_NOT_ADDED);
            return false;
        }
        MainUtil.sendMessage(player, C.DESC_SET);
        return true;
    }
}
