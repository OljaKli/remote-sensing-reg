package org.klisho.crawler.handlers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ola-Mola on 27/12/16.
 */
public class KmlPruneFilter implements PruneFilter {

    @Override
    public boolean prune(File dir) {
//        //String [] fileNames;
//        String separator = "_";
//
//        String[] fileRegex = dir.getAbsolutePath().split(separator);
////      Pattern pattern = Pattern.compile(file.getAbsolutePath());
//        boolean bool = false;
//        int i = 0;
//
//        File[] listOfFiles = dir.listFiles();
//        for (File file : listOfFiles) {
//            if (file.isFile()) {
//                String[] neighFileRegex = file.getAbsolutePath().split(separator);
//                for (String partNeigh : neighFileRegex) {
//                  for (String part : fileRegex) {
//                      if (partNeigh.contains(part)){
//                          i++;
//                      }
//                  }
//                }
//                if (i >= 3){
//
//                }
//
//
//
//            } else if (file.isDirectory()) {
//                handlersMngr.handle(file);
//                //  System.out.println(handlersMngr);
//                if (!handlersMngr.prune(file)) {
//                    scan(file);
//                }
//            }
//        }
//
//
//        String[] result = pattern.split(separator);
//        for (File fileEx : neighbourFiles) {
//            Matcher matcher = pattern.matcher(fileEx.getAbsolutePath());
//            boolean match = matcher.matches();
//            if (match = true) {
//                bool = true;
//            } else bool = false;
//        }
//        return bool;

        return false;
    }

}
