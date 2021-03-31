package com.example.memoproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    UserFragment userFragment;
    CalenderFragment calenderFragment;
    ViewPager viewpager;

    //마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;

    //첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BottomNavigationView 에서 각 항목을 눌렀을때 해당하는 fragment로 이동시키기
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_memo:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.navigation_calender:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.navigation_search:
                        viewpager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        viewpager = findViewById(R.id.viewPager);
        viewpager.setOffscreenPageLimit(3);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        //초기 화면 프래그먼트
        mainFragment = new MainFragment();
        pagerAdapter.addItem(mainFragment);

        //두번째 페이지
        calenderFragment = new CalenderFragment();
        pagerAdapter.addItem(calenderFragment);

        //세번째 페이지
        userFragment = new UserFragment();
        pagerAdapter.addItem(userFragment);


        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);


        //ViewPager 스와이프하면 하단 BottomNavigationView 인덱스 따라오게 하는 부분
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // 뒤로가기 버튼 2번 터치하면 종료되는 이벤트
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

