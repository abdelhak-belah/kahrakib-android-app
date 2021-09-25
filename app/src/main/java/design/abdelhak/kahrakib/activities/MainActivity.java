package design.abdelhak.kahrakib.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.utils.NavigationUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Kahrakib);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            NavigationUtil.navigateToAuthFragment(this,null);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //Toast.makeText(getBaseContext(), "Entery : "+getSupportFragmentManager().getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*-----------------Handle on back pressed-----------------*/

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            super.finish();
        }else {
            super.onBackPressed();
        }

    }



}