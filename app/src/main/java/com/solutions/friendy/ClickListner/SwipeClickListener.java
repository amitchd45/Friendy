package com.solutions.friendy.ClickListner;

import android.view.View;

public interface SwipeClickListener {

    void leftClick(View view, int position);

    void rightClick(View view, int position);

    void bottomClick(View view, int position,String id);
}
