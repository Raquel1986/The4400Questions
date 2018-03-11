package raquel.mobilecomputing;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
    IN CategoriesActivity, WE CONTROL THE VIEW WHERE THE USER CAN CHOOSE THE NEXT CATEGORY.
    THIS HAPPENS WHEN, IN THE RouletteActivity, THE CATEGORY WAS THE JOKER.
*/

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        /*
            The next activity will be QuestionActivity unless the user
            press OPTIONS or QUIT GAME.
        */
        GlobalClass.intent=new Intent(this,QuestionActivity.class);

        /*
            If the user press OPTIONS and after that press SAVE OPTIONS, the app
            has to go back to the previous Activity. We know which is the previous
            Activity because of the global variable GlobalClass.optionsManager. So, in
            the case of CategoriesActivity, the value has to be 1.
         */
        GlobalClass.optionsManager=1;

        /*
            We release the MediaPlayer objects. Because if we do not do this, in a certain moment
            the sound does not work anymore. We think maybe it is because of a buffer.
         */
        GlobalClass.cleanMediaPlayer();

        /*
            If AUTOSAVE is activated, we save the state (optionsManager) of the game in order to continue from CategoriesActivity
            next time, if now the user press QUIT GAME. So, it is not only in order to save the score.
        */
        if(GlobalClass.autosave) {
            GlobalClass.saveGame(getApplicationContext());
        }

    }

    //When the user press "music".
    public void music(View v){

        //In order to select a question of the "music" category.
        GlobalClass.categoryInt=1;

        //If the sound is activated, the app says "music" when the user press this category.
        if(GlobalClass.sounds) {
            GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.music);
            GlobalClass.mpCategory.start();
        }

        //Go to QuestionActivity.
        startActivity(GlobalClass.intent);
        finish();

    }

    //When the user press "cinema".
    public void cinema(View v){

        //In order to select a question of the "cinema" category.
        GlobalClass.categoryInt=2;

        //If the sound is activated, the app says "cinema" when the user press this category.
        if(GlobalClass.sounds) {
            GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.cinema);
            GlobalClass.mpCategory.start();
        }

        //Go to QuestionActivity.
        startActivity(GlobalClass.intent);
        finish();

    }

    //When the user press "series".
    public void series(View v){

        //In order to select a question of the "series" category.
        GlobalClass.categoryInt=3;

        //If the sound is activated, the app says "series" when the user press this category.
        if(GlobalClass.sounds) {
            GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.series);
            GlobalClass.mpCategory.start();
        }

        //Go to QuestionActivity.
        startActivity(GlobalClass.intent);
        finish();

    }

    //When the user press "video games".
    public void videogames(View v){

        //In order to select a question of the "video games" category.
        GlobalClass.categoryInt=4;

        //If the sound is activated, the app says "video games" when the user press this category.
        if(GlobalClass.sounds) {
            GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.videogames);
            GlobalClass.mpCategory.start();
        }

        //Go to QuestionActivity.
        startActivity(GlobalClass.intent);
        finish();

    }

    //When the user press "sport".
    public void sport(View v){

        //In order to select a question of the "sport" category
        GlobalClass.categoryInt=5;

        //If the sound is activated, the app says "sport" when the user press this category.
        if(GlobalClass.sounds) {
            GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.sport);
            GlobalClass.mpCategory.start();
        }

        //Go to QuestionActivity.
        startActivity(GlobalClass.intent);
        finish();

    }

    //Go to the options menu
    public void optionsMenu(View v){
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
