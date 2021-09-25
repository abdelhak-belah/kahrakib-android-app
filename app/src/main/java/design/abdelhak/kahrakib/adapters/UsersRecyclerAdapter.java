package design.abdelhak.kahrakib.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.intermediate.UtilisateurIntermediate;
import design.abdelhak.kahrakib.keys.AutoCompleteTextViewKeys;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.models.UserModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserHolder> {

    List<UserModel> mUsers;
    Animation rotateOpen;
    Context mContext;
    UtilisateurIntermediate utilisateurIntermediate;

    public UsersRecyclerAdapter(List<UserModel> users, Context context,UtilisateurIntermediate utilisateurIntermediate){
        mUsers = users;
        mContext = context;
        this.utilisateurIntermediate = utilisateurIntermediate;
    }


    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        UserHolder holder = new UserHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UsersRecyclerAdapter.UserHolder holder, int position) {
        holder.tvNomComplete.setText(mUsers.get(position).getPrenom()+" "+mUsers.get(position).getNom());
        if (mUsers.get(position).getDirection().isEmpty()){
            holder.tvDirectionTitre.setVisibility(View.GONE);
            holder.tvDirection.setVisibility(View.GONE);
        }else {
            holder.tvDirectionTitre.setVisibility(View.VISIBLE);
            holder.tvDirection.setVisibility(View.VISIBLE);
            holder.tvDirection.setText(mUsers.get(position).getDirection());
        }

        if (mUsers.get(position).getChantier().isEmpty()){
            holder.tvChantierTitre.setVisibility(View.GONE);
            holder.tvChantier.setVisibility(View.GONE);
        }else {
            holder.tvChantierTitre.setVisibility(View.VISIBLE);
            holder.tvChantier.setVisibility(View.VISIBLE);
            holder.tvChantier.setText(mUsers.get(position).getChantier());
        }

        switch (mUsers.get(position).getProfile()){
            case NetworkKeys.ROLE_ADMIN_KEY:
                holder.tvProfile.setText(AutoCompleteTextViewKeys.ADMINISATRATEUR_KEY);
                break;
            case NetworkKeys.ROLE_CLIENT_KEY:
                holder.tvProfile.setText(AutoCompleteTextViewKeys.CLIENT_KEY);
                break;
            case NetworkKeys.ROLE_CASSIER_KEY:
                holder.tvProfile.setText(AutoCompleteTextViewKeys.CASSIER_KEY);
                break;
            case NetworkKeys.ROLE_CASSIER_RESPO_KEY:
                holder.tvProfile.setText(AutoCompleteTextViewKeys.CASSIER_RESPO_KEY);
                break;
            case NetworkKeys.ROLE_COMPTABLE_KEY:
                holder.tvProfile.setText(AutoCompleteTextViewKeys.COMPTABLE_KEY);
                break;
        }

        holder.tvEmail.setText(mUsers.get(position).getEmail());
        holder.tvNaissance.setText(mUsers.get(position).getNaissance());
        holder.tvTelephone.setText(mUsers.get(position).getTelephone());

        Boolean isExpanded = mUsers.get(position).isExpanded();

        if (isExpanded){
            holder.glDetails.setVisibility(View.VISIBLE);
            rotateOpen = AnimationUtils.loadAnimation(mContext,R.anim.anim_rotate_open);
            holder.ivSeeMore.setAnimation(rotateOpen);
        }else {
            holder.glDetails.setVisibility(View.GONE);
        }

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterPopUpMenu(holder.ivMore,holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        TextView tvNomComplete, tvDirection, tvChantier, tvProfile, tvEmail, tvNaissance, tvTelephone,tvDirectionTitre,tvChantierTitre;
        ImageView ivMore, ivSeeMore;
        GridLayout glDetails;
        ConstraintLayout clContainer;

        public UserHolder(View itemView) {
            super(itemView);
            tvNomComplete = itemView.findViewById(R.id.tv_item_user_full_name);
            tvDirection = itemView.findViewById(R.id.tv_item_user_direction);
            tvDirectionTitre = itemView.findViewById(R.id.tv_item_user_direction_titre);
            tvChantier = itemView.findViewById(R.id.tv_item_user_Chabtier);
            tvChantierTitre = itemView.findViewById(R.id.tv_item_user_Chabtier_titre);
            tvProfile = itemView.findViewById(R.id.tv_item_user_Profile);
            tvEmail = itemView.findViewById(R.id.tv_item_user_email);
            tvNaissance = itemView.findViewById(R.id.tv_item_user_naissance);
            tvTelephone = itemView.findViewById(R.id.tv_item_user_telephone);
            ivMore = itemView.findViewById(R.id.iv_item_user_more);
            ivSeeMore = itemView.findViewById(R.id.iv_item_user_see_more);
            glDetails = itemView.findViewById(R.id.gl_item_user_details);
            clContainer = itemView.findViewById(R.id.cl_item_user);

            clContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserModel user = mUsers.get(getAdapterPosition());
                    user.setExpanded(!user.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }


    private void showFilterPopUpMenu(View v,UserHolder holder) {
        PopupMenu popupMenu = new PopupMenu(mContext,v);
        popupMenu.inflate(R.menu.menu_item_user);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_user_modifier:
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(BundleKeys.IS_UPDATE_KEY,true);
                        bundle.putSerializable(BundleKeys.UTILISATEUR_KEY,mUsers.get(holder.getAdapterPosition()));
                        NavigationUtil.navigateToAjouterUserFragment(mContext,bundle);
                        return true;
                    case  R.id.menu_item_user_supprimer:
                        utilisateurIntermediate.deleteUtilisateur(mUsers.get(holder.getAdapterPosition()).getId());
                        return true;
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }
}
