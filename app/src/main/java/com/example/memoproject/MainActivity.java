package com.example.memoproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    UserFragment userFragment;
    CalenderFragment calenderFragment;
    ViewPager viewpager;
    Toolbar toolbar;
    ActionBar actionBar;

    //마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;

    //첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
//
//        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);
//        actionBar.setCustomView(actionbar);
//
//        Toolbar parent = (Toolbar) actionbar.getParent();
//        parent.setContentInsetsAbsolute(0,0);
//
//        return true;
//    }

    @Override
    public void onBackPressed() {

        //마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        //마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        //2000milliseconds = 2seconds
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this,"\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        //마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        //마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        //현재 표시된 Toast 취소

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
            toast.cancel();
        }


    }

}

