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

import java.math.BigDecimal;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.fragments.client.AjouterAchatsFragment;
import design.abdelhak.kahrakib.intermediate.AchatInterrmediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.models.AchatModel;
import design.abdelhak.kahrakib.networks.requests.AchatWithDpsRequestModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class AchatRecyclerAdapter extends RecyclerView.Adapter<AchatRecyclerAdapter.AchatHolder>{

    private Context mContext;
    private List<AchatWithDpsRequestModel> mAchats;
    private AchatInterrmediate mAchatInterrmediate;


    public AchatRecyclerAdapter(Context context, List<AchatWithDpsRequestModel> mAchats,AchatInterrmediate achatInterrmediate) {
        this.mContext = context;
        this.mAchats = mAchats;
        this.mAchatInterrmediate = achatInterrmediate;
    }

    @NonNull
    @Override
    public AchatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_achat,parent,false);
        AchatHolder achatHolder = new AchatHolder(v);
        return achatHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AchatHolder holder, int position) {
        holder.tvTitre.setText(mAchats.get(position).getDesignation());
        holder.tvQuantie.setText("×"+mAchats.get(position).getQuantite());
        holder.tvPrix.setText(mAchats.get(position).getPrixUnitaire().multiply(new BigDecimal(mAchats.get(position).getQuantite()))+" da");
    }

    @Override
    public int getItemCount() {
        return mAchats.size();
    }


    class AchatHolder extends RecyclerView.ViewHolder {

        TextView tvTitre,tvQuantie,tvPrix;
        ImageView ivMore;

        public AchatHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tv_item_achat_designation);
            tvQuantie = itemView.findViewById(R.id.tv_item_achat_quantite);
            tvPrix = itemView.findViewById(R.id.tv_item_achat_prix_unitaire);
            ivMore = itemView.findViewById(R.id.iv_item_achat_more);

            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFilterPopUpMenu(ivMore,getAdapterPosition());
                }
            });
        }
    }




    private void showFilterPopUpMenu(View v,int position) {
        PopupMenu popupMenu = new PopupMenu(mContext,v);
        popupMenu.inflate(R.menu.menu_item_achat);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.menu_item_achat_supprimer:
                        mAchatInterrmediate.debitePrixTotal(mAchats.get(position));
                        mAchats.remove(position);
                        notifyDataSetChanged();
                        mAchatInterrmediate.affichageFenetereVide();
                        return true;
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }
}
