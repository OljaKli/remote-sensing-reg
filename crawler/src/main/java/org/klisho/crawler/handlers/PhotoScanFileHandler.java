package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 07/11/16.
 */
public class PhotoScanFileHandler implements Handler {

    public static final Set<String> PS_PROJ_EXTENTIONS = new HashSet<String>() {{
        add("psx");
//        add("psz");
    }};

    public static final Set<String> ARCHIVE_EXTENTIONS = new HashSet<String>() {{
        add("zip");
    }};

    public static final Set<String> PS_FILES_EXTENTIONS = new HashSet<String>() {{
        add("files");
    }};

    private long nPSFiles = 0;

    private String psxFile;
    private String filesFile;


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
                        if (ext != null && PS_PROJ_EXTENTIONS.contains(ext.toLowerCase())) {
                            psxFile = pathname.getAbsolutePath();
                            return true;
                        }
                        if (ext != null && PS_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                            filesFile = pathname.getAbsolutePath();
                        }
                        return false;
                    }
                });

        if (files.length > 0) {
            nPSFiles += files.length;
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

    public long getnPSFiles() {
        return nPSFiles;
    }

    public String getPsxFile() {
        return psxFile;
    }
    public String getFilesFile() {
        return filesFile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhotoScanFileHandler{");
        sb.append("nPSFiles=").append(nPSFiles);
        sb.append('}');
        return sb.toString();
    }
}
