package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.models.DpsModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class DpsRecyclerAdapter extends RecyclerView.Adapter<DpsRecyclerAdapter.DpsHolder>{


    private Context mContext;
    private List<DpsResponseModel> mDps;
    private int mLauncher;

    public DpsRecyclerAdapter(Context mContext, List<DpsResponseModel> mDps,int launcher) {
        this.mContext = mContext;
        this.mDps = mDps;
        this.mLauncher = launcher;
    }

    @NonNull
    @Override
    public DpsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_dps,parent,false);
        DpsHolder dpsHolder = new DpsHolder(v);
        return dpsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DpsHolder holder, int position) {
        holder.tvDate.setText("Le "+mDps.get(position).getDateDeCreation());
        holder.tvPrestataire.setText("À "+mDps.get(position).getPrestataire());
        holder.tvMontent.setText(mDps.get(position).getTotalAchatMontant()+"da");
    }

    @Override
    public int getItemCount() {
        return mDps.size();
    }

    class DpsHolder extends RecyclerView.ViewHolder {

        TextView tvDate,tvMontent,tvPrestataire;

        public DpsHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_item_dps_date);
            tvPrestataire = itemView.findViewById(R.id.tv_item_dps_prestataire);
            tvMontent = itemView.findViewById(R.id.tv_item_dps_montent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleData = new Bundle();
                    bundleData.putInt(BundleKeys.LAUNCHER_KEY,mLauncher);
                    bundleData.putSerializable(BundleKeys.DPS_KEY,mDps.get(getAdapterPosition()));
                    NavigationUtil.navigateToDpsDetailsFragment(mContext,bundleData);
                }
            });
        }
    }
}
