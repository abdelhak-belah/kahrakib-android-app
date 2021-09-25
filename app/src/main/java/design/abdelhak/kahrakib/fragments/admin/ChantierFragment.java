package design.abdelhak.kahrakib.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.ChantierRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.ChantierIntermediate;
import design.abdelhak.kahrakib.intermediate.ConfermeSupprissionIntermediate;
import design.abdelhak.kahrakib.models.ChantierModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.service.ChantierService;
import design.abdelhak.kahrakib.utils.DialogUtil;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChantierFragment extends Fragment implements View.OnClickListener, ChantierIntermediate, ConfermeSupprissionIntermediate {

    /*-------------------------------------------------------------------*/
    private RecyclerView mRvChantier;
    private TextView mTvFilter;
    private ImageView mIvFilter;
    private LinearLayout mLlFilter;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mClContainer;
    private FloatingActionButton mFabAjouterChantier;
    private MaterialButton mBtnComponentErrorRessayer;
    private MaterialButton mBtnComponentErrorAccueill;

    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public ChantierFragment() {
        // Required empty public constructor
    }

    public static ChantierFragment newInstance(Bundle data) {
        ChantierFragment fragment = new ChantierFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chantier, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_chantier_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_chantier_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_chantier_container);
        mRvChantier = view.findViewById(R.id.rv_fragment_chantier);
        mTvFilter = view.findViewById(R.id.tv_fragment_chantier_filter);
        mIvFilter = view.findViewById(R.id.iv_fragment_chantier_filter);
        mLlFilter = view.findViewById(R.id.ll_fragment_chantier_filter);
        mRvChantier = view.findViewById(R.id.rv_fragment_chantier);
        mFabAjouterChantier = view.findViewById(R.id.fab_fragment_chantier_ajouter_chantier);

        mBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mBtnComponentErrorAccueill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        /*-------------------------------------------------------------------*/

        /*preload*/
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mBtnComponentErrorAccueill.setVisibility(View.INVISIBLE);

        /*fetch*/
        getChantiers();

        /*call listener*/
        mLlFilter.setOnClickListener(this);
        mFabAjouterChantier.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == mLlFilter) {
            showFilterPopUpMenu();
        }
        if (v == mFabAjouterChantier) {
            NavigationUtil.navigateToAjouterChantierFragment(getContext(), null);
        }
        if (v == mBtnComponentErrorRessayer) {
            getChantiers();
        }
    }


    private void getChantiers() {
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
                        initChantiersRecycler(chantierResponseModels);
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

    private void getChantiersDtpi() {
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
                        List<ChantierResponseModel> filteredChantiers = new LinkedList<>();
                        for (ChantierResponseModel chantierResponseModel : chantierResponseModels) {
                            if (!chantierResponseModel.getDirection().getImputation().equals("Dtpi")) {
                                filteredChantiers.add(chantierResponseModel);
                            }
                        }
                        initChantiersRecycler(filteredChantiers);
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

    private void getChantiersDtlc() {
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
                        List<ChantierResponseModel> filteredChantiers = new LinkedList<>();
                        for (ChantierResponseModel chantierResponseModel : chantierResponseModels) {
                            if (!chantierResponseModel.getDirection().getImputation().equals("Dtlc")) {
                                filteredChantiers.add(chantierResponseModel);
                            }
                        }
                        initChantiersRecycler(filteredChantiers);
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

    private void initChantiersRecycler(List<ChantierResponseModel> chantiers) {
        ChantierRecyclerAdapter adapter = new ChantierRecyclerAdapter(getContext(), chantiers, this);
        mRvChantier.setAdapter(adapter);
        mRvChantier.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvChantier.setHasFixedSize(true);
        showLayout();
    }


    private void showFilterPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), mIvFilter);
        popupMenu.inflate(R.menu.menu_chantier_filter);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_chantier_filter_dtlc:
                        mTvFilter.setText(R.string.menu_chantier_filter_dtlc);
                        getChantiersDtlc();
                        return true;
                    case R.id.menu_chantier_filter_dtpi:
                        mTvFilter.setText(R.string.menu_chantier_filter_dtpi);
                        getChantiersDtpi();
                        return true;
                    case R.id.menu_chantier_filter_tout:
                        mTvFilter.setText(R.string.menu_chantier_filter_tout);
                        getChantiers();
                        return true;
                    default:
                        mTvFilter.setText(R.string.menu_user_filter_tout);
                        getChantiers();
                        return true;
                }
            }
        });
        popupMenu.show();
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
    public void deleteChantier(Long chantierId) {
        DialogUtil.showConfermeSupprissionDialog(getContext(), this, chantierId, "êtes-vous sûr de vouloir supprimer définitivement ce chantier");
    }


    @Override
    public void confermeSupprission(Boolean conferme, Long id) {
        if (conferme) {
            showLoading();
            ChantierService.newInstance()
                    .deleteChantier(getContext(), id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DeleteResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(DeleteResponseModel deleteResponseModel) {
                            showLayout();
                            getChantiers();
                            Toast.makeText(getContext(), deleteResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }

}