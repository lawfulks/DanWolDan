package com.woongyi.lawfulks.danwoldan.ExpansionFile;

import com.google.android.vending.expansion.downloader.impl.DownloaderService;

/**
 * Created by lawfulks on 15. 7. 4..
 */
public class ServiceDownloader extends DownloaderService {

    public static final String BASE64_PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnKvRuODAr2zQBfjuJ9EqQ6ucqhiPee9rzsCC8hVkN8b+5aHlpceaLlRCfHhIOPHP6InUwLHdF+paH/W0baooMiRlowSjnld5QbUIUMZj5zU+/rXWKapGHNGcyVUazP0vYEc8EBdwNojPLgFd1TWFnNPfABddX2YqJ26cssdLmcdf4nij6K2dMuv22Y4wzdEFafQ90Z9lGi5HHjMvtN7NCLfPqfgpU+HnnA9msubk8VOkjTeKQfmRA7NTCv3VNrz9AJ1VKX01Qu3L0EslVfRZ5h60cJieaU5IaBC7mBwws7EddnDaZnJMPgvmOC5JEZQeZct5agEAtfPmHjKlvUXdWwIDAQAB";
    // You should also modify this salt
    public static final byte[] SALT = new byte[] {1, 42, -12, -1, 61, 98,
            -100, -12, 43, 2, -3, -4, 9, 5, -106, -109, -33, 45, -1, 84};

    @Override
    public String getPublicKey() {
        return BASE64_PUBLIC_KEY;
    }

    @Override
    public byte[] getSALT() {
        return SALT;
    }

    @Override
    public String getAlarmReceiverClassName() {
        return ReceiverAlarm.class.getName();
    }
}