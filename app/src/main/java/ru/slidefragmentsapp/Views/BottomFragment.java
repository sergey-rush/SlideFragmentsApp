package ru.slidefragmentsapp.Views;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ru.slidefragmentsapp.Core.AnimationEndListener;
import ru.slidefragmentsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private View.OnClickListener clickListener;
    private AnimationEndListener listener;
    private FrameLayout frameLayout;
    private TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        view.setOnClickListener(clickListener);

        frameLayout = (FrameLayout)view.findViewById(R.id.frame_layout);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);

        TabLayout.Tab defaultTab = tabLayout.newTab();
        defaultTab.setText("Default");
        tabLayout.addTab(defaultTab);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("First");
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Second");
        tabLayout.addTab(secondTab);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DefaultTabFragment();
                        break;
                    case 1:
                        fragment = new FirstTabFragment();
                        break;
                    case 2:
                        fragment = new SecondTabFragment();
                        break;
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        Log.d("TAG", "Enter: " + enter + " NextAnim: " + nextAnim);
        if (nextAnim > 0) {
            Animator anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
            if (enter) {
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        listener.onAnimationEnd();
                    }
                });
            }
            return anim;
        }
        return null;
    }

    public void setOnBottomFragmentAnimationEnd(AnimationEndListener listener)
    {
        this.listener = listener;
    }
}