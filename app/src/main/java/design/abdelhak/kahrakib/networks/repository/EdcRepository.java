package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.EdsRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatEdsRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.EdsResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EdcRepository {

    @GET(Urls.EDSS_URL)
    Observable<List<EdsResponseModel>> getEdcs(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.EDS_URL)
    Observable<EdsResponseModel> getEdc(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("edsId") Long edsId);

    @POST(Urls.EDS_URL)
    Observable<EdsResponseModel> saveEdc(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body EdsRequestModel edsRequestModel);

    @PUT(Urls.EDS_ETAT_URL)
    Observable<EdsResponseModel> updateEdcEtat(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("edsId") Long edsId, @Body EtatEdsRequestModel etatEdsRequestModel);

    @DELETE(Urls.EDS_URL)
    Observable<DeleteResponseModel> deleteEdc(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("edsId") Long edsId);
}
