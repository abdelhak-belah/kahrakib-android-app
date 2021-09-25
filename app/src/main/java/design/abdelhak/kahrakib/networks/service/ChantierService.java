package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.ChantierRepository;
import design.abdelhak.kahrakib.networks.requests.ChantierRequestModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChantierService {

    private static ChantierService chantierService;
    private final ChantierRepository chantierRepository;

    public ChantierService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chantierRepository = retrofit.create(ChantierRepository.class);
    }

    public static ChantierService newInstance() {
        if (chantierService == null) {
            chantierService = new ChantierService();
        }
        return chantierService;
    }

    public Observable<List<ChantierResponseModel>> getChantiers(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return chantierRepository.getChantiers(utilisateurBearerToken);
    }

    public Observable<ChantierResponseModel> getChantier(Context context,Long chantierId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return chantierRepository.getChantier(utilisateurBearerToken,chantierId);
    }

    public Observable<ChantierResponseModel> saveChantier(Context context, ChantierRequestModel chantierRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return chantierRepository.saveChantier(utilisateurBearerToken,chantierRequestModel);
    }

    public Observable<ChantierResponseModel> updateChantier(Context context,Long chantierId,ChantierRequestModel chantierRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return chantierRepository.updateChantier(utilisateurBearerToken,chantierId,chantierRequestModel);
    }

    public Observable<DeleteResponseModel> deleteChantier(Context context,Long chantierId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return chantierRepository.deleteChantier(utilisateurBearerToken,chantierId);
    }
}
