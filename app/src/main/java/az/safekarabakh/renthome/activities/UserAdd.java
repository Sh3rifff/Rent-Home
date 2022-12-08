package az.safekarabakh.renthome.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import az.safekarabakh.renthome.MainActivity;
import az.safekarabakh.renthome.database.Place;
import az.safekarabakh.renthome.database.Room.PlaceDao;
import az.safekarabakh.renthome.database.Room.PlaceDatabase;
import az.safekarabakh.renthome.databinding.ActivityUserAddBinding;

public class UserAdd extends AppCompatActivity {

    private ActivityUserAddBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap selectImage;
    PlaceDao placeDao;
    byte[] byteArray;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            final String rayon = savedInstanceState.getString("RAYON");
            Objects.requireNonNull(binding.evinRayonu.getEditText()).setText(rayon);

            final String qiymet = savedInstanceState.getString("QIYMET");
            Objects.requireNonNull(binding.evinQiymeti.getEditText()).setText(qiymet);

            final String melumat = savedInstanceState.getString("MELUMAT");
            Objects.requireNonNull(binding.evinMelumati.getEditText()).setText(melumat);

            final String wifi = savedInstanceState.getString("WIFI");
            Objects.requireNonNull(binding.evinWifi.getEditText()).setText(wifi);

            final String otaq = savedInstanceState.getString("OTAQ");
            Objects.requireNonNull(binding.evinOtaq.getEditText()).setText(otaq);

            final String mertebe = savedInstanceState.getString("MERTEBE");
            Objects.requireNonNull(binding.evinMertebe.getEditText()).setText(mertebe);
        }

        placeDao = PlaceDatabase.getDatabase(this).placeDao();

        binding.yerButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserAdd.this, MapsActivity.class);
            startActivity(intent);
        });



        registerLauncher();
        setListeners();
        paylasSetListener();

    }

    private void paylasSetListener() {

        binding.paylas.setOnClickListener(v -> {

            String evinRayonu = Objects.requireNonNull(binding.evinRayonu.getEditText()).getText().toString();
            String evinQiymeti = Objects.requireNonNull(binding.evinQiymeti.getEditText()).getText().toString();
            String evinMelumati = Objects.requireNonNull(binding.evinMelumati.getEditText()).getText().toString();
            String evinWifisi = Objects.requireNonNull(binding.evinWifi.getEditText()).getText().toString();
            String evinOtagi = Objects.requireNonNull(binding.evinOtaq.getEditText()).getText().toString();
            String evinMertebesi = Objects.requireNonNull(binding.evinMertebe.getEditText()).getText().toString();

            String name = getIntent().getStringExtra("Name");
            Double latitude = getIntent().getDoubleExtra("Latitude", 0);
            Double longitude = getIntent().getDoubleExtra("Longitude", 0);

            Place place = new Place(name, latitude, longitude, evinRayonu, evinQiymeti
                    , evinMelumati, evinWifisi, evinOtagi, evinMertebesi,byteArray);
            placeDao.insert(place);


            if (paylasValidation()) {
                Intent intent = new Intent(UserAdd.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerLauncher() {

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent intentFromResult = result.getData();
                if (intentFromResult != null) {

                    Uri imageData = intentFromResult.getData();
                    //  binding.imageView.setImageURI(imageData)
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), imageData);
                            selectImage = ImageDecoder.decodeBitmap(source);
                            binding.imageAdd.setImageBitmap(selectImage);
                        } else {
                            selectImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageData);
                            //.setImageBitmap(selectImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byteArray = stream.toByteArray();
                    selectImage.recycle();
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentGallery);
            } else {
                Toast.makeText(getApplicationContext(), "Permission needed !", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        final String rayon = Objects.requireNonNull(savedInstanceState).getString("RAYON");
        Objects.requireNonNull(binding.evinRayonu.getEditText()).setText(rayon);

        final String qiymet = savedInstanceState.getString("QIYMET");
        Objects.requireNonNull(binding.evinQiymeti.getEditText()).setText(qiymet);

        final String melumat = savedInstanceState.getString("MELUMAT");
        Objects.requireNonNull(binding.evinMelumati.getEditText()).setText(melumat);

        final String wifi = savedInstanceState.getString("WIFI");
        Objects.requireNonNull(binding.evinWifi.getEditText()).setText(wifi);

        final String otaq = savedInstanceState.getString("OTAQ");
        Objects.requireNonNull(binding.evinOtaq.getEditText()).setText(otaq);

        final String mertebe = savedInstanceState.getString("MERTEBE");
        Objects.requireNonNull(binding.evinMertebe.getEditText()).setText(mertebe);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        final String rayon = Objects.requireNonNull(binding.evinRayonu.getEditText()).getText().toString();
        outState.putString("RAYON", rayon);

        final String qiymet = Objects.requireNonNull(binding.evinQiymeti.getEditText()).getText().toString();
        outState.putString("QIYMET", qiymet);

        final String melumat = Objects.requireNonNull(binding.evinMelumati.getEditText()).getText().toString();
        outState.putString("MELUMAT", melumat);

        final String wifi = Objects.requireNonNull(binding.evinWifi.getEditText()).getText().toString();
        outState.putString("WIFI", wifi);

        final String otaq = Objects.requireNonNull(binding.evinOtaq.getEditText()).getText().toString();
        outState.putString("OTAQ", otaq);

        final String mertebe = Objects.requireNonNull(binding.evinMertebe.getEditText()).getText().toString();
        outState.putString("MERTEBE", mertebe);

        super.onSaveInstanceState(outState);
    }

    private void setListeners() {
        binding.imageAdd.setOnClickListener(v -> selectImage());
    }

    private void selectImage() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(binding.getRoot(), "Permission needed for Gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", v -> permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)).show();
            } else {
                //Request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        } else {
            //Go to gallery
            Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentGallery);
        }
    }

    private Boolean validateRayon() {
        String val = Objects.requireNonNull(binding.evinRayonu.getEditText()).getText().toString();

        if (val.isEmpty()) {
            binding.evinRayonu.setError("Bura bos qala bilmez");
            return false;
        } else {
            binding.evinRayonu.setError(null);
            binding.evinRayonu.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateQiymet() {
        String val = Objects.requireNonNull(binding.evinQiymeti.getEditText()).getText().toString();

        if (val.isEmpty()) {
            binding.evinQiymeti.setError("Bura bos qala bilmez");
            return false;
        } else {
            binding.evinQiymeti.setError(null);
            binding.evinQiymeti.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateMelumat() {
        String val = Objects.requireNonNull(binding.evinMelumati.getEditText()).getText().toString();

        if (val.isEmpty()) {
            binding.evinMelumati.setError("Bura bos qala bilmez");
            return false;
        } else {
            binding.evinMelumati.setError(null);
            binding.evinMelumati.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateWifi() {
        String val = Objects.requireNonNull(binding.evinWifi.getEditText()).getText().toString();

        if (val.isEmpty()) {
            binding.evinWifi.setError("Bura bos qala bilmez");
            return false;
        } else {
            binding.evinWifi.setError(null);
            binding.evinWifi.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateOtaq() {
        String val = Objects.requireNonNull(binding.evinOtaq.getEditText()).getText().toString();

        if (val.isEmpty()) {
            binding.evinOtaq.setError("Bura bos qala bilmez");
            return false;
        } else {
            binding.evinOtaq.setError(null);
            binding.evinOtaq.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateMertebe() {
        String val = Objects.requireNonNull(binding.evinMertebe.getEditText()).getText().toString();

        if (val.isEmpty()) {
            binding.evinMertebe.setError("Bura bos qala bilmez");
            return false;
        } else {
            binding.evinMertebe.setError(null);
            binding.evinMertebe.setErrorEnabled(false);
            return true;
        }
    }

    public boolean paylasValidation() {
        return !(!validateRayon() | !validateQiymet() | !validateWifi() | !validateOtaq() | !validateMertebe() | !validateMelumat());
    }
}