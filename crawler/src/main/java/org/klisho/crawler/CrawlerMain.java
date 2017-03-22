package org.klisho.crawler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.klisho.crawler.handlers.Handler;
import org.klisho.crawler.handlers.HandlersManager;
import org.klisho.crawler.handlers.ImageryDirHandler;
import org.klisho.crawler.utils.SessionF;

public class CrawlerMain {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args)); //look at what is written in console
        // TODO advanced CLI parser
        // https://commons.apache.org/proper/commons-cli/

        String path = args[0]; // TODO check index

        File root = new File(path);
        if (root.exists() && root.isDirectory()) {



            HandlersManager mngr = HandlersManager.createDefaultHandlersManager();

            Scanner scanner = new Scanner(mngr);
            scanner.scan(root);

            mngr.closeSession();


        } else {
            System.err.println("directory is not available: " + path);
        }

    }
}
