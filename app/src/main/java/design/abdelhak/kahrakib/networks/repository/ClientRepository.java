package design.abdelhak.kahrakib.networks.repository;

import java.util.List;


import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.ClientRequestModel;
import design.abdelhak.kahrakib.networks.responses.ClientResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientRepository {

    @GET(Urls.CLIENTS_URL)
    Observable<List<ClientResponseModel>> getClients(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.CLIENT_URL)
    Observable<ClientResponseModel> getClient(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("clientId") Long clientId);

    @POST(Urls.CLIENT_URL)
    Observable<ClientResponseModel> saveClient(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body ClientRequestModel clientRequestModel);
}
