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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.DpsRecyclerAdapter;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.networks.service.ClientService;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DpsFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private RecyclerView mRvDps;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mClContainer;
    private TextView mTvTitre;
    private RelativeLayout mRlDpsVide;
    private MaterialButton  mMBtnComponentErrorRessayer, mMBtnComponentErrorAcceuill;

    private int launcher;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public DpsFragment() {
        // Required empty public constructor
    }

    public static DpsFragment newInstance(Bundle data) {
        DpsFragment fragment = new DpsFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            launcher = getArguments().getInt(BundleKeys.LAUNCHER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_dps_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_dps_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_dps_container);
        mRvDps = view.findViewById(R.id.rv_fragment_dps);
        mTvTitre = view.findViewById(R.id.tv_fragment_dps_titre);
        mRlDpsVide = view.findViewById(R.id.rl_fragment_dps_vide);

        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        /*-------------------------------------------------------------------*/

        /*preload*/
        mRlDpsVide.setVisibility(View.GONE);
        mMBtnComponentErrorAcceuill.setVisibility(View.INVISIBLE);
        switcher();

        mMBtnComponentErrorRessayer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mMBtnComponentErrorRessayer){
            switcher();
        }
    }

    private void switcher() {
        switch (launcher){
            case R.id.menu_bnv_client_dps:
                mTvTitre.setText(getString(R.string.fragment_dps_titre));
                getDpsNonValide();
                break;
            case R.id.menu_bnv_client_dps_valide:
                mTvTitre.setText(getString(R.string.fragment_dps_titre_valide));
                getDpsValide();
                break;
        }
    }

    private void getDpsNonValide() {
        showLoading();
        ClientService.newInstance()
                .getClient(getContext(), (long) SharedPreferencesUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClientResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ClientResponseModel clientResponseModel) {
                        showLayout();
                        List<DpsResponseModel> filtredDps = new LinkedList<>();
                        for (DpsResponseModel dpsResponseModel:clientResponseModel.getDpss()){
                            if (!dpsResponseModel.getEtatClient()){
                                dpsResponseModel.setClient(clientResponseModel);
                                dpsResponseModel.setCassier(clientResponseModel.getChantier().getCassier());
                                filtredDps.add(dpsResponseModel);
                            }
                        }
                        initDpsRecycler(filtredDps);
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

    private void getDpsValide(){
        showLoading();
        ClientService.newInstance()
                .getClient(getContext(), (long) SharedPreferencesUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClientResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ClientResponseModel clientResponseModel) {
                        showLayout();
                        List<DpsResponseModel> filtredDps = new LinkedList<>();
                        for (DpsResponseModel dpsResponseModel:clientResponseModel.getDpss()){
                            if (dpsResponseModel.getEtatClient()){
                                dpsResponseModel.setClient(clientResponseModel);
                                dpsResponseModel.setCassier(clientResponseModel.getChantier().getCassier());
                                filtredDps.add(dpsResponseModel);
                            }
                        }
                        initDpsRecycler(filtredDps);
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

    private void initDpsRecycler(List<DpsResponseModel> dpsResponseModels) {
        if (dpsResponseModels.size() == 0){
            mRlDpsVide.setVisibility(View.VISIBLE);
            mRvDps.setVisibility(View.GONE);
        }else {
            mRlDpsVide.setVisibility(View.GONE);
            mRvDps.setVisibility(View.VISIBLE);
        }

        DpsRecyclerAdapter dpsRecyclerAdapter = new DpsRecyclerAdapter(getContext(),dpsResponseModels, launcher);
        mRvDps.setAdapter(dpsRecyclerAdapter);
        mRvDps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDps.setHasFixedSize(true);
    }


    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
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