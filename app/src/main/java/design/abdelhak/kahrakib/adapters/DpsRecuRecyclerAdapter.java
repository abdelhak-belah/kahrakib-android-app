package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.intermediate.DpsRecuIntermediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.models.DpsModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class DpsRecuRecyclerAdapter extends RecyclerView.Adapter<DpsRecuRecyclerAdapter.DpsRecuHolder> {


    private Context mContext;
    private List<DpsResponseModel> mDpsResponseModel;
    private int mLauncher;
    private boolean mIsSelectMode = false;
    private List<DpsResponseModel> mSelectedItem = new LinkedList<>();
    private DpsRecuIntermediate mDpsRecuIntermediate;

    public DpsRecuRecyclerAdapter(Context mContext, List<DpsResponseModel> mDps,DpsRecuIntermediate dpsRecuIntermediate, int launcher) {
        this.mContext = mContext;
        this.mDpsResponseModel = mDps;
        this.mDpsRecuIntermediate = dpsRecuIntermediate;
        this.mLauncher = launcher;
    }

    @NonNull
    @Override
    public DpsRecuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_dps_recu, parent, false);
        DpsRecuHolder dpsRecuHolder = new DpsRecuHolder(v);
        return dpsRecuHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DpsRecuHolder holder, int position) {
        holder.tvDate.setText("Le " + mDpsResponseModel.get(position).getDateDeCreation());
        holder.tvPrestataire.setText("À " + mDpsResponseModel.get(position).getPrestataire());
        holder.tvMontent.setText(mDpsResponseModel.get(position).getTotalAchatMontant() + "da");
    }

    @Override
    public int getItemCount() {
        return mDpsResponseModel.size();
    }

    class DpsRecuHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvMontent, tvPrestataire;

        public DpsRecuHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_item_dps_recu_date);
            tvPrestataire = itemView.findViewById(R.id.tv_item_dps_recu_prestataire);
            tvMontent = itemView.findViewById(R.id.tv_item_dps_recu_montent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsSelectMode){
                        if (mSelectedItem.contains(mDpsResponseModel.get(getAdapterPosition()))){
                            ((CardView) itemView).setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.kahrakib_white));
                            mSelectedItem.remove( mDpsResponseModel.get(getAdapterPosition()) );
                            mDpsRecuIntermediate.setPrixTotal(mSelectedItem);
                        }else {
                            ((CardView) itemView).setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryColor));
                            mSelectedItem.add(mDpsResponseModel.get(getAdapterPosition()));
                            mDpsRecuIntermediate.setPrixTotal(mSelectedItem);
                        }
                        if (mSelectedItem.size() == 0){
                            mIsSelectMode = false;
                        }
                    }else {
                        Bundle bundleData = new Bundle();
                        bundleData.putInt(BundleKeys.LAUNCHER_KEY, mLauncher);
                        bundleData.putSerializable(BundleKeys.DPS_KEY,mDpsResponseModel.get(getAdapterPosition()));
                        NavigationUtil.navigateToDpsDetailsFragment(mContext, bundleData);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mIsSelectMode = true;
                    if (mSelectedItem.contains(mDpsResponseModel.get(getAdapterPosition()))){
                        ((CardView) itemView).setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.kahrakib_white));
                        mSelectedItem.remove( mDpsResponseModel.get(getAdapterPosition()) );
                        mDpsRecuIntermediate.setPrixTotal(mSelectedItem);
                    }else {
                        ((CardView) itemView).setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryColor));
                        mSelectedItem.add(mDpsResponseModel.get(getAdapterPosition()));
                        mDpsRecuIntermediate.setPrixTotal(mSelectedItem);
                    }
                    if (mSelectedItem.size() == 0){
                        mIsSelectMode = false;
                    }
                    return true;
                }
            });
        }
    }
}
