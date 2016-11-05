package org.klisho.crawler;

import org.klisho.crawler.handlers.Handler;

import java.io.File;
import java.util.Collections;
import java.util.List;


/**
 * Created by Ola-Mola on 05/11/16.
 */
public class Scanner {

    private final List<Handler> handlers;

    public Scanner(List<Handler> handlers) {
        this.handlers = Collections.unmodifiableList(handlers);
    }


    public void scan(File dir) {
        File[] listOfFiles = dir.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            } else if (file.isDirectory()) {
                System.out.println(file.getName() + File.separator);
                scan(file);
            }
        }
    }


}
