package com.example.memoproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    UserFragment userFragment;
    CalenderFragment calenderFragment;
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = findViewById(R.id.viewPager);
        viewpager.setOffscreenPageLimit(3);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        userFragment = new UserFragment();
        pagerAdapter.addItem(userFragment);
        mainFragment = new MainFragment();
        pagerAdapter.addItem(mainFragment);
        calenderFragment = new CalenderFragment();
        pagerAdapter.addItem(calenderFragment);

        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}

