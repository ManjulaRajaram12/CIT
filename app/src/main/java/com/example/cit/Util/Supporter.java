package com.example.cit.Util;

import android.os.Environment;

import java.io.File;

public class Supporter {

    public static boolean isSdPresent() {

        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);

    }


    public static File getAppCommonPath() {

        File file = null;

        if (isSdPresent()) {
            File root_path = Environment.getExternalStorageDirectory();

            File CIT_common_path = new File(root_path.getAbsoluteFile() + "/"
                    + "Android/CIT");

            if (!CIT_common_path.exists()) {
                CIT_common_path.mkdirs();
            }

            file = CIT_common_path;
        }

        return file;

    }
}
