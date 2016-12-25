package org.klisho.crawler;

import org.klisho.crawler.handlers.Handler;
import org.klisho.crawler.handlers.HandlersManager;

import java.io.File;
import java.util.Collections;
import java.util.List;


/**
 * Created by Ola-Mola on 05/11/16.
 */
public class Scanner {

    private final HandlersManager handlersMngr;


    public Scanner(HandlersManager handlerMngr) {
        this.handlersMngr = handlerMngr;
    }


    public void scan(File dir) {
        File[] listOfFiles = dir.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                // System.out.println(file.getName());
                handlersMngr.handle(file);

                //''' System.out.println(handlersMngr);

            } else if (file.isDirectory()) {
                //System.out.println(file.getName() + File.separator);
                handlersMngr.handle(file);
                //  System.out.println(handlersMngr);
                if (!handlersMngr.prune(file)) {
                    scan(file);
                }
            }


        }

    }


}
