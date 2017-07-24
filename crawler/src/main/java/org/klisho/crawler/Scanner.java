package org.klisho.crawler;

import org.klisho.crawler.handlers.Handler;
import org.klisho.crawler.handlers.HandlersManager;

import java.io.File;
import java.util.Arrays;
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
        if (listOfFiles == null) {
            System.err.println("Failed to read content of " + dir.getAbsolutePath());
            return ;
        }

        if (listOfFiles != null) {
            Arrays.sort(listOfFiles);
        }

        for (File file : listOfFiles) {

            if (file.isFile()) {
                handlersMngr.handle(file);

            } else if (file.isDirectory()) {
                handlersMngr.handle(file);

                if (!handlersMngr.prune(file)) {
                    scan(file);
                }
            }
        }
        //hm.closeSession();

    }


}
