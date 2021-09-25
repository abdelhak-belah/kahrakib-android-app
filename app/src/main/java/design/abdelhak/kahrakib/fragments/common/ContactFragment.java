package design.abdelhak.kahrakib.fragments.common;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.appbar.MaterialToolbar;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.utils.NavigationUtil;


public class ContactFragment extends Fragment implements View.OnClickListener {

    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbContact;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private LinearLayout mLlAdresse, mLlTelephone, mLlEmail, mLlRequete;

    /*-------------------------------------------------------------------*/

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance(Bundle data) {
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(data);
        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbContact = view.findViewById(R.id.mtb_fragment_password);
        mComponentLoading = view.findViewById(R.id.pb_fragment_contact_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_contact_component_error);
        mClContainer = view.findViewById(R.id.cl_fragment_contact_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_contact_sub_container);
        mLlAdresse = view.findViewById(R.id.ll_fragment_contact_adresse);
        mLlTelephone = view.findViewById(R.id.ll_fragment_contact_telephone);
        mLlEmail = view.findViewById(R.id.ll_fragment_contact_email);
        mLlRequete = view.findViewById(R.id.ll_fragment_contact_requete);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();

        mLlAdresse.setOnClickListener(this);
        mLlTelephone.setOnClickListener(this);
        mLlEmail.setOnClickListener(this);
        mLlRequete.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == mLlAdresse) {
            Uri uri = Uri.parse("https://www.google.com/maps/place/Kahrakib/@36.7672308,3.0486492,20z/data=!4m5!3m4!1s0x128fb25cda2c3c13:0xbb801f582f98b3f5!8m2!3d36.7674325!4d3.048491!5m1!1e4?hl=fr");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        if (v == mLlTelephone) {
            Uri uri = Uri.parse("tel:" + getString(R.string.fragment_contact_telephone));
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(dialIntent);
        }
        if (v == mLlEmail) {
            Uri uri = Uri.fromParts("mailto", getString(R.string.fragment_contact_email), null);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(emailIntent);
        }
        if (v == mLlRequete) {
            NavigationUtil.navigateToRequeteFragment(getContext(),null);
        }
    }


    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mMtbContact);

        //call listener
        mMtbContact.setNavigationOnClickListener(new View.OnClickListener() {
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