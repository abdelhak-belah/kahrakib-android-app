package design.abdelhak.kahrakib.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.utils.NavigationUtil;


public class AjouterDpsFragment extends Fragment implements View.OnClickListener {


    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbAjouterDps;
    private MaterialButton mMBtnSuivant;
    private TextInputLayout mTilNomPrestataire,mTilAdressePrestataire, mTilNaissanceJour, mTilNaissanceMois, mTilNaissanceAnnee;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    /*-------------------------------------------------------------------*/

    public AjouterDpsFragment() {
        // Required empty public constructor
    }


    public static AjouterDpsFragment newInstance(Bundle data) {
        AjouterDpsFragment fragment = new AjouterDpsFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_dps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbAjouterDps = view.findViewById(R.id.mtb_fragment_ajouter_dps);
        mComponentLoading = view.findViewById(R.id.pb_fragment_ajouter_dps_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_ajouter_dps_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_ajouter_dps_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_ajouter_dps_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_ajouter_dps_sub_container);
        mTilNomPrestataire = view.findViewById(R.id.til_fragment_ajouter_dps_nom_prestataire);
        mTilAdressePrestataire = view.findViewById(R.id.til_fragment_ajouter_dps_adresse_prestataire);
        mMBtnSuivant = view.findViewById(R.id.mbtn_fragment_ajouter_dps_suivant);
        /*-------------------------------------------------------------------*/


        /*Preload function*/
        showLoading();
        setUpActionBar();

        /*call listener*/
        mMBtnSuivant.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if (v == mMBtnSuivant){
            String nomPrestataire = mTilNomPrestataire.getEditText().getText().toString().trim();
            String adressePrestataire = mTilAdressePrestataire.getEditText().getText().toString().trim();

            if (checkNomPrestataireField(nomPrestataire) & checkAdressePrestataireField(adressePrestataire)){

                Bundle bundleData = new Bundle();
                bundleData.putString(BundleKeys.DPS_NOM_PRESTATAIRE_KEY,nomPrestataire);
                bundleData.putString(BundleKeys.DPS_ADRESSE_PRESTATAIRE_KEY,adressePrestataire);
                NavigationUtil.navigateToAjouterAchatsFragment(getContext(),bundleData);
            }else {
                return;
            }

        }
    }




    private Boolean checkNomPrestataireField(String nomPrestataire) {
        if (nomPrestataire.isEmpty()) {
            mTilNomPrestataire.setErrorEnabled(true);
            mTilNomPrestataire.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilNomPrestataire.setErrorEnabled(false);
            mTilNomPrestataire.setError("");
            return true;
        }
    }


    private Boolean checkAdressePrestataireField(String adressePrestataire) {
        if (adressePrestataire.isEmpty()) {
            mTilAdressePrestataire.setErrorEnabled(true);
            mTilAdressePrestataire.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilAdressePrestataire.setErrorEnabled(false);
            mTilAdressePrestataire.setError("");
            return true;
        }
    }


    private Boolean checkNaissanceJourField(String naissanceJour) {
        if (naissanceJour.isEmpty()) {
            mTilNaissanceJour.setErrorEnabled(true);
            mTilNaissanceJour.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        }

        int naissanceJourValue = Integer.parseInt(naissanceJour);
        if (naissanceJourValue <= 0 || naissanceJourValue > 31) {
            mTilNaissanceJour.setErrorEnabled(true);
            mTilNaissanceJour.setError(getString(R.string.message_entre_jour_valide));
            return false;
        } else {
            mTilNaissanceJour.setErrorEnabled(false);
            mTilNaissanceJour.setError("");
            return true;
        }
    }

    private Boolean checkNaissanceMoisField(String naissanceMois) {
        if (naissanceMois.isEmpty()) {
            mTilNaissanceMois.setErrorEnabled(true);
            mTilNaissanceMois.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        }


        int naissanceMoisValue = Integer.parseInt(naissanceMois);
        if (naissanceMoisValue <= 0 || naissanceMoisValue > 12) {
            mTilNaissanceMois.setErrorEnabled(true);
            mTilNaissanceMois.setError(getString(R.string.message_entre_mois_valide));
            return false;
        } else {
            mTilNaissanceMois.setErrorEnabled(false);
            mTilNaissanceMois.setError("");
            return true;
        }
    }

    private Boolean checkNaissanceAnneeField(String naissanceAnnee) {
        if (naissanceAnnee.isEmpty()) {
            mTilNaissanceAnnee.setErrorEnabled(true);
            mTilNaissanceAnnee.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        }

        int naissanceAnneeValue = Integer.parseInt(naissanceAnnee);
        if (naissanceAnneeValue <= 1900 || naissanceAnneeValue > 2021) {
            mTilNaissanceAnnee.setErrorEnabled(true);
            mTilNaissanceAnnee.setError(getString(R.string.message_entre_annee_valide));
            return false;
        } else {
            mTilNaissanceAnnee.setErrorEnabled(false);
            mTilNaissanceAnnee.setError("");
            return true;
        }
    }



    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mMtbAjouterDps);

        //call listener
        mMtbAjouterDps.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });

        showLayout();
    }

    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showSucces() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }

}