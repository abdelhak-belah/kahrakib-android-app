package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.intermediate.RechercheIntermediate;

public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.FilterHolder>{

    private Context mContext;
    private List<String> mNoms;
    private static int selectedItemIndex;
    private RechercheIntermediate rechercheIntermediate;

    public FilterRecyclerAdapter(Context mContext, List<String> mNoms,RechercheIntermediate rechercheIntermediate) {
        this.mContext = mContext;
        this.mNoms = mNoms;
        this.rechercheIntermediate = rechercheIntermediate;
    }

    @NonNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_filter,parent,false);
        FilterHolder filterHolder = new FilterHolder(v);
        return filterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterHolder holder, int position) {
        holder.tvNom.setText(mNoms.get(position));
        holder.cvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemIndex = position;
                notifyDataSetChanged();
            }
        });

        if (selectedItemIndex == position){
            holder.tvNom.setTextColor(ContextCompat.getColor(mContext,R.color.kahrakib_white));
            holder.cvFilter.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.primaryColor));
            rechercheIntermediate.setFilterItemIndex(selectedItemIndex);
        }else {
            holder.tvNom.setTextColor(ContextCompat.getColor(mContext,R.color.kahrakib_black));
            holder.cvFilter.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.kahrakib_white));
        }

    }

    @Override
    public int getItemCount() {
        return mNoms.size();
    }

    public static int getSelectedItem(){
        return selectedItemIndex;
    }

    class FilterHolder extends RecyclerView.ViewHolder {

        CardView cvFilter;
        TextView tvNom;

        public FilterHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tv_item_filter_nom);
            cvFilter = itemView.findViewById(R.id.cv_item_filter);
        }
    }
}
