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

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.DpsDetailsRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.ConfermeEnvoiIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeRejeterIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeSupprissionIntermediate;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.requests.EdsRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatClientRequestModel;
import design.abdelhak.kahrakib.networks.responses.AchatResponseModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import design.abdelhak.kahrakib.networks.service.DpsService;
import design.abdelhak.kahrakib.networks.service.EdcService;
import design.abdelhak.kahrakib.utils.DialogUtil;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DpsDetailsFragment extends Fragment implements View.OnClickListener, ConfermeSupprissionIntermediate, ConfermeEnvoiIntermediate, ConfermeRejeterIntermediate {


    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbDpsDetails;
    private LinearLayout mLlEditeurContainer;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError, mComponentSucces;
    private MaterialButton mMBtnComponentSuccesContinue, mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage, mTvComponentErrorMessage;
    private ImageView mIvValide, mIvRejete, mIvSupprimer;
    private RecyclerView mRvDpsDetails;
    private ConstraintLayout mClRejete, mClValide, mClSupprimer;
    private TextView mTvEtatClient,mTvEtatCassier, mTvClient, mTvPrestataire, mTvAdressePrestataire, mTvDateAchat, mTvTotalAchat;

    private int mLauncher;
    private DpsResponseModel dpsResponseModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public DpsDetailsFragment() {
        // Required empty public constructor
    }


    public static DpsDetailsFragment newInstance(Bundle data) {
        DpsDetailsFragment fragment = new DpsDetailsFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLauncher = getArguments().getInt(BundleKeys.LAUNCHER_KEY);
            dpsResponseModel = (DpsResponseModel) getArguments().getSerializable(BundleKeys.DPS_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dps_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbDpsDetails = view.findViewById(R.id.mtb_fragment_dps_details);
        mComponentLoading = view.findViewById(R.id.pb_fragment_dps_details_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_dps_details_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_dps_details_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_dps_details_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_dps_details_sub_container);
        mLlEditeurContainer = view.findViewById(R.id.ll_fragment_dps_details_editeur_container);
        mIvValide = view.findViewById(R.id.iv_fragment_dps_details_valide);
        mIvRejete = view.findViewById(R.id.iv_fragment_dps_details_rejete);
        mIvSupprimer = view.findViewById(R.id.iv_fragment_dps_details_supprimer);
        mRvDpsDetails = view.findViewById(R.id.rv_fragment_dps_details);
        mClValide = view.findViewById(R.id.cl_fragment_dps_details_valide);
        mClRejete = view.findViewById(R.id.cl_fragment_dps_details_rejete);
        mClSupprimer = view.findViewById(R.id.cl_fragment_dps_details_supprimer);
        mTvEtatClient = view.findViewById(R.id.tv_fragment_dps_details_etat_client);
        mTvEtatCassier = view.findViewById(R.id.tv_fragment_dps_details_etat_cassier);
        mTvClient = view.findViewById(R.id.tv_fragment_dps_details_client);
        mTvPrestataire = view.findViewById(R.id.tv_fragment_dps_details_prestataire);
        mTvAdressePrestataire = view.findViewById(R.id.tv_fragment_dps_details_adresse_prestataire);
        mTvDateAchat = view.findViewById(R.id.tv_fragment_dps_details_date_achat);
        mTvTotalAchat = view.findViewById(R.id.tv_fragment_dps_details_total_achat);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        mTvComponentErrorMessage = mComponentError.findViewById(R.id.tv_component_error_message);
        /*-------------------------------------------------------------------*/


        /*Preload function*/
        setUpActionBar();
        mMBtnComponentErrorRessayer.setVisibility(View.INVISIBLE);
        switcher();
        initLayout();

        /*call listener*/
        mIvValide.setOnClickListener(this);
        mIvRejete.setOnClickListener(this);
        mIvSupprimer.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);
    }


    private void switcher() {
        switch (mLauncher) {
            case R.id.menu_bnv_client_dps:
                mClRejete.setVisibility(View.GONE);
                break;
            case R.id.menu_bnv_client_dps_valide:
                mClValide.setVisibility(View.GONE);
                mClRejete.setVisibility(View.GONE);
                mClSupprimer.setVisibility(View.GONE);
                break;
            case R.id.menu_bnv_cassier_dps:
                mClSupprimer.setVisibility(View.GONE);
                break;
        }
    }


    private void initLayout(){
        if (dpsResponseModel.getEtatClient()){
            mTvEtatClient.setText(getString(R.string.message_dps_etat_client_valide));
            mTvEtatClient.setTextColor(getResources().getColor(R.color.kahrakib_green));
        }else {
            mTvEtatClient.setText(getString(R.string.message_dps_etat_client_non_valide));
            mTvEtatClient.setTextColor(getResources().getColor(R.color.kahrakib_red));
        }

        if (dpsResponseModel.getEtatCassier()){
            mTvEtatCassier.setText(getString(R.string.message_dps_etat_cassier_valide));
            mTvEtatCassier.setTextColor(getResources().getColor(R.color.kahrakib_green));
        }else {
            mTvEtatCassier.setText(getString(R.string.message_dps_etat_cassier_non_valide));
            mTvEtatCassier.setTextColor(getResources().getColor(R.color.kahrakib_red));
        }

        mTvClient.setText(dpsResponseModel.getClient().getPrenom()+" "+dpsResponseModel.getClient().getNom());
        mTvPrestataire.setText(dpsResponseModel.getPrestataire());
        mTvAdressePrestataire.setText(dpsResponseModel.getAdressePrestataire());
        mTvDateAchat.setText(dpsResponseModel.getDateDeCreation());
        mTvTotalAchat.setText(dpsResponseModel.getTotalAchatMontant()+" da");
        initDpsDetailsRecycler(dpsResponseModel.getAchats());
    }


    @Override
    public void onClick(View v) {
        List<Long> ids = new LinkedList<>();
        ids.add(dpsResponseModel.getDpsId());
        if (v == mIvValide) {
            DialogUtil.showConfermeEnvoiDialog(getContext(),this,ids,"êtes-vous sûr de vouloir valider et envoyer cette DPS");
        }
        if (v == mIvRejete) {
            DialogUtil.showConfermeRejeterDialog(getContext(),this,ids,"Si vous rejeter cette DPS elle sera définitivement supprimée");
        }
        if (v == mIvSupprimer) {
            DialogUtil.showConfermeSupprissionDialog(getContext(),this,dpsResponseModel.getDpsId(),"êtes-vous sûr de vouloir supprimée définitivement cette DPS");
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
    public void confermeSupprission(Boolean conferme, Long id) {
        if (conferme){
            DpsService.newInstance()
                    .deleteDps(getContext(),id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DeleteResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(DeleteResponseModel deleteResponseModel) {
                            showSucces("DPS supprimer avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError("");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            return;
        }
    }


    @Override
    public void confermeRejeter(Boolean conferme, List<Long> ids) {
        if (conferme){
            DpsService.newInstance()
                    .deleteDps(getContext(),ids.get(0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DeleteResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(DeleteResponseModel deleteResponseModel) {
                            showSucces("DPS supprimée avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError("");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            return;
        }
    }


    @Override
    public void confermeEnvoyer(Boolean conferme, List<Long> ids) {
        if (conferme && mLauncher == R.id.menu_bnv_client_dps){
            showLoading();
            EtatClientRequestModel etatClientRequestModel = new EtatClientRequestModel(true);

            DpsService.newInstance()
                    .updateDpsEtatClient(getContext(),ids.get(0),etatClientRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DpsResponseModel>() {
                        @Override
                        public void onSubscribe( Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(DpsResponseModel dpsResponseModel) {
                            showSucces("Demande de prestation de service envoyée avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError("");
                            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        if (conferme && mLauncher == R.id.menu_bnv_cassier_dps){
            showLoading();
            Long cassierId = (long) SharedPreferencesUtil.getUserId(getContext());

            EdsRequestModel edsRequestModel= new EdsRequestModel(cassierId, ids);

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
                            showSucces("État de caisse créé avec succès");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError("Montant de DPS dépasse le budget alloué");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        return;
    }


    private void initDpsDetailsRecycler(List<AchatResponseModel> achatResponseModels) {
        DpsDetailsRecyclerAdapter dpsDetailsRecyclerAdapter = new DpsDetailsRecyclerAdapter(getContext(), achatResponseModels);
        mRvDpsDetails.setAdapter(dpsDetailsRecyclerAdapter);
        mRvDpsDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDpsDetails.setHasFixedSize(true);
    }


    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbDpsDetails);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        /*call back nav listener*/
        mMtbDpsDetails.setNavigationOnClickListener(new View.OnClickListener() {
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

    private void showLoading() {
        mNsvSubContainer.setVisibility(View.GONE);
        mLlEditeurContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }

    private void showSucces(String message) {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);
        if (!message.isEmpty()) {
            mTvComponentSuccesMessage.setText(message);
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