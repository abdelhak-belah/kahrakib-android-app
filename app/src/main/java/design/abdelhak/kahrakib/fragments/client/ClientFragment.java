package design.abdelhak.kahrakib.fragments.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.BundleKeys;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class ClientFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private BottomNavigationView mBnvClient;
    private MaterialToolbar mMtbClient;
    private FloatingActionButton mFabAjouterDps;

    private Bundle mBundleData = new Bundle();
    private boolean isSended = false;
    private String currentFragment = FragmentTagKeys.DPS_FRAGMENT_KEY;
    /*-------------------------------------------------------------------*/


    public ClientFragment() {
        // Required empty public constructor
    }


    public static ClientFragment newInstance(Bundle data) {
        ClientFragment clientFragment = new ClientFragment();
        clientFragment.setArguments(data);
        return clientFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (currentFragment){
            case FragmentTagKeys.DPS_FRAGMENT_KEY:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_client_dps);
                NavigationUtil.navigateToDpsFragment(getContext(), mBundleData);
                break;
            case FragmentTagKeys.DPS_VALIDE_FRAGMENT_KEY:
                isSended = true;
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_client_dps_valide);
                NavigationUtil.navigateToDpsFragment(getContext(), mBundleData);
                break;
        }


        /*----------------------------inflate-------------------------------*/
        mBnvClient = view.findViewById(R.id.bnv_fragment_client);
        mMtbClient = view.findViewById(R.id.tb_fragment_client);
        mFabAjouterDps = view.findViewById(R.id.fab_fragment_client_ajouter_dps);
        /*-------------------------------------------------------------------*/

        //support action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbClient);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        //call listener
        mBnvClient.setOnNavigationItemSelectedListener(this);
        mFabAjouterDps.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == mFabAjouterDps){
            NavigationUtil.navigateToAjouterDpsFragment(getContext(),null);
        }
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
            case R.id.menu_bnv_client_dps:
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_client_dps);
                NavigationUtil.navigateToDpsFragment(getContext(), mBundleData);
                currentFragment = FragmentTagKeys.DPS_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_client_dps_valide:
                isSended = true;
                mBundleData.putInt(BundleKeys.LAUNCHER_KEY,R.id.menu_bnv_client_dps_valide);
                NavigationUtil.navigateToDpsFragment(getContext(), mBundleData);
                currentFragment = FragmentTagKeys.DPS_VALIDE_FRAGMENT_KEY;
                return true;
        }
        return true;
    }
}