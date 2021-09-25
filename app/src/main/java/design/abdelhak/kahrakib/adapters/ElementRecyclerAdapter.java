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
import design.abdelhak.kahrakib.intermediate.ElementIntermediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.models.ElementModel;
import design.abdelhak.kahrakib.networks.responses.ElementResponseModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class ElementRecyclerAdapter extends RecyclerView.Adapter<ElementRecyclerAdapter.ElementHolder>{

    Context mContext;
    List<ElementResponseModel> mElements;
    ElementIntermediate elementIntermediate;

    public ElementRecyclerAdapter(Context context, List<ElementResponseModel> elements,ElementIntermediate elementIntermediate) {
        this.mContext = context;
        this.mElements = elements;
        this.elementIntermediate = elementIntermediate;
    }

    @NonNull
    @Override
    public ElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_element,parent,false);
        ElementHolder elementHolder = new ElementHolder(v);
        return elementHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ElementHolder holder, int position) {
        holder.tvNomElement.setText(mElements.get(position).getNom());
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterPopUpMenu(holder.ivMore,holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    class ElementHolder extends RecyclerView.ViewHolder {
        TextView tvNomElement;
        ImageView ivMore;

        public ElementHolder(@NonNull View itemView) {
            super(itemView);
            tvNomElement = itemView.findViewById(R.id.tv_item_element_nom);
            ivMore = itemView.findViewById(R.id.iv_item_element_more);
        }
    }

    private void showFilterPopUpMenu(View v,ElementHolder holder) {
        PopupMenu popupMenu = new PopupMenu(mContext,v);
        popupMenu.inflate(R.menu.menu_item_element);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_element_modifier:
                        Toast.makeText(mContext, "modifier", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(BundleKeys.IS_UPDATE_KEY,true);
                        bundle.putSerializable(BundleKeys.ELEMENT_KEY,mElements.get(holder.getAdapterPosition()));
                        NavigationUtil.navigateToAjouterElementFragment(mContext,bundle);
                        return true;
                    case  R.id.menu_item_element_supprimer:
                        elementIntermediate.deleteElement(mElements.get(holder.getAdapterPosition()).getElementId());
                        return true;
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }
}
