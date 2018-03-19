package com.github.intellectualcrafters.plotsquared.plot.object.comment;

import com.github.intellectualcrafters.plotsquared.plot.database.DBFunc;
import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal;
import com.github.intellectualcrafters.plotsquared.plot.util.TaskManager;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

public class InboxPublic extends CommentInbox {

    @Override
    public boolean getComments(final Plot plot, final RunnableVal<List<PlotComment>> whenDone) {
        Optional<ArrayList<PlotComment>> comments = plot.getSettings().getComments(toString());
        if (comments.isPresent()) {
            whenDone.value = comments.get();
            TaskManager.runTask(whenDone);
            return true;
        }
        DBFunc.getComments(plot, toString(), new RunnableVal<List<PlotComment>>() {
            @Override
            public void run(List<PlotComment> value) {
                whenDone.value = value;
                if (value != null) {
                    for (PlotComment comment : value) {
                        plot.getSettings().addComment(comment);
                    }
                }
                TaskManager.runTask(whenDone);
            }
        });
        return true;
    }
    
    @Override
    public boolean addComment(Plot plot, PlotComment comment) {
        plot.getSettings().addComment(comment);
        DBFunc.setComment(plot, comment);
        return true;
    }
    
    @Override
    public String toString() {
        return "public";
    }


}
