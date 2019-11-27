package com.example.dalyeodalyeok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.dalyeodalyeok.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;

    private Context mContext;
    private  FloatingActionButton fab;
    private FloatingActionButton fab_sub1, fab_sub2;

    private Animation fab_open, fab_close;

    private boolean isFabOpen = false;

    static String userCheckList;
    static String myDate = HomeFragment.getMyDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mContext = getApplicationContext();
        setSupportActionBar(toolbar);
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);

        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab = findViewById(R.id.fab);
        fab_sub1 = (FloatingActionButton) findViewById(R.id.fab_sub1);

        fab_sub2 = (FloatingActionButton) findViewById(R.id.fab_sub2);
        fab.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_lms, R.id.nav_background, R.id.nav_font)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        System.out.println("날짜 : " + myDate);
    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab:

                toggleFab();

                break;

            case R.id.fab_sub1: // add check list button - 팝업창


                toggleFab();

                OnClickHandler();
                Toast.makeText(this, "Camera Open-!", Toast.LENGTH_SHORT).show();

                break;



            case R.id.fab_sub2: // 일정추가 버튼

                toggleFab();

                Toast.makeText(this, "Map Open-!", Toast.LENGTH_SHORT).show();
                Intent intentSchedule = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intentSchedule);

                break;

        }

    }

        public void OnClickHandler()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add Check List");
            builder.setMessage("추가 할 내용을 입력해 주세요.");

            final EditText name = new EditText(this);
            builder.setView(name);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(MainActivity.this, HomeFragment.class);
                    userCheckList = name.getText().toString();
                    homeIntent.putExtra("userCheckList", userCheckList);
                    startActivity(homeIntent);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                }
            });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    private void toggleFab() {

        if (isFabOpen) {

            fab_sub1.startAnimation(fab_close);

            fab_sub2.startAnimation(fab_close);

            fab_sub1.setClickable(false);

            fab_sub2.setClickable(false);

            isFabOpen = false;

        } else {

            fab_sub1.startAnimation(fab_open);

            fab_sub2.startAnimation(fab_open);

            fab_sub1.setClickable(true);

            fab_sub2.setClickable(true);

            isFabOpen = true;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static String getList() {
        return userCheckList;
    }
}
