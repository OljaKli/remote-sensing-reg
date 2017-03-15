package org.klisho.crawler.handlers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ola-Mola on 06/11/16.
 * contains list of handlers and applies them in given order
 */

public class HandlersManager {

    private final List<Handler> handlers = new LinkedList<>();
    private final List<PruneFilter> pruneFilters = new LinkedList<>();


    public static HandlersManager createDefaultHandlersManager() {
        HandlersManager mngr = new HandlersManager();
        //mngr.handlers.add(new ImageryDirHandler());
        //mngr.handlers.add(new PhotoScanFileHandler());
        //mngr.handlers.add(new OrthoFileHandler());
//        mngr.handlers.add(new KmlHandler());
       // mngr.handlers.add(new PStxtFileHandler());
        mngr.handlers.add(new PhotoHandler());

        //TODO add another handlers

        mngr.pruneFilters.add(new SASPlanetPruneFilter());

        return mngr;
    }


    /**
     * apply handlers to the given resource
     */
    public void handle(File res) {
        for (Handler handler : handlers) {
            if (handler.canHandle(res)) {
                handler.handle(res);
            }
        }
    }

    @Override
    public String toString () {
        StringBuilder strB = new StringBuilder();
        for (Handler handler : handlers) {
            strB.append(handler.toString());
        }


        return strB.toString();
    }

    public boolean prune(File dir) {
        for (PruneFilter filter : pruneFilters) {
            if (filter.prune(dir)) {
                return true;
            }
        }
        return false;
    }
}
