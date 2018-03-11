package raquel.mobilecomputing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//THE MainActivity IS THE FIRST Activity, WHEN THE APPLICATION STARTS. HERE, WE HAVE THE MAIN MENU.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button continueButton=(Button) findViewById(R.id.continue_button_mainmenu);

        /*
            We release the MediaPlayer objects. Because if we do not do this, in a certain moment
            the sound does not work anymore. We think maybe it is because of a buffer.
         */
        GlobalClass.cleanMediaPlayer();

        /*
            We have to check if there is already a PREFERENCES file or it is the first time
            the user executes the application. If the file exists, then we load their saved configuration.
            If the file does not exist, we just use the default configuration.
        */
        if(GlobalClass.checkPreferences(getApplicationContext())){
            GlobalClass.loadGame(getApplicationContext());
        }else{
            GlobalClass.newGame();
        }

        /*
            If the user press OPTIONS and after that press SAVE OPTIONS, the app
            has to go back to the previous Activity. We know which is the previous
            Activity because of the global variable GlobalClass.optionsManager. So, in
            the case of MainActivity, the value has to be 0.
         */
        GlobalClass.optionsManager=0;

        //The next activity will be RouletteActivity unless the user selects OPTIONS or QUIT GAME.
        GlobalClass.intent=new Intent(this,RouletteActivity.class);

        //If the user does not have a game saved, he cannot press CONTINUE.
        if(!GlobalClass.newGame){
            continueButton.setEnabled(true);
        }

    }

    //When the user press NEW GAME.
    public void newGame(View v){

        /*
            We call the other method newGame(), which is in GlobalClass.
            We do this in order to initialize the variables.
         */
        GlobalClass.newGame();

        //We save the configuration, in order to update the file "PREFERENCES".
        GlobalClass.saveGame(getApplicationContext());

        //Go to RouletteActivity.
        startActivity(GlobalClass.intent);
        finish();

    }

    //When the user press CONTINUE.
    public void continueGame(View v){

        //We read the configuration from the file "PREFERENCES".
        GlobalClass.loadGame(getApplicationContext());

        /*
            If the user was in CategoriesActivity when he closed the game and now he press
            CONTINUE in the MAIN MENU, then the application has to continue from CategoriesActivity.
        */
        if(GlobalClass.categoryInt==0){
            Intent i=new Intent(this,CategoriesActivity.class);
            startActivity(i);
        }else{
            startActivity(GlobalClass.intent);
        }

        finish();

    }

    //Go to OptionsActivity.
    public void optionsGame(View v){
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
