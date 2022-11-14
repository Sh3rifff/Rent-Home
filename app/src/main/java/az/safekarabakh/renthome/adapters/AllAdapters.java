//package az.safekarabakh.renthome.adapters;
//
//import android.annotation.SuppressLint;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import az.safekarabakh.renthome.R;
//import az.safekarabakh.renthome.helperClass.FeatureHelperClass;
//import az.safekarabakh.renthome.recycleritem.RecyclerViewInterface;
//
//public class AllAdapters extends RecyclerView.Adapter<AllAdapters.AllAdaptersViewHolder> {
//
//    ArrayList<FeatureHelperClass> featureLocations;
//
//    @SuppressLint("NotifyDataSetChanged")
//    public void setFilteredList(List<FeatureHelperClass> filteredList){
//        this.featureLocations = (ArrayList<FeatureHelperClass>) filteredList;
//        notifyDataSetChanged();
//    }
//
//    public RecyclerViewInterface getRecyclerViewInterface() {
//        return recyclerViewInterface;
//    }
//
//    public void setRecyclerViewInterface(RecyclerViewInterface recyclerViewInterface) {
//        this.recyclerViewInterface = recyclerViewInterface;
//    }
//
//    private RecyclerViewInterface recyclerViewInterface;
//
//    public AllAdapters(ArrayList<FeatureHelperClass> featureLocations, RecyclerViewInterface recyclerViewInterface) {
//        this.featureLocations = featureLocations;
//        this.recyclerViewInterface = recyclerViewInterface;
//    }
//
//    @NonNull
//    @Override
//    public AllAdaptersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.featured_card_design,parent,false);
//
//        AllAdaptersViewHolder allAdaptersViewHolder = new AllAdaptersViewHolder(view, recyclerViewInterface);
//        return allAdaptersViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AllAdaptersViewHolder holder, int position) {
//
//        FeatureHelperClass featureHelperClass = featureLocations.get(position);
//
//        holder.image.setImageResource(featureHelperClass.getImage());
//        holder.title.setText(featureHelperClass.getTitle());
//        holder.desc.setText(featureHelperClass.getDescription());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return featureLocations.size();
//    }
//
//    //
//    public static class AllAdaptersViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        TextView title, desc;
//
//        public AllAdaptersViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
//            super(itemView);
//
//            image = itemView.findViewById(R.id.feature_image);
//            title = itemView.findViewById(R.id.feature_title);
//            desc = itemView.findViewById(R.id.feature_description);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (recyclerViewInterface != null){
//                        int pos = getAdapterPosition();
//                        if (pos != RecyclerView.NO_POSITION){
//                            recyclerViewInterface.onItemClick(pos);
//                        }
//                    }
//
//                }
//            });
//        }
//    }
//}
