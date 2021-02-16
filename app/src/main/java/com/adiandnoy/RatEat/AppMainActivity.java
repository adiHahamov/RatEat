package com.adiandnoy.RatEat;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppMainActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        BottomNavigationView bottomNavigation = (BottomNavigationView)findViewById(R.id.button_nav);
        navController = Navigation.findNavController(this, R.id.nav_fragment);

        NavigationUI.setupWithNavController(bottomNavigation, navController);

    }

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
        return NavigationUI.onNavDestinationSelected(item, navController);
    }


}