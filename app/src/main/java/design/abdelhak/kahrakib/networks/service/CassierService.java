package design.abdelhak.kahrakib.networks.service;

import android.content.Context;
import java.util.List;
import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.CassierRepository;
import design.abdelhak.kahrakib.networks.requests.CassierRequestModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CassierService {

    private static CassierService cassierService;
    private final CassierRepository cassierRepository;

    public CassierService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cassierRepository = retrofit.create(CassierRepository.class);
    }

    public static CassierService newInstance() {
        if (cassierService == null) {
            cassierService = new CassierService();
        }
        return cassierService;
    }

    public Observable<List<CassierResponseModel>> getCassiers(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRepository.getCassiers(utilisateurBearerToken);
    }

    public Observable<CassierResponseModel> getCassier(Context context,Long cassierId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRepository.getCassier(utilisateurBearerToken,cassierId);
    }

    public Observable<CassierResponseModel> saveCassier(Context context, CassierRequestModel cassierRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRepository.saveCassier(utilisateurBearerToken,cassierRequestModel);
    }

    public Observable<CassierResponseModel> updateCassier(Context context,Long cassierId,CassierResponseModel cassierResponseModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return cassierRepository.updateCassier(utilisateurBearerToken,cassierId,cassierResponseModel);
    }

}
