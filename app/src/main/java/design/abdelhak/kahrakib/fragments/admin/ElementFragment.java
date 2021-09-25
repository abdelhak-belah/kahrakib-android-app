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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.adapters.ElementRecyclerAdapter;
import design.abdelhak.kahrakib.intermediate.ConfermeSupprissionIntermediate;
import design.abdelhak.kahrakib.intermediate.ElementIntermediate;
import design.abdelhak.kahrakib.models.ElementModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.ElementResponseModel;
import design.abdelhak.kahrakib.networks.service.ElementService;
import design.abdelhak.kahrakib.utils.DialogUtil;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ElementFragment extends Fragment implements View.OnClickListener, ElementIntermediate, ConfermeSupprissionIntermediate {


    /*-------------------------------------------------------------------*/
    private RecyclerView mRvElement;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mClContainer;
    private FloatingActionButton mFabAjouterElement;
    private MaterialButton mBtnComponentErrorRessayer,mBtnComponentErrorAccueill;

    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public ElementFragment() {
        // Required empty public constructor
    }


    public static ElementFragment newInstance(Bundle data) {
        ElementFragment fragment = new ElementFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_element, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_element_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_element_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_element_container);
        mRvElement = view.findViewById(R.id.rv_fragment_element);
        mFabAjouterElement = view.findViewById(R.id.fab_fragment_element_ajouter_element);

        mBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mBtnComponentErrorAccueill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        /*-------------------------------------------------------------------*/

        /*preload*/
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        showLoading();
        mBtnComponentErrorAccueill.setVisibility(View.INVISIBLE);

        /*fetch methode*/
        getElements();


        //call listener
        mFabAjouterElement.setOnClickListener(this);
        mBtnComponentErrorRessayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFabAjouterElement){
            NavigationUtil.navigateToAjouterElementFragment(getContext(),null);
        }
        if (v == mBtnComponentErrorRessayer){
            getElements();
        }
    }


    private void getElements(){
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
                        initUsersRecycler(elementResponseModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void deleteElement(Long elementId) {
        DialogUtil.showConfermeSupprissionDialog(getContext(),this,elementId,"êtes-vous sûr de vouloir supprimer définitivement cet élément");
    }

    @Override
    public void confermeSupprission(Boolean conferme,Long id) {
        if (conferme){
            showLoading();
            ElementService.newInstance()
                    .deleteElement(getContext(),id)
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
                            getElements();
                            Toast.makeText(getContext(), deleteResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            showError();
                        }

                        @Override
                        public void onComplete() {}
                    });
        }
    }


    private void initUsersRecycler(List<ElementResponseModel> elements) {
        ElementRecyclerAdapter adapter = new ElementRecyclerAdapter(getContext(),elements,this);
        mRvElement.setAdapter(adapter);
        mRvElement.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvElement.setHasFixedSize(true);
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
    public void onDestroy() {
        if (disposable != null){
            disposable.clear();
        }
        super.onDestroy();
    }
}