package com.adx2099.bakingapp.ui.steps;

public class StepsInteractor implements StepsInt {
    private OnStepsListener listener;

    public StepsInteractor(OnStepsListener listener) {
        this.listener = listener;
    }
}
