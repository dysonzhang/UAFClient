/*
 * Copyright 2016 YaLin Jin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jin.uitoolkit.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.jin.uitoolkit.R;
import com.jin.uitoolkit.widget.RevealBackgroundView;


/**
 * Created by 雅麟 on 2015/4/22.
 */
public class RevealBackgroundActivity extends ToolbarActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    RevealBackgroundView backgroundView;
    RelativeLayout rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_reveal_background);
        findView();
        View view = View.inflate(this, layoutResID, null);
        rlContent.addView(view);
        setupRevealBackground();
    }

    private void findView() {
        backgroundView = (RevealBackgroundView) findViewById(R.id.vRevealBackground);
        rlContent = (RelativeLayout) findViewById(R.id.reveal_rl_content);
        rlContent.setAlpha(0);
    }

    private void setupRevealBackground() {
        backgroundView.setOnStateChangeListener(this);
        final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
        backgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                backgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                backgroundView.startFromLocation(startingLocation);
                return true;
            }
        });
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            rlContent.setVisibility(View.VISIBLE);
            onStateFinish();
        } else {
            rlContent.setVisibility(View.INVISIBLE);
        }
    }

    protected void onStateFinish() {
        rlContent.animate()
                .alpha(1)
                .setDuration(200)
                .start();
    }
}
