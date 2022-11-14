package az.safekarabakh.renthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import az.safekarabakh.renthome.databinding.ActivityMainBinding;
import az.safekarabakh.renthome.fragments.AddFragment;
import az.safekarabakh.renthome.fragments.HomeFragment;
import az.safekarabakh.renthome.fragments.ProfilFragment;
import az.safekarabakh.renthome.fragments.SavedFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        az.safekarabakh.renthome.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        replaceFragment(new HomeFragment());

        auth = FirebaseAuth.getInstance();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    replaceFragment(new ProfilFragment());
                    binding.bottomNavigationView.setItemIconTintList(null);
                    break;
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    binding.bottomNavigationView.setItemIconTintList(null);
                    break;
                case R.id.add:
                    replaceFragment(new AddFragment());
                    binding.bottomNavigationView.setItemIconTintList(null);
                    break;
                case R.id.love:
                    replaceFragment(new SavedFragment());
                    binding.bottomNavigationView.setItemIconTintList(null);
                    break;
                default:
                    break;
            }
            return true;
        });

        binding.bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void SignOut(View view){
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginPanel.class);
        startActivity(intent);
    }
}