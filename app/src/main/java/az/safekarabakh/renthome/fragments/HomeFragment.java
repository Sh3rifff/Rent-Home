package az.safekarabakh.renthome.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import az.safekarabakh.renthome.R;
import az.safekarabakh.renthome.activities.MainActivity3;
import az.safekarabakh.renthome.activities.UserAdd;
import az.safekarabakh.renthome.adapters.CategoriesAdapter;
import az.safekarabakh.renthome.adapters.FeatureAdapter;
import az.safekarabakh.renthome.adapters.NearestAdapter;
import az.safekarabakh.renthome.database.Room.PlaceDao;
import az.safekarabakh.renthome.database.Room.PlaceDatabase;
import az.safekarabakh.renthome.databinding.FragmentHomeBinding;
import az.safekarabakh.renthome.helperClass.CategoriesHelperClass;
import az.safekarabakh.renthome.helperClass.FeatureHelperClass;
import az.safekarabakh.renthome.helperClass.NearestHelperClass;
import az.safekarabakh.renthome.recycleritem.RecyclerViewInterface;

public class HomeFragment extends Fragment {

    RecyclerView featuredRecycler, categoriesRecycler, nearestRecycler;
    TextView localeName;
    LocationManager locationManager;
    LocationListener locationListener;
    RecyclerViewInterface recyclerViewInterface;
    TextInputLayout searchView;
    ArrayList<FeatureHelperClass> featureLocations;
    FeatureAdapter featureAdapter;
    FloatingActionButton current;

    private PlaceDao placeDao;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        az.safekarabakh.renthome.databinding.FragmentHomeBinding binding = FragmentHomeBinding.bind(view);

        featuredRecycler = binding.getRoot().findViewById(R.id.feature_recycler);
        categoriesRecycler = binding.getRoot().findViewById(R.id.categories_recycler);
        nearestRecycler = binding.getRoot().findViewById(R.id.nearest_recycler);
        current = binding.getRoot().findViewById(R.id.currentLocation);

        current.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserAdd.class);
            startActivity(intent);
        });

        searchView = binding.getRoot().findViewById(R.id.tilSearch);
        searchView.clearFocus();

        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String query = s.toString();
                filterList(query);
            }
        });

        localeName = view.findViewById(R.id.localeName);
        localeName.setOnClickListener(v -> replaceFragment(new AddFragment()));

        initDatabase();
        categoriesRecycler();
        featuredRecycler();
        nearestRecycler();
        localeName();

        locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = location -> {
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void categoriesRecycler() {

        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager
                (requireContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelperClass> categoriesLocations = new ArrayList<>();

        categoriesLocations.add(new CategoriesHelperClass(R.drawable.homeapp, "Menzil"));
        categoriesLocations.add(new CategoriesHelperClass(R.drawable.binaapp, "Bina"));
        categoriesLocations.add(new CategoriesHelperClass(R.drawable.oficceapp, "Ofis"));
        categoriesLocations.add(new CategoriesHelperClass(R.drawable.pic, "Doma"));

        final CategoriesAdapter adapter = new CategoriesAdapter(categoriesLocations);
        categoriesRecycler.setAdapter(adapter);
    }

    private void localeName() {

        new CountDownTimer(10, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mapString();
            }
        }.start();
    }

    private void mapString() {

        double latitude = getLastKnownLocation().getLatitude();
        double longitude = getLastKnownLocation().getLongitude();

        try {
            Geocoder geo = new Geocoder(this.requireContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geo.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses == null) return;
            if (addresses.isEmpty()) {
                Toast.makeText(requireContext(), "Waiting for Location", Toast.LENGTH_SHORT).show();
            } else {
                String name = addresses.get(0).getSubLocality() + "-" + addresses.get(0).getLocality()+ "," + addresses.get(0).getCountryName();
                localeName.setText(name);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private void filterList(String newText) {
        List<FeatureHelperClass> filteredList = new ArrayList<>();
        for (FeatureHelperClass featureHelperClass : featureLocations) {
            if (featureHelperClass.getTitle().toLowerCase().contains(newText.toLowerCase()) || featureHelperClass.getDescription().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))) {
                filteredList.add(featureHelperClass);
            }
        }
        if (filteredList.isEmpty()) {
        } else {
            featureAdapter.setFilteredList(filteredList);
        }
    }

    private void featuredRecycler() {
        featuredRecycler.setLayoutManager(new LinearLayoutManager
                (requireContext(), LinearLayoutManager.HORIZONTAL, false));

        featureLocations = new ArrayList<>();

        featureLocations.add(new FeatureHelperClass(R.drawable.bina_az_ehmedli, "Ehmedli",
                "600₼ /Ayliq", "İcarəyə verilir 3 otaqlı yeni tikili 136 m², Əhmədli m.,\tYeni tikili", "Var", "3", "15/17"));
        featureLocations.add(new FeatureHelperClass(R.drawable.ev3, "Nerimanov", "40₼/Gunluk", "Loremmmmmmmmmm", "Yox", "5", "15/17"));
        featureLocations.add(new FeatureHelperClass(R.drawable.ev1, "Hezi Aslanov", "400₼/Ayliq", "Loremmmmmmmmmm", "Var", "2", "15/17"));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final List<FeatureHelperClass> locations = placeDao.getAll().stream().map(place -> new FeatureHelperClass(
                    R.drawable.ev3,
                    place.getName(),
                    place.getMelumat(),
                    place.getMelumat(),
                    place.getWifi(),
                    place.getOtaq(),
                    place.getMertebe()
            )).collect(Collectors.toList());

            featureLocations.addAll(locations);
        }

        featureAdapter = new FeatureAdapter(featureLocations, recyclerViewInterface);
        featureAdapter.setRecyclerViewInterface(position -> {
            final Intent intent = new Intent(getActivity(), MainActivity3.class);
            intent.putExtra("Name", featureLocations.get(position).getTitle());
            intent.putExtra("Cost", featureLocations.get(position).getDescription());
            intent.putExtra("image", featureLocations.get(position).getImage());
            intent.putExtra("desc_all", featureLocations.get(position).getDescriptionAll());
            intent.putExtra("Wifi", featureLocations.get(position).getWifi());
            intent.putExtra("Otaq", featureLocations.get(position).getOtaq());
            intent.putExtra("Mertebe", featureLocations.get(position).getMertebe());
            startActivity(intent);

        });
        featuredRecycler.setAdapter(featureAdapter);
    }

    private void nearestRecycler() {
        nearestRecycler.setLayoutManager(new LinearLayoutManager
                (requireContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<NearestHelperClass> nearestLocations = new ArrayList<>();

        nearestLocations.add(new NearestHelperClass(R.drawable.ev3, "Ofis", "30₼/Gunluk"));
        nearestLocations.add(new NearestHelperClass(R.drawable.ev2, "Bina", ""));
        nearestLocations.add(new NearestHelperClass(R.drawable.oficceapp, "Ofis", ""));
        nearestLocations.add(new NearestHelperClass(R.drawable.resa, "Doma", ""));

        final NearestAdapter nearestAdapter = new NearestAdapter(nearestLocations, recyclerViewInterface);
        nearestRecycler.setAdapter(nearestAdapter);
        nearestAdapter.setRecyclerViewInterface(position -> {

            final Intent intent = new Intent(getActivity(), MainActivity3.class);
            intent.putExtra("Name", nearestLocations.get(position).getTitle());
            intent.putExtra("Cost", nearestLocations.get(position).getDescription());
            intent.putExtra("image", nearestLocations.get(position).getImage());
            startActivity(intent);
        });

    }

    private void initDatabase() {
        PlaceDatabase db = PlaceDatabase.getDatabase(getContext());
        placeDao = db.placeDao();
    }

}