package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import design.abdelhak.kahrakib.R;

public class RechercheResultatRecyclerAdapter extends RecyclerView.Adapter<RechercheResultatRecyclerAdapter.RechercheResultatHolder>{

    Context mContext;
    List<String> mNoms;

    public RechercheResultatRecyclerAdapter(Context mContext,List<String> mNoms) {
        this.mContext = mContext;
        this.mNoms = mNoms;
    }

    @NonNull
    @Override
    public RechercheResultatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recherche_resultat,parent,false);
        RechercheResultatHolder rechercheResultatHolder = new RechercheResultatHolder(v);
        return rechercheResultatHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RechercheResultatHolder holder, int position) {
        holder.tvRechercheNom.setText(mNoms.get(position));
    }

    @Override
    public int getItemCount() {
        return mNoms.size();
    }

    class RechercheResultatHolder extends RecyclerView.ViewHolder {
        LinearLayout llRechercheContainer;
        TextView tvRechercheNom;

        public RechercheResultatHolder(@NonNull View itemView) {
            super(itemView);
            llRechercheContainer = itemView.findViewById(R.id.ll_item_recherche_resultat_container);
            tvRechercheNom = itemView.findViewById(R.id.tv_item_recherche_resultat_nom);
        }
    }
}
