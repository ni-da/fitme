package com.example.nidailyas.fitme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.wenchao.cardstack.CardStack;

public class TipsActivity extends AppCompatActivity implements CardStack.CardEventListener {

    private CardStack cardStack;
    private CardAdapter cardAdapter;
    private LottieAnimationView lottieAnimationViewlike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        initImages();
        cardStack = findViewById(R.id.card_stack);
        cardStack.setContentResource(R.layout.card_layout);
        cardStack.setStackMargin(20);
        cardStack.setAdapter(cardAdapter);

        cardStack.setListener(this);

        cardStack.bringToFront();

//        lottieAnimationViewlike = findViewById(R.id.animation_view_like);
//        lottieAnimationViewlike.playAnimation();


    }

    private void initImages() {
        cardAdapter = new CardAdapter(getApplicationContext(), 0);
        cardAdapter.add(R.drawable.camera);
        cardAdapter.add(R.drawable.ic_yoga);
        cardAdapter.add(R.drawable.star);
        cardAdapter.add(R.drawable.profile_pic);
    }

    @Override
    public boolean swipeEnd(int i, float v) {
        return (v > 300) ? true : false;
    }

    @Override
    public boolean swipeStart(int i, float v) {
        return false;
    }

    @Override
    public boolean swipeContinue(int i, float v, float v1) {
        return false;
    }

    @Override
    public void discarded(int mIndex, int direction) {
        if (cardAdapter.getCount() == mIndex) {
            cardStack.setVisibility(View.GONE);
        }
    }

    @Override
    public void topCardTapped() {

    }
}
