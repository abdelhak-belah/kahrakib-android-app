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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.requests.ChantierRequestModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.networks.responses.DirectionResponseModel;
import design.abdelhak.kahrakib.networks.service.ChantierService;
import design.abdelhak.kahrakib.networks.service.DirectionService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AjouterChantierFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbAjouterChantier;
    private MaterialButton mMBtnAjouterChantier;
    private TextInputLayout mTilDirection, mTilNom, mTilAdresse;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage;

    private Boolean isUpdate = false;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ChantierResponseModel chantierResponseModel;
    /*-------------------------------------------------------------------*/


    public AjouterChantierFragment() {
        // Required empty public constructor
    }

    public static AjouterChantierFragment newInstance(Bundle data) {
        AjouterChantierFragment fragment = new AjouterChantierFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUpdate = (Boolean) getArguments().getSerializable(BundleKeys.IS_UPDATE_KEY);
            chantierResponseModel = (ChantierResponseModel) getArguments().getSerializable(BundleKeys.CHANTIER_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_chantier, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbAjouterChantier = view.findViewById(R.id.mtb_fragment_ajouter_chantier);
        mComponentLoading = view.findViewById(R.id.pb_fragment_ajouter_chantier_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_ajouter_chantier_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_ajouter_chantier_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_ajouter_chantier_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_ajouter_chantier_sub_container);
        mTilDirection = view.findViewById(R.id.til_fragment_ajouter_chantier_direction);
        mTilNom = view.findViewById(R.id.til_fragment_ajouter_chantier_nom);
        mTilAdresse = view.findViewById(R.id.til_fragment_ajouter_chantier_adresse);
        mMBtnAjouterChantier = view.findViewById(R.id.mbtn_fragment_ajouter_chantier_ajouter);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        /*-------------------------------------------------------------------*/

        //fetch function
        getDirections();

        /*Preload function*/
        setUpActionBar();
        if (isUpdate) {
            mMBtnAjouterChantier.setText(R.string.fragment_ajouter_chantier_maj);
            initField();
        }


        //call listener
        mMBtnAjouterChantier.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mMBtnAjouterChantier) {
            if (isUpdate) {
                updateChantier();
            } else {
                saveChantier();
            }
        }

        if (v == mMBtnComponentSuccesContinue) {
            NavigationUtil.back(getContext());
        }

        if (v == mMBtnComponentErrorRessayer) {
            if (isUpdate) {
                updateChantier();
            } else {
                saveChantier();
            }
        }

        if (v == mMBtnComponentErrorAcceuill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }


    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbAjouterChantier);

        if (isUpdate) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.fragment_ajouter_chantier_maj_titre);
        }

        //call listener
        mMtbAjouterChantier.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }


    private void setUpActvForDirection(String[] imputation) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, imputation);
        AutoCompleteTextView mMactv = (AutoCompleteTextView) mTilDirection.getEditText();
        mMactv.setAdapter(adapter);
    }

    private void initField() {
        mTilDirection.getEditText().setText(chantierResponseModel.getDirection().getImputation());
        mTilNom.getEditText().setText(chantierResponseModel.getNom());
        mTilAdresse.getEditText().setText(chantierResponseModel.getAdresse());
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

    private void showSucces(String message) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);
        mTvComponentSuccesMessage.setText(message);
    }

    private void showLoading() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
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
                        String[] imputation = new String[directionResponseModels.size()];
                        for (int i = 0; i < directionResponseModels.size(); i++) {
                            imputation[i] = directionResponseModels.get(i).getImputation();
                        }
                        setUpActvForDirection(imputation);
                        showLayout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                        Toast.makeText(getContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void updateChantier() {
        String nom = mTilNom.getEditText().getText().toString().trim();
        String adresse = mTilAdresse.getEditText().getText().toString().trim();
        String directionImputation = mTilDirection.getEditText().getText().toString().trim();

        if (!checkFieldNom(nom) | !checkFieldAdresse(adresse) | !checkDirectionField(directionImputation)) {
            return;
        } else {
            showLoading();
            ChantierRequestModel chantierRequestModel = new ChantierRequestModel(
                    nom,
                    adresse,
                    directionImputation
            );

            ChantierService.newInstance()
                    .updateChantier(getContext(), chantierResponseModel.getChantierId(), chantierRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ChantierResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(ChantierResponseModel chantierResponseModel) {
                            showSucces("Modification du chantier réussie");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError();
                            Toast.makeText(getContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    private void saveChantier() {


        String nom = mTilNom.getEditText().getText().toString().trim();
        String adresse = mTilAdresse.getEditText().getText().toString().trim();
        String directionImputation = mTilDirection.getEditText().getText().toString().trim();

        if (!checkFieldNom(nom) | !checkFieldAdresse(adresse) | !checkDirectionField(directionImputation)) {
            return;
        } else {
            showLoading();

            ChantierRequestModel chantierRequestModel = new ChantierRequestModel(nom, adresse, directionImputation);

            ChantierService.newInstance()
                    .saveChantier(getContext(), chantierRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ChantierResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(ChantierResponseModel chantierResponseModel) {
                            showSucces("chantier ajouté avec succès ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }


    private Boolean checkFieldNom(String nom) {
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

    private Boolean checkFieldAdresse(String adresse) {
        if (adresse.isEmpty()) {
            mTilAdresse.setErrorEnabled(true);
            mTilAdresse.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilNom.setErrorEnabled(false);
            mTilNom.setError("");
            return true;
        }
    }

    private Boolean checkDirectionField(String direction) {
        if (direction.isEmpty()) {
            mTilDirection.setErrorEnabled(true);
            mTilDirection.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilDirection.setErrorEnabled(false);
            mTilDirection.setError("");
            return true;
        }
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }
}