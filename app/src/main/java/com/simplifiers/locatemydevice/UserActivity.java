package com.simplifiers.locatemydevice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private DrawerLayout mDrawerLayout;
    String username,password,user,pass;
    Intent i;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        password = extras.getString("password");


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.nav_view1);
        View header=navigationView.getHeaderView(0);
        t1 = (TextView)header.findViewById(R.id.student_username);
        t1.setText(username);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        if(menuItem.getTitle().equals("Register Device"))
                        {
                            i = new Intent(UserActivity.this,AddDevice.class);
                            Bundle extras = new Bundle();
                            extras.putString("username", username);
                            extras.putString("password", password);
                            i.putExtras(extras);
                            startActivity(i);
                        }
                        if(menuItem.getTitle().equals("Profile"))
                        {
                            db = FirebaseFirestore.getInstance();
                            db.collection("Registered_User")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    user = document.getString("Username");
                                                    pass = document.getString("Password");
                                                    if (username.equalsIgnoreCase(user) && password.equalsIgnoreCase(pass)) {
                                                        //Toast.makeText(LoginActivity.this,"Welcome" + username +password,Toast.LENGTH_LONG).show();
                                                        i = new Intent(UserActivity.this, UserLogin.class);
                                                        Bundle extras = new Bundle();
                                                        extras.putString("username", username);
                                                        extras.putString("password", password);
                                                        i.putExtras(extras);
                                                        startActivity(i);
                                                        Toast.makeText(UserActivity.this, "Welcome" + username + password, Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(UserActivity.this, "Invalid " + username + password, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            } else {
                                                Exception err = task.getException();
                                                Toast.makeText(UserActivity.this, "Error" + err, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        if(menuItem.getTitle().equals("Logout"))
                        {
                            Toast.makeText(UserActivity.this, "Logged Out Successfully",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent(UserActivity.this,UserLogin.class);
                            startActivity(i);
                            finish();

                        }
                        if(menuItem.getTitle().equals("All Devices"))
                        {
                            i = new Intent(UserActivity.this,GetDevices.class);
                            Bundle extras = new Bundle();
                            extras.putString("username", username);
                            extras.putString("password", password);
                            i.putExtras(extras);
                            startActivity(i);
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
