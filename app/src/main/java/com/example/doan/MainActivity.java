package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.SearchEvent;

import com.example.doan.fragment.HomeFragment;
import com.example.doan.fragment.LoginFragment;
import com.example.doan.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mnBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mnBottom = findViewById(R.id.navMenu);
        //
        loadFragment(new HomeFragment());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mnBottom.setOnItemSelectedListener(getListener());
    }

    @NonNull
    private NavigationBarView.OnItemSelectedListener getListener() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnHome:
                        loadFragment(new HomeFragment());
                        return  true;
                    case R.id.mnLogin:
                        loadFragment(new LoginFragment());
                        return true;
                    case R.id.mnSetting:
                        loadFragment(new SettingFragment());
                        return true;
                    case R.id.mnSearch:
                        loadFragment(new SettingFragment());
                        return true;
                }
                return true;
            }
        };
    }

    void loadFragment(Fragment fmNew)
    {
        FragmentTransaction fmTran = getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.main_fragment, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }
}