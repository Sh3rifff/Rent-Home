package az.safekarabakh.renthome.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import az.safekarabakh.renthome.R;
import az.safekarabakh.renthome.helperClass.NearestHelperClass;

public class NearestAdapter extends RecyclerView.Adapter<NearestAdapter.NearestViewHolder>{

    ArrayList<NearestHelperClass> nearestLocations;

    public NearestAdapter(ArrayList<NearestHelperClass> nearestLocations) {
        this.nearestLocations = nearestLocations;
    }

    @NonNull
    @Override
    public NearestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nearest_card_design,parent,false);

        NearestViewHolder nearestViewHolder = new NearestViewHolder(view);
        return nearestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NearestViewHolder holder, int position) {

        NearestHelperClass nearestHelperClass = nearestLocations.get(position);

        holder.image.setImageResource(nearestHelperClass.getImage());
        holder.title.setText(nearestHelperClass.getTitle());
        holder.desc.setText(nearestHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return nearestLocations.size();
    }

    public static class NearestViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, desc;

        public NearestViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ny_image);
            title = itemView.findViewById(R.id.ny_title);
            desc = itemView.findViewById(R.id.ny_description);
        }
    }
}
