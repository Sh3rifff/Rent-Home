package az.safekarabakh.renthome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import az.safekarabakh.renthome.R;

public class MainActivity3 extends AppCompatActivity {

    TextView textView,description,cost,wifis,otaqs,mertebes;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        String name = getIntent().getStringExtra("Name");
        String Cost = getIntent().getStringExtra("Cost");
        String DESC_all = getIntent().getStringExtra("desc_all");
        String wifi = getIntent().getStringExtra("Wifi");
        String otaq = getIntent().getStringExtra("Otaq");
        String mertebe = getIntent().getStringExtra("Mertebe");
        int image = getIntent().getIntExtra("image",0);

        textView = findViewById(R.id.feature_recyle_item_title);
        imageView = findViewById(R.id.feature_recyle_item_image);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        description = findViewById(R.id.feature_recyle_item_description);
        cost = findViewById(R.id.feature_recyle_item_cost);
        wifis = findViewById(R.id.wifi);
        otaqs = findViewById(R.id.otaq);
        mertebes = findViewById(R.id.mertebe);

        textView.setText(name);
        imageView.setImageResource(image);
        collapsingToolbarLayout.setTitle(name);
        description.setText(DESC_all);
        cost.setText(Cost);
        wifis.setText(wifi);
        otaqs.setText(otaq);
        mertebes.setText(mertebe);
    }
}