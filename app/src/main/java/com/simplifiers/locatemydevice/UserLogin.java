package com.simplifiers.locatemydevice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserLogin extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    public FirebaseFirestore db;
    String user,pass;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterUser.class));
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }


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
                                        Toast.makeText(UserLogin.this,"Welcome" + username +password,Toast.LENGTH_LONG).show();
                                        i = new Intent(UserLogin.this, UserActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("username", username);
                                        extras.putString("password", password);
                                        i.putExtras(extras);
                                        startActivity(i);
                                        Toast.makeText(UserLogin.this, "Welcome" + username + password, Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Exception err = task.getException();
                                Toast.makeText(UserLogin.this, "Error" + err, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


}
