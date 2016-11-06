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

    public static HandlersManager createDefaultHandlersManager() {
        HandlersManager mngr = new HandlersManager();
        mngr.handlers.add(new ImageryDirHandler());
        //TODO add another handlers

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

}
