package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.EdcRepository;
import design.abdelhak.kahrakib.networks.requests.EdsRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatEdsRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EdcService {

    private static EdcService edcService;
    private final EdcRepository edcRepository;

    private EdcService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        edcRepository = retrofit.create(EdcRepository.class);
    }

    public static EdcService newInstance() {
        if (edcService == null) {
            edcService = new EdcService();
        }
        return edcService;
    }

    public Observable<List<EdsResponseModel>> getEdcs(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return edcRepository.getEdcs(utilisateurBearerToken);
    }

    public Observable<EdsResponseModel> getEdc(Context context,Long edsId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return edcRepository.getEdc(utilisateurBearerToken,edsId);
    }

    public Observable<EdsResponseModel> saveEdc(Context context, EdsRequestModel edsRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return edcRepository.saveEdc(utilisateurBearerToken,edsRequestModel);
    }

    public Observable<EdsResponseModel> updateEdcEtat(Context context, Long edsId, EtatEdsRequestModel etatEdsRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return edcRepository.updateEdcEtat(utilisateurBearerToken,edsId,etatEdsRequestModel);
    }

    public Observable<DeleteResponseModel> deleteEdc(Context context,Long edsId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return edcRepository.deleteEdc(utilisateurBearerToken,edsId);
    }
}
