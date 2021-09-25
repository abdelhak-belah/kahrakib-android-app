package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.CassierRespoRequestModel;
import design.abdelhak.kahrakib.networks.responses.CassierRespoResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CassierRespoRepository {

    @GET(Urls.CASSIERS_RESPO_URL)
    Observable<List<CassierRespoResponseModel>> getCassiersRespo(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.CASSIER_RESPO_URL)
    Observable<CassierRespoResponseModel> getCassierRespo(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("cassierRespoId") Long cassierRespoId);

    @POST(Urls.CASSIER_RESPO_URL)
    Observable<CassierRespoResponseModel> saveCassierRespo(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body CassierRespoRequestModel cassierRespoRequestModel);
}
