package design.abdelhak.kahrakib.fragments.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.networks.responses.DirectionResponseModel;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.SharedPreferencesUtil;

public class
ParametreFragment extends Fragment {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbParametre;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private TextInputLayout mTilLangue;
    private Switch mSwitchSession;
    /*-------------------------------------------------------------------*/

    public ParametreFragment() {
        // Required empty public constructor
    }

    public static ParametreFragment newInstance(Bundle data) {
        ParametreFragment parametreFragment = new ParametreFragment();
        parametreFragment.setArguments(data);
        return parametreFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parametre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbParametre = view.findViewById(R.id.mtb_fragment_parametre);
        mComponentLoading = view.findViewById(R.id.pb_fragment_parametre_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_parametre_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_parametre_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_parametre_sub_container);
        mTilLangue = view.findViewById(R.id.til_fragment_parametre_Langue);
        mSwitchSession = view.findViewById(R.id.s_fragment_parametre_session);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        if (SharedPreferencesUtil.getParametreSession(getContext())){
            mSwitchSession.setChecked(true);
        }else {
            mSwitchSession.setChecked(false);
        }
        setUpActionBar();
        setUpActvForLangue();


        mSwitchSession.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.saveParametreSession(getContext(),isChecked);
            }
        });
    }






    private void setUpActvForLangue() {
        String[] roles = {"français","عربي"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, roles);
        MaterialAutoCompleteTextView mMACTV = (MaterialAutoCompleteTextView) mTilLangue.getEditText();
        mMACTV.setAdapter(adapter);
    }

    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mMtbParametre);

        //call listener
        mMtbParametre.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }


    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }
}