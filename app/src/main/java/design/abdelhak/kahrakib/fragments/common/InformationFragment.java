package design.abdelhak.kahrakib.fragments.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Observable;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.networks.service.UtilisateurService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.ValidatorUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InformationFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbDetails;
    private MaterialButton mMBtnMaj;
    private TextInputLayout mTilNom, mTilPrenom, mTilEmail, mTilNaissanceJour, mTilNaissanceMois, mTilNaissanceAnnee, mTilTelephone;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;

    private UtilisateurResponseModel utilisateurResponseModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/


    public InformationFragment() {
        // Required empty public constructor
    }

    public static InformationFragment newInstance(Bundle data) {
        InformationFragment informationFragment = new InformationFragment();
        informationFragment.setArguments(data);
        return informationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            utilisateurResponseModel = (UtilisateurResponseModel) getArguments().getSerializable(BundleKeys.USER_DETAILS_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_information_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_information_component_error);
        mComponentSucces = view.findViewById(R.id.cl_fragment_information_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_information_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_information_sub_container);
        mMtbDetails = view.findViewById(R.id.mtb_fragment_information);
        mTilNom = view.findViewById(R.id.til_fragment_information_nom);
        mTilPrenom = view.findViewById(R.id.til_fragment_information_prenom);
        mTilEmail = view.findViewById(R.id.til_fragment_information_email);
        mTilNaissanceJour = view.findViewById(R.id.til_fragment_information_naissance_jour);
        mTilNaissanceMois = view.findViewById(R.id.til_fragment_information_naissance_mois);
        mTilNaissanceAnnee = view.findViewById(R.id.til_fragment_information_naissance_annee);
        mTilTelephone = view.findViewById(R.id.til_fragment_information_telephone);
        mMBtnMaj = view.findViewById(R.id.mbtn_fragment_information_maj);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/


        /*Preload function*/
        initFields();
        setUpActionBar();

        /*call listeners*/
        mMBtnMaj.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == mMBtnMaj) {
            updateUtilisateur();
        }

        if (v == mMBtnComponentSuccesContinue) {
            NavigationUtil.back(getContext());
        }

        if (v == mMBtnComponentErrorRessayer) {
            updateUtilisateur();
        }

        if (v == mMBtnComponentErrorAcceuill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }



    private void initFields() {
        showLayout();
        mTilNom.getEditText().setText(utilisateurResponseModel.getPrenom());
        mTilPrenom.getEditText().setText(utilisateurResponseModel.getNom());
        mTilEmail.getEditText().setText(utilisateurResponseModel.getEmail());
        String[] naissance = utilisateurResponseModel.getDateNaissance().split("/");
        mTilNaissanceAnnee.getEditText().setText(naissance[0] + "");
        mTilNaissanceMois.getEditText().setText(naissance[1] + "");
        mTilNaissanceJour.getEditText().setText(naissance[2] + "");
        mTilTelephone.getEditText().setText(utilisateurResponseModel.getTelephone());
    }

    private void updateUtilisateur() {
        String nom = mTilNom.getEditText().getText().toString().trim();
        String prenom = mTilPrenom.getEditText().getText().toString().trim();
        String naissanceJour = mTilNaissanceJour.getEditText().getText().toString().trim();
        String naissanceMois = mTilNaissanceMois.getEditText().getText().toString().trim();
        String naissanceAnnee = mTilNaissanceAnnee.getEditText().getText().toString().trim();
        String email = mTilEmail.getEditText().getText().toString().trim();
        String telephone = mTilTelephone.getEditText().getText().toString().trim();


        if (checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email)) {
            showLoading();
            utilisateurResponseModel.setNom(nom);
            utilisateurResponseModel.setPrenom(prenom);
            utilisateurResponseModel.setEmail(email);
            utilisateurResponseModel.setDateNaissance(Integer.parseInt(naissanceAnnee)+"/"+Integer.parseInt(naissanceMois)+"/"+Integer.parseInt(naissanceJour));
            utilisateurResponseModel.setTelephone(telephone);

            UtilisateurService.newInstance()
                    .updateUtilisateur(getContext(),utilisateurResponseModel.getUtilisateurId(),utilisateurResponseModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UtilisateurResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(UtilisateurResponseModel utilisateurResponseModel) {
                            showSucces("vos coordonnées ont été mises à jour avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError();
                        }

                        @Override
                        public void onComplete() {}
                    });

        }else {
            return;
        }
    }








    /*------------------------------check fields start-----------------------------------*/
    private Boolean checkNomField(String nom) {
        if (nom.isEmpty()) {
            mTilNom.setErrorEnabled(true);
            mTilNom.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilNom.setErrorEnabled(false);
            mTilNom.setError("");
            return true;
        }
    }

    private Boolean checkPrenomField(String prenom) {
        if (prenom.isEmpty()) {
            mTilPrenom.setErrorEnabled(true);
            mTilPrenom.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilPrenom.setErrorEnabled(false);
            mTilPrenom.setError("");
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

    private Boolean checkEmailField(String email) {
        if (email.isEmpty()) {
            mTilEmail.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else if (!ValidatorUtil.isValidEmail(email)) {
            mTilEmail.setError(getString(R.string.message_entre_email_valide));
            return false;
        } else {
            mTilEmail.setError(null);
            mTilEmail.setErrorEnabled(false);
            return true;
        }
    }

    /*------------------------------check fields ends-----------------------------------*/








    private void setUpActionBar() {
        /*support action bar*/
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbDetails);

        /*call nav listener*/
        mMtbDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }

    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showSucces(String message) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);
        if (!message.isEmpty()) {
            mTvComponentSuccesMessage.setText(message);
        }
    }

    private void showLoading() {
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }



    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }
}