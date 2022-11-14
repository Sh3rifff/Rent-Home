package az.safekarabakh.renthome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import az.safekarabakh.renthome.databinding.ActivityLoginPanelBinding;

public class LoginPanel extends AppCompatActivity {

    private ActivityLoginPanelBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPanelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null){
            Intent intent = new Intent(LoginPanel.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void SignInClick(View view) {

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Doldur", Toast.LENGTH_SHORT).show();
        } else {

            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                Intent intent = new Intent(LoginPanel.this, MainActivity.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e ->
                    Toast.makeText(LoginPanel.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    public void SignUpClick(View view) {

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Doldur", Toast.LENGTH_SHORT).show();
        } else {

            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                Intent intent = new Intent(LoginPanel.this, MainActivity.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e ->
                    Toast.makeText(LoginPanel.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}