package com.proctor.App.helper;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class CommonHelper {
    Context context;

    public CommonHelper() {
    }

    public CommonHelper(Context context) {
        this.context = context;
    }

    /**
     * check gps status
     *
     * @return true if GPS ON  otherwise false
     */
    public boolean isGpsPresent(final Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }


    /**
     * check internet connectivity
     *
     * @return true if connect the internet otherwise false
     */
    public boolean isConnectingToInternet(final Context context) {
        final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            /**
             * connectivity not null
             */
            final NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
