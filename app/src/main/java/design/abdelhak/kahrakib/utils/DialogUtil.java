package design.abdelhak.kahrakib.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.intermediate.ConfermeEnAttenteIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeEnvoiIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeRejeterIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeSupprissionIntermediate;
import design.abdelhak.kahrakib.intermediate.ElementIntermediate;

public class DialogUtil {

    public static void showConfermeSupprissionDialog(Context context, ConfermeSupprissionIntermediate confermeSupprissionIntermediate, Long id,String message) {
        Dialog confermeSupprissionDialog = new Dialog(context);

        confermeSupprissionDialog.setContentView(R.layout.dialog_conferme_suppression);
        confermeSupprissionDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        confermeSupprissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confermeSupprissionDialog.setCanceledOnTouchOutside(false);

        //inflate
        ImageView mIvClose = confermeSupprissionDialog.findViewById(R.id.iv_dialog_conferme_suppression_close);
        TextView mTvMessage = confermeSupprissionDialog.findViewById(R.id.tv_dialog_conferme_suppression_message);
        MaterialButton mMbtnConferme = confermeSupprissionDialog.findViewById(R.id.mbtn_dialog_conferme_suppression_conferme);
        MaterialButton mMbtnAnnuler = confermeSupprissionDialog.findViewById(R.id.mbtn_dialog_conferme_suppression_annuler);

        mTvMessage.setText(message);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeSupprissionDialog.dismiss();
                confermeSupprissionIntermediate.confermeSupprission(false,id);
            }
        });

        mMbtnConferme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeSupprissionIntermediate.confermeSupprission(true,id);
                confermeSupprissionDialog.dismiss();
            }
        });


        mMbtnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeSupprissionDialog.dismiss();
                confermeSupprissionIntermediate.confermeSupprission(false,id);
            }
        });

        confermeSupprissionDialog.show();
    }

    public static void showConfermeEnvoiDialog(Context context, ConfermeEnvoiIntermediate confermeEnvoiIntermediate, List<Long> ids, String message) {
        Dialog confermeEnvoiDialog = new Dialog(context);

        confermeEnvoiDialog.setContentView(R.layout.dialog_conferme_envoi);
        confermeEnvoiDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        confermeEnvoiDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confermeEnvoiDialog.setCanceledOnTouchOutside(false);

        //inflate
        ImageView mIvClose = confermeEnvoiDialog.findViewById(R.id.iv_dialog_conferme_envoi_close);
        TextView mTvMessage = confermeEnvoiDialog.findViewById(R.id.tv_dialog_conferme_envoi_message);
        MaterialButton mMbtnEnvoyer = confermeEnvoiDialog.findViewById(R.id.mbtn_dialog_conferme_envoi_envoyer);
        MaterialButton mMbtnAnnuler = confermeEnvoiDialog.findViewById(R.id.mbtn_dialog_conferme_envoi_annuler);

        mTvMessage.setText(message);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeEnvoiDialog.dismiss();
                confermeEnvoiIntermediate.confermeEnvoyer(false,ids);
            }
        });

        mMbtnEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeEnvoiIntermediate.confermeEnvoyer(true,ids);
                confermeEnvoiDialog.dismiss();
            }
        });


        mMbtnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeEnvoiDialog.dismiss();
                confermeEnvoiIntermediate.confermeEnvoyer(false,ids);
            }
        });

        confermeEnvoiDialog.show();
    }

    public static void showConfermeRejeterDialog(Context context, ConfermeRejeterIntermediate confermeRejeterIntermediate, List<Long> ids, String message) {
        Dialog confermeRejeterDialog = new Dialog(context);

        confermeRejeterDialog.setContentView(R.layout.dialog_conferme_rejeter);
        confermeRejeterDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        confermeRejeterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confermeRejeterDialog.setCanceledOnTouchOutside(false);

        //inflate
        ImageView mIvClose = confermeRejeterDialog.findViewById(R.id.iv_dialog_conferme_rejeter_close);
        TextView mTvMessage = confermeRejeterDialog.findViewById(R.id.tv_dialog_conferme_rejeter_message);
        MaterialButton mMbtnRejeter = confermeRejeterDialog.findViewById(R.id.mbtn_dialog_conferme_rejeter_rejeter);
        MaterialButton mMbtnAnnuler = confermeRejeterDialog.findViewById(R.id.mbtn_dialog_conferme_rejeter_annuler);

        mTvMessage.setText(message);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeRejeterDialog.dismiss();
                confermeRejeterIntermediate.confermeRejeter(false,ids);
            }
        });

        mMbtnRejeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeRejeterIntermediate.confermeRejeter(true,ids);
                confermeRejeterDialog.dismiss();
            }
        });


        mMbtnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeRejeterDialog.dismiss();
                confermeRejeterIntermediate.confermeRejeter(false,ids);
            }
        });

        confermeRejeterDialog.show();
    }

    public static void showConfermeEnAttenteDialog(Context context, ConfermeEnAttenteIntermediate confermeEnAttenteIntermediate, List<Long> ids, String message) {
        Dialog confermeEnAttenteDialog = new Dialog(context);

        confermeEnAttenteDialog.setContentView(R.layout.dialog_conferme_en_attente);
        confermeEnAttenteDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        confermeEnAttenteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confermeEnAttenteDialog.setCanceledOnTouchOutside(false);

        //inflate
        ImageView mIvClose = confermeEnAttenteDialog.findViewById(R.id.iv_dialog_conferme_en_attente_close);
        TextView mTvMessage = confermeEnAttenteDialog.findViewById(R.id.tv_dialog_conferme_en_attente_message);
        MaterialButton mMbtnConfirmer = confermeEnAttenteDialog.findViewById(R.id.mbtn_dialog_conferme_en_attente_confirmer);
        MaterialButton mMbtnAnnuler = confermeEnAttenteDialog.findViewById(R.id.mbtn_dialog_conferme_en_attente_annuler);

        mTvMessage.setText(message);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeEnAttenteDialog.dismiss();
                confermeEnAttenteIntermediate.confermeEnAttente(false,ids);
            }
        });

        mMbtnConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeEnAttenteIntermediate.confermeEnAttente(true,ids);
                confermeEnAttenteDialog.dismiss();
            }
        });


        mMbtnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermeEnAttenteDialog.dismiss();
                confermeEnAttenteIntermediate.confermeEnAttente(false,ids);
            }
        });

        confermeEnAttenteDialog.show();
    }

}
