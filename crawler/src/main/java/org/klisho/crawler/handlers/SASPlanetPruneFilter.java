package org.klisho.crawler.handlers;

import java.io.File;

/**
 * Created by Ola-Mola on 25/12/16.
 */
public class SASPlanetPruneFilter implements PruneFilter {

    public boolean prune(File dir) {
        //Volumes/upload_AFS/test_Bugry/backup/Downloads/SAS.Planet.Release.151111/SAS.Planet.Release.151111/cache/maps.yandex.com.Map/z18/74/x76397/37
        if (dir.getAbsolutePath().contains("SAS.Planet")) {
            return true;
        }
        return false;
    }
}
