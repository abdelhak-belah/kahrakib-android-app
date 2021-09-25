package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.RegisterRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.UtilisateurResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UtilisateurRepository {

    @GET(Urls.UTILISATEURS_URL)
    Observable<List<UtilisateurResponseModel>> getUtlisateurs(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.UTILISATEUR_URL)
    Observable<UtilisateurResponseModel> getUtilisateur(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("utilisateurId") long utilisateurId);

    @POST(Urls.UTILISATEUR_URL)
    Observable<UtilisateurResponseModel> saveUtilisateur(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body RegisterRequestModel registerRequestModel);

    @PUT(Urls.UTILISATEUR_URL)
    Observable<UtilisateurResponseModel> updateUtilisateur(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("utilisateurId") Long utilisateurId,@Body UtilisateurResponseModel utilisateurResponseModel);

    @DELETE(Urls.UTILISATEUR_URL)
    Observable<DeleteResponseModel> deleteUtilisateur(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("utilisateurId") Long utilisateurId);

}
