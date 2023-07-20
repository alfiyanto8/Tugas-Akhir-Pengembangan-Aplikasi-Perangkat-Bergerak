package com.example.tugass.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugass.R;
import com.example.tugass.activities.DetailActivity;
import com.example.tugass.model.ModelMain;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements Filterable {

    private final Context context;
    private final List<ModelMain> modelMainList;
    private final List<ModelMain> modelMainFilterList;

    @Override
    public Filter getFilter() {
        return modelFilter;
    }

    private final Filter modelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelMain> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(modelMainFilterList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelMain modelMainFilter : modelMainFilterList) {
                    if (modelMainFilter.getNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(modelMainFilter);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelMainList.clear();
            modelMainList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public MainAdapter(Context context, List<ModelMain> modelMain) {
        this.context = context;
        this.modelMainList = modelMain;
        this.modelMainFilterList = new ArrayList<>(modelMain);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        ModelMain item = modelMainList.get(position);

        holder.tvNamaMateri.setText(item.getNama());

        //send data to detail activity
        holder.cvListMateri.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.DETAIL_MATERI, modelMainList.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return modelMainList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        CardView cvListMateri;
        TextView tvNamaMateri;

        public MainViewHolder(View itemView) {
            super(itemView);
            cvListMateri = itemView.findViewById(R.id.cvListMateri);
            tvNamaMateri = itemView.findViewById(R.id.tvNamaMateri);
        }
    }

}
