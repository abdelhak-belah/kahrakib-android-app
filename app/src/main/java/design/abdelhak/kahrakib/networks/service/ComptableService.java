package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.ComptableRepository;
import design.abdelhak.kahrakib.networks.requests.ComptableRequestModel;
import design.abdelhak.kahrakib.networks.responses.ComptableResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComptableService {

    private static ComptableService comptableService;
    private final ComptableRepository comptableRepository;

    private ComptableService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        comptableRepository = retrofit.create(ComptableRepository.class);
    }

    public static ComptableService newInstance() {
        if (comptableService == null) {
            comptableService = new ComptableService();
        }
        return comptableService;
    }

    public Observable<List<ComptableResponseModel>> getComptables(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return comptableRepository.getComptables(utilisateurBearerToken);
    }

    public Observable<ComptableResponseModel> getComptable(Context context,Long comptableId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return comptableRepository.getComptable(utilisateurBearerToken,comptableId);
    }

    public Observable<ComptableResponseModel> saveComptable(Context context, ComptableRequestModel comptableRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return comptableRepository.saveComptable(utilisateurBearerToken,comptableRequestModel);
    }
}
