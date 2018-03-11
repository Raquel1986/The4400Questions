package raquel.mobilecomputing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

//THIS IS THE ACTIVITY WHICH THE APP SHOWS AFTER EVERY QUESTION.

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        ProgressBar score=(ProgressBar) findViewById(R.id.score);
        String actualScore=GlobalClass.score+"/"+GlobalClass.maxScore;
        TextView textScore=(TextView) findViewById(R.id.textScoreTransition);
        textScore.setText(actualScore);
        score.setMax(GlobalClass.maxScore);
        score.setProgress(GlobalClass.score);

        /*
            We release the MediaPlayer objects. Because if we do not do this, in a certain moment
            the sound does not work anymore. We think maybe it is because of a buffer.
         */
        GlobalClass.cleanMediaPlayer();

        /*
            We save the state of the game, because we need to save the score and the options in the
            PREFERENCES file. We do not save the score if AUTOSAVE is not selected, but that is implemented
            inside the method saveGame().
        */
        GlobalClass.saveGame(getApplicationContext());

        //When the user has already won or has already lost, we go directly to EndGameActivity.
        if(GlobalClass.score>=GlobalClass.maxScore || GlobalClass.score<1){
            Intent i=new Intent(this,EndGameActivity.class);
            startActivity(i);
            finish();

        }

        /*
            If the user press OPTIONS and after that press SAVE OPTIONS, the app
            has to go back to the previous Activity. We know what is the previous
            Activity because of the global variable GlobalClass.optionsManager. So, in
            the case of TransitionActivity, the value has to be 2.
        */
        GlobalClass.optionsManager=2;

        /*
            If the user has less than 15 points, the score (the numbers and the progress bar) is showed in red.
            If the user has more than (maximum points - 15), the score (the numbers and the progress bar) is showed in red.
         */
        if(GlobalClass.score<15){
            score.setBackgroundColor(Color.RED);
            textScore.setTextColor(Color.RED);
        }else if(GlobalClass.score>(score.getMax()-15)){
            score.setBackgroundColor(Color.BLUE);
            textScore.setTextColor(Color.BLUE);
        }

    }

    //Go to RouletteActivity.
    public void continueGame(View v){
        Intent i=new Intent(this,RouletteActivity.class);
        startActivity(i);
        finish();
    }

    //Go to OptionsActivity.
    public void options(View v){
        Intent i=new Intent(this,OptionsActivity.class);
        startActivity(i);
        finish();
    }

    //Close the Activity (and the App, because we always close each Activity after we call a new one).
    public void quitGame(View v){
        GlobalClass.cleanMediaPlayer();
        finish();
    }

}
