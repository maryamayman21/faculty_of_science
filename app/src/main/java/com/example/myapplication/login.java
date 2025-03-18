package com.example.myapplication;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private TextView forgetPasswordTextView;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextView signUpTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInputLayout = findViewById(R.id.email_input_et);
        emailEditText = findViewById(R.id.email_et);
        passwordInputLayout = findViewById(R.id.password_input_et);
        passwordEditText = findViewById(R.id.password_et);
        loginButton = findViewById(R.id.email_login_btn);
        signUpTextView = findViewById(R.id.signup_tv);

        Intent incomingIntent = getIntent();


        //shared prefernce intialization
        SharedPreferences preferences = getSharedPreferences("userdetails", 0);
        SharedPreferences.Editor editor = preferences.edit();

        handelTextFields();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String[] userData = verifyUser();
                    setEditor(editor , userData[0] , userData[1]);  //->> deal with editor

                        if (userData != null ) {
                            Intent mainActivityIntent = new Intent(login.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                        else
                            Toast.makeText(login.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });


        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupintent = new Intent(login.this , SignupActivity.class);
                startActivity(signupintent);
                finish();
            }
        });

    }


    private void handelTextFields() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (!input.contains("@") || !input.contains("."))
                    emailInputLayout.setError("Invalid Email!");
                else
                    emailInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (input.length() < 8) {
                    passwordInputLayout.setError("Short Password! Make it Longer!");
                } else if (input.length() > 10) {
                    passwordInputLayout.setError("Short Password! Make it Shorter!");
                } else {
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    boolean validate() {
        if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


//function to verify user email and password
    @SuppressLint("Range")
    public  String[]  verifyUser(){
        String userEmail = emailEditText.getText().toString();
        String userPassword = passwordEditText.getText().toString();
        UserDB userDB = new UserDB(login.this , "Users" , 1);
          String[] userData = new String[2];

        Cursor cursor = userDB.searchByEmail(userEmail);



        //cursor contains record
        //user not registered
        if(cursor == null){
            return null;

        }
               //user found
        cursor.moveToPosition(-1);


        String username ="";
        String password = "";
        while ((cursor.moveToNext())){

            username = cursor.getString(cursor.getColumnIndex("username"));
            password = cursor.getString(cursor.getColumnIndex("password"));
        }



          if (password.equals(userPassword)){
                userData[0] = username;
                userData[1] = userEmail;
             return  userData;
          }

         return null;
    }


     //editor for edit shared preference function
    public void setEditor(SharedPreferences.Editor editor ,String username , String useremail ) {
        editor.putBoolean("isUserLogin", true);
        editor.putString("UserName" , username);
        editor.putString("UserEmail" , useremail);
        editor.commit();
    }




    }







