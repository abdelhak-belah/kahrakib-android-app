package design.abdelhak.kahrakib.fragments.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.requests.MotDePasseRequest;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.networks.service.AuthService;
import design.abdelhak.kahrakib.networks.service.UtilisateurService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PassFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbPass;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private MaterialButton mMBtnEnregistrer;
    private TextInputLayout mTilMotDePasseActuel,mTilMotDePasseNouveau,mTilMotDePasseConferme;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;

    private UtilisateurResponseModel utilisateurResponseModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/


    public PassFragment() {
        // Required empty public constructor
    }


    public static PassFragment newInstance(Bundle data) {
        PassFragment passFragment = new PassFragment();
        passFragment.setArguments(data);
        return passFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            utilisateurResponseModel = (UtilisateurResponseModel) getArguments().getSerializable(BundleKeys.UTILISATEUR_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbPass = view.findViewById(R.id.mtb_fragment_password);
        mComponentLoading = view.findViewById(R.id.pb_fragment_pass_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_pass_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_pass_component_succes);
        mMBtnEnregistrer = view.findViewById(R.id.mbtn_fragment_pass_enregistrer);
        mClContainer = view.findViewById(R.id.cl_fragment_pass_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_pass_sub_container);
        mTilMotDePasseActuel = view.findViewById(R.id.til_fragment_pass_actuel_mot_de_passe);
        mTilMotDePasseNouveau = view.findViewById(R.id.til_fragment_pass_nouveau_mot_de_passe);
        mTilMotDePasseConferme = view.findViewById(R.id.til_fragment_pass_confermez_mot_de_passe);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();


        /*call listener*/
        mMBtnEnregistrer.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == mMBtnEnregistrer){
            updateMotDePasse();
        }

        if (v == mMBtnComponentSuccesContinue) {
            NavigationUtil.back(getContext());
        }

        if (v == mMBtnComponentErrorRessayer) {
            updateMotDePasse();
        }

        if (v == mMBtnComponentErrorAcceuill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }

    private void updateMotDePasse() {
        String actuelMotDePasse = mTilMotDePasseActuel.getEditText().getText().toString().trim();
        String nouveauMotDePasse = mTilMotDePasseNouveau.getEditText().getText().toString().trim();
        String confermeMotDePasse = mTilMotDePasseConferme.getEditText().getText().toString().trim();

        if (checkActuelMotDePasseField(actuelMotDePasse) & checkNouveauMotDePasseField(nouveauMotDePasse) & checkConfermeMotDePasseField(confermeMotDePasse) & checkMotDePasseMatching(nouveauMotDePasse,confermeMotDePasse)){
            showLoading();
            MotDePasseRequest motDePasseRequest = new MotDePasseRequest(
                    utilisateurResponseModel.getUtilisateurId(),
                    utilisateurResponseModel.getEmail(),
                    actuelMotDePasse,
                    nouveauMotDePasse
            );

            AuthService.newInstance()
                    .updateMotDePasse(getContext(),motDePasseRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UtilisateurResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(UtilisateurResponseModel utilisateurResponseModel) {
                            showSucces("Mot de passe mis à jour avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showLayout();
                            mTilMotDePasseActuel.setErrorEnabled(true);
                            mTilMotDePasseActuel.setError(getString(R.string.message_mot_de_passe_incorrecte));
                        }

                        @Override
                        public void onComplete() {}
                    });


        }else {
            return;
        }


    }




    private Boolean checkActuelMotDePasseField(String motDePasseActuel) {
        if (motDePasseActuel.isEmpty()) {
            mTilMotDePasseActuel.setErrorEnabled(true);
            mTilMotDePasseActuel.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilMotDePasseActuel.setErrorEnabled(false);
            mTilMotDePasseActuel.setError("");
            return true;
        }
    }

    private Boolean checkNouveauMotDePasseField(String motDePasseNouveau) {
        if (motDePasseNouveau.isEmpty()) {
            mTilMotDePasseNouveau.setErrorEnabled(true);
            mTilMotDePasseNouveau.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilMotDePasseNouveau.setErrorEnabled(false);
            mTilMotDePasseNouveau.setError("");
            return true;
        }
    }

    private Boolean checkConfermeMotDePasseField(String motDePasseConferme) {
        if (motDePasseConferme.isEmpty()) {
            mTilMotDePasseConferme.setErrorEnabled(true);
            mTilMotDePasseConferme.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilMotDePasseConferme.setErrorEnabled(false);
            mTilMotDePasseConferme.setError("");
            return true;
        }
    }

    private Boolean checkMotDePasseMatching(String motDePasseNouveau,String motDePasseConferme){
        if (!motDePasseNouveau.equals(motDePasseConferme)) {
            mTilMotDePasseConferme.setErrorEnabled(true);
            mTilMotDePasseConferme.setError(getString(R.string.message_mot_de_passe_correspondance));
            return false;
        } else {
            mTilMotDePasseConferme.setErrorEnabled(false);
            mTilMotDePasseConferme.setError("");
            return true;
        }
    }

    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mMtbPass);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        //call listener
        mMtbPass.setNavigationOnClickListener(new View.OnClickListener() {
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

    private void showLoading() {
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }

    private void showSucces(String message) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);

        mTvComponentSuccesMessage.setText(message);
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }

}