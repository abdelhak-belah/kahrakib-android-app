package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.ChantierRequestModel;
import design.abdelhak.kahrakib.networks.responses.ChantierResponseModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ChantierRepository {

    @GET(Urls.CHANTIERS_URL)
    Observable<List<ChantierResponseModel>> getChantiers(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.CHANTIER_URL)
    Observable<ChantierResponseModel> getChantier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("chantierId") Long chantierId);

    @POST(Urls.CHANTIER_URL)
    Observable<ChantierResponseModel> saveChantier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body ChantierRequestModel chantierRequestModel);

    @PUT(Urls.CHANTIER_URL)
    Observable<ChantierResponseModel> updateChantier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("chantierId") Long chantierId,@Body ChantierRequestModel chantierRequestModel);

    @DELETE(Urls.CHANTIER_URL)
    Observable<DeleteResponseModel> deleteChantier(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("chantierId") Long chantierId);
}
