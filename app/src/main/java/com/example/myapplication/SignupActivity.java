package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {


    private TextInputLayout fullNameInputLayout, emailInputLayout, passwordInputLayout, repeatPasswordInputLayout;
    private EditText fullNameEditText, emailEditText, passwordEditText, repeatPasswordEditText;
    private Button signupButton;
    private TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullNameInputLayout = findViewById(R.id.full_name_input_et);
        emailInputLayout = findViewById(R.id.email_input_et);
        passwordInputLayout = findViewById(R.id.password_input_et);

        fullNameEditText = findViewById(R.id.full_name_et);
        emailEditText = findViewById(R.id.email_et);
        passwordEditText = findViewById(R.id.password_et);

        signupButton = findViewById(R.id.email_sign_up_btn);
        loginTextView = findViewById(R.id.login_tv);
        UserDB userDB = new UserDB(SignupActivity.this , "Users" , 1);
        Intent incomintIntent = getIntent();
        handelTextFields();


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    //creates new account and sign in
                   String username = fullNameEditText.getText().toString();
                   String email = emailEditText.getText().toString();
                   String password  = passwordEditText.getText().toString();
                    Users user = new Users(username , email , password);



                   if( userDB.insert(user)){
                       //go to login activity to log in

                       Intent loginIntent = new Intent(SignupActivity.this , login.class);
                       startActivity(loginIntent);
                       finish();

                   }

                   else {   //error in sign up
                       Toast.makeText(SignupActivity.this, "Error in sign up", Toast.LENGTH_SHORT).show();

                   }




                }
            }
        });

         loginTextView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //go to login activity
                 Intent loginIntent = new Intent(SignupActivity.this , login.class);
                 startActivity(loginIntent);
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
                else emailInputLayout.setError(null);
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


//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.email_sign_up_btn:
//                if (validate()) {
//                   // createNewAccount();  database handles
//                }
//                break;
//            case R.id.login_tv:
//                startActivity(new Intent(SignupActivity.this, login.class));
//                finish();
//                break;
//        }
//    }


    boolean validate() {
        if (fullNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            emailEditText.setError("Password Don't Match");
            return false;
        } else if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


}

