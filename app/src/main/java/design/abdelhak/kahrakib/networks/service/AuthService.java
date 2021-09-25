package design.abdelhak.kahrakib.networks.service;


import android.content.Context;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.AuthRepository;
import design.abdelhak.kahrakib.networks.requests.AuthRequestModel;
import design.abdelhak.kahrakib.networks.requests.MotDePasseRequest;
import design.abdelhak.kahrakib.networks.responses.AuthResponseModel;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {

    private static AuthService authService;
    private final AuthRepository authRepository;

    private AuthService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authRepository = retrofit.create(AuthRepository.class);
    }

    public static AuthService newInstance() {
        if (authService == null) {
            authService = new AuthService();
        }
        return authService;
    }

    public Observable<AuthResponseModel> auth(AuthRequestModel authRequestModel){
        return authRepository.userAuth(authRequestModel);
    }

    public Observable<UtilisateurResponseModel> updateMotDePasse(Context context, MotDePasseRequest motDePasseRequest){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return authRepository.updateMotDePasse(motDePasseRequest);
    }
}
