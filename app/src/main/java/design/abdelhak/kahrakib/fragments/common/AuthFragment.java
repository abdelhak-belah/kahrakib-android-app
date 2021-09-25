package design.abdelhak.kahrakib.fragments.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.responses.AuthResponseModel;
import design.abdelhak.kahrakib.networks.requests.AuthRequestModel;
import design.abdelhak.kahrakib.networks.service.AuthService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import design.abdelhak.kahrakib.utils.ValidatorUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AuthFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialButton mMbConnecter, mMbOublier;
    private TextInputLayout mTilEmail, mTilMotDePasse;
    private ProgressBar mPbLoadingCircle;
    private ConstraintLayout mClContainer;
    private final CompositeDisposable disposable = new CompositeDisposable();
    /*-------------------------------------------------------------------*/

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance(Bundle data) {
        AuthFragment authFragment = new AuthFragment();
        authFragment.setArguments(data);
        return authFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*check if user already logged in*/
        checkUserAuth();

        /*----------------------------inflate-------------------------------*/
        mMbConnecter = view.findViewById(R.id.mb_fragment_auth_connecter);
        mMbOublier = view.findViewById(R.id.mb_fragment_auth_forget_password);
        mTilEmail = view.findViewById(R.id.til_fragment_auth_email);
        mTilMotDePasse = view.findViewById(R.id.til_fragment_auth_mot_de_passe);
        mPbLoadingCircle = view.findViewById(R.id.pb_loading_cirlce);
        mClContainer = view.findViewById(R.id.cl_fragment_auth_container);
        /*-------------------------------------------------------------------*/

        /*call listener*/
        mMbConnecter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mMbConnecter) {
            authUser();
        }
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.clear();
        }
        super.onDestroy();
    }


    private void authUser() {
        showLoading();

        String email = mTilEmail.getEditText().getText().toString().trim();
        String password = mTilMotDePasse.getEditText().getText().toString().trim();
        AuthRequestModel authRequestModel = new AuthRequestModel(email, password);

        if (checkUserEntredEmail(email) & checkUserEntredPassword(password)) {
            AuthService.newInstance()
                    .auth(authRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<AuthResponseModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(AuthResponseModel authResponseModel) {
                            if (authResponseModel.getJwt() != null) {
                                SharedPreferencesUtil.createUserSession(getContext(), authResponseModel);
                                sendUserToHisSpace(authResponseModel.getRole());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showLayout();
                            Toast.makeText(getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            showLayout();
        }
    }

    private Boolean checkUserEntredEmail(String email) {
        if (ValidatorUtil.isEmpty(email)) {
            mTilEmail.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else if (!ValidatorUtil.isValidEmail(email)) {
            mTilEmail.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilEmail.setError(null);
            mTilEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean checkUserEntredPassword(String password) {
        if (ValidatorUtil.isEmpty(password)) {
            mTilMotDePasse.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilMotDePasse.setError(null);
            mTilMotDePasse.setErrorEnabled(false);
            return true;
        }
    }

    private void checkUserAuth() {
        if (SharedPreferencesUtil.isUserLoggedIn(getContext()) && SharedPreferencesUtil.getParametreSession(getContext())) {
            String role = SharedPreferencesUtil.getUserRole(getContext());
            sendUserToHisSpace(role);
        }
    }

    private void sendUserToHisSpace(String role) {
        switch (role) {
            case NetworkKeys.ROLE_ADMIN_KEY:
                NavigationUtil.navigateToAdminFragment(getContext(), null);
                break;
            case NetworkKeys.ROLE_CLIENT_KEY:
                NavigationUtil.navigateToClientFragment(getContext(), null);
                break;
            case NetworkKeys.ROLE_CASSIER_KEY:
                NavigationUtil.navigateToCassierFragment(getContext(), null);
                break;
            case NetworkKeys.ROLE_CASSIER_RESPO_KEY:
                NavigationUtil.navigateToCassierRespo(getContext(), null);
                break;
            case NetworkKeys.ROLE_COMPTABLE_KEY:
                NavigationUtil.navigateToComptable(getContext(), null);
                break;
        }
    }

    private void showLoading() {
        mClContainer.setVisibility(View.GONE);
        mPbLoadingCircle.setVisibility(View.VISIBLE);
    }

    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mPbLoadingCircle.setVisibility(View.GONE);
    }

}