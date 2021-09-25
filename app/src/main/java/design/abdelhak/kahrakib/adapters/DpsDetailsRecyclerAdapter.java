package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.networks.responses.AchatResponseModel;

public class DpsDetailsRecyclerAdapter extends RecyclerView.Adapter<DpsDetailsRecyclerAdapter.AchatDetailsHolder>{

    private Context mContext;
    private List<AchatResponseModel> mAchats;


    public DpsDetailsRecyclerAdapter(Context context, List<AchatResponseModel> mAchats) {
        this.mContext = context;
        this.mAchats = mAchats;
    }

    @NonNull
    @Override
    public AchatDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_dps_details,parent,false);
        AchatDetailsHolder achatDetailsHolder = new AchatDetailsHolder(v);
        return achatDetailsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AchatDetailsHolder holder, int position) {
        holder.tvTitre.setText(mAchats.get(position).getDesignation());
        holder.tvQuantie.setText("×"+mAchats.get(position).getQuantite());
        holder.tvPrix.setText(""+mAchats.get(position).getPrixUnitaire().multiply(new BigDecimal(mAchats.get(position).getQuantite()))+" da");
    }

    @Override
    public int getItemCount() {
        return mAchats.size();
    }



    class AchatDetailsHolder extends RecyclerView.ViewHolder {

        TextView tvTitre,tvQuantie,tvPrix;

        public AchatDetailsHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tv_item_dps_details_designation);
            tvQuantie = itemView.findViewById(R.id.tv_item_dps_details_quantite);
            tvPrix = itemView.findViewById(R.id.tv_item_dps_details_prix_unitaire);
        }
    }


}
