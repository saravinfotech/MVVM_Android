package com.nothingspecial.foodrecipes;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressBar progressBar;

    @Override
    public void setContentView(int layoutResID) {

        //Getting the root layout of the base content in absence of layout inflater
        ConstraintLayout constraintLayout = (ConstraintLayout)getLayoutInflater().inflate(R.layout.activity_base,null);
        //using the root layout in previous step to inflate other items
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        progressBar = constraintLayout.findViewById(R.id.progress_bar);

        //attaching the layout resource id from other activities to the frame layout
        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        //should be constraint layout as at this point the base root layout would incorporate the layout passed by the calling activity
        super.setContentView(constraintLayout);
    }

    protected void showProgressBar(boolean visible){
        progressBar.setVisibility(visible? View.VISIBLE : View.INVISIBLE);
    }
}
