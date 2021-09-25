package design.abdelhak.kahrakib.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import design.abdelhak.kahrakib.R;
import design.abdelhak.kahrakib.fragments.common.ContactFragment;
import design.abdelhak.kahrakib.fragments.common.InformationFragment;
import design.abdelhak.kahrakib.fragments.common.ConditionFragment;
import design.abdelhak.kahrakib.fragments.common.PassFragment;
import design.abdelhak.kahrakib.fragments.common.ParametreFragment;
import design.abdelhak.kahrakib.fragments.common.RequeteFragment;
import design.abdelhak.kahrakib.fragments.admin.AjouterChantierFragment;
import design.abdelhak.kahrakib.fragments.admin.AjouterElementFragment;
import design.abdelhak.kahrakib.fragments.admin.AjouterUserFragment;
import design.abdelhak.kahrakib.fragments.admin.ChantierFragment;
import design.abdelhak.kahrakib.fragments.admin.ElementFragment;
import design.abdelhak.kahrakib.fragments.admin.RechercheFragment;
import design.abdelhak.kahrakib.fragments.common.DpsRecuFragment;
import design.abdelhak.kahrakib.fragments.common.EdcDetailsFragment;
import design.abdelhak.kahrakib.fragments.common.EdcFragment;
import design.abdelhak.kahrakib.fragments.client.AjouterAchatsFragment;
import design.abdelhak.kahrakib.fragments.client.AjouterDpsFragment;
import design.abdelhak.kahrakib.fragments.common.DpsFragment;
import design.abdelhak.kahrakib.fragments.common.DpsDetailsFragment;
import design.abdelhak.kahrakib.fragments.comptable.ComptableFragment;
import design.abdelhak.kahrakib.fragments.admin.AdminFragment;
import design.abdelhak.kahrakib.fragments.admin.UsersFragment;
import design.abdelhak.kahrakib.fragments.cassier.CassierFragment;
import design.abdelhak.kahrakib.fragments.cassier_respo.CassierRespoFragment;
import design.abdelhak.kahrakib.fragments.common.AuthFragment;
import design.abdelhak.kahrakib.fragments.common.ProfileFragment;
import design.abdelhak.kahrakib.fragments.client.ClientFragment;
import design.abdelhak.kahrakib.keys.FragmentTagKeys;

public class NavigationUtil {

    /*-----------------------------variables-----------------------------*/
    public static final Integer MAIN_ACTIVITY_CONTAINER = R.id.fcv_activity_main;
    public static final Integer ADMIN_FRAGMENT_CONTAINER = R.id.fcv_fragment_admin;
    public static final Integer CLIENT_FRAGMENT_CONTAINER = R.id.fcv_fragment_client;
    public static final Integer CASSIER_FRAGMENT_CONTAINER = R.id.fcv_fragment_cassier;
    public static final Integer CASSIER_RESPO_FRAGMENT_CONTAINER = R.id.fcv_fragment_cassier_respo;
    public static final Integer COMPTABLE_FRAGMENT_CONTAINER = R.id.fcv_fragment_comptable;
    /*-------------------------------------------------------------------*/


