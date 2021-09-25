package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.ClientRepository;
import design.abdelhak.kahrakib.networks.requests.ClientRequestModel;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {

    private static ClientService clientService;
    private final ClientRepository clientRepository;

    private ClientService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clientRepository = retrofit.create(ClientRepository.class);
    }

    public static ClientService newInstance() {
        if (clientService == null) {
            clientService = new ClientService();
        }
        return clientService;
    }

    public Observable<List<ClientResponseModel>> getClients(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return clientRepository.getClients(utilisateurBearerToken);
    }

    public Observable<ClientResponseModel> getClient(Context context,Long clientId){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return clientRepository.getClient(utilisateurBearerToken,clientId);
    }

    public Observable<ClientResponseModel> saveClient(Context context, ClientRequestModel clientRequestModel){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return clientRepository.saveClient(utilisateurBearerToken,clientRequestModel);
    }
}
