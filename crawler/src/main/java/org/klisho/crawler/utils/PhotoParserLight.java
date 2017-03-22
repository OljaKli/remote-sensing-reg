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


    public ArrayList<Date> getAvgExposureTime(File[] images) {
        Integer length = images.length;
        ArrayList<Date> times = new ArrayList<>(length);

        if (images.length > 10) {
            File firstPhoto = images[10]; //eleventh image

            File lastPhoto = images[length - 1];

//        Period period = Period.between(firstPhotoTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
//                lastPhotoTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            firstPhotoTime = getExposureTime(firstPhoto);
            lastPhotoTime = getExposureTime(lastPhoto);


            if (firstPhotoTime != null && lastPhotoTime != null) {
                DateTime first = new DateTime(firstPhotoTime);
                DateTime last = new DateTime(lastPhotoTime);
                org.joda.time.Period period = new org.joda.time.Period(first, last);

                double dSec = (double)(period.getSeconds()+period.getMinutes()*60);
                double interval =  dSec * 1000.0 / (length-11);


                for (int i = 10; i >= 0; i--) {
                    DateTime imageTime = first.minusMillis((int)(interval * i));
                    times.add(imageTime.toDate());
                }

                for (int j = 11; j < length; j++) {
                    DateTime imageTime = first.plusMillis((int)(interval * (j - 10)));
                    times.add(imageTime.toDate());
                }
            }
        }
        return times;
    }

}

