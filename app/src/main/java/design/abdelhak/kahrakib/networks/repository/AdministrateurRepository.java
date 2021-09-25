package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.AdministrateurRequestModel;
import design.abdelhak.kahrakib.networks.responses.AdministrateurResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AdministrateurRepository {

    @GET(Urls.ADMINS_URL)
    Observable<List<AdministrateurResponseModel>> getAdministrateurs(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.ADMIN_URL)
    Observable<AdministrateurResponseModel> getAdministrateur(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("adminId") Long adminId);

    @POST(Urls.ADMIN_URL)
    Observable<AdministrateurResponseModel> saveAdminisatrateurs(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body AdministrateurRequestModel administrateurRequestModel);
}
