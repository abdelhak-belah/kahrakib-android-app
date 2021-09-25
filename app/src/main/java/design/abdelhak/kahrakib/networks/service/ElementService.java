package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.ElementRepository;
import design.abdelhak.kahrakib.networks.repository.UtilisateurRepository;
import design.abdelhak.kahrakib.networks.requests.ElementRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.ElementResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ElementService {

    private static ElementService elementService;
    private final ElementRepository elementRepository;

    private ElementService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        elementRepository = retrofit.create(ElementRepository.class);
    }

    public static ElementService newInstance() {
        if (elementService == null) {
            elementService = new ElementService();
        }
        return elementService;
    }

    public Observable<List<ElementResponseModel>> getElements(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return elementRepository.getElements(utilisateurBearerToken);
    }

    public Observable<ElementResponseModel> getElement(Context context,Long elementId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return elementRepository.getElement(utilisateurBearerToken,elementId);
    }

    public Observable<ElementResponseModel> saveElement(Context context, ElementRequestModel elementRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return elementRepository.saveElement(utilisateurBearerToken,elementRequestModel);
    }

    public Observable<ElementResponseModel> updateElement(Context context,Long elementId,ElementResponseModel elementResponseModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return elementRepository.updateElement(utilisateurBearerToken,elementId,elementResponseModel);
    }

    public Observable<DeleteResponseModel> deleteElement(Context context, Long elementId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return elementRepository.deleteElement(utilisateurBearerToken,elementId);
    }
}
