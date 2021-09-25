package design.abdelhak.kahrakib.fragments.admin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.AutoCompleteTextViewKeys;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.models.UserModel;
import design.abdelhak.kahrakib.networks.requests.AdministrateurRequestModel;
import design.abdelhak.kahrakib.networks.requests.CassierRequestModel;
import design.abdelhak.kahrakib.networks.requests.CassierRespoRequestModel;
import design.abdelhak.kahrakib.networks.requests.ClientRequestModel;
import design.abdelhak.kahrakib.networks.requests.ComptableRequestModel;
import design.abdelhak.kahrakib.networks.responses.AdministrateurResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierRespoResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import design.abdelhak.kahrakib.networks.responses.ComptableResponseModel;
import design.abdelhak.kahrakib.networks.responses.DirectionResponseModel;
import design.abdelhak.kahrakib.networks.responses.RoleResponseModel;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.networks.service.AdministrateurService;
import design.abdelhak.kahrakib.networks.service.CassierRespoService;
import design.abdelhak.kahrakib.networks.service.CassierService;
import design.abdelhak.kahrakib.networks.service.ChantierService;
import design.abdelhak.kahrakib.networks.service.ClientService;
import design.abdelhak.kahrakib.networks.service.ComptableService;
import design.abdelhak.kahrakib.networks.service.DirectionService;
import design.abdelhak.kahrakib.networks.service.RoleService;
import design.abdelhak.kahrakib.networks.service.UtilisateurService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.ValidatorUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AjouterUserFragment extends Fragment implements View.OnClickListener {

    /*-----------------------------------variables starts--------------------------------*/
    private MaterialToolbar mMtbAjouterUser;
    private MaterialButton mMBtnAjouterUser;
    private TextInputLayout mTilChantier, mTilProfil, mTilDirection, mTilNom, mTilPrenom, mTilEmail, mTilNaissanceJour, mTilNaissanceMois, mTilNaissanceAnnee, mTilMotDePasse;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;

    private Boolean isUpdate = false;
    private UserModel userModel;
    private UtilisateurResponseModel mUtilisateurResponseModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-----------------------------------variables ends--------------------------------*/

    public AjouterUserFragment() {
        // Required empty public constructor
    }

    public static AjouterUserFragment newInstance(Bundle data) {
        AjouterUserFragment fragment = new AjouterUserFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUpdate = (Boolean) getArguments().getSerializable(BundleKeys.IS_UPDATE_KEY);
            userModel = (UserModel) getArguments().getSerializable(BundleKeys.UTILISATEUR_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbAjouterUser = view.findViewById(R.id.mtb_fragment_ajouter_user);
        mComponentLoading = view.findViewById(R.id.pb_fragment_ajouter_user_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_ajouter_user_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_ajouter_user_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_ajouter_user_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_ajouetr_user_sub_container);
        mTilProfil = view.findViewById(R.id.til_fragment_ajouter_user_profil);
        mTilChantier = view.findViewById(R.id.til_fragment_ajouter_user_chantier);
        mTilDirection = view.findViewById(R.id.til_fragment_ajouter_user_direction);
        mTilNom = view.findViewById(R.id.til_fragment_ajouter_user_nom);
        mTilPrenom = view.findViewById(R.id.til_fragment_ajouter_user_prenom);
        mTilEmail = view.findViewById(R.id.til_fragment_ajouter_user_email);
        mTilNaissanceJour = view.findViewById(R.id.til_fragment_ajouter_user_naissance_jour);
        mTilNaissanceMois = view.findViewById(R.id.til_fragment_ajouter_user_naissance_mois);
        mTilNaissanceAnnee = view.findViewById(R.id.til_fragment_ajouter_user_naissance_annee);
        mTilMotDePasse = view.findViewById(R.id.til_fragment_ajouter_user_mot_de_passe);
        mMBtnAjouterUser = view.findViewById(R.id.mbtn_fragment_ajouter_user_ajouter);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/


        /*Preload function*/
        setUpActionBar();
        mTilDirection.setVisibility(View.GONE);
        if (isUpdate) {
            mMBtnAjouterUser.setText(R.string.fragment_ajouter_element_maj);
            getUtilisateur();
        } else {
            /*fetch function*/
            getRoles();
            getChantier();
        }

        /*call listener*/
        mMBtnAjouterUser.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    /*---------------------------------listener functions starts-----------------------------------*/
    @Override
    public void onClick(View v) {
        if (v == mMBtnAjouterUser) {
            if (isUpdate) {
                updateUtilisateur();
            } else {
                saveUtilisateur();
            }
        }


        if (v == mMBtnComponentSuccesContinue) {
            NavigationUtil.back(getContext());
        }

        if (v == mMBtnComponentErrorRessayer) {
            if (isUpdate) {
                updateUtilisateur();
            } else {
                saveUtilisateur();
            }
        }

        if (v == mMBtnComponentErrorAcceuill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }
    /*---------------------------------listener functions ends-----------------------------------*/


    /*---------------------------------fetch functions starts-----------------------------------*/
    private void getRoles() {
        showLoading();
        RoleService.newInstance()
                .getRoles(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RoleResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<RoleResponseModel> roleResponseModels) {
                        showLayout();
                        setUpActvForProfile(roleResponseModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getChantier() {
        showLoading();
        ChantierService.newInstance()
                .getChantiers(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ChantierResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<ChantierResponseModel> chantierResponseModels) {
                        showLayout();
                        setUpActvForChantier(chantierResponseModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getDirections() {
        showLoading();
        DirectionService.newInstance()
                .getDirections(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DirectionResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<DirectionResponseModel> directionResponseModels) {
                        setUpActvForDirection(directionResponseModels);
                        showLayout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("");
                        Toast.makeText(getContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getUtilisateur() {
        UtilisateurService.newInstance()
                .getUtilisateur(getContext(), userModel.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UtilisateurResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(UtilisateurResponseModel utilisateurResponseModel) {
                        mUtilisateurResponseModel = utilisateurResponseModel;
                        initFieldsForUpdate(utilisateurResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void updateUtilisateur() {
        String nom = mTilNom.getEditText().getText().toString().trim();
        String prenom = mTilPrenom.getEditText().getText().toString().trim();
        String naissanceJour = mTilNaissanceJour.getEditText().getText().toString().trim();
        String naissanceMois = mTilNaissanceMois.getEditText().getText().toString().trim();
        String naissanceAnnee = mTilNaissanceAnnee.getEditText().getText().toString().trim();
        String email = mTilEmail.getEditText().getText().toString().trim();

        if (checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email)) {
            showLoading();
            mUtilisateurResponseModel.setNom(nom);
            mUtilisateurResponseModel.setPrenom(prenom);
            mUtilisateurResponseModel.setDateNaissance(Integer.parseInt(naissanceAnnee) + "/" + Integer.parseInt(naissanceMois) + "/" + Integer.parseInt(naissanceJour));
            mUtilisateurResponseModel.setEmail(email);

            UtilisateurService.newInstance()
                    .updateUtilisateur(getContext(), mUtilisateurResponseModel.getUtilisateurId(), mUtilisateurResponseModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UtilisateurResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(UtilisateurResponseModel utilisateurResponseModel) {
                            showSucces("les détails de l'utilisateur ont été mis à jour avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError("");
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            return;
        }

    }

    private void saveUtilisateur() {
        String profile = mTilProfil.getEditText().getText().toString().trim();
        String chantier = mTilChantier.getEditText().getText().toString().trim();
        String direction = mTilDirection.getEditText().getText().toString().trim();
        String nom = mTilNom.getEditText().getText().toString().trim();
        String prenom = mTilPrenom.getEditText().getText().toString().trim();
        String naissanceJour = mTilNaissanceJour.getEditText().getText().toString().trim();
        String naissanceMois = mTilNaissanceMois.getEditText().getText().toString().trim();
        String naissanceAnnee = mTilNaissanceAnnee.getEditText().getText().toString().trim();
        String email = mTilEmail.getEditText().getText().toString().trim();
        String motDePasse = mTilMotDePasse.getEditText().getText().toString().trim();


        switch (profile) {
            case AutoCompleteTextViewKeys.ADMINISATRATEUR_KEY:
                if (checkProfilField(profile) & checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email) & checkMotDePasseField(motDePasse)) {
                    AdministrateurRequestModel administrateurRequestModel = new AdministrateurRequestModel(
                            email,
                            motDePasse,
                            nom, prenom,
                            naissanceAnnee + "/" + naissanceMois + "/" + naissanceJour
                    );
                    saveAdminisatrateur(administrateurRequestModel);
                    break;
                }
                break;
            case AutoCompleteTextViewKeys.CLIENT_KEY:
                if (checkProfilField(profile) & checkChantierField(chantier) & checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email) & checkMotDePasseField(motDePasse)) {
                    ClientRequestModel clientRequestModel = new ClientRequestModel(
                            email,
                            motDePasse,
                            nom,
                            prenom,
                            naissanceAnnee + "/" + naissanceMois + "/" + naissanceJour,
                            chantier
                    );
                    saveClient(clientRequestModel);
                    break;
                }
                break;
            case AutoCompleteTextViewKeys.CASSIER_KEY:
                if (checkProfilField(profile) & checkChantierField(chantier) & checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email) & checkMotDePasseField(motDePasse)) {
                    CassierRequestModel cassierRequestModel = new CassierRequestModel(
                            email,
                            motDePasse,
                            nom,
                            prenom,
                            naissanceAnnee + "/" + naissanceMois + "/" + naissanceJour,
                            chantier
                    );
                    saveCassier(cassierRequestModel);
                    break;
                }
                break;
            case AutoCompleteTextViewKeys.CASSIER_RESPO_KEY:
                if (checkProfilField(profile) & checkChantierField(chantier) & checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email) & checkMotDePasseField(motDePasse)) {
                    CassierRespoRequestModel cassierRespoRequestModel = new CassierRespoRequestModel(
                            email,
                            motDePasse,
                            nom,
                            prenom,
                            naissanceAnnee + "/" + naissanceMois + "/" + naissanceJour,
                            chantier
                    );
                    saveCassierRespo(cassierRespoRequestModel);
                    break;
                }
                break;
            case AutoCompleteTextViewKeys.COMPTABLE_KEY:
                if (checkProfilField(profile) & checkNomField(nom) & checkPrenomField(prenom) & checkNaissanceJourField(naissanceJour) & checkNaissanceMoisField(naissanceMois) & checkNaissanceAnneeField(naissanceAnnee) & checkEmailField(email) & checkMotDePasseField(motDePasse) & checkDirectionField(direction)) {
                    ComptableRequestModel comptableRequestModel = new ComptableRequestModel(
                            email,
                            motDePasse,
                            nom,
                            prenom,
                            naissanceAnnee + "/" + naissanceMois + "/" + naissanceJour,
                            direction
                    );
                    saveComptable(comptableRequestModel);
                    break;
                }
                break;
        }
    }

    private void saveComptable(ComptableRequestModel comptableRequestModel) {
        showLoading();
        ComptableService.newInstance()
                .saveComptable(getContext(), comptableRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ComptableResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ComptableResponseModel comptableResponseModel) {
                        showSucces("comptable ajouté avec succès");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("il y'a déjà un comptable dans cette direction");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void saveCassierRespo(CassierRespoRequestModel cassierRespoRequestModel) {
        showLoading();
        CassierRespoService.newInstance()
                .saveCassiersRespo(getContext(), cassierRespoRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CassierRespoResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(CassierRespoResponseModel cassierRespoResponseModel) {
                        showSucces("caissier responsable ajouté avec succès");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("il y'a déjà un responsable caissier  existe dans cette chantier");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveCassier(CassierRequestModel cassierRequestModel) {
        showLoading();
        CassierService.newInstance()
                .saveCassier(getContext(), cassierRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CassierResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(CassierResponseModel cassierResponseModel) {
                        showSucces("caissier ajouté avec succès");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        showError("il y'a déjà une caissier dans cette chantier");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveClient(ClientRequestModel clientRequestModel) {
        showLoading();
        ClientService.newInstance()
                .saveClient(getContext(), clientRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClientResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ClientResponseModel clientResponseModel) {
                        showSucces("client ajouté avec succès");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void saveAdminisatrateur(AdministrateurRequestModel administrateurRequestModel) {
        showLoading();
        AdministrateurService.newInstance()
                .saveAdministareur(getContext(), administrateurRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdministrateurResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(AdministrateurResponseModel administrateurResponseModel) {
                        showSucces("administrateur ajouté avec succès");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError("");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /*---------------------------------fetch functions ends-----------------------------------*/


    /*---------------------------------setup functions starts-----------------------------------*/
    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbAjouterUser);

        if (isUpdate) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.fragment_ajouter_user_maj_titre);
        }

        //call listener
        mMtbAjouterUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }

    private void setUpActvForChantier(List<ChantierResponseModel> chantierResponseModels) {
        String[] chantier = new String[chantierResponseModels.size()];
        for (int i = 0; i < chantierResponseModels.size(); i++) {
            chantier[i] = chantierResponseModels.get(i).getImputation();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, chantier);
        MaterialAutoCompleteTextView mMACTV = (MaterialAutoCompleteTextView) mTilChantier.getEditText();
        mMACTV.setAdapter(adapter);
    }

    private void setUpActvForProfile(List<RoleResponseModel> roleResponseModels) {
        String[] roles = new String[roleResponseModels.size()];

        for (int i = 0; i < roleResponseModels.size(); i++) {
            roles[i] = roleResponseModels.get(i).getRole();
            if (roles[i].equals(NetworkKeys.ROLE_ADMIN_KEY)) {
                roles[i] = AutoCompleteTextViewKeys.ADMINISATRATEUR_KEY;
            }
            if (roles[i].equals(NetworkKeys.ROLE_CLIENT_KEY)) {
                roles[i] = AutoCompleteTextViewKeys.CLIENT_KEY;
            }
            if (roles[i].equals(NetworkKeys.ROLE_CASSIER_KEY)) {
                roles[i] = AutoCompleteTextViewKeys.CASSIER_KEY;
            }
            if (roles[i].equals(NetworkKeys.ROLE_CASSIER_RESPO_KEY)) {
                roles[i] = AutoCompleteTextViewKeys.CASSIER_RESPO_KEY;
            }
            if (roles[i].equals(NetworkKeys.ROLE_COMPTABLE_KEY)) {
                roles[i] = AutoCompleteTextViewKeys.COMPTABLE_KEY;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, roles);
        MaterialAutoCompleteTextView mMACTV = (MaterialAutoCompleteTextView) mTilProfil.getEditText();
        mMACTV.setAdapter(adapter);
        mMACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                if (item.equals(AutoCompleteTextViewKeys.COMPTABLE_KEY)) {
                    getDirections();
                    mTilDirection.setVisibility(View.VISIBLE);
                    mTilChantier.setVisibility(View.GONE);
                } else if (item.equals(AutoCompleteTextViewKeys.ADMINISATRATEUR_KEY)) {
                    mTilDirection.setVisibility(View.GONE);
                    mTilChantier.setVisibility(View.GONE);
                } else {
                    mTilDirection.setVisibility(View.GONE);
                    mTilChantier.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setUpActvForDirection(List<DirectionResponseModel> directionResponseModels) {
        String[] roles = new String[directionResponseModels.size()];
        for (int i = 0; i < directionResponseModels.size(); i++) {
            roles[i] = directionResponseModels.get(i).getImputation();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, roles);
        MaterialAutoCompleteTextView mMACTV = (MaterialAutoCompleteTextView) mTilDirection.getEditText();
        mMACTV.setAdapter(adapter);
    }

    private void initFieldsForUpdate(UtilisateurResponseModel utilisateurResponseModel) {
        switch (utilisateurResponseModel.getRole().getRole()) {
            case NetworkKeys.ROLE_ADMIN_KEY:
                mTilProfil.getEditText().setText(AutoCompleteTextViewKeys.ADMINISATRATEUR_KEY);
                break;
            case NetworkKeys.ROLE_CLIENT_KEY:
                mTilProfil.getEditText().setText(AutoCompleteTextViewKeys.CLIENT_KEY);
                break;
            case NetworkKeys.ROLE_CASSIER_KEY:
                mTilProfil.getEditText().setText(AutoCompleteTextViewKeys.CASSIER_KEY);
                break;
            case NetworkKeys.ROLE_CASSIER_RESPO_KEY:
                mTilProfil.getEditText().setText(AutoCompleteTextViewKeys.CASSIER_RESPO_KEY);
                break;
            case NetworkKeys.ROLE_COMPTABLE_KEY:
                mTilProfil.getEditText().setText(AutoCompleteTextViewKeys.COMPTABLE_KEY);
                break;
        }
        mTilNom.getEditText().setText(utilisateurResponseModel.getNom());
        mTilPrenom.getEditText().setText(utilisateurResponseModel.getPrenom());
        mTilEmail.getEditText().setText(utilisateurResponseModel.getEmail());

        String[] naissance = utilisateurResponseModel.getDateNaissance().split("/");
        mTilNaissanceAnnee.getEditText().setText(naissance[0] + "");
        mTilNaissanceMois.getEditText().setText(naissance[1] + "");
        mTilNaissanceJour.getEditText().setText(naissance[2] + "");

        mTilChantier.setVisibility(View.GONE);
        mTilDirection.setVisibility(View.GONE);
        mTilMotDePasse.setVisibility(View.GONE);
    }
    /*---------------------------------setup functions ends-----------------------------------*/


    /*---------------------------------checking functions starts-----------------------------------*/
    private Boolean checkProfilField(String profile) {
        if (profile.isEmpty()) {
            mTilProfil.setErrorEnabled(true);
            mTilProfil.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilProfil.setErrorEnabled(false);
            mTilProfil.setError("");
            return true;
        }
    }

    private Boolean checkChantierField(String chantier) {
        if (chantier.isEmpty()) {
            mTilChantier.setErrorEnabled(true);
            mTilChantier.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilChantier.setErrorEnabled(false);
            mTilChantier.setError("");
            return true;
        }
    }

    private Boolean checkDirectionField(String chantier) {
        if (chantier.isEmpty()) {
            mTilDirection.setErrorEnabled(true);
            mTilDirection.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilDirection.setErrorEnabled(false);
            mTilDirection.setError("");
            return true;
        }
    }

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

    private Boolean checkMotDePasseField(String motDePasse) {
        if (motDePasse.isEmpty()) {
            mTilMotDePasse.setErrorEnabled(true);
            mTilMotDePasse.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilMotDePasse.setErrorEnabled(false);
            mTilMotDePasse.setError("");
            return true;
        }
    }
    /*---------------------------------checking functions ends-----------------------------------*/


    /*---------------------------------handling functions starts-----------------------------------*/
    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
    }

    private void showError(String messsage) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
        if (!messsage.isEmpty()) {
            mMBtnComponentErrorRessayer.setVisibility(View.INVISIBLE);
            mTvComponentErrorMessage.setText(messsage);
        }
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
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }
    /*---------------------------------checking functions ends-----------------------------------*/


    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }
}