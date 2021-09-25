package design.abdelhak.kahrakib.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.AchatRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.AchatInterrmediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.models.AchatModel;
import design.abdelhak.kahrakib.networks.requests.AchatWithDpsRequestModel;
import design.abdelhak.kahrakib.networks.requests.DpsRequestModel;
import design.abdelhak.kahrakib.networks.responses.AchatResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.networks.responses.ElementResponseModel;
import design.abdelhak.kahrakib.networks.service.DpsService;
import design.abdelhak.kahrakib.networks.service.ElementService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AjouterAchatsFragment extends Fragment implements View.OnClickListener, AchatInterrmediate {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbAjouterAchats;
    private MaterialButton mMBtnAjouterAchat;
    private TextInputLayout mTilDesignation, mTilPrixUnitaire;
    private EditText mEtQty;
    private ImageView mIvPlus, mIvMinus;
    private TextView mTvPrixTotal,mTvErreur;
    private RecyclerView mRvAchats;
    private FloatingActionButton mFabEnvoyerAchats;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError,mComponentSucces,mComponentQty,mClSomme;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private RelativeLayout mRlVide;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;

    private int qtyCounter = 1;
    private List<AchatWithDpsRequestModel> mAchats;
    private AchatRecyclerAdapter achatRecyclerAdapter;
    private BigDecimal mPrixTotal = new BigDecimal(0);

    private final CompositeDisposable disposable = new CompositeDisposable();
    private String nomPrestataire,adressePrestataire;
    /*-------------------------------------------------------------------*/

    public AjouterAchatsFragment() {
        // Required empty public constructor
    }

    public static AjouterAchatsFragment newInstance(Bundle data) {
        AjouterAchatsFragment fragment = new AjouterAchatsFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            nomPrestataire = getArguments().getString(BundleKeys.DPS_NOM_PRESTATAIRE_KEY);
            adressePrestataire = getArguments().getString(BundleKeys.DPS_ADRESSE_PRESTATAIRE_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_achats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbAjouterAchats = view.findViewById(R.id.mtb_fragment_ajouter_achats);
        mComponentLoading = view.findViewById(R.id.pb_fragment_ajouter_achats_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_ajouter_achats_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_ajouter_achats_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_ajouter_achats_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_ajouter_achats_sub_container);
        mTilDesignation = view.findViewById(R.id.til_fragment_ajouter_achats_designation);
        mTilPrixUnitaire = view.findViewById(R.id.til_fragment_ajouter_achats_prix_unitaire);
        mMBtnAjouterAchat = view.findViewById(R.id.mbtn_fragment_ajouter_achats_ajouter);
        mRvAchats = view.findViewById(R.id.rv_fragment_ajouter_achats);
        mTvPrixTotal = view.findViewById(R.id.tv_fragment_ajouter_achats_prix_totale);
        mFabEnvoyerAchats = view.findViewById(R.id.fab_fragment_ajouter_achats_envoyer);
        mRlVide = view.findViewById(R.id.rl_fragment_ajouter_achats_vide);
        mClSomme = view.findViewById(R.id.cl_fragment_ajouter_achats_somme);

        mComponentQty = view.findViewById(R.id.cl_fragment_ajouter_achats_component_qty);
        mEtQty = mComponentQty.findViewById(R.id.et_component_qty_number);
        mIvPlus = mComponentQty.findViewById(R.id.iv_component_qty_plus);
        mIvMinus = mComponentQty.findViewById(R.id.iv_component_qty_minus);
        mTvErreur = mComponentQty.findViewById(R.id.tv_component_qty_erreur);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/


        /*fetch functions*/
        getElements();

        /*Preload function*/
        setUpActionBar();
        setUpRvAchats();


        /*call listener*/
        mMBtnAjouterAchat.setOnClickListener(this);
        mFabEnvoyerAchats.setOnClickListener(this);
        mIvMinus.setOnClickListener(this);
        mIvPlus.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mMBtnAjouterAchat) {
            ajouterAchat();
        }
        if (v == mFabEnvoyerAchats) {
            saveDps();
        }

        if (v == mMBtnComponentSuccesContinue) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }

        if (v == mMBtnComponentErrorRessayer) {
            saveDps();
        }

        if (v == mMBtnComponentErrorAcceuill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }

        if (v == mIvMinus) {
            if (qtyCounter > 1) {
                qtyCounter--;
                mEtQty.setText("" + qtyCounter);
            }
        }
        if (v == mIvPlus) {
            qtyCounter++;
            mEtQty.setText("" + qtyCounter);
        }
    }

    private void saveDps() {
        BigDecimal montantGlobal = new BigDecimal(0);
        for (AchatWithDpsRequestModel achatWithDpsRequestModel:mAchats){
            montantGlobal = montantGlobal.add(achatWithDpsRequestModel.getPrixUnitaire().multiply(new BigDecimal(achatWithDpsRequestModel.getQuantite())));
        }

        if (mAchats.size() == 0){
            Toast.makeText(getContext(), "s'il vous plaît ajouter un achat", Toast.LENGTH_SHORT).show();
            return;
        }else if (montantGlobal.compareTo(new BigDecimal(10000)) > 0){
            Toast.makeText(getContext(), "le montant des achats doit être inférieur à 10000 da", Toast.LENGTH_SHORT).show();
            return;
        }else {
            showLoading();
            DpsRequestModel dpsRequestModel = new DpsRequestModel(
                    nomPrestataire,
                    adressePrestataire,
                    (long) SharedPreferencesUtil.getUserId(getContext()),
                    mAchats
            );

            DpsService.newInstance()
                    .saveDps(getContext(),dpsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DpsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(DpsResponseModel dpsResponseModel) {
                            showSucces("DPS ajoutée avec succès");
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


    private void ajouterAchat() {
        String desingation = mTilDesignation.getEditText().getText().toString().trim();
        String quantite = mEtQty.getText().toString().trim();
        String prixUnitaire = mTilPrixUnitaire.getEditText().getText().toString().trim();

        if (verifieDesignation(desingation) & verifieQuantite(quantite) & verifiePrixUnitaire(prixUnitaire)) {
            mAchats.add(new AchatWithDpsRequestModel(desingation, Integer.parseInt(quantite), new BigDecimal(prixUnitaire)));
            achatRecyclerAdapter.notifyDataSetChanged();
            creditePrixTotal(new AchatWithDpsRequestModel(desingation, Integer.parseInt(quantite), new BigDecimal(prixUnitaire)));
            reinitialiserLesChamps();
        }
        affichageFenetereVide();
    }




    @Override
    public void debitePrixTotal(AchatWithDpsRequestModel achatWithDpsRequestModel) {
        mPrixTotal = mPrixTotal.subtract(
                achatWithDpsRequestModel.getPrixUnitaire().multiply(
                        new BigDecimal( achatWithDpsRequestModel.getQuantite())
                )
        );
        mTvPrixTotal.setText(mPrixTotal + " da");

    }

    @Override
    public void creditePrixTotal(AchatWithDpsRequestModel achatWithDpsRequestModel) {
        mPrixTotal = mPrixTotal.add(
                achatWithDpsRequestModel.getPrixUnitaire().multiply(
                        new BigDecimal(achatWithDpsRequestModel.getQuantite())
                )
        );
        mTvPrixTotal.setText(mPrixTotal + " da");
    }

    @Override
    public void affichageFenetereVide() {
        if (mAchats.size() > 0) {
            mRvAchats.setVisibility(View.VISIBLE);
            mRlVide.setVisibility(View.GONE);
        } else {
            mRlVide.setVisibility(View.VISIBLE);
            mRvAchats.setVisibility(View.GONE);
        }
    }


    private void setUpRvAchats() {
        mAchats = new LinkedList<>();
        achatRecyclerAdapter = new AchatRecyclerAdapter(getContext(), mAchats, this);
        mRvAchats.setAdapter(achatRecyclerAdapter);
        mRvAchats.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        mRvAchats.setHasFixedSize(true);
    }


    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbAjouterAchats);
        //call listener
        mMtbAjouterAchats.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }






    private void getElements(){
        showLoading();
        ElementService.newInstance()
                .getElements(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ElementResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<ElementResponseModel> elementResponseModels) {
                        setUpActvForDesignation(elementResponseModels);
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


    private void setUpActvForDesignation(List<ElementResponseModel> elementResponseModels) {
        String[] elements = new String[elementResponseModels.size()];
        for (int i = 0; i < elementResponseModels.size(); i++) {
            elements[i] = elementResponseModels.get(i).getNom();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, elements);
        MaterialAutoCompleteTextView mMACTV = (MaterialAutoCompleteTextView) mTilDesignation.getEditText();
        mMACTV.setAdapter(adapter);
        showLayout();
    }

    private void reinitialiserLesChamps() {
        mTilDesignation.getEditText().setText("");
        mTilPrixUnitaire.getEditText().setText("");
        mEtQty.setText("1");
        qtyCounter = 1;
    }

    private boolean verifiePrixUnitaire(String prixUnitaire) {
        if (prixUnitaire.isEmpty()) {
            mTilPrixUnitaire.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilPrixUnitaire.setError("");
            mTilPrixUnitaire.setErrorEnabled(false);
            return true;
        }
    }

    private boolean verifieQuantite(String quantite) {
        if (Integer.parseInt(quantite) == 0) {
            mTvErreur.setVisibility(View.VISIBLE);
            return false;
        } else {
            mTvErreur.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean verifieDesignation(String designtion) {
        if (designtion.isEmpty()) {
            mTilDesignation.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilDesignation.setError("");
            mTilDesignation.setErrorEnabled(false);
            return true;
        }
    }

    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mClSomme.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mClSomme.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showSucces(String message) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mClSomme.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);

        if (!message.isEmpty()){
            mTvComponentSuccesMessage.setText(message);
        }
    }

    private void showLoading() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
        mClSomme.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        if (disposable != null){
            disposable.clear();
        }
        super.onDestroy();
    }
}