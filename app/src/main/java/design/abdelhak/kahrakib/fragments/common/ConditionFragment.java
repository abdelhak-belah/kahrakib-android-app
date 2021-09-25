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
import android.widget.ProgressBar;

import com.google.android.material.appbar.MaterialToolbar;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.utils.NavigationUtil;


public class ConditionFragment extends Fragment {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbTerms;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    /*-------------------------------------------------------------------*/

    public ConditionFragment() {
        // Required empty public constructor
    }

    public static ConditionFragment newInstance(Bundle data) {
        ConditionFragment conditionFragment = new ConditionFragment();
        conditionFragment.setArguments(data);
        return conditionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_condition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbTerms = view.findViewById(R.id.mtb_fragment_password);
        mComponentLoading = view.findViewById(R.id.pb_fragment_pass_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_pass_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_pass_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_pass_sub_container);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();

    }

    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mMtbTerms);

        //call listener
        mMtbTerms.setNavigationOnClickListener(new View.OnClickListener() {
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