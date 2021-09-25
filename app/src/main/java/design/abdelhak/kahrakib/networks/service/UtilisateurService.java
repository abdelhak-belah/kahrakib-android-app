package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.AuthRepository;
import design.abdelhak.kahrakib.networks.repository.UtilisateurRepository;
import design.abdelhak.kahrakib.networks.requests.RegisterRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilisateurService {

    private static UtilisateurService utilisateurService;
    private final UtilisateurRepository utilisateurRepository;

    private UtilisateurService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        utilisateurRepository = retrofit.create(UtilisateurRepository.class);
    }

    public static UtilisateurService newInstance() {
        if (utilisateurService == null) {
            utilisateurService = new UtilisateurService();
        }
        return utilisateurService;
    }

    public Observable<UtilisateurResponseModel> getCurrentUtilisateur(Context context) {
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        int utilisateurId = SharedPreferencesUtil.getUserId(context);
        return utilisateurRepository.getUtilisateur(utilisateurBearerToken, utilisateurId);
    }

    public Observable<UtilisateurResponseModel> getUtilisateur(Context context,Long utilisateurId) {
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return utilisateurRepository.getUtilisateur(utilisateurBearerToken, utilisateurId);
    }

    public Observable<List<UtilisateurResponseModel>> getUtilisateurs(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return utilisateurRepository.getUtlisateurs(utilisateurBearerToken);
    }

    public Observable<UtilisateurResponseModel> saveUtilisateur(Context context, RegisterRequestModel registerRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return utilisateurRepository.saveUtilisateur(utilisateurBearerToken,registerRequestModel);
    }

    public Observable<UtilisateurResponseModel> updateUtilisateur(Context context,Long utilisateurId,UtilisateurResponseModel utilisateurResponseModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return utilisateurRepository.updateUtilisateur(utilisateurBearerToken,utilisateurId,utilisateurResponseModel);
    }

    public Observable<DeleteResponseModel> deleteUtilisateur(Context context,Long utilisateurId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return utilisateurRepository.deleteUtilisateur(utilisateurBearerToken,utilisateurId);
    }
}
