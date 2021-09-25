package design.abdelhak.kahrakib.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.FilterRecyclerAdapter;
import design.abdelhak.kahrakib.adapters.RechercheResultatRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.RechercheIntermediate;
import design.abdelhak.kahrakib.networks.responses.AdministrateurResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierRespoResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import design.abdelhak.kahrakib.networks.responses.ComptableResponseModel;
import design.abdelhak.kahrakib.networks.service.AdministrateurService;
import design.abdelhak.kahrakib.networks.service.CassierRespoService;
import design.abdelhak.kahrakib.networks.service.CassierService;
import design.abdelhak.kahrakib.networks.service.ChantierService;
import design.abdelhak.kahrakib.networks.service.ClientService;
import design.abdelhak.kahrakib.networks.service.ComptableService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RechercheFragment extends Fragment implements View.OnClickListener, TextWatcher, RechercheIntermediate {

    /*-------------------------------------------------------------------*/
    private RecyclerView mRvFilter, mRvRechercheResultat;
    private LinearLayout mLlRechercheBar;
    private LinearLayout mLlRecherchePasResultat;
    private EditText mEtRecherche;
    private ImageView mIvQuiteRecherche, mIvFiltreRecherche;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mClContainer;

    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/


    public RechercheFragment() {
        // Required empty public constructor
    }


    public static RechercheFragment newInstance(Bundle data) {
        RechercheFragment fragment = new RechercheFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recherche, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_recherche_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_recherche_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_recherche_container);
        mRvFilter = view.findViewById(R.id.rv_fragment_recherche_filter);
        mRvRechercheResultat = view.findViewById(R.id.rv_fragment_recherche_resultat);
        mLlRechercheBar = view.findViewById(R.id.ll_fragment_recherche_bar);
        mEtRecherche = view.findViewById(R.id.et_fragment_recherche);
        mIvQuiteRecherche = view.findViewById(R.id.iv_fragment_recherche_quite);
        mIvFiltreRecherche = view.findViewById(R.id.iv_fragment_recherche_filter);
        mLlRecherchePasResultat = view.findViewById(R.id.ll_fragment_recherche_pas_result);
        mComponentError = view.findViewById(R.id.rl_fragment_recherche_component_error);
        /*-------------------------------------------------------------------*/

        /*preload*/
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        initRecylerFilter();

        /*call listener*/
        mIvQuiteRecherche.setOnClickListener(this);
        mIvFiltreRecherche.setOnClickListener(this);
        mEtRecherche.addTextChangedListener(this);

    }

    private void initRecyclerRechercheResultat(List<String> resultats, CharSequence s) {
        List<String> filtredResultat = new LinkedList<>();
        if (!s.equals("")) {
            for (String resultat : resultats) {
                if (resultat.contains(s)) {
                    filtredResultat.add(resultat);
                }
            }
        }

        if (filtredResultat.size() > 0) {
            mLlRecherchePasResultat.setVisibility(View.GONE);
            mRvRechercheResultat.setVisibility(View.VISIBLE);
        } else {
            mLlRecherchePasResultat.setVisibility(View.VISIBLE);
            mRvRechercheResultat.setVisibility(View.GONE);
        }

        RechercheResultatRecyclerAdapter rechercheResultatRecyclerAdapter = new RechercheResultatRecyclerAdapter(getContext(), filtredResultat);
        mRvRechercheResultat.setAdapter(rechercheResultatRecyclerAdapter);
        mRvRechercheResultat.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvRechercheResultat.setHasFixedSize(true);
    }

    private void initRecylerFilter() {
        List<String> filtres = new LinkedList<>();
        filtres.add("Client");
        filtres.add("Caissier");
        filtres.add("Caissier Responsable");
        filtres.add("Comptable");
        filtres.add("Administarteur");
        filtres.add("Chantier");

        FilterRecyclerAdapter filterRecyclerAdapter = new FilterRecyclerAdapter(getContext(), filtres, this);
        mRvFilter.setAdapter(filterRecyclerAdapter);
        mRvFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvFilter.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        if (v == mIvQuiteRecherche) {
            mEtRecherche.setText("");
        }
        if (v == mIvFiltreRecherche) {
            Toast.makeText(getContext(), "Fonctionnalité en cours de développement", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (after == 0) {
            mIvQuiteRecherche.setVisibility(View.GONE);
        } else {
            mIvQuiteRecherche.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count == 0) {
            initRecyclerRechercheResultat(null, "");
        } else {
            switch (FilterRecyclerAdapter.getSelectedItem()) {
                case 0:
                    getClients(s);
                    break;
                case 1:
                    getCassiers(s);
                    break;
                case 2:
                    getCassiersRespo(s);
                    break;
                case 3:
                    getComptables(s);
                    break;
                case 4:
                    getAdministrateurs(s);
                    break;
                case 5:
                    getChantiers(s);
                    break;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }


    private void getAdministrateurs(CharSequence s) {
        AdministrateurService.newInstance()
                .getAdministrateurs(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AdministrateurResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<AdministrateurResponseModel> administrateurResponseModels) {
                        List<String> nomComplete = new LinkedList<>();
                        for (AdministrateurResponseModel administrateurResponseModel : administrateurResponseModels) {
                            nomComplete.add(administrateurResponseModel.getNom() + " " + administrateurResponseModel.getPrenom());
                        }
                        initRecyclerRechercheResultat(nomComplete, s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getClients(CharSequence s) {
        ClientService.newInstance()
                .getClients(getContext())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ClientResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<ClientResponseModel> clientResponseModels) {
                        List<String> nomComplete = new LinkedList<>();
                        for (ClientResponseModel clientResponseModel : clientResponseModels) {
                            nomComplete.add(clientResponseModel.getNom() + " " + clientResponseModel.getPrenom());
                        }
                        initRecyclerRechercheResultat(nomComplete, s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getCassiers(CharSequence s) {
        CassierService.newInstance()
                .getCassiers(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CassierResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<CassierResponseModel> cassierResponseModels) {
                        List<String> nomComplete = new LinkedList<>();
                        for (CassierResponseModel cassierResponseModel : cassierResponseModels) {
                            nomComplete.add(cassierResponseModel.getNom() + " " + cassierResponseModel.getPrenom());
                        }
                        initRecyclerRechercheResultat(nomComplete, s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCassiersRespo(CharSequence s) {
        CassierRespoService.newInstance()
                .getCassiersRespo(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CassierRespoResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<CassierRespoResponseModel> cassierRespoResponseModels) {
                        List<String> nomComplete = new LinkedList<>();
                        for (CassierRespoResponseModel cassierRespoResponseModel : cassierRespoResponseModels) {
                            nomComplete.add(cassierRespoResponseModel.getNom() + " " + cassierRespoResponseModel.getPrenom());
                        }
                        initRecyclerRechercheResultat(nomComplete, s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getComptables(CharSequence s) {
        ComptableService.newInstance()
                .getComptables(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ComptableResponseModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<ComptableResponseModel> comptableResponseModels) {
                        List<String> nomComplete = new LinkedList<>();
                        for (ComptableResponseModel comptableResponseModel : comptableResponseModels) {
                            nomComplete.add(comptableResponseModel.getNom() + " " + comptableResponseModel.getPrenom());
                        }
                        initRecyclerRechercheResultat(nomComplete, s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getChantiers(CharSequence s) {
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
                        List<String> nomComplete = new LinkedList<>();
                        ;
                        for (ChantierResponseModel chantierResponseModel : chantierResponseModels) {
                            nomComplete.add(chantierResponseModel.getNom());
                        }
                        initRecyclerRechercheResultat(nomComplete, s);
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
    public void setFilterItemIndex(int index) {
        CharSequence s = mEtRecherche.getText().toString();
        switch (FilterRecyclerAdapter.getSelectedItem()) {
            case 0:
                getClients(s);
                break;
            case 1:
                getCassiers(s);
                break;
            case 2:
                getCassiersRespo(s);
                break;
            case 3:
                getComptables(s);
                break;
            case 4:
                getAdministrateurs(s);
                break;
            case 5:
                getChantiers(s);
                break;
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