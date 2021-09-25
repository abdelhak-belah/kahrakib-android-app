package design.abdelhak.kahrakib.networks.repository;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.requests.ElementRequestModel;
import design.abdelhak.kahrakib.networks.responses.DeleteResponseModel;
import design.abdelhak.kahrakib.networks.responses.ElementResponseModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ElementRepository {

    @GET(Urls.ELEMENTS_URL)
    Observable<List<ElementResponseModel>> getElements(@Header(NetworkKeys.AUTHORIZATION_KEY) String token);

    @GET(Urls.ELEMENT_URL)
    Observable<ElementResponseModel> getElement(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("elementId") Long elementId);

    @POST(Urls.ELEMENT_URL)
    Observable<ElementResponseModel> saveElement(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Body ElementRequestModel elementRequestModel);

    @PUT(Urls.ELEMENT_URL)
    Observable<ElementResponseModel> updateElement(@Header(NetworkKeys.AUTHORIZATION_KEY) String token,@Query("elementId") Long elementId,@Body ElementResponseModel elementResponseModel);

    @DELETE(Urls.ELEMENT_URL)
    Observable<DeleteResponseModel> deleteElement(@Header(NetworkKeys.AUTHORIZATION_KEY) String token, @Query("elementId") Long elementId);
}
