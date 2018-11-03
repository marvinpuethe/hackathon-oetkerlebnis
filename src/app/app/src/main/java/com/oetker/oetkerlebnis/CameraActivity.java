package com.oetker.oetkerlebnis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class CameraActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println("DEBUG: home");
                    startActivity(new Intent(CameraActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_camera:
                    System.out.println("DEBUG: camera");
                    return true;
                case R.id.navigation_settings:
                    System.out.println("DEBUG: settings");
                    //startActivity(new Intent(CameraActivity.this, SettingsActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_camera);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_preview, Camera2BasicFragment.newInstance())
                .commit();
    }
}
