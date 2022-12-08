package az.safekarabakh.renthome.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import az.safekarabakh.renthome.R;
import az.safekarabakh.renthome.database.Place;
import az.safekarabakh.renthome.database.Room.PlaceDao;
import az.safekarabakh.renthome.database.Room.PlaceDatabase;
import az.safekarabakh.renthome.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {

    private GoogleMap mMap;
    private FragmentAddBinding binding;
    ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    PlaceDao placeDao;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
            locationListener = location -> {
            };

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(binding.getRoot(), "Permission Needed for maps", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", v -> permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)).show();
                } else {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            } else {
                if (getLastKnownLocation() != null) {
                    LatLng lastUserLocation = new LatLng(getLastKnownLocation().getLatitude(), getLastKnownLocation().getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 9));
                }
                mMap.setMyLocationEnabled(true);
            }
            mMap.getUiSettings().setMyLocationButtonEnabled(false);


            LatLng Sharif = new LatLng(40.410853, 49.846406);
            mMap.addMarker(new MarkerOptions().position(Sharif).title("Marker in Sharif")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.markersvg)).snippet("Standard"));
            mMap.setOnMarkerClickListener(marker -> false);

            placeDao = PlaceDatabase.getDatabase(getContext()).placeDao();

            for (Place place : placeDao.getAll()) {
                if (place.getLatitude() != null && place.getLongitude() != null) {
                    LatLng position = new LatLng(place.getLatitude(), place.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(position).title(place.getRayon())
                            .icon(bitmapDescriptorFromVector(getContext(), R.drawable.markersvg)));
                    System.out.println(place.getLongitude());
                }
            }

//            mMap.setOnMarkerClickListener(marker -> {
//                for (Place place : placeDao.getAll()) {
//                    if (place.getLatitude() != null && place.getLongitude() != null) {
//                        LatLng position = new LatLng(place.getLatitude(), place.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(position).title(place.getRayon())
//                                .icon(bitmapDescriptorFromVector(getContext(), R.drawable.markersvg)));
//
//                        System.out.println(place.getQiymet());
//
//                    }
//                }
//                return false;
//
//            });
        }
    };


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        Objects.requireNonNull(vectorDrawable).setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddBinding.bind(view);
        SupportMapFragment addFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (addFragment != null) {
            addFragment.getMapAsync(callback);
        }

        binding.currentLocation.setOnClickListener(v -> moveToCurrentLocation());
        registerLauncher();
    }

    @SuppressLint("MissingPermission")
    private void registerLauncher() {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                //Permission granted
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } else {
                //Permission denied
                Toast.makeText(getActivity(), "Permission needed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void moveToCurrentLocation() {
        if (mMap != null) {
            final LatLng currentLocation = new LatLng(getLastKnownLocation().getLatitude(), getLastKnownLocation().getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
        }
    }


}