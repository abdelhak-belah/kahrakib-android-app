package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.ChantierRepository;
import design.abdelhak.kahrakib.networks.repository.DirectionRepository;
import design.abdelhak.kahrakib.networks.responses.DirectionResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DirectionService {

    private static DirectionService directionService;
    private final DirectionRepository directionRepository;

    public DirectionService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        directionRepository = retrofit.create(DirectionRepository.class);
    }

    public static DirectionService newInstance() {
        if (directionService == null) {
            directionService = new DirectionService();
        }
        return directionService;
    }

    public Observable<List<DirectionResponseModel>> getDirections(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return directionRepository.getDirections(utilisateurBearerToken);
    }
}
