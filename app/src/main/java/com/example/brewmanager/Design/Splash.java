package com.example.brewmanager.Design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.brewmanager.MainActivity;
import com.example.brewmanager.R;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);
        TextView tagline = findViewById(R.id.tagline);

        // Load combined fade-in + scale animation
        Animation fadeScale = AnimationUtils.loadAnimation(this, R.anim.fade_scale);
        logo.startAnimation(fadeScale);

        // Tagline fade-in animation
        tagline.setAlpha(0f);
        tagline.animate().alpha(1f).setDuration(2000).start();

        // Circular reveal effect
        new Handler().postDelayed(() -> {
            View rootView = findViewById(android.R.id.content);
            int cx = rootView.getWidth() / 2;
            int cy = rootView.getHeight() / 2;
            float finalRadius = (float) Math.hypot(cx, cy);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
            circularReveal.setDuration(1000);
            circularReveal.start();

            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Intent intent = new Intent(Splash.this, First.class);
                    startActivity(intent);
                    finish();
                }
            });
        }, 3000); // Duration aligns with logo animation
    }
}
