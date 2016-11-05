package org.klisho.crawler.handlers;

import java.io.File;

/**
 * Created by Ola-Mola on 05/11/16.
 */
public interface Handler {

    boolean canHandle(File res); //res - resource
    void handle(File res);
}
