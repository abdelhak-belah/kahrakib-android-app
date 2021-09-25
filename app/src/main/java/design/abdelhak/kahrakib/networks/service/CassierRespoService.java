package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.CassierRespoRepository;
import design.abdelhak.kahrakib.networks.requests.CassierRespoRequestModel;
import design.abdelhak.kahrakib.networks.responses.CassierRespoResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CassierRespoService {

    private static CassierRespoService cassierRespoService;
    private final CassierRespoRepository cassierRespoRepository;

    private CassierRespoService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cassierRespoRepository = retrofit.create(CassierRespoRepository.class);
    }

    public static CassierRespoService newInstance() {
        if (cassierRespoService == null) {
            cassierRespoService = new CassierRespoService();
        }
        return cassierRespoService;
    }

    public Observable<List<CassierRespoResponseModel>> getCassiersRespo(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRespoRepository.getCassiersRespo(utilisateurBearerToken);
    }

    public Observable<CassierRespoResponseModel> getCassierRespo(Context context,Long cassierRespoId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRespoRepository.getCassierRespo(utilisateurBearerToken,cassierRespoId);
    }

    public Observable<CassierRespoResponseModel> saveCassiersRespo(Context context, CassierRespoRequestModel cassierRespoRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRespoRepository.saveCassierRespo(utilisateurBearerToken,cassierRespoRequestModel);
    }
}
