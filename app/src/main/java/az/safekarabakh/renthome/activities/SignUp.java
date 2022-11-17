package az.safekarabakh.renthome.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import az.safekarabakh.renthome.R;
import az.safekarabakh.renthome.helperClass.UserHelperClass;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    Button regButton, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.user_name);
        regUsername = findViewById(R.id.user_username);
        regEmail = findViewById(R.id.user_email);
        regPhone = findViewById(R.id.user_phone);
        regPassword = findViewById(R.id.user_password);

        regButton = findViewById(R.id.qeydiyyat);
        regToLoginBtn = findViewById(R.id.hesabin_varsa);

        regButton.setOnClickListener(v -> {

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            if (registerUser()){
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regToLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Bura bos qala bilmez");
            return false;
        }
        else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Bura bos qala bilmez");
            return false;
        }else if (val.length()>15 ){
            regUsername.setError("15den Uzundu");
            return false;
        }else if (!val.matches(noWhiteSpace)){
            regUsername.setError("Bunlar olmaz");
            return false;
        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();

        if (val.isEmpty()) {
            regEmail.setError("Bura bos qala bilmez");
            return false;
        }
        else if (val.length()<=12){
            regEmail.setError("Minimum uzunluq");
            return false;
        }else{
            regEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePhone() {
        String val = regPhone.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhone.setError("Bura bos qala bilmez");
            return false;
        }else if (val.length()<=9){
            regPhone.setError("Minimum uzunluq");
            return false;
        }
        else{
            regPhone.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPassword.setError("Bura bos qala bilmez");
            return false;
        }else if (val.length()<=6){
            regPassword.setError("Minimum uzunluq");
            return false;
        }
        else{
            regPassword.setError(null);
            return true;
        }
    }

    public boolean registerUser() {
        if (!validateName() | !validateUsername() | !validateEmail() | !validatePhone() | !validatePassword()){
            return false;
        }

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phone = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        UserHelperClass userHelperClass = new UserHelperClass(name, username, email, phone, password);
        reference.child(username).setValue(userHelperClass);

        return true;
    }
}