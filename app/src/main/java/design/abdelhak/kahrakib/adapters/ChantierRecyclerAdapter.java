package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.intermediate.ChantierIntermediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.models.ChantierModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class ChantierRecyclerAdapter extends RecyclerView.Adapter<ChantierRecyclerAdapter.ChantierHolder>{

    Context mContext;
    List<ChantierResponseModel> mChantiers;
    ChantierIntermediate chantierIntermediate;

    public ChantierRecyclerAdapter(Context context, List<ChantierResponseModel> mChantiers,ChantierIntermediate chantierIntermediate) {
        this.mContext = context;
        this.mChantiers = mChantiers;
        this.chantierIntermediate  = chantierIntermediate;
    }

    @NonNull
    @Override
    public ChantierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_chantier,parent,false);
        ChantierHolder chantierHolder = new ChantierHolder(v);
        return chantierHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChantierHolder holder, int position) {
        holder.tvNomChantier.setText(mChantiers.get(position).getNom());
        holder.tvImputation.setText(mChantiers.get(position).getImputation());
        holder.tvAdresse.setText(mChantiers.get(position).getAdresse());
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterPopUpMenu(holder.ivMore,holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mChantiers.size();
    }

    class ChantierHolder extends RecyclerView.ViewHolder{
        TextView tvNomChantier,tvImputation,tvAdresse;
        ImageView ivMore;

        public ChantierHolder(@NonNull View itemView) {
            super(itemView);
            tvNomChantier = itemView.findViewById(R.id.tv_item_chantier_nom);
            tvImputation = itemView.findViewById(R.id.tv_item_Chantier_imputation);
            tvAdresse = itemView.findViewById(R.id.tv_item_Chantier_adresse);
            ivMore = itemView.findViewById(R.id.iv_item_chantier_more);

        }
    }



    private void showFilterPopUpMenu(View v,ChantierHolder holder) {
        PopupMenu popupMenu = new PopupMenu(mContext,v);
        popupMenu.inflate(R.menu.menu_item_chantier);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_chantier_modifier:
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(BundleKeys.IS_UPDATE_KEY,true);
                        bundle.putSerializable(BundleKeys.CHANTIER_KEY,mChantiers.get(holder.getAdapterPosition()));
                        NavigationUtil.navigateToAjouterChantierFragment(mContext,bundle);
                        return true;
                    case  R.id.menu_item_chantier_supprimer:
                        chantierIntermediate.deleteChantier(mChantiers.get(holder.getAdapterPosition()).getChantierId());
                        return true;
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }

}
