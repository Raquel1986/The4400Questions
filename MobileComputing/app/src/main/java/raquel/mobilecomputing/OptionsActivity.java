package raquel.mobilecomputing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

//THE APP COMES TO THIS ACTIVITY IF THE USER PRESS OPTIONS.

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        ToggleButton soundToggleButton = (ToggleButton) findViewById(R.id.soundToggleButton);
        ToggleButton timeLimitToggleButton = (ToggleButton) findViewById(R.id.timeLimitToggleButtom);
        ToggleButton autoSaveToggleButton = (ToggleButton) findViewById(R.id.autoSaveToggleButton);
        ToggleButton difficulty=(ToggleButton) findViewById(R.id.difficultyToggleButton);
        Button defaultButton=(Button) findViewById(R.id.defaultoptions_button);

        /*
            We release the MediaPlayer objects. Because if we do not do this, in a certain moment
            the sound does not work anymore. We think maybe it is because of a buffer.
         */
        GlobalClass.cleanMediaPlayer();

        /*
            If the user selected before something different to the default options, then
            we have to keep the buttons and the attributes of the GlobalClass that way.
         */
        if(!GlobalClass.sounds){
            soundToggleButton.setChecked(false);
        }

        if(!GlobalClass.timelimit){
            timeLimitToggleButton.setChecked(false);
        }

        if(!GlobalClass.autosave){
            autoSaveToggleButton.setChecked(false);
        }

        if(GlobalClass.maxScore==500){
            difficulty.setChecked(true);
        }

        /*
            When the user has more than 99 points and he/she is in the hard mode, he/she cannot change the difficulty,
            except from the MAIN MENU (because we do not know if he/she wants to start a new game).
        */
        if(GlobalClass.optionsManager!=0 && GlobalClass.maxScore==500 && GlobalClass.score>99){
            difficulty.setEnabled(false);
            defaultButton.setEnabled(false);
        }

        /*
            If the user changes the options, then we have to change the attributes
            of the GlobalClass, in order to save the options the way the user wants.
        */
        soundToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    GlobalClass.sounds=true;
                }else{
                    GlobalClass.sounds=false;
                }

            }
        });

        timeLimitToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    GlobalClass.timelimit=true;
                }else{
                    GlobalClass.timelimit=false;
                }

            }
        });

        autoSaveToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    GlobalClass.autosave=true;
                }else{
                    GlobalClass.autosave=false;
                }

            }
        });

        difficulty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    GlobalClass.maxScore=500;
                }else{
                    GlobalClass.maxScore=100;
                }

            }
        });

    }

    /*
        If the user press the button DEFAULT OPTIONS, then we have to change the options to
        the way they are when the app starts. So, we need to change the buttons and the
        attributes of the GlobalClass.
    */
    public void defaultOptions(View v){
        ToggleButton soundToggleButton = (ToggleButton) findViewById(R.id.soundToggleButton);
        ToggleButton timeLimitToggleButton = (ToggleButton) findViewById(R.id.timeLimitToggleButtom);
        ToggleButton autoSaveToggleButton = (ToggleButton) findViewById(R.id.autoSaveToggleButton);
        ToggleButton difficulty=(ToggleButton) findViewById(R.id.difficultyToggleButton);

        soundToggleButton.setChecked(true);
        GlobalClass.sounds=true;

        timeLimitToggleButton.setChecked(true);
        GlobalClass.timelimit=true;

        autoSaveToggleButton.setChecked(true);
        GlobalClass.autosave=true;

        difficulty.setChecked(false);
        GlobalClass.maxScore=100;

    }

    //When the user press SAVE OPTIONS.
    public void saveOptions(View v){
        Intent i;

        /*
        If the user press the button SAVE OPTIONS, then we have to come back
        to the Activity where the user pressed OPTIONS.

        0 -> MainActivity
        1 -> CategoriesActivity
        2 -> TransitionActivity
        */
        if(GlobalClass.optionsManager==1){
            i=new Intent(this,CategoriesActivity.class);
        }else if(GlobalClass.optionsManager==2){
            i=new Intent(this,TransitionActivity.class);
        }else{
            i=new Intent(this,MainActivity.class);
        }

        //We save the options.
        GlobalClass.saveGame(getApplicationContext());

        startActivity(i);
        finish();

    }

    //Close the Activity (and the App, because we always close each Activity after we call a new one).
    public void quitGame(View v){
        GlobalClass.cleanMediaPlayer();
        finish();
    }

}
