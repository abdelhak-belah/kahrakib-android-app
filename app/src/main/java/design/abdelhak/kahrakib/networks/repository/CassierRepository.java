package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.CassierRequestModel;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CassierRepository {

    @GET(Urls.CASSIERS_URL)
    Observable<List<CassierResponseModel>> getCassiers(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.CASSIER_URL)
    Observable<CassierResponseModel> getCassier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("cassierId") Long cassierId);

    @POST(Urls.CASSIER_URL)
    Observable<CassierResponseModel> saveCassier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body CassierRequestModel cassierRequestModel);

    @PUT(Urls.CASSIER_URL)
    Observable<CassierResponseModel> updateCassier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("cassierId") Long cassierId,@Body CassierResponseModel cassierResponseModel);
}
