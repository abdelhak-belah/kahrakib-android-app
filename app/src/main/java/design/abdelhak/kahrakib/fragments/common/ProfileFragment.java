package design.abdelhak.kahrakib.fragments.common;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.networks.service.UtilisateurService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbProfile;
    private LinearLayout mLlUserInfo, mLlChangeMotDePasse, mLlParametre, mLlContact, mLlCondition, mLlDeconnecte;
    private TextView mTvNomComplete, mTvEmail;
    private TextView mTvToolbarTitle;
    private LinearLayout mllUserInfoContainer;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private MaterialButton mMbtnComponentErrorRessayer;
    private MaterialButton mMbtnComponentErrorAllerAccueill;

    private UtilisateurResponseModel mUtilisateurResponseModel;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Bundle data) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(data);
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*----------------------------inflate-------------------------------*/
        mComponentLoading = view.findViewById(R.id.pb_fragment_profile_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_profile_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_profile_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_profile_sub_container);
        mllUserInfoContainer = view.findViewById(R.id.ll_fragment_profile_user_info_container);
        mTvNomComplete = view.findViewById(R.id.tv_fragment_profile_full_name);
        mTvEmail = view.findViewById(R.id.tv_fragment_profile_email);
        mMtbProfile = view.findViewById(R.id.mtb_fragment_profile);
        mLlUserInfo = view.findViewById(R.id.ll_fragment_profile_information);
        mLlChangeMotDePasse = view.findViewById(R.id.ll_fragment_profile_change_mot_de_passe);
        mLlParametre = view.findViewById(R.id.ll_fragment_profile_parametre);
        mLlContact = view.findViewById(R.id.ll_fragment_profile_contact);
        mLlCondition = view.findViewById(R.id.ll_fragment_profile_condition_de_utilisation);
        mLlDeconnecte = view.findViewById(R.id.ll_fragment_profile_deconnecter);

        mMbtnComponentErrorRessayer = mComponentError.findViewById(R.id.mbtn_component_error_ressayer);
        mMbtnComponentErrorAllerAccueill = mComponentError.findViewById(R.id.mbtn_component_error_aller_accueill);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();

        /*Call listener*/
        mLlUserInfo.setOnClickListener(this);
        mLlChangeMotDePasse.setOnClickListener(this);
        mLlParametre.setOnClickListener(this);
        mLlContact.setOnClickListener(this);
        mLlCondition.setOnClickListener(this);
        mLlDeconnecte.setOnClickListener(this);
        mMbtnComponentErrorRessayer.setOnClickListener(this);
        mMbtnComponentErrorAllerAccueill.setOnClickListener(this);

        /*retrieve user information*/
        getUserInformation();

    }


    @Override
    public void onClick(View v) {
        if (v == mLlUserInfo) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKeys.USER_DETAILS_KEY, mUtilisateurResponseModel);
            NavigationUtil.navigateToInformationFragment(getContext(), bundle);
        }
        if (v == mLlChangeMotDePasse) {
            Bundle bundleData = new Bundle();
            bundleData.putSerializable(BundleKeys.UTILISATEUR_KEY,mUtilisateurResponseModel);
            NavigationUtil.navigateToPassFragment(getContext(), bundleData);
        }
        if (v == mLlParametre) {
            NavigationUtil.navigateToParametreFragment(getContext(), null);
        }
        if (v == mLlContact) {
            NavigationUtil.navigateToContactFragment(getContext(), null);
        }
        if (v == mLlCondition) {
            NavigationUtil.navigateToConditionFragment(getContext(), null);
        }
        if (v == mLlDeconnecte) {
            showLoading();
            SharedPreferencesUtil.logoutUser(getContext());
            NavigationUtil.logoutAndNavigateToAuthFragment(getContext(), null);
        }
        if (v == mMbtnComponentErrorRessayer) {
            getUserInformation();
        }
        if (v == mMbtnComponentErrorAllerAccueill) {
            NavigationUtil.popUntil(getContext(), FragmentTagKeys.HOME_FRAGMENT_KEY);
        }
    }




    private void getUserInformation() {
        showLoading();
        UtilisateurService.newInstance()
                .getCurrentUtilisateur(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UtilisateurResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(UtilisateurResponseModel utilisateurResponseModel) {
                        showLayout();
                        mUtilisateurResponseModel = utilisateurResponseModel;
                        mTvEmail.setText(utilisateurResponseModel.getEmail());
                        mTvNomComplete.setText(utilisateurResponseModel.getPrenom() + " " + utilisateurResponseModel.getNom());
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                        Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }


    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbProfile);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        /*call back nav listener*/
        mMtbProfile.setNavigationOnClickListener(new View.OnClickListener() {
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
        mllUserInfoContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mNsvSubContainer.setVisibility(View.GONE);
        mllUserInfoContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }



    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }

}