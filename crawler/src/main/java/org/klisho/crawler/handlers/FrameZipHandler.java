package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.klisho.crawler.utils.parsers.FrameZipParser;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import static org.klisho.crawler.handlers.PhotoScanFileHandler.ARCHIVE_EXTENTIONS;

/**
 * Created by Ola-Mola on 02/04/17.
 */
public class FrameZipHandler implements Handler {

    String frameZipPath;

    @Override
    public boolean canHandle(File res) {
        if (!res.isFile()) {
            return false;
        }

        //String ext = FilenameUtils.getExtension(res.getAbsolutePath());
        if (res.getName().equals("frame.zip")) {
            frameZipPath = res.getAbsolutePath();
            return true;
        }
        else return false;
    }

    public void handle(File res) {
        FrameZipParser zipParser = new FrameZipParser();
        ArrayList<String> photos = zipParser.frameZipParse(frameZipPath);
    }
}
