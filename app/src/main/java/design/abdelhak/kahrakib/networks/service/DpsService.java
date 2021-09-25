package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.DpsRepository;
import design.abdelhak.kahrakib.networks.requests.DpsRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatCassierRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatClientRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DpsService {

    private static DpsService dpsService;
    private final DpsRepository dpsRepository;

    public DpsService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dpsRepository = retrofit.create(DpsRepository.class);
    }

    public static DpsService newInstance() {
        if (dpsService == null) {
            dpsService = new DpsService();
        }
        return dpsService;
    }


    public Observable<List<DpsResponseModel>> getDpss(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return dpsRepository.getDpss(utilisateurBearerToken);
    }

    public Observable<DpsResponseModel> getDps(Context context,Long dpsId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return dpsRepository.getDps(utilisateurBearerToken,dpsId);
    }

    public Observable<DpsResponseModel> updateDpsEtatClient(Context context, Long dpsId, EtatClientRequestModel etatClientRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return dpsRepository.updateClientEtat(utilisateurBearerToken,dpsId,etatClientRequestModel);
    }

    public Observable<DpsResponseModel> updateDpsEtatCassier(Context context, Long dpsId, EtatCassierRequestModel etatCassierRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return dpsRepository.updateCassierEtat(utilisateurBearerToken,dpsId,etatCassierRequestModel);
    }

    public Observable<DeleteResponseModel> deleteDps(Context context,Long dpsId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return dpsRepository.deleteDps(utilisateurBearerToken,dpsId);
    }

    public Observable<DpsResponseModel> saveDps(Context context, DpsRequestModel dpsRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return dpsRepository.saveDps(utilisateurBearerToken,dpsRequestModel);
    }
}
