package design.abdelhak.kahrakib.networks.repository;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.AuthRequestModel;
import design.abdelhak.kahrakib.networks.requests.MotDePasseRequest;
import design.abdelhak.kahrakib.networks.responses.AuthResponseModel;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AuthRepository {

    @POST(Urls.LOGIN_URL)
    Observable<AuthResponseModel> userAuth(@Body AuthRequestModel authRequestModel);

    @PUT(Urls.MOT_DE_PASSE_URL)
    Observable<UtilisateurResponseModel> updateMotDePasse(@Body MotDePasseRequest motDePasseRequest);
}
