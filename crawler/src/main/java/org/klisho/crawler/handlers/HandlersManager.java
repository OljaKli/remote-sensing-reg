package org.klisho.crawler.handlers;

import org.hibernate.Session;
import org.klisho.crawler.utils.SessionF;

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

    private final Session session;

    private HandlersManager() {
        this.session = new SessionF().openSession();
    }

    public static HandlersManager createDefaultHandlersManager() {


        //create session, передать через конструктор в photoHandler, там сессию сохранить, а не использовать каждый раз новую
        HandlersManager mngr = new HandlersManager();
        //mngr.handlers.add(new ImageryDirHandler());
        //mngr.handlers.add(new PhotoScanFileHandler());
        //mngr.handlers.add(new OrthoFileHandler());
//        mngr.handlers.add(new KmlHandler());
       // mngr.handlers.add(new PStxtFileHandler());
        mngr.handlers.add(new FrameZipHandler());
        mngr.handlers.add(new PhotoHandler(mngr.session));


        //TODO add another handlers

        mngr.pruneFilters.add(new SASPlanetPruneFilter());

        return mngr;
    }

    public void closeSession(){
        this.session.close();
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
