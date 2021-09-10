package com.example.worker_recruitment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_add_post)
        {
            Intent ij =new Intent(getApplicationContext(),add_post.class);
            startActivity(ij);
        }
        else if(id==R.id.nav_complaint_sent)
        {
            Intent ij =new Intent(getApplicationContext(),complaint_send.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_profile)
        {
            Intent ij =new Intent(getApplicationContext(),view_profile.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_complaint)
        {
            Intent ij =new Intent(getApplicationContext(),view_complaint.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_friend_request)
        {
            Intent ij =new Intent(getApplicationContext(),view_friend_request.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_friends)
        {
            Intent ij =new Intent(getApplicationContext(),view_friends.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_other_user)
        {
            Intent ij =new Intent(getApplicationContext(),view_other_user.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_post)
        {
            Intent ij =new Intent(getApplicationContext(),Viewpost.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_friends_post)
        {
            Intent ij =new Intent(getApplicationContext(),view_friends_post.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_view_send_friend_request)
        {
            Intent ij =new Intent(getApplicationContext(),view_send_friend_request.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_home)
        {
            Intent ij =new Intent(getApplicationContext(),Home.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_logout)
        {
            Intent ij =new Intent(getApplicationContext(),Login.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_rec_req)
        {
            Intent ij =new Intent(getApplicationContext(),view_work_requests.class);
            startActivity(ij);

        }
        else if(id==R.id.nav_sent_req)
        {
            Intent ij =new Intent(getApplicationContext(),view_sent_work_req.class);
            startActivity(ij);

        }
        return false;
    }
}