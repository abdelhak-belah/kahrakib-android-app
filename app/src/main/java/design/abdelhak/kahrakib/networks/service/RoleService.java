package design.abdelhak.kahrakib.networks.service;

import android.content.Context;

import java.util.List;

import design.abdelhak.kahrakib.keys.NetworkKeys;
import design.abdelhak.kahrakib.networks.Urls;
import design.abdelhak.kahrakib.networks.repository.RoleRepository;
import design.abdelhak.kahrakib.networks.repository.UtilisateurRepository;
import design.abdelhak.kahrakib.networks.responses.RoleResponseModel;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoleService {

    private static RoleService roleService;
    private final RoleRepository roleRepository;

    private RoleService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        roleRepository = retrofit.create(RoleRepository.class);
    }

    public static RoleService newInstance() {
        if (roleService == null) {
            roleService = new RoleService();
        }
        return roleService;
    }

    public Observable<List<RoleResponseModel>> getRoles(Context context){
        String utilisateurBearerToken = NetworkKeys.BEARER_KEY + SharedPreferencesUtil.getUserToken(context);
        return roleRepository.getRoles(utilisateurBearerToken);
    }
}
