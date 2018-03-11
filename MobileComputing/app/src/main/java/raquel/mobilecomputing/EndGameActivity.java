package raquel.mobilecomputing;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

//IF THE APP IS IN THIS ACTIVITY, IT MEANS THE USER HAS ALREADY WON (score>=maxScore) OR HE/SHE HAS ALREADY LOST (score==0).

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        ProgressBar score=(ProgressBar) findViewById(R.id.score);
        String actualScore=GlobalClass.score+"/"+GlobalClass.maxScore;
        TextView textScore=(TextView) findViewById(R.id.textScoreEndGame);
        textScore.setText(actualScore);
        TextView textEndGame=(TextView) findViewById(R.id.textEndGame);
        ImageView imagEndGame=(ImageView) findViewById(R.id.endGame);
        final Button mainMenu=(Button) findViewById(R.id.mainmenu_button);
        final Button quitButton=(Button) findViewById(R.id.quit_button_endgame);
        int waiting=1500; //We want to delay the next instructions while the MediaPlayer sounds OR 1.5 seconds if the sound is NOT activated.
        score.setMax(GlobalClass.maxScore);
        score.setProgress(GlobalClass.score);

         /*
            If the user press OPTIONS and after that press SAVE OPTIONS, the app
            has to go back to the previous Activity. We know which is the previous
            Activity because of the global variable GlobalClass.continueInt. So, in
            the case of MainActivity, the value has to be 0.

            In this case, we are in EndGameActivity, so, the next Activity (if there is one) is always going
            to be MainActivity.
         */
        GlobalClass.optionsManager=0;

        /*
            We release the MediaPlayer objects. Because if we do not do this, in a certain moment
            the sound does not work anymore. We think maybe it is because of a buffer.
         */
        GlobalClass.cleanMediaPlayer();


        /*
            If the App call this Activity is because the user has already won or he/she has already lost the game.
            If he/she loses, then it shows the text "GAME OVER!!" and sounds the "Game Over" sound.
            If he/she wins, then it shows the text "YOU WIN!!" and sounds the "You Win" sound.
         */
        if(GlobalClass.score<1){
            score.setBackgroundColor(Color.RED);
            textScore.setTextColor(Color.RED);
            textEndGame.setText("GAME OVER!!");
            textEndGame.setTextColor(Color.RED);
            GlobalClass.mp = MediaPlayer.create(this, R.raw.gameover);
            imagEndGame.setImageResource(R.drawable.gameover);
        }else{
            score.setBackgroundColor(Color.BLUE);
            textScore.setTextColor(Color.BLUE);
            textEndGame.setText("YOU WIN!!");
            textEndGame.setTextColor(Color.BLUE);
            GlobalClass.mp = MediaPlayer.create(this, R.raw.win);
            imagEndGame.setImageResource(R.drawable.win);
        }

        //The sound starts only if the option "Sound" is activated.
        if(GlobalClass.sounds) {

            GlobalClass.mp.start();

            //We want to delay the next instructions while the MediaPlayer sounds. So, we need to know the duration.
            waiting=GlobalClass.mp.getDuration();

        }

        //We want to delay the next instructions while the MediaPlayer sounds OR 1.5 seconds if the sound is NOT activated.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                public void run() {

                    //When the game ends, we have to restart the score.
                    GlobalClass.newGame();
                    GlobalClass.saveGame(getApplicationContext());

                    //When the "delay" ends, we enable the buttons.
                    mainMenu.setEnabled(true);
                    quitButton.setEnabled(true);

                }
        }, waiting);

    }

    //If the user wants to go to the Main Menu
    public void mainMenu(View v){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    //Close the Activity (and the App, because we always close each Activity after we call a new one).
    public void quitGame(View v){
        GlobalClass.cleanMediaPlayer();
        finish();
    }
}
