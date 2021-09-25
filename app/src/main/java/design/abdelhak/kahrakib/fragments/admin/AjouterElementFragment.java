package design.abdelhak.kahrakib.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.requests.ElementRequestModel;
import design.abdelhak.kahrakib.networks.responses.ElementResponseModel;
import design.abdelhak.kahrakib.networks.service.ElementService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AjouterElementFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbAjouterElement;
    private MaterialButton mMBtnAjouterElement;
    private TextInputLayout mTilNom;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private MaterialButton mMBtnComponentSuccesContinue,mMBtnComponentErrorRessayer,mMBtnComponentErrorAcceuill;
    private TextView mTvComponentSuccesMessage;

    private Boolean isUpdate = false;
    private final CompositeDisposable disposable  = new CompositeDisposable();
    private ElementResponseModel elementResponseModel;
    /*-------------------------------------------------------------------*/

    public AjouterElementFragment() {
        // Required empty public constructor
    }


    public static AjouterElementFragment newInstance(Bundle data) {
        AjouterElementFragment fragment = new AjouterElementFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            isUpdate = (Boolean) getArguments().getSerializable(BundleKeys.IS_UPDATE_KEY);
            elementResponseModel = (ElementResponseModel) getArguments().getSerializable(BundleKeys.ELEMENT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_element, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbAjouterElement = view.findViewById(R.id.mtb_fragment_ajouter_element);
        mComponentLoading = view.findViewById(R.id.pb_fragment_ajouter_element_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_ajouter_element_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_ajouter_element_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_ajouter_element_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_ajouter_element_sub_container);
        mTilNom = view.findViewById(R.id.til_fragment_ajouter_element_nom);
        mMBtnAjouterElement = view.findViewById(R.id.mbtn_fragment_ajouter_element_ajouter);

        mMBtnComponentSuccesContinue = mComponentSucces.findViewById(R.id.mbtn_component_succes_continue);
        mMBtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMBtnComponentErrorAcceuill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        mTvComponentSuccesMessage = mComponentSucces.findViewById(R.id.tv_lav_component_succes_message);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        if (isUpdate){
            mMBtnAjouterElement.setText(R.string.fragment_ajouter_element_maj);
            mTilNom.getEditText().setText(elementResponseModel.getNom());
        }
        setUpActionBar();

        //call listener
        mMBtnAjouterElement.setOnClickListener(this);
        mMBtnComponentSuccesContinue.setOnClickListener(this);
        mMBtnComponentErrorRessayer.setOnClickListener(this);
        mMBtnComponentErrorAcceuill.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == mMBtnAjouterElement){
            if (isUpdate){
                updateElement();
            }else {
                saveElement();
            }
        }
        if (v == mMBtnComponentSuccesContinue){
            NavigationUtil.back(getContext());
        }
        if (v == mMBtnComponentErrorRessayer){
            if (isUpdate){
                updateElement();
            }else {
                saveElement();
            }
        }
        if (v == mMBtnComponentErrorAcceuill){
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }

    private void updateElement() {
        String nomElement = mTilNom.getEditText().getText().toString();
        if (nomElement.isEmpty()){
            mTilNom.setErrorEnabled(true);
            mTilNom.setError(getString(R.string.message_obligatoire_non_vide));
        }else {
            mTilNom.setErrorEnabled(false);
            mTilNom.setError("");
            elementResponseModel.setNom(nomElement);
            ElementService.newInstance()
                    .updateElement(getContext(),elementResponseModel.getElementId(),elementResponseModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ElementResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(ElementResponseModel elementResponseModel) {
                            showSucces("mise à jour de l'élément réussie");
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

    private void saveElement() {
        String nomElement = mTilNom.getEditText().getText().toString();

        if (nomElement.isEmpty()){
            mTilNom.setErrorEnabled(true);
            mTilNom.setError(getString(R.string.message_obligatoire_non_vide));
        }else {
            mTilNom.setErrorEnabled(false);
            mTilNom.setError("");
            ElementService.newInstance()
                    .saveElement(getContext(),new ElementRequestModel(nomElement))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ElementResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(ElementResponseModel elementResponseModel) {
                            showSucces("élément ajouté avec succès");
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


    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mMtbAjouterElement);

        if (isUpdate){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.fragment_ajouter_element_maj_titre);
        }

        //call listener
        mMtbAjouterElement.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }

    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
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
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
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