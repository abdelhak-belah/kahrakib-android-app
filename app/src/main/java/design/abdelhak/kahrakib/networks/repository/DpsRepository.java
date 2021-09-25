package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.DpsRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatCassierRequestModel;
import design.abdelhak.kahrakib.networks.requests.EtatClientRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DpsRepository {

    @GET(Urls.DPSS_URL)
    Observable<List<DpsResponseModel>> getDpss(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.DPS_URL)
    Observable<DpsResponseModel> getDps(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("dpsId") Long dpsId);

    @POST(Urls.DPS_URL)
    Observable<DpsResponseModel> saveDps(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body DpsRequestModel dpsRequestModel);

    @PUT(Urls.DPS_ETAT_CLIENT_URL)
    Observable<DpsResponseModel> updateClientEtat(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("dpsId") Long dpsId, @Body EtatClientRequestModel etatClientRequestModel);

    @PUT(Urls.DPS_ETAT_CASSIER_URL)
    Observable<DpsResponseModel> updateCassierEtat(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("dpsId") Long dpsId, @Body EtatCassierRequestModel etatCassierRequestModel);

    @DELETE(Urls.DPS_URL)
    Observable<DeleteResponseModel> deleteDps(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("dpsId") Long dpsId);
}
