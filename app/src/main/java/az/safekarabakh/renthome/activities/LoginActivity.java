package az.safekarabakh.renthome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import az.safekarabakh.renthome.MainActivity;
import az.safekarabakh.renthome.R;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp,login_btn,guest;
    TextInputLayout username, password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_screen);
        login_btn = findViewById(R.id.login_btn);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        guest = findViewById(R.id.guest);

        guest.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        callSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,SignUp.class);
            startActivity(intent);
        });

        login_btn.setOnClickListener(v -> loginUser());



    }
    private Boolean validateUsername() {
        String val = Objects.requireNonNull(username.getEditText()).getText().toString();

        if (val.isEmpty()) {
            username.setError("Bura bos qala bilmez");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword()  {
        String val = Objects.requireNonNull(password.getEditText()).getText().toString();

        if (val.isEmpty()) {
            password.setError("Bura bos qala bilmez");
            return false;
        }else{
            password.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private void loginUser(){
        if (!validateUsername() | !validatePassword()){
            return;
        }else {
            isUser();
        }
    }

    private void isUser() {
        String userEnteredUsername = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
        String userEnteredPassword = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUsers = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    assert passwordFromDB != null;
                    if (passwordFromDB.equals(userEnteredPassword)){

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                        //PutExtralar olacaq ...

                        startActivity(intent);
                    }else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("No user tapildi");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    
}