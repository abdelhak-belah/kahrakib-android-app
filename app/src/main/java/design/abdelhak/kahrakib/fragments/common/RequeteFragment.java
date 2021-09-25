package design.abdelhak.kahrakib.fragments.common;

import android.content.Intent;
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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.utils.NavigationUtil;
import design.abdelhak.kahrakib.utils.ValidatorUtil;

public class RequeteFragment extends Fragment implements View.OnClickListener {


    /*-------------------------------------------------------------------*/
    private MaterialToolbar mMtbRequete;
    private MaterialButton mMBtnEnvoyerRequete;
    private TextInputLayout mTilType;
    private EditText mEtMessage;
    private ProgressBar mComponentLoading;
    private ConstraintLayout mComponentError;
    private ConstraintLayout mComponentSucces;
    private CoordinatorLayout mClContainer;
    private NestedScrollView mNsvSubContainer;
    /*-------------------------------------------------------------------*/

    public RequeteFragment() {
        // Required empty public constructor
    }


    public static RequeteFragment newInstance(Bundle data) {
        RequeteFragment fragment = new RequeteFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*----------------------------inflate-------------------------------*/
        mMtbRequete = view.findViewById(R.id.mtb_fragment_requete);
        mComponentLoading = view.findViewById(R.id.pb_fragment_requete_component_loading);
        mComponentError = view.findViewById(R.id.rl_fragment_requete_component_error);
        mComponentSucces = view.findViewById(R.id.rl_fragment_requete_component_succes);
        mClContainer = view.findViewById(R.id.cl_fragment_requete_container);
        mNsvSubContainer = view.findViewById(R.id.nsv_fragment_requete_sub_container);
        mTilType = view.findViewById(R.id.til_fragment_requete_type);
        mEtMessage = view.findViewById(R.id.et_fragment_requete_message);
        mMBtnEnvoyerRequete = view.findViewById(R.id.mbtn_fragment_requete_envoyer);
        /*-------------------------------------------------------------------*/

        /*Preload function*/
        setUpActionBar();
        setUpActvForType();
        mMBtnEnvoyerRequete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mMBtnEnvoyerRequete){
            envoyerRequete();
        }
    }

    private void envoyerRequete() {
        String subject = mTilType.getEditText().getText().toString().trim();
        String message = mEtMessage.getText().toString().trim();

        if (checkTypeField(subject) & checkMessageField(message)){
            /* Create the Intent */
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"contact-kahrakib@kahrakib.dz"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mTilType.getEditText().getText().toString().trim());
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mEtMessage.getText().toString().trim());

            /* Send it off to the Activity-Chooser */
            getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }else {
            return;
        }
    }


    private void setUpActvForType() {
        String[] roles = {"Ajouter Un Element"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.component_list, roles);
        AutoCompleteTextView mMactv = (AutoCompleteTextView) mTilType.getEditText();
        mMactv.setAdapter(adapter);
    }


    private void setUpActionBar() {
        //support action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mMtbRequete);

        //call listener
        mMtbRequete.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.back(getContext());
            }
        });
    }


    private Boolean checkTypeField(String type) {
        if (type.isEmpty()) {
            mTilType.setErrorEnabled(true);
            mTilType.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mTilType.setErrorEnabled(false);
            mTilType.setError("");
            return true;
        }
    }

    private Boolean checkMessageField(String message) {
        if (message.isEmpty()) {
            mEtMessage.setError(getString(R.string.message_obligatoire_non_vide));
            return false;
        } else {
            mEtMessage.setError("");
            return true;
        }
    }


    private void showLayout() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.VISIBLE);
        mComponentLoading.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
    }

    private void showError() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.VISIBLE);
    }

    private void showSucces() {
        mComponentLoading.setVisibility(View.GONE);
        mClContainer.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mClContainer.setVisibility(View.VISIBLE);
        mNsvSubContainer.setVisibility(View.GONE);
        mComponentError.setVisibility(View.GONE);
        mComponentSucces.setVisibility(View.GONE);
        mComponentLoading.setVisibility(View.VISIBLE);
    }
}