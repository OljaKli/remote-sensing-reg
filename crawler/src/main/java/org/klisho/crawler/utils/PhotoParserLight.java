package org.klisho.crawler.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.IntToDoubleFunction;

/**
 * Created by Ola-Mola on 21/03/17.
 */
public class PhotoParserLight {


    //private File jpegFile = new File("myImage.jpg");
    private Metadata metadata;
    Date firstPhotoTime;
    Date lastPhotoTime;


    public Date getExposureTime(File file) {
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

        return date;
    }

    private static final int MAX_PHOTOS_ACTUALLY_READ = 10;

    public ArrayList<Date> getAvgExposureTime(File[] images) {
        ArrayList<Date> times = new ArrayList<>(images.length);

        for (int i = 0; i <= Math.min(images.length, MAX_PHOTOS_ACTUALLY_READ); i++) {
            Date imageTime = getExposureTime(images[i]);
            times.add(imageTime);
        }

        if (images.length > MAX_PHOTOS_ACTUALLY_READ) { // 11
            File firstPhoto = images[MAX_PHOTOS_ACTUALLY_READ - 1]; //eleventh image
            File lastPhoto = images[images.length - 1];

            firstPhotoTime = getExposureTime(firstPhoto);
            lastPhotoTime = getExposureTime(lastPhoto);

            if (firstPhotoTime != null && lastPhotoTime != null) {
                DateTime first = new DateTime(firstPhotoTime);
                DateTime last = new DateTime(lastPhotoTime);
                org.joda.time.Period period = new org.joda.time.Period(first, last);

                double dSec = (double)(period.getSeconds()+period.getMinutes()*60);
                double interval =  dSec * 1000.0 / (images.length - MAX_PHOTOS_ACTUALLY_READ);

                for (int i = MAX_PHOTOS_ACTUALLY_READ; i < images.length; i++) {
                    DateTime imageTime = first.plusMillis((int)(interval * (i - MAX_PHOTOS_ACTUALLY_READ + 1)));
                    times.add(imageTime.toDate());
                }
            }
        }
        return times;
    }

}

