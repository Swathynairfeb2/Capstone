package com.example.planahead_capstone;

import android.view.View;

import androidx.test.espresso.IdlingResource;

import android.view.View;

import androidx.test.espresso.IdlingResource;
import android.view.View;

import androidx.test.espresso.IdlingResource;

public class ViewIdlingResource implements IdlingResource {
    private final View view;
    private ResourceCallback resourceCallback;
    private boolean isIdle;

    public ViewIdlingResource(View view) {
        this.view = view;
        this.isIdle = false;
    }

    @Override
    public String getName() {
        return ViewIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        isIdle = view.getWindowVisibility() == View.VISIBLE && view.getGlobalVisibleRect(new android.graphics.Rect());
        if (isIdle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}