    public static void navigateToAuthFragment(Context context, Bundle data) {
        AuthFragment authFragment = AuthFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, authFragment)
                .commit();
    }

    public static void navigateToAdminFragment(Context context, Bundle data) {
        AdminFragment adminFragment = AdminFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, adminFragment)
                .addToBackStack(FragmentTagKeys.HOME_FRAGMENT_KEY)
                .commit();
    }

    public static void navigateToClientFragment(Context context, Bundle data) {
        ClientFragment clientFragment = ClientFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, clientFragment)
                .addToBackStack(FragmentTagKeys.HOME_FRAGMENT_KEY)
                .commit();
    }

    public static void navigateToCassierFragment(Context context, Bundle data) {
        CassierFragment cassierFragment = CassierFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, cassierFragment)
                .addToBackStack(FragmentTagKeys.HOME_FRAGMENT_KEY)
                .commit();
    }

    public static void navigateToCassierRespo(Context context, Bundle data) {
        CassierRespoFragment cassierRespoFragment = CassierRespoFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, cassierRespoFragment)
                .addToBackStack(FragmentTagKeys.HOME_FRAGMENT_KEY)
                .commit();
    }

    public static void navigateToComptable(Context context, Bundle data) {
        ComptableFragment comptableFragment = ComptableFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, comptableFragment)
                .addToBackStack(FragmentTagKeys.HOME_FRAGMENT_KEY)
                .commit();
    }


    public static void navigateToProfileFragment(Context context, Bundle data) {
        ProfileFragment profileFragment = ProfileFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, profileFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToInformationFragment(Context context, Bundle data) {
        InformationFragment informationFragment = InformationFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, informationFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToConditionFragment(Context context, Bundle data) {
        ConditionFragment conditionFragment = ConditionFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, conditionFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToParametreFragment(Context context, Bundle data) {
        ParametreFragment parametreFragment = ParametreFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, parametreFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToPassFragment(Context context, Bundle data) {
        PassFragment passFragment = PassFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, passFragment)
                .addToBackStack(null)
                .commit();
    }


    public static void navigateToContactFragment(Context context, Bundle data) {
        ContactFragment contactFragment = ContactFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, contactFragment)
                .addToBackStack(null)
                .commit();
    }


    public static void navigateToUsersFragment(Context context, Bundle data) {
        UsersFragment contactFragment = UsersFragment.newInstance(data);

        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(ADMIN_FRAGMENT_CONTAINER, contactFragment)
                .commit();
    }

    public static void navigateToChantierFragment(Context context, Bundle data) {
        ChantierFragment chantierFragment = ChantierFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(ADMIN_FRAGMENT_CONTAINER, chantierFragment)
                .commit();
    }

    public static void navigateToElementFragment(Context context, Bundle data) {
        ElementFragment elementFragment = ElementFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(ADMIN_FRAGMENT_CONTAINER, elementFragment)
                .commit();
    }

    public static void navigateToRechercheFragment(Context context, Bundle data) {
        RechercheFragment rechercheFragment = RechercheFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(ADMIN_FRAGMENT_CONTAINER, rechercheFragment)
                .commit();
    }

    public static void navigateToAjouterUserFragment(Context context, Bundle data) {
        AjouterUserFragment ajouterUserFragment = AjouterUserFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(MAIN_ACTIVITY_CONTAINER, ajouterUserFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToAjouterChantierFragment(Context context, Bundle data) {
        AjouterChantierFragment ajouterChantierFragment = AjouterChantierFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(MAIN_ACTIVITY_CONTAINER, ajouterChantierFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToAjouterElementFragment(Context context, Bundle data) {
        AjouterElementFragment ajouterElementFragment = AjouterElementFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(MAIN_ACTIVITY_CONTAINER, ajouterElementFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToRequeteFragment(Context context, Bundle data) {
        RequeteFragment requeteFragment = RequeteFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(MAIN_ACTIVITY_CONTAINER, requeteFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToDpsFragment(Context context, Bundle data) {
        DpsFragment dpsFragment = DpsFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(CLIENT_FRAGMENT_CONTAINER, dpsFragment)
                    .commit();
    }

    public static void navigateToDpsRecuFragment(Context context, Bundle data) {
        DpsRecuFragment dpsRecuFragment = DpsRecuFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(CASSIER_FRAGMENT_CONTAINER, dpsRecuFragment)
                .commit();
    }

    public static void navigateToEdcFromCassierFragment(Context context, Bundle data) {
        EdcFragment edcFragment = EdcFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(CASSIER_FRAGMENT_CONTAINER, edcFragment)
                .commit();
    }

    public static void navigateToEdcFromCassierRespoFragment(Context context, Bundle data) {
        EdcFragment edcFragment = EdcFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(CASSIER_RESPO_FRAGMENT_CONTAINER, edcFragment)
                .commit();
    }

    public static void navigateToEdcFromComptableFragment(Context context, Bundle data) {
        EdcFragment edcFragment = EdcFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(COMPTABLE_FRAGMENT_CONTAINER, edcFragment)
                .commit();
    }


    public static void navigateToAjouterDpsFragment(Context context, Bundle data) {
        AjouterDpsFragment ajouterDpsFragment = AjouterDpsFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, ajouterDpsFragment)
                .addToBackStack(null)
                .commit();
    }


    public static void navigateToAjouterAchatsFragment(Context context, Bundle data) {
        AjouterAchatsFragment ajouterAchatsFragment = AjouterAchatsFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, ajouterAchatsFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToDpsDetailsFragment(Context context, Bundle data) {
        DpsDetailsFragment dpsDetailsFragment = DpsDetailsFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, dpsDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void navigateToEdcDetailsFragment(Context context, Bundle data) {
        EdcDetailsFragment edcDetailsFragment = EdcDetailsFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, edcDetailsFragment)
                .addToBackStack(null)
                .commit();
    }


    /*------------------------Navigation Tools-------------------------*/
    public static void logoutAndNavigateToAuthFragment(Context context, Bundle data) {
        AuthFragment authFragment = AuthFragment.newInstance(data);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(MAIN_ACTIVITY_CONTAINER, authFragment)
                .commit();
    }

    public static void back(Context context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    public static void popUntil(Context context, String fragmentTagKey) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStack(fragmentTagKey, 0);
    }
    /*------------------------------------------------------------------*/
}
