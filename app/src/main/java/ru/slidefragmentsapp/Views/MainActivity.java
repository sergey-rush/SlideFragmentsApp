package ru.slidefragmentsapp.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ru.slidefragmentsapp.Core.AnimationEndListener;
import ru.slidefragmentsapp.Core.State;
import ru.slidefragmentsapp.R;

public class MainActivity extends Activity implements
        AnimationEndListener, FragmentManager.OnBackStackChangedListener, View.OnClickListener {

    private TopFragment topFragment;
    private BottomFragment bottomFragment;
    private View shadowView;
    private Context context;

    private boolean animating = false;
    private State state = State.COLLAPSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        shadowView = findViewById(R.id.shadow_view);
        shadowView.setAlpha(0);

        topFragment = (TopFragment) getFragmentManager().findFragmentById(R.id.top_fragment);
        bottomFragment = new BottomFragment();

        getFragmentManager().addOnBackStackChangedListener(this);

        bottomFragment.setClickListener(this);
        bottomFragment.setOnBottomFragmentAnimationEnd(this);
        shadowView.setOnClickListener(this);
        toggleBottom();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bottom_fragment:
                toggleTop();
                break;
            case R.id.shadow_view:
                toggleBottom();
                break;
            case View.NO_ID:
            default:
                break;
        }
    }

    private void toggleTop() {
        Toast.makeText(context, "Toggle Top code required here", Toast.LENGTH_SHORT).show();
    }

    private void toggleBottom() {
        if (animating) {
            return;
        }
        animating = true;

        if (state == State.HALFDRAWN) {            
            getFragmentManager().popBackStack();
            state = State.COLLAPSED;
        } else {
            Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator arg0) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.enter, 0,0,R.animator.exit);
                    transaction.add(R.id.main_container, bottomFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            };
            slideIn(listener);
            state = State.HALFDRAWN;
        }
    }

    @Override
    public void onBackStackChanged() {
        if (state == State.COLLAPSED) {
            slideOut();
        }
    }

    public void slideIn(Animator.AnimatorListener listener) {
        ObjectAnimator shadowViewAnimator = ObjectAnimator.ofFloat(shadowView, "alpha", 0.0f, 0.4f);
        AnimatorSet s = new AnimatorSet();
        s.playTogether(shadowViewAnimator);
        s.setStartDelay(getResources().getInteger(R.integer.delay_animation));
        s.addListener(listener);
        s.start();
    }

    public void slideOut() {
        ObjectAnimator shadowViewAnimator = ObjectAnimator.ofFloat(shadowView, "alpha", 0.4f, 0.0f);
        AnimatorSet s = new AnimatorSet();
        s.playTogether(shadowViewAnimator);
        s.setStartDelay(getResources().getInteger(R.integer.delay_animation));
        s.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }
        });
        s.start();
    }

    public void onAnimationEnd() {
        animating = false;
    }
}
