package com.woongyi.lawfulks.danwoldan.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.woongyi.lawfulks.danwoldan.ExpansionFile.ServiceDownloader;
import com.woongyi.lawfulks.danwoldan.ExpansionFile.XAPKFile;
import com.woongyi.lawfulks.danwoldan.Utils.PreferenceUtil;

/**
 * Created by lawfulks on 15. 6. 9..
 */
public class ActivityIntro extends Activity {

    private final XAPKFile[] xAPKS = {
            new XAPKFile(
                    true, 1, 32567352L
            )
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!expansionFilesDelivered()) {
            Intent notifierIntent = new Intent(this, getClass());
            notifierIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notifierIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            try {
                int startResult = DownloaderClientMarshaller.startDownloadServiceIfRequired(this,
                        pendingIntent, ServiceDownloader.class);

                if (startResult != DownloaderClientMarshaller.NO_DOWNLOAD_REQUIRED) {
                    finish();
                    return;
                }
            } catch (PackageManager.NameNotFoundException e) {
            }

        }

        int currentVersion = checkVersion();

        String appVersion = PreferenceUtil.instance(getApplicationContext()).getStringPref("appVersion");

        if (appVersion == null || currentVersion > Integer.valueOf(appVersion)) {
            PreferenceUtil.instance(getApplicationContext()).setStringPref("appVersion", String.valueOf(currentVersion));
            PreferenceUtil.instance(getApplicationContext()).setBooleanPref("dbFileCheck", true);
        } else if (currentVersion == Integer.valueOf(appVersion)) {
            PreferenceUtil.instance(getApplicationContext()).setBooleanPref("dbFileCheck", false);
        } else {
            PreferenceUtil.instance(getApplicationContext()).setStringPref("appVersion", String.valueOf(currentVersion));
        }

        Intent mainIntent = new Intent(this, ActivityMain.class);
        startActivity(mainIntent);
        finish();
    }

    private int checkVersion() {
        int version = 0;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return version;
    }

    private boolean expansionFilesDelivered() {
        for (XAPKFile xf : xAPKS) {
            String fileName = Helpers.getExpansionAPKFileName(this, xf.mIsMain,
                    xf.mFileVersion);
            if (!Helpers.doesFileExist(this, fileName, xf.mFileSize, false))
                return false;
        }
        return true;
    }
}
