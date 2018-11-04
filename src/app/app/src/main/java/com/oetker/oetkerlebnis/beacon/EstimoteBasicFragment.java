/*
package com.oetker.oetkerlebnis.beacon;

import com.estimote.indoorsdk.EstimoteCloudCredentials;
import com.estimote.indoorsdk.IndoorLocationManagerBuilder;
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener;
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.CloudCallback;
import com.estimote.indoorsdk_module.cloud.CloudCredentials;
import com.estimote.indoorsdk_module.cloud.EstimoteCloudException;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManager;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManagerFactory;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.cloud.LocationPosition;

public class EstimoteBasicFragment {
    private CloudCredentials cloudCredentials;
    private IndoorCloudManager cloudManager;
    private ScanningIndoorLocationManager indoorLocationManager;

    public EstimoteBasicFragment() {
        cloudCredentials = new EstimoteCloudCredentials("YOUR APP ID HERE", "YOUR APP TOKEN HERE");
        cloudManager = new IndoorCloudManagerFactory().create(applicationContext, cloudCredentials);

        public final Location location = cloudManager.getLocation("IDK", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {
                // success
            }

            @Override
            public void failure(EstimoteCloudException e) {
                // oops!
            }
        });

        ScanningIndoorLocationManager indoorLocationManager = new IndoorLocationManagerBuilder(this, location, cloudCredentials).withDefaultScanner().build();

        indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
            @Override
            public void onPositionUpdate(LocationPosition locationPosition) {
                //TODO: notify user within range
            }

            @Override
            public void onPositionOutsideLocation() {
                //TODO: notify user outside range
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        indoorLocationManager.startPositioning();
    }

    @Override
    protected void onStop() {
        super.onStop();
        indoorLocationManager.stopPositioning();
    }
}
*/