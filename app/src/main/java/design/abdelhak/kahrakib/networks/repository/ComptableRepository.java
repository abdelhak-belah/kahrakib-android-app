package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.ComptableRequestModel;
import design.abdelhak.kahrakib.networks.responses.AdministrateurResponseModel;
import design.abdelhak.kahrakib.networks.responses.ComptableResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ComptableRepository {

    @GET(Urls.COMPTABLES_URL)
    Observable<List<ComptableResponseModel>> getComptables(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.COMPTABLE_URL)
    Observable<ComptableResponseModel> getComptable(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("comptableId") Long comptableId);

    @POST(Urls.COMPTABLE_URL)
    Observable<ComptableResponseModel> saveComptable(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body ComptableRequestModel comptableRequestModel);
}
