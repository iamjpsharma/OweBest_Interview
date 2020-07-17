package com.interview.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    float dX;
    float dY;
    int lastAction;

    FloatingActionButton fab;
    String TAG = "lastlocation";

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);

        fab.setX((float) PowerPreference.getDefaultFile().getFloat("key2"));
        fab.setY((float)PowerPreference.getDefaultFile().getFloat("key3"));

        fab.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        Log.d(TAG, "ACTION_DOWN: "+dX+" "+dY);

                        Log.d(TAG, "ACTION_DOWN: "+dX+" "+dY+" "+view.getX()+" "+view.getY());
                        PowerPreference.getDefaultFile().putFloat("key",view.getX());
                        PowerPreference.getDefaultFile().putFloat("key1",view.getY());

                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        Log.d(TAG, "ACTION_MOVE: "+dX+" "+dY);

                        PowerPreference.getDefaultFile().putFloat("key2",view.getX());
                        PowerPreference.getDefaultFile().putFloat("key3",view.getY());
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