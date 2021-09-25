package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.AdministrateurRepository;
import design.abdelhak.kahrakib.networks.repository.AuthRepository;
import design.abdelhak.kahrakib.networks.requests.AdministrateurRequestModel;
import design.abdelhak.kahrakib.networks.responses.AdministrateurResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdministrateurService {

    private static AdministrateurService administrateurService;
    private final AdministrateurRepository administrateurRepository;

    public AdministrateurService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        administrateurRepository = retrofit.create(AdministrateurRepository.class);
    }

    public static AdministrateurService newInstance() {
        if (administrateurService == null) {
            administrateurService = new AdministrateurService();
        }
        return administrateurService;
    }

    public Observable<List<AdministrateurResponseModel>> getAdministrateurs(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return administrateurRepository.getAdministrateurs(utilisateurBearerToken);
    }

    public Observable<AdministrateurResponseModel> getAdministrateur(Context context,Long adminId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return administrateurRepository.getAdministrateur(utilisateurBearerToken,adminId);
    }

    public Observable<AdministrateurResponseModel> saveAdministareur(Context context, AdministrateurRequestModel administrateurRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return administrateurRepository.saveAdminisatrateurs(utilisateurBearerToken,administrateurRequestModel);
    }
}
