package com.interview.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.interview.myapplication.R;
import com.preference.PowerPreference;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    float dX;
    float dY;
    int lastAction;

    FloatingActionButton fab;
    String TAG = "lastlocation";

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);




        fab.setX((float) PowerPreference.getDefaultFile().getFloat("key"));
        fab.setY((float)PowerPreference.getDefaultFile().getFloat("key1"));
        Log.d(TAG, "onCreateView: "+ PowerPreference.getDefaultFile().getFloat("key")
                +" "+ PowerPreference.getDefaultFile().getFloat("1key"));



        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();

                        Log.d(TAG, "ACTION_DOWN: "+dX+" "+dY+" "+view.getX()+" "+view.getY());
                        PowerPreference.getDefaultFile().putFloat("key",view.getX());
                        PowerPreference.getDefaultFile().putFloat("key1",view.getY());

                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        Log.d(TAG, "ACTION_MOVE: "+dX+" "+dY);
                        PowerPreference.getDefaultFile().putFloat("key",view.getX());
                        PowerPreference.getDefaultFile().putFloat("key1",view.getY());
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    
                    default:
                        return false;
                }
                return true;
            }
        });
        return root;
    }
}