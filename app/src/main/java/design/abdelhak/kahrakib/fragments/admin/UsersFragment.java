package design.abdelhak.kahrakib.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
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
import design.abdelhak.kahrakib.adapters.UsersRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.ConfermeSupprissionIntermediate;
import design.abdelhak.kahrakib.intermediate.UtilisateurIntermediate;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.models.UserModel;
import design.abdelhak.kahrakib.networks.responses.AdministrateurResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierRespoResponseModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import design.abdelhak.kahrakib.networks.responses.ComptableResponseModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.networks.service.AdministrateurService;
import design.abdelhak.kahrakib.networks.service.CassierRespoService;
import design.abdelhak.kahrakib.networks.service.CassierService;
import design.abdelhak.kahrakib.networks.service.ClientService;
import design.abdelhak.kahrakib.networks.service.ComptableService;
import design.abdelhak.kahrakib.networks.service.UtilisateurService;
import design.abdelhak.kahrakib.utils.DialogUtil;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UsersFragment extends Fragment implements View.OnClickListener, UtilisateurIntermediate, ConfermeSupprissionIntermediate {

    /*-------------------------------------------------------------------*/
    private FloatingActionButton mFabAjouterUser;
    private RecyclerView mRvUsers;
    private TextView mTvFilter;
    private ImageView mIvFilter;
    private LinearLayout mLlFilter;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mClContainer;
    private MaterialButton mBtnComponentErrorRessayer;
    private MaterialButton mBtnComponentErrorAccueill;

    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance(Bundle data) {
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_users_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_users_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_users_container);
        mRvUsers = view.findViewById(R.id.rv_fragment_users);
        mTvFilter = view.findViewById(R.id.tv_fragment_users_filter);
        mIvFilter = view.findViewById(R.id.iv_fragment_users_filter);
        mLlFilter = view.findViewById(R.id.ll_fragment_users_filter);
        mFabAjouterUser = view.findViewById(R.id.fab_fragment_users_ajouter_user);

        mBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mBtnComponentErrorAccueill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        /*-------------------------------------------------------------------*/

        /*preload*/
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        mBtnComponentErrorAccueill.setVisibility(View.INVISIBLE);

        //fetch function
        getUtilisateurs();


        mLlFilter.setOnClickListener(this);
        mFabAjouterUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mLlFilter) {
            showFilterPopUpMenu();
        }
        if (v == mFabAjouterUser) {
            NavigationUtil.navigateToAjouterUserFragment(getContext(), null);
        }
        if (v == mBtnComponentErrorRessayer){
            getUtilisateurs();
        }
    }

    @Override
    public void onDestroy() {
        if (disposable != null){
            disposable.clear();
        }
        super.onDestroy();
    }

    private void showFilterPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), mIvFilter);
        popupMenu.inflate(R.menu.menu_user_filter);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_user_filter_administrateur:
                        mTvFilter.setText(R.string.menu_user_filter_administratuer);
                        getAdministrateurs();
                        return true;
                    case R.id.menu_user_filter_cassier:
                        mTvFilter.setText(R.string.menu_user_filter_cassier);
                        getCassiers();
                        return true;
                    case R.id.menu_user_filter_cassier_respo:
                        mTvFilter.setText(R.string.menu_user_filter_cassier_respo);
                        getCassiersRespo();
                        return true;
                    case R.id.menu_user_filter_comptable:
                        mTvFilter.setText(R.string.menu_user_filter_comptable);
                        getComptables();
                        return true;
                    case R.id.menu_user_filter_client:
                        mTvFilter.setText(R.string.menu_user_filter_client);
                        getClients();
                        return true;
                    case R.id.menu_user_filter_tout:
                        mTvFilter.setText(R.string.menu_user_filter_tout);
                        getUtilisateurs();
                        return true;
                    default:
                        mTvFilter.setText(R.string.menu_user_filter_tout);
                        getUtilisateurs();
                        return true;
                }
            }
        });
        popupMenu.show();
    }

    private void getUtilisateurs() {
        showLoading();
        UtilisateurService.newInstance()
                .getUtilisateurs(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UtilisateurResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<UtilisateurResponseModel> utilisateurResponseModels) {
                        List<UserModel> users = new LinkedList<>();
                        for (UtilisateurResponseModel utilisateurResponseModel:utilisateurResponseModels) {
                            users.add(new UserModel(
                                    utilisateurResponseModel.getUtilisateurId(),
                                    utilisateurResponseModel.getNom(),
                                    utilisateurResponseModel.getPrenom(),
                                    "",
                                    "",
                                    utilisateurResponseModel.getRole().getRole(),
                                    utilisateurResponseModel.getEmail(),
                                    utilisateurResponseModel.getDateNaissance(),
                                    utilisateurResponseModel.getTelephone()
                            ));
                        }
                        initUsersRecycler(users);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        showError();
                        Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void getAdministrateurs(){
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
                        List<UserModel> admins = new LinkedList<>();
                        for (AdministrateurResponseModel administrateurResponseModel:administrateurResponseModels) {
                            admins.add(new UserModel(
                                    administrateurResponseModel.getUtilisateurId(),
                                    administrateurResponseModel.getNom(),
                                    administrateurResponseModel.getPrenom(),
                                    "",
                                    "",
                                    administrateurResponseModel.getRole().getRole(),
                                    administrateurResponseModel.getEmail(),
                                    administrateurResponseModel.getDateNaissance(),
                                    administrateurResponseModel.getTelephone()
                            ));
                        }
                        initUsersRecycler(admins);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    private void getClients(){
        ClientService.newInstance()
                .getClients(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ClientResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<ClientResponseModel> clientResponseModels) {
                        List<UserModel> clients = new LinkedList<>();
                        for (ClientResponseModel clientResponseModel:clientResponseModels){
                            clients.add(new UserModel(
                                    clientResponseModel.getUtilisateurId(),
                                    clientResponseModel.getNom(),
                                    clientResponseModel.getPrenom(),
                                    clientResponseModel.getChantier().getDirection().getImputation(),
                                    clientResponseModel.getChantier().getNom(),
                                    clientResponseModel.getRole().getRole(),
                                    clientResponseModel.getEmail(),
                                    clientResponseModel.getDateNaissance(),
                                    clientResponseModel.getTelephone()
                            ));
                        }
                        initUsersRecycler(clients);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void getCassiers(){
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
                        List<UserModel> cassiers = new LinkedList<>();
                        for (CassierResponseModel cassierResponseModel:cassierResponseModels){
                            cassiers.add(new UserModel(
                                    cassierResponseModel.getUtilisateurId(),
                                    cassierResponseModel.getNom(),
                                    cassierResponseModel.getPrenom(),
                                    cassierResponseModel.getChantier().getDirection().getImputation(),
                                    cassierResponseModel.getChantier().getNom(),
                                    cassierResponseModel.getRole().getRole(),
                                    cassierResponseModel.getEmail(),
                                    cassierResponseModel.getDateNaissance(),
                                    cassierResponseModel.getTelephone()
                            ));
                        }
                        initUsersRecycler(cassiers);
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

    private void getCassiersRespo(){
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
                        List<UserModel> cassiersRespo = new LinkedList<>();
                        for (CassierRespoResponseModel cassierRespoResponseModel:cassierRespoResponseModels){
                            cassiersRespo.add(new UserModel(
                                    cassierRespoResponseModel.getUtilisateurId(),
                                    cassierRespoResponseModel.getNom(),
                                    cassierRespoResponseModel.getPrenom(),
                                    cassierRespoResponseModel.getChantier().getDirection().getImputation(),
                                    cassierRespoResponseModel.getChantier().getNom(),
                                    cassierRespoResponseModel.getRole().getRole(),
                                    cassierRespoResponseModel.getEmail(),
                                    cassierRespoResponseModel.getDateNaissance(),
                                    cassierRespoResponseModel.getTelephone()
                            ));
                        }
                        initUsersRecycler(cassiersRespo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void getComptables(){
        ComptableService.newInstance()
                .getComptables(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ComptableResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<ComptableResponseModel> comptableResponseModels) {
                        List<UserModel> comptables = new LinkedList<>();
                        for (ComptableResponseModel comptableResponseModel:comptableResponseModels){
                            comptables.add(new UserModel(
                                    comptableResponseModel.getUtilisateurId(),
                                    comptableResponseModel.getNom(),
                                    comptableResponseModel.getPrenom(),
                                    comptableResponseModel.getDirection().getImputation(),
                                    "",
                                    comptableResponseModel.getRole().getRole(),
                                    comptableResponseModel.getEmail(),
                                    comptableResponseModel.getDateNaissance(),
                                    comptableResponseModel.getTelephone()
                            ));
                        }
                        initUsersRecycler(comptables);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void initUsersRecycler(List<UserModel> users) {
        UsersRecyclerAdapter adapter = new UsersRecyclerAdapter(users, getContext(),this);
        mRvUsers.setAdapter(adapter);
        mRvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvUsers.setHasFixedSize(true);
        showLayout();
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
    public void deleteUtilisateur(Long utilisateurId) {
        DialogUtil.showConfermeSupprissionDialog(getContext(),this,utilisateurId,"êtes-vous sûr de vouloir supprimer définitivement cet utilisateur");
    }

    @Override
    public void confermeSupprission(Boolean conferme, Long id) {
        if (conferme){
            showLoading();
            UtilisateurService.newInstance()
                    .deleteUtilisateur(getContext(),id)
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
                            getUtilisateurs();
                            Toast.makeText(getContext(), "utilisateur supprimé avec succès", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}