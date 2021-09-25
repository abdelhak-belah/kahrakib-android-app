package design.abdelhak.kahrakib.fragments.cassier;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.networks.responses.CassierResponseModel;
import design.abdelhak.kahrakib.networks.service.CassierService;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CassierFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{

    /*-------------------------------------------------------------------*/
    private BottomNavigationView mBnvCassier;
    private MaterialToolbar mMtbCassier;

    Bundle mBundleData = new Bundle();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private String currentFragment = FragmentTagKeys.DPS_RECU_FRAGMENT_KEY;
    /*-------------------------------------------------------------------*/

    public CassierFragment() {
        // Required empty public constructor
    }

    public static CassierFragment newInstance(Bundle data) {
        CassierFragment cassierFragment = new CassierFragment();
        cassierFragment.setArguments(data);
        return cassierFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cassier, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (currentFragment){
            case FragmentTagKeys.DPS_RECU_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_cassier_dps);
                NavigationUtil.navigateToDpsRecuFragment(getContext(),mBundleData);
                break;
            case FragmentTagKeys.EDC_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_cassier_edc);
                NavigationUtil.navigateToEdcFromCassierFragment(getContext(),mBundleData);
                break;
        }

        /*----------------------------inflate-------------------------------*/
        mBnvCassier = view.findViewById(R.id.bnv_fragment_cassier);
        mMtbCassier = view.findViewById(R.id.tb_fragment_cassier);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();

        //call listener
        mBnvCassier.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_ab, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_ab_profile);
        View v = menuItem.getActionView();

        FrameLayout flAvatar = v.findViewById(R.id.menu_ab_profile);

        flAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.navigateToProfileFragment(getContext(), null);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ab_notification:
                Toast.makeText(getContext(), "Fonctionnalité en cours de développement", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bnv_cassier_dps:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToDpsRecuFragment(getContext(),mBundleData);
                currentFragment = FragmentTagKeys.DPS_RECU_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_cassier_edc:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromCassierFragment(getContext(),mBundleData);
                currentFragment = FragmentTagKeys.EDC_FRAGMENT_KEY;
                return true;
        }
        return true;
    }


    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbCassier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }

    @Override
    public void onDestroy() {
        if (disposable != null){
            disposable.clear();
        }
        super.onDestroy();
    }
}