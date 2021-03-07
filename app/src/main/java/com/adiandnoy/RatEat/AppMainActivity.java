package com.adiandnoy.RatEat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.adiandnoy.RatEat.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class AppMainActivity extends AppCompatActivity {

    NavController navController;
    private static final int RC_SIGN_IN = 123;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

//        ProfileFragment profile_fragment = new ProfileFragment();
//        FragmentManager fm = getSupportFragmentManager();
//
//        fm.beginTransaction().add(R.id.nav_fragment, profile_fragment).commit();


        BottomNavigationView bottomNavigation = (BottomNavigationView)findViewById(R.id.button_nav);
        navController = Navigation.findNavController(this, R.id.nav_fragment);

        //If the user is login navigate to all dishes view
        if(currentUser != null) {
            navController.navigate(R.id.studentListFragment);
        }

        NavigationUI.setupWithNavController(bottomNavigation, navController);

    }

//    public void createSignInIntent() {
//        // [START auth_fui_create_intent]
//        // Choose authentication providers
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build());
//
//        // Create and launch sign-in intent
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .build(),
//                RC_SIGN_IN);
//        // [END auth_fui_create_intent]
//    }

//    // [START auth_fui_result]
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//
//            if (resultCode == RESULT_OK) {
//                // Successfully signed in
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                // ...
//            } else {
//                // Sign in failed. If response is null the user canceled the
//                // sign-in flow using the back button. Otherwise check
//                // response.getError().getErrorCode() and handle the error.
//                // ...
//            }
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.signOutBtn){
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            if(user == null){
//                Toast.makeText(this, "Not allowed! sign in first", Toast.LENGTH_LONG).show();
//            }
//            else{
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(this, "User signed out", Toast.LENGTH_LONG).show();
//                navController.navigate(R.id.goBackToSignIn);
//            }
//        }
        if(item.getItemId() == R.id.signOutBotton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user == null){
                Toast.makeText(this, "Sign in first", Toast.LENGTH_LONG).show();
            }
            else{
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "User signed out", Toast.LENGTH_LONG).show();
                navController.navigate(R.id.tabBarFragment);
            }
        }

        return NavigationUI.onNavDestinationSelected(item, navController);
    }



}