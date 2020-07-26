package com.rodimisas.dash_cam_video.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.rodimisas.dash_cam_video.Adapters.DevicePagerAdapter;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.utils.Util;

public class DetailDeviceActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String cookie;
    private EquipmentItem equipo;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_device);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            equipo = (EquipmentItem) bundle.getSerializable("device");
        } else{
        }

        Bundle args = new Bundle();
        args.putSerializable("equipo",equipo);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(getString(R.color.colorWhite)));
        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.addTab(tabLayout.newTab().setText("Ubicación"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        DevicePagerAdapter adapter = new DevicePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),args);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(DetailDeviceActivity.this, "Selected -> "+tab.getText(), Toast.LENGTH_SHORT).show();
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Toast.makeText(DetailDeviceActivity.this, "Unselected -> "+tab.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Toast.makeText(DetailDeviceActivity.this, "Reselected -> "+tab.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                this.finish();
                return true;
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_forget_logout:
                Util.removeSharedPreferences(prefs);
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}