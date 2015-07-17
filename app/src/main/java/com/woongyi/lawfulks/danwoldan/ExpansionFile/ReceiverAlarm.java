package com.woongyi.lawfulks.danwoldan.ExpansionFile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;

import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;

/**
 * Created by lawfulks on 15. 7. 4..
 */
public class ReceiverAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            DownloaderClientMarshaller.startDownloadServiceIfRequired(context,
                    intent, ServiceDownloader.class);
        } catch (NameNotFoundException e) {
        }
    }
}
