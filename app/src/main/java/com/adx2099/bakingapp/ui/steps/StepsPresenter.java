package com.adx2099.bakingapp.ui.steps;

public class StepsPresenter implements OnStepsListener, StepPres {
    StepsView stepsView;
    StepsInteractor stepsInteractor;
    public StepsPresenter(StepsView stepsView) {
        this.stepsView = stepsView;
        stepsInteractor =  new StepsInteractor(this);

    }


}
