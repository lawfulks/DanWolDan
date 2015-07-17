package com.woongyi.lawfulks.danwoldan.Utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import com.android.vending.expansion.zipfile.ZipResourceFile;

import java.io.IOException;

/**
 * Created by lawfulks on 15. 7. 14..
 */
public class AssetFileDescript {
    private static int PACKAGE_VERSION_CODE = 14;

    public static AssetFileDescriptor getAssetFileDescriptor(Context context, String filePath) {
        String packageName = context.getPackageName();

        String zipFilePath = "/mnt/sdcard/Android/obb/" + packageName + "/main." + PACKAGE_VERSION_CODE + "."+ packageName + ".obb";

        ZipResourceFile expansionFile = null;
        AssetFileDescriptor assetFileDescriptor = null;

        try {
            expansionFile = new ZipResourceFile(zipFilePath);
            assetFileDescriptor = expansionFile.getAssetFileDescriptor(filePath);
        } catch (IOException e) {
        }

        return assetFileDescriptor;
    }
}
