package design.abdelhak.kahrakib.fragments.cassier_respo;

import android.os.Bundle;

import androidx.annotation.BoolRes;
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
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class CassierRespoFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    /*-------------------------------------------------------------------*/
    private BottomNavigationView mBnvCassierRespo;
    private MaterialToolbar mMtbCassierRespo;

    private Bundle mBundleData = new Bundle();
    private String currentFragment = FragmentTagKeys.EDC_FRAGMENT_KEY;
    /*-------------------------------------------------------------------*/

    public CassierRespoFragment() {
        // Required empty public constructor
    }

    public static CassierRespoFragment newInstance(Bundle data) {
        CassierRespoFragment cassierRespoFragment = new CassierRespoFragment();
        cassierRespoFragment.setArguments(data);
        return cassierRespoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cassier_respo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (currentFragment){
            case FragmentTagKeys.EDC_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_cassier_respo_edc);
                NavigationUtil.navigateToEdcFromCassierRespoFragment(getContext(), mBundleData);
                break;
            case FragmentTagKeys.EDC_VALIDE_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_cassier_respo_valide);
                NavigationUtil.navigateToEdcFromCassierRespoFragment(getContext(), mBundleData);
                break;
            case FragmentTagKeys.EDC_EN_ATTENTE_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_cassier_respo_en_attente);
                NavigationUtil.navigateToEdcFromCassierRespoFragment(getContext(), mBundleData);
                break;
        }

        /*----------------------------inflate-------------------------------*/
        mBnvCassierRespo = view.findViewById(R.id.bnv_fragment_cassier_respo);
        mMtbCassierRespo = view.findViewById(R.id.tb_fragment_cassier_respo);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();

        //call listener
        mBnvCassierRespo.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
            case R.id.menu_bnv_cassier_respo_edc:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromCassierRespoFragment(getContext(), mBundleData);
                currentFragment = FragmentTagKeys.EDC_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_cassier_respo_en_attente:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromCassierRespoFragment(getContext(), mBundleData);
                currentFragment = FragmentTagKeys.EDC_EN_ATTENTE_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_cassier_respo_valide:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromCassierRespoFragment(getContext(), mBundleData);
                currentFragment = FragmentTagKeys.EDC_VALIDE_FRAGMENT_KEY;
                return true;
        }
        return true;
    }


    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbCassierRespo);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }
}