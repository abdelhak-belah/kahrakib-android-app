package design.abdelhak.kahrakib.fragments.comptable;

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
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.utils.NavigationUtil;


public class ComptableFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{

    /*-------------------------------------------------------------------*/
    private BottomNavigationView mBnvComptable;
    private MaterialToolbar mMtbComptable;

    private Bundle mBundleData = new Bundle();
    private String currentFragment = FragmentTagKeys.EDC_FRAGMENT_KEY;
    /*-------------------------------------------------------------------*/

    public ComptableFragment() {
        // Required empty public constructor
    }


    public static ComptableFragment newInstance(Bundle data) {
        ComptableFragment comptableFragment = new ComptableFragment();
        comptableFragment.setArguments(data);
        return comptableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comptable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (currentFragment){
            case FragmentTagKeys.EDC_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_comptable_edc);
                NavigationUtil.navigateToEdcFromComptableFragment(getContext(),mBundleData);
                break;
            case FragmentTagKeys.EDC_VALIDE_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_comptable_valide);
                NavigationUtil.navigateToEdcFromComptableFragment(getContext(),mBundleData);
                break;
            case FragmentTagKeys.EDC_EN_ATTENTE_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_comptable_en_attente);
                NavigationUtil.navigateToEdcFromComptableFragment(getContext(),mBundleData);
                break;
        }

        /*----------------------------inflate-------------------------------*/
        mBnvComptable = view.findViewById(R.id.bnv_fragment_comptable);
        mMtbComptable = view.findViewById(R.id.tb_fragment_comptable);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();

        //call listener
        mBnvComptable.setOnNavigationItemSelectedListener(this);
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
            case R.id.menu_bnv_comptable_edc:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromComptableFragment(getContext(),mBundleData);
                currentFragment = FragmentTagKeys.EDC_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_comptable_valide:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromComptableFragment(getContext(),mBundleData);
                currentFragment = FragmentTagKeys.EDC_VALIDE_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_comptable_en_attente:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,item.getItemId());
                NavigationUtil.navigateToEdcFromComptableFragment(getContext(),mBundleData);
                currentFragment = FragmentTagKeys.EDC_EN_ATTENTE_FRAGMENT_KEY;
                return true;
        }
        return true;
    }


    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbComptable);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }
}