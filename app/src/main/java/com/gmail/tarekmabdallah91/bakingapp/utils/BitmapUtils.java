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
import android.os.Environment;

import com.gmail.tarekmabdallah91.bakingapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.DATE_PATTERN;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_FILE_NAME;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.IMAGE_SUFFIX;

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
}
