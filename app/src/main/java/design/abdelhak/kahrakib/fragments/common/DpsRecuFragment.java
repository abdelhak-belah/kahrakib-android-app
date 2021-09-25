package design.abdelhak.kahrakib.fragments.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.DpsRecuRecyclerAdapter;
import design.abdelhak.kahrakib.fragments.cassier.CassierFragment;
import design.abdelhak.kahrakib.intermediate.ConfermeEnvoiIntermediate;
import design.abdelhak.kahrakib.intermediate.DpsRecuIntermediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.models.AchatModel;
import design.abdelhak.kahrakib.models.DpsModel;
import design.abdelhak.kahrakib.models.UserModel;
import design.abdelhak.kahrakib.networks.requests.EdsRequestModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import design.abdelhak.kahrakib.networks.service.CassierService;
import design.abdelhak.kahrakib.networks.service.EdcService;
import design.abdelhak.kahrakib.utils.DialogUtil;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DpsRecuFragment extends Fragment implements View.OnClickListener, DpsRecuIntermediate, ConfermeEnvoiIntermediate {

    /*-------------------------------------------------------------------*/
    private RecyclerView mRvDpsRecu;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError,mComponentSucces;
    private ConstraintLayout mClContainer,mClSelectionneContainer;
    private TextView mTvMontentTotal;
    private FloatingActionButton mFabValide;
    private RelativeLayout mRlDpsVide;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;
    private TextView mTvBudget;
    public LinearLayout mLlBudget;

    private int mLauncher;
    private List<DpsResponseModel> mDpsResponseModels;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public DpsRecuFragment() {
        // Required empty public constructor
    }

    public static DpsRecuFragment newInstance(Bundle data) {
        DpsRecuFragment fragment = new DpsRecuFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mLauncher = getArguments().getInt(BundleKeys.LAUNCHER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dps_recu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_dps_recu_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_dps_recu_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_dps_recu_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_dps_recu_container);
        mRvDpsRecu = view.findViewById(R.id.rv_fragment_dps_recu);
        mClSelectionneContainer = view.findViewById(R.id.cl_fragment_dps_recu_selectionne_container);
        mTvMontentTotal = view.findViewById(R.id.tv_fragment_dps_recu_montent_total);
        mFabValide = view.findViewById(R.id.fab_fragment_dps_recu_valide);
        mRlDpsVide = view.findViewById(R.id.rl_fragment_dps_recu_vide);
        mTvBudget = view.findViewById(R.id.tv_fragment_dps_recu_budget);
        mLlBudget = view.findViewById(R.id.ll_fragment_dps_recu_budget);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/

        /*fetch functions*/
        updateBudget();
        getDps();

        /*preload*/
        mMBtnComponentErrorAcceuill.setVisibility(View.INVISIBLE);
        mRlDpsVide.setVisibility(View.GONE);


        /*call listener*/
        mFabValide.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mFabValide){
            saveEds();
        }

        if (v == mMBtnComponentSuccesContinue) {
            showLayout();
            getDps();
        }

        if (v == mMBtnComponentErrorRessayer) {
            getDps();
        }
    }

    private void saveEds() {
        List<Long> dpsIds = new LinkedList<>();
        BigDecimal somme = new BigDecimal(0);
        for (int i = 0; i < mDpsResponseModels.size(); i++) {
            somme = somme.add(mDpsResponseModels.get(i).getTotalAchatMontant());
            dpsIds.add(mDpsResponseModels.get(i).getDpsId());
        }

        if (somme.compareTo(mDpsResponseModels.get(0).getCassier().getBudget()) < 0 ){
            DialogUtil.showConfermeEnvoiDialog(getContext(),this,dpsIds,"\"êtes-vous sûr de vouloir valider et envoyer cette DPS\"");
        }else {
            Toast.makeText(getContext(), "Montant depasse le budget alloué", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPrixTotal(List<DpsResponseModel> dpsResponseModels) {
        mDpsResponseModels = dpsResponseModels;
        mClSelectionneContainer.setVisibility(View.VISIBLE);
        BigDecimal somme = new BigDecimal(0);
        for (int i = 0; i < dpsResponseModels.size(); i++) {
            somme = somme.add(dpsResponseModels.get(i).getTotalAchatMontant());
        }
        mTvMontentTotal.setText(somme+" da");
        if (somme.equals(new BigDecimal(0))){
            mClSelectionneContainer.setVisibility(View.GONE);
        }
    }



    private void getDps(){
        showLoading();
        CassierService.newInstance()
                .getCassier(getContext(), (long) SharedPreferencesUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CassierResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(CassierResponseModel cassierResponseModel) {
                        showLayout();
                        List<DpsResponseModel> filtredDps = new LinkedList<>();

                        for (ClientResponseModel clientResponseModel:cassierResponseModel.getChantier().getClients()){
                            for (DpsResponseModel dpsResponseModel:clientResponseModel.getDpss()){
                                if (!dpsResponseModel.getEtatCassier() && dpsResponseModel.getEtatClient()){
                                    dpsResponseModel.setClient(clientResponseModel);
                                    dpsResponseModel.setCassier(cassierResponseModel);
                                    filtredDps.add(dpsResponseModel);
                                }
                            }
                            initDpsRecycler(filtredDps);
                        }
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





    private void updateBudget(){
        CassierService.newInstance()
                .getCassier(getContext(), (long) SharedPreferencesUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CassierResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(CassierResponseModel cassierResponseModel) {
                        mTvBudget.setText(cassierResponseModel.getBudget()+" da");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    public void confermeEnvoyer(Boolean conferme, List<Long> ids) {
        if (conferme){
            EdsRequestModel edsRequestModel = new EdsRequestModel(mDpsResponseModels.get(0).getCassier().getUtilisateurId(),ids);
            EdcService.newInstance()
                    .saveEdc(getContext(),edsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EdsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(EdsResponseModel edsResponseModel) {
                            mDpsResponseModels.clear();
                            mClSelectionneContainer.setVisibility(View.GONE);
                            updateBudget();
                            showSucces("EDC ajoutée avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            return;
        }
    }


    private void initDpsRecycler(List<DpsResponseModel> dpsResponseModels) {
        if (dpsResponseModels.size() == 0){
            mRlDpsVide.setVisibility(View.VISIBLE);
            mRvDpsRecu.setVisibility(View.GONE);
        }else {
            mRlDpsVide.setVisibility(View.GONE);
            mRvDpsRecu.setVisibility(View.VISIBLE);
        }
        DpsRecuRecyclerAdapter dpsRecuRecyclerAdapter = new DpsRecuRecyclerAdapter(getContext(),dpsResponseModels,this,mLauncher);
        mRvDpsRecu.setAdapter(dpsRecuRecyclerAdapter);
        mRvDpsRecu.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDpsRecu.setHasFixedSize(true);
    }


    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
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

        if (!message.isEmpty()){
            mTvComponentSuccesMessage.setText(message);
        }
    }

    private void showLoading() {
        mComponentError.setVisibility(View.GONE);
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