/*
 Copyright 2018 tarekmabdallah91@gmail.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.gmail.tarekmabdallah91.bakingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;

import com.gmail.tarekmabdallah91.bakingapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.DATE_PATTERN;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_FILE_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_SUFFIX;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ONE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.TWO;

public class BitmapUtils {


    public static File createImageFile(Context context) throws IOException {
        String timeStamp =
                new SimpleDateFormat(DATE_PATTERN,
                        Locale.getDefault()).format(new Date());
        String imageFileName = String.format(IMAGE_FILE_NAME, timeStamp);
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir != null) {
            Timber.v(context.getString(R.string.storage_dir_msg), storageDir.getAbsolutePath());
        }
        return File.createTempFile(
                imageFileName,        /* prefix */
                IMAGE_SUFFIX,         /* suffix */
                storageDir            /* directory */
        );
    }

    /**
     * to convert Selected image uri to Bitmap
     * create BitmapFactory.Options object and assign it's "inJustDecodeBounds = true";
     * then convert the uri to a decodedStream to get width and height from it
     * then get the suitable scale according to the current selected image size by while loop ..
     * then create new BitmapFactory.Options object and assign the scale to it's "inSampleSize"
     * then convert the uri of the selected image to a a decodedStream to return it as a Bitmap
     *
     * @param context          of the activity
     * @param selectedImageUri the uri of the selected image
     * @param REQUIRED_SIZE    the size that we want to set the image
     * @return Bitmap
     */
    public static Bitmap uriToBitmap(Context context, Uri selectedImageUri, int REQUIRED_SIZE) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImageUri),
                    null, options);

            int width = options.outWidth;
            int height = options.outHeight;
            int scale = ONE;

            while (true) {
                if (width / TWO < REQUIRED_SIZE || height / TWO < REQUIRED_SIZE) {
                    break;
                }
                width /= TWO;
                height /= TWO;
                scale *= TWO;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImageUri),
                    null, options2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * to rotate image by 90
     *
     * @param source
     * @param angle
     * @return
     */
    private static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
