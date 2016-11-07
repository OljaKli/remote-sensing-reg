package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 05/11/16.
 */
public class ImageryDirHandler implements Handler {

    private static final Set<String> IMAGERY_EXTENTIONS = new HashSet<String>() {{
        add("jpeg");
        add("jpg");
        add("png");
        add("arw");
        add("tif");
        add("tiff");
    }};

    private long nDirs = 0;
    private long nImages = 0;


    @Override
    public boolean canHandle(File res) {
        if (!res.isDirectory()) {
            return false;
        }

        File[] files = res.listFiles(
                new FileFilter() { //anonymous class
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) {
                    return false;
                }

                String ext = FilenameUtils.getExtension(pathname.getAbsolutePath());
                if (ext != null && IMAGERY_EXTENTIONS.contains(ext.toLowerCase())) {
                    return true;
                }
                return false;
            }
        });

        if (files.length > 9) {
            nDirs++;
            nImages += files.length;
            return true;
            //TODO check for better ext distinguishing
        }
        return false;

    }

    @Override
    public void handle(File res) {
        System.out.println(res.getAbsolutePath() + res.separator);
        System.out.println(this);
    }

    public long getDirsNum() {
        return nDirs;
    }

    public long getImagesNum() {
        return nImages;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImageryDirHandler{");
        sb.append("nDirs=").append(nDirs);
        sb.append(", nImages=").append(nImages);
        sb.append('}');
        return sb.toString();
    }
}
