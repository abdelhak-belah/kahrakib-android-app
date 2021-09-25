package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.responses.RoleResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RoleRepository {

    @GET(Urls.ROLES_URL)
    Observable<List<RoleResponseModel>> getRoles(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);
}
