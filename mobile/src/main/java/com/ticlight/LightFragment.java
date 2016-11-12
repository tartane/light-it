package com.ticlight;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LightFragment extends Fragment {
    public static final String ARG_BLINK = "blink";
    private boolean mBlink;
    private boolean lightActivated = true;
    @Bind(R.id.layLight)
    FrameLayout layLight;
    Handler handler;
    final int delay = 400; //milliseconds
    OnLightChangeListener listener;
    public LightFragment() {
        // Required empty public constructor
    }

    public static LightFragment newInstance(boolean blink) {
        LightFragment fragment = new LightFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_BLINK, blink);
        fragment.setArguments(args);
        return fragment;
    }

    public void SetOnLightChangeListener(OnLightChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mBlink = getArguments().getBoolean(ARG_BLINK);
            /*
            if(mBlink) {

                handler = new Handler();

                handler.postDelayed(runnable, delay);
            }*/
        }
        return view;
    }
    public Runnable runnable = new Runnable() {
        public void run() {
            if (lightActivated) {
                layLight.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                lightActivated = false;
            } else {
                layLight.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                lightActivated = true;
            }
            if(listener != null) {
                listener.onLightChange(lightActivated);
            }
            handler.postDelayed(this, delay);
        }
    };
    @Override
    public void onResume() {
        if(mBlink) {
            if (handler == null)
                handler = new Handler();

            handler.postDelayed(runnable, delay);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(handler != null && runnable != null)
            handler.removeCallbacks(runnable);
        super.onPause();
    }

    public interface OnLightChangeListener {
        void onLightChange(boolean lightActived);
    }
}
