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

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.EdcRecyclerAdapter;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.responses.CassierRespoResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ComptableResponseModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import design.abdelhak.kahrakib.networks.service.CassierRespoService;
import design.abdelhak.kahrakib.networks.service.CassierService;
import design.abdelhak.kahrakib.networks.service.ComptableService;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class EdcFragment extends Fragment {

    /*-------------------------------------------------------------------*/
    private RecyclerView mRvEdc;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mClContainer;
    private TextView mTvTitre;
    private RelativeLayout mRlVide;

    private int launcher;
    private boolean mIsSended;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public EdcFragment() {
        // Required empty public constructor
    }


    public static EdcFragment newInstance(Bundle data) {
        EdcFragment fragment = new EdcFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            launcher = getArguments().getInt(BundleKeys.LAUNCHER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_edc_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_edc_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_edc_container);
        mRvEdc = view.findViewById(R.id.rv_fragment_edc);
        mTvTitre = view.findViewById(R.id.tv_fragment_edc_titre);
        mRlVide = view.findViewById(R.id.rl_fragment_edc_vide);
        /*------------------------------------------------------------------*/

        /*preload*/
        switcher();
        mRlVide.setVisibility(View.GONE);

    }

    private void switcher() {
        switch (launcher) {
            case R.id.menu_bnv_cassier_edc:
                getEdcCassier();
                break;
            case R.id.menu_bnv_cassier_respo_edc:
                mTvTitre.setText(getString(R.string.fragment_edc_titre));
                getEdcCassierRespo();
                break;
            case R.id.menu_bnv_cassier_respo_valide:
                mTvTitre.setText(getString(R.string.fragment_edc_titre_valide));
                mIsSended = true;
                getEdcCassierRespo();
                break;
            case R.id.menu_bnv_cassier_respo_en_attente:
                mTvTitre.setText(getString(R.string.fragment_edc_titre_en_attente));
                getEdcCassierRespo();
                break;
            case R.id.menu_bnv_comptable_edc:
                getEdcComptable();
                break;
            case R.id.menu_bnv_comptable_valide:
                mTvTitre.setText(getString(R.string.fragment_edc_titre_valide));
                mIsSended = true;
                getEdcComptable();
                break;
            case R.id.menu_bnv_comptable_en_attente:
                mTvTitre.setText(getString(R.string.fragment_edc_titre_en_attente));
                getEdcComptable();
                break;

        }
    }

    private void getEdcComptable(){
        ComptableService.newInstance()
                .getComptable(getContext(), (long) SharedPreferencesUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ComptableResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ComptableResponseModel comptableResponseModel) {
                        List<EdsResponseModel> filteredEdc = new LinkedList<>();
                        for (EdsResponseModel edsResponseModel:comptableResponseModel.getEdss()){
                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_VALID_CAISSIER_RESPO_KEY) && launcher == R.id.menu_bnv_comptable_edc){
                                filteredEdc.add(edsResponseModel);
                            }

                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_EN_ATTENTE_COMPTABLE_KEY) && launcher == R.id.menu_bnv_comptable_en_attente){
                                filteredEdc.add(edsResponseModel);
                            }

                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_VALID_COMPTABLE_KEY) && launcher == R.id.menu_bnv_comptable_valide){
                                filteredEdc.add(edsResponseModel);
                            }

                        }
                        showLayout();
                        initEdcRecycler(filteredEdc);
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

    private void getEdcCassierRespo() {
        CassierRespoService.newInstance()
                .getCassierRespo(getContext(), (long) SharedPreferencesUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CassierRespoResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext( CassierRespoResponseModel cassierRespoResponseModel) {
                        List<EdsResponseModel> filteredEdc = new LinkedList<>();
                        for (EdsResponseModel edsResponseModel:cassierRespoResponseModel.getEdss()){
                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_VALID_CAISSIER_KEY) && launcher == R.id.menu_bnv_cassier_respo_edc){
                                filteredEdc.add(edsResponseModel);
                            }

                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_EN_ATTENTE_CAISSIER_RESPO_KEY) && launcher == R.id.menu_bnv_cassier_respo_en_attente){
                                filteredEdc.add(edsResponseModel);
                            }

                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_VALID_CAISSIER_RESPO_KEY) && launcher == R.id.menu_bnv_cassier_respo_valide){
                                filteredEdc.add(edsResponseModel);
                            }
                        }
                        showLayout();
                        initEdcRecycler(filteredEdc);
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

    private void getEdcCassier() {
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
                        List<EdsResponseModel> filteredEdc = new LinkedList<>();
                        for (EdsResponseModel edsResponseModel:cassierResponseModel.getEdss()){
                            if (edsResponseModel.getEtat().getEtat().equals(NetworkKeys.ETAT_NON_VALID_CAISSIER_KEY)){
                                filteredEdc.add(edsResponseModel);
                            }
                        }
                        showLayout();
                        initEdcRecycler(filteredEdc);
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


    private void initEdcRecycler(List<EdsResponseModel> edsResponseModels) {
        if (edsResponseModels.size() == 0){
            mRlVide.setVisibility(View.VISIBLE);
        }else {
            mRlVide.setVisibility(View.GONE);
            EdcRecyclerAdapter edcRecyclerAdapter = new EdcRecyclerAdapter(getContext(), edsResponseModels, launcher);
            mRvEdc.setAdapter(edcRecyclerAdapter);
            mRvEdc.setLayoutManager(new LinearLayoutManager(getContext()));
            mRvEdc.setHasFixedSize(true);
        }
    }

    private void showLayout() {
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.VISIBLE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showSucces() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
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