package org.klisho.crawler.handlers;

import java.io.File;

/**
 *
 */
public interface PruneFilter {
    boolean prune(File dir);
}
