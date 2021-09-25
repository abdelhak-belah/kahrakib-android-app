package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.models.DpsModel;
import design.abdelhak.kahrakib.models.EdcModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class EdcRecyclerAdapter extends RecyclerView.Adapter<EdcRecyclerAdapter.EdcHolder>{


    private Context mContext;
    private List<EdsResponseModel> mEdc;
    private int mLauncher;

    public EdcRecyclerAdapter(Context mContext, List<EdsResponseModel> edc,int launcher) {
        this.mContext = mContext;
        this.mEdc = edc;
        this.mLauncher = launcher;
    }

    @NonNull
    @Override
    public EdcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_edc,parent,false);
        EdcHolder edcHolder = new EdcHolder(v);
        return edcHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EdcHolder holder, int position) {
        holder.tvNumeroEtat.setText("#"+mEdc.get(position).getEdsId());
        holder.tvMontentGlobal.setText(mEdc.get(position).getMontantGlobal()+" da");
        holder.tvImputation.setText(mEdc.get(position).getImputation());
    }

    @Override
    public int getItemCount() {
        return mEdc.size();
    }

    class EdcHolder extends RecyclerView.ViewHolder {

        TextView tvNumeroEtat, tvImputation, tvMontentGlobal;

        public EdcHolder(@NonNull View itemView) {
            super(itemView);

            tvNumeroEtat = itemView.findViewById(R.id.tv_item_edc_numero_etat);
            tvMontentGlobal = itemView.findViewById(R.id.tv_item_edc_montent_global);
            tvImputation = itemView.findViewById(R.id.tv_item_edc_imputation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleData = new Bundle();
                    bundleData.putInt(BundleKeys.LAUNCHER_KEY,mLauncher);
                    bundleData.putSerializable(BundleKeys.EDC_KEY,mEdc.get(getAdapterPosition()));
                    NavigationUtil.navigateToEdcDetailsFragment(mContext,bundleData);
                }
            });
        }
    }
}
