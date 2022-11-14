package az.safekarabakh.renthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MainActivity3 extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        String name = getIntent().getStringExtra("Name");
        int image = getIntent().getIntExtra("image",0);

        textView = findViewById(R.id.feature_recyle_item_title);
        imageView = findViewById(R.id.feature_recyle_item_image);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        textView.setText(name);
        imageView.setImageResource(image);
        collapsingToolbarLayout.setTitle(name);
    }
}