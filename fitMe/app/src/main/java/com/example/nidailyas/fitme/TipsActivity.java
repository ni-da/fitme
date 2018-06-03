package com.example.nidailyas.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    }

    private void initImages() {
        cardAdapter = new CardAdapter(getApplicationContext(), 0);

        cardAdapter.add(new TipPair("EAT SMART", getString(R.string.tipText_eatsmart),
                R.drawable.tip_eatsmart_96));
        cardAdapter.add(new TipPair("GET MOVING", getString(R.string.tipText_getmoving),
                R.drawable.tip_getmoving_96));
        cardAdapter.add(new TipPair("WATCH YOUR WEIGHT", getString(R.string.tipText_watchyourweight),
                R.drawable.tip_weight_96));
        cardAdapter.add(new TipPair("DON'T STRESS", getString(R.string.topText_dontstress),
                R.drawable.icon_yoga_96));
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
//            cardStack.setVisibility(View.GONE);
            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
        }
    }

    @Override
    public void topCardTapped() {

    }
}
