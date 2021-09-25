package design.abdelhak.kahrakib.fragments.admin;

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

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class AdminFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    /*-------------------------------------------------------------------*/
    private BottomNavigationView mBnvAdmin;
    private MaterialToolbar mMtbAdmin;
    private String currentFragment = FragmentTagKeys.UTILISATEURS_FRAGMENT_KEY;
    /*-------------------------------------------------------------------*/

    public AdminFragment() {
        // Required empty public constructor
    }


    public static AdminFragment newInstance(Bundle data) {
        AdminFragment adminFragment = new AdminFragment();
        adminFragment.setArguments(data);
        return adminFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (currentFragment){
            case FragmentTagKeys.UTILISATEURS_FRAGMENT_KEY:
                NavigationUtil.navigateToUsersFragment(getContext(), null);
                break;
            case FragmentTagKeys.CHANTIERS_FRAGMENT_KEY:
                NavigationUtil.navigateToChantierFragment(getContext(),null);
                break;
            case FragmentTagKeys.ELEMENTS_FRAGMENT_KEY:
                NavigationUtil.navigateToElementFragment(getContext(),null);
                break;
            case FragmentTagKeys.RECHERCHE_FRAGMENT_KEY:
                NavigationUtil.navigateToRechercheFragment(getContext(),null);
                break;
        }




        /*----------------------------inflate-------------------------------*/
        mBnvAdmin = view.findViewById(R.id.bnv_fragment_admin);
        mMtbAdmin = view.findViewById(R.id.tb_fragment_admin);
        /*-------------------------------------------------------------------*/


        /*Preload function*/
        setUpActionBar();

        //call listener
        mBnvAdmin.setOnNavigationItemSelectedListener(this);

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
            case R.id.menu_bnv_admin_users:
                NavigationUtil.navigateToUsersFragment(getContext(), null);
                currentFragment = FragmentTagKeys.UTILISATEURS_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_admin_elements:
                NavigationUtil.navigateToElementFragment(getContext(), null);
                currentFragment = FragmentTagKeys.ELEMENTS_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_admin_chantier:
                NavigationUtil.navigateToChantierFragment(getContext(), null);
                currentFragment = FragmentTagKeys.CHANTIERS_FRAGMENT_KEY;
                return true;
            case R.id.menu_bnv_admin_search:
                NavigationUtil.navigateToRechercheFragment(getContext(), null);
                currentFragment = FragmentTagKeys.RECHERCHE_FRAGMENT_KEY;
                return true;
        }
        return true;
    }


    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbAdmin);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }

}