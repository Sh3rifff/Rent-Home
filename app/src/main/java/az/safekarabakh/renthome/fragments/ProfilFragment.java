package az.safekarabakh.renthome.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import az.safekarabakh.renthome.R;
import az.safekarabakh.renthome.databinding.FragmentProfilBinding;

public class ProfilFragment extends Fragment {

    private FragmentProfilBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap selectImage;

    public ProfilFragment() {
        super(R.layout.fragment_profil);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfilBinding.bind(view);

        registerLauncher();
        setListeners();
    }

    private void setListeners() {
        binding.imageProfil.setOnClickListener(v -> selectImage());
    }

    private void selectImage() {

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(binding.getRoot(), "Permission needed for Gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", v -> permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)).show();
            }else {
                //Request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        } else {
            //Go to gallery
            Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentGallery);
        }
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
                            ImageDecoder.Source source = ImageDecoder.createSource(requireContext().getContentResolver(), imageData);
                            selectImage = ImageDecoder.decodeBitmap(source);
                            binding.imageProfil.setImageBitmap(selectImage);
                        } else {
                            selectImage = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageData);
                            //.setImageBitmap(selectImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentGallery);
            } else {
                Toast.makeText(requireContext(), "Permission needed !", Toast.LENGTH_LONG).show();
            }
        });

    }
}