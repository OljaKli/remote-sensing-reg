package org.klisho.crawler.handlers;

import java.io.File;

/**
 * Created by Ola-Mola on 05/11/16.
 */
public class ImageryDirHandler implements Handler {


    @Override
    public boolean canHandle(File res) {
        if (!res.isDirectory()) {
            return false;
        }




        return false;
    }

    @Override
    public void handle(File res) {

    }
}
