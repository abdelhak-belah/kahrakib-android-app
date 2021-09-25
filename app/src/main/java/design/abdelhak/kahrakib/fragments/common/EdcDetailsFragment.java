package design.abdelhak.kahrakib.fragments.common;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.DpsDetailsRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.ConfermeEnAttenteIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeEnvoiIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeSupprissionIntermediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.requests.EtatEdsRequestModel;
import design.abdelhak.kahrakib.networks.responses.AchatResponseModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import design.abdelhak.kahrakib.networks.service.EdcService;
import design.abdelhak.kahrakib.utils.DialogUtil;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class EdcDetailsFragment extends Fragment implements View.OnClickListener, ConfermeEnvoiIntermediate,ConfermeSupprissionIntermediate, ConfermeEnAttenteIntermediate {


    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbEdcDetails;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private LinearLayout mLlEditeurContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError,mComponentSucces;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;
    private ImageView mIvValide,mIvSupprimer,mIvEnAttente;
    private RecyclerView mRvEdcDetails;
    private ConstraintLayout mClSupprimer, mClValide,mClEnAttente;
    private TextView mTvEtat,mTvNumeroEtat,mTvImputation,mTvMontentGlobal;

    private int launcher;
    private EdsResponseModel edsResponseModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public EdcDetailsFragment() {
        // Required empty public constructor
    }


    public static EdcDetailsFragment newInstance(Bundle data) {
        EdcDetailsFragment fragment = new EdcDetailsFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            launcher = getArguments().getInt(BundleKeys.LAUNCHER_KEY);
            edsResponseModel = (EdsResponseModel) getArguments().getSerializable(BundleKeys.EDC_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edc_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbEdcDetails = view.findViewById(R.id.mtb_fragment_edc_details);
        mComponentLoading = view.findViewById(R.id.pb_fragment_edc_details_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_edc_details_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_edc_details_component_succes);

        mClContainer = view.findViewById(R.id.cl_fragment_edc_details_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_edc_details_sub_container);
        mLlEditeurContainer = view.findViewById(R.id.ll_fragment_edc_details_editeur_container);
        mIvValide = view.findViewById(R.id.iv_fragment_edc_details_valide);
        mIvSupprimer = view.findViewById(R.id.iv_fragment_edc_details_supprimer);
        mIvEnAttente = view.findViewById(R.id.iv_fragment_edc_details_en_attente);
        mRvEdcDetails = view.findViewById(R.id.rv_fragment_edc_details);
        mClValide = view.findViewById(R.id.cl_fragment_edc_details_valide);
        mClSupprimer = view.findViewById(R.id.cl_fragment_edc_details_supprimer);
        mClEnAttente = view.findViewById(R.id.cl_fragment_edc_details_en_attente);
        mTvEtat = view.findViewById(R.id.tv_fragment_edc_details_etat);
        mTvNumeroEtat = view.findViewById(R.id.tv_fragment_edc_details_numero_etat);
        mTvImputation = view.findViewById(R.id.tv_fragment_edc_details_imputation);
        mTvMontentGlobal = view.findViewById(R.id.tv_fragment_edc_details_montent_global);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        switcher();
        setUpActionBar();
        initLayout();
        mMBtnComponentErrorRessayer.setVisibility(View.INVISIBLE);

        /*call listener*/
        mIvValide.setOnClickListener(this);
        mIvSupprimer.setOnClickListener(this);
        mIvEnAttente.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    private void switcher() {
        switch (launcher) {
            case R.id.menu_bnv_cassier_edc:
                mClEnAttente.setVisibility(View.GONE);
                mClSupprimer.setVisibility(View.VISIBLE);
                mClValide.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_bnv_cassier_respo_edc:
                mClEnAttente.setVisibility(View.VISIBLE);
                mClSupprimer.setVisibility(View.GONE);
                mClValide.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_bnv_cassier_respo_valide:
                mClEnAttente.setVisibility(View.GONE);
                mClSupprimer.setVisibility(View.GONE);
                mClValide.setVisibility(View.GONE);
                break;
            case R.id.menu_bnv_cassier_respo_en_attente:
                mClEnAttente.setVisibility(View.GONE);
                mClSupprimer.setVisibility(View.GONE);
                mClValide.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_bnv_comptable_edc:
                mClEnAttente.setVisibility(View.VISIBLE);
                mClSupprimer.setVisibility(View.GONE);
                mClValide.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_bnv_comptable_valide:
                mClEnAttente.setVisibility(View.GONE);
                mClSupprimer.setVisibility(View.GONE);
                mClValide.setVisibility(View.GONE);
                break;
            case R.id.menu_bnv_comptable_en_attente:
                mClEnAttente.setVisibility(View.GONE);
                mClSupprimer.setVisibility(View.GONE);
                mClValide.setVisibility(View.VISIBLE);
                break;
        }
    }






    @Override
    public void onClick(View v) {
        if (v == mIvValide){
            List<Long> ids = new LinkedList<>();
            ids.add(edsResponseModel.getEdsId());
            DialogUtil.showConfermeEnvoiDialog(getContext(),this,ids,"êtes-vous sûr de vouloir valider cet EDC");
        }

        if (v == mIvEnAttente){
            List<Long> ids = new LinkedList<>();
            ids.add(edsResponseModel.getEdsId());
            DialogUtil.showConfermeEnAttenteDialog(getContext(),this,ids,"êtes-vous sûr de vouloir mettre cet EDC en attente");
        }

        if (v == mIvSupprimer){
            DialogUtil.showConfermeSupprissionDialog(getContext(),this,edsResponseModel.getEdsId(),"êtes-vous sûr de vouloir supprimer cet EDC (les DPS seront récupérées)");
        }

        if (v == mMBtnComponentSuccesContinue) {
            NavigationUtil.back(getContext());
        }

        if (v == mMBtnComponentErrorRessayer) {

        }

        if (v == mMBtnComponentErrorAcceuill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }






    @Override
    public void confermeEnvoyer(Boolean conferme, List<Long> ids) {

        if (conferme && launcher == R.id.menu_bnv_cassier_edc){
            showLoading();
            EtatEdsRequestModel etatEdsRequestModel = new EtatEdsRequestModel(NetworkKeys.ETAT_VALID_CAISSIER_KEY);

            EdcService.newInstance()
                    .updateEdcEtat(getContext(),ids.get(0),etatEdsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EdsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(EdsResponseModel edsResponseModel) {
                            showSucces("EDC validé avec succès, il sera envoyer au responsable caissier pour le vérifier");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError("Montant de cet EDC dépasse votre budget");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        if (conferme && (launcher == R.id.menu_bnv_cassier_respo_edc || launcher == R.id.menu_bnv_cassier_respo_en_attente) ){
            showLoading();

            EtatEdsRequestModel etatEdsRequestModel = new EtatEdsRequestModel(NetworkKeys.ETAT_VALID_CAISSIER_RESPO_KEY);
            EdcService.newInstance()
                    .updateEdcEtat(getContext(),edsResponseModel.getEdsId(),etatEdsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EdsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(EdsResponseModel edsResponseModel) {
                            showSucces("EDC valider avec succès, il sera envoyée au comptable pour le vérifier");
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

        if (conferme && (launcher == R.id.menu_bnv_comptable_edc || launcher == R.id.menu_bnv_comptable_en_attente)){
            showLoading();
            EtatEdsRequestModel etatEdsRequestModel = new EtatEdsRequestModel(NetworkKeys.ETAT_VALID_COMPTABLE_KEY);
            EdcService.newInstance()
                    .updateEdcEtat(getContext(),edsResponseModel.getEdsId(),etatEdsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EdsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(EdsResponseModel edsResponseModel) {
                            showSucces("EDC validé,le budget du caissier a été crédité avec succès");
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
        return;
    }

    @Override
    public void confermeSupprission(Boolean conferme, Long id) {
        if (conferme && launcher == R.id.menu_bnv_cassier_edc){
            showLoading();
            EdcService.newInstance()
                    .deleteEdc(getContext(),id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DeleteResponseModel>() {
                        @Override
                        public void onSubscribe( Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(DeleteResponseModel deleteResponseModel) {
                            showSucces("EDC supprimer avec succès et les DPS sont récupérées");
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

        return;
    }


    @Override
    public void confermeEnAttente(Boolean conferme, List<Long> ids) {
        if (conferme && launcher == R.id.menu_bnv_cassier_respo_edc){
            showLoading();
            EtatEdsRequestModel etatEdsRequestModel = new EtatEdsRequestModel(NetworkKeys.ETAT_EN_ATTENTE_CAISSIER_RESPO_KEY);
            EdcService.newInstance()
                    .updateEdcEtat(getContext(),edsResponseModel.getEdsId(),etatEdsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EdsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(EdsResponseModel edsResponseModel) {
                            showSucces("EDC a été mis en attente");
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

        if (conferme && launcher == R.id.menu_bnv_comptable_edc){
            showLoading();
            EtatEdsRequestModel etatEdsRequestModel = new EtatEdsRequestModel(NetworkKeys.ETAT_EN_ATTENTE_COMPTABLE_KEY);
            EdcService.newInstance()
                    .updateEdcEtat(getContext(),edsResponseModel.getEdsId(),etatEdsRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EdsResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(EdsResponseModel edsResponseModel) {
                            showSucces("EDC est en état d'attente");
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

        return;
    }



    private void initLayout(){
        mTvEtat.setText(edsResponseModel.getEtat().getEtat());
        mTvNumeroEtat.setText("#"+edsResponseModel.getEdsId());
        mTvImputation.setText(edsResponseModel.getImputation());
        mTvMontentGlobal.setText(edsResponseModel.getMontantGlobal()+" da");

        List<AchatResponseModel> achats = new LinkedList<>();
        for (DpsResponseModel dpsResponseModel:edsResponseModel.getDpss()){
            for (AchatResponseModel achatResponseModel:dpsResponseModel.getAchats()){
                achats.add(achatResponseModel);
            }
        }
        initEdcDetailsRecycler(achats);
    }



    private void initEdcDetailsRecycler(List<AchatResponseModel> achatResponseModels) {
        DpsDetailsRecyclerAdapter dpsDetailsRecyclerAdapter = new DpsDetailsRecyclerAdapter(getContext(),achatResponseModels);
        mRvEdcDetails.setAdapter(dpsDetailsRecyclerAdapter);
        mRvEdcDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvEdcDetails.setHasFixedSize(true);
    }



    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbEdcDetails);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        /*call back nav listener*/
        mMtbEdcDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                NavigationUtil.back(getContext());
            }
        });
    }


    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mLlEditeurContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
    }

    private void showError(String message) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);

        if (!message.isEmpty()){
            mTvComponentErrorMessage.setText(message);
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
        mNsvSubContainer.setVisibility(View.GONE);
        mLlEditeurContainer.setVisibility(View.GONE);
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