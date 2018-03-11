package raquel.mobilecomputing;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import java.util.Random;

//THIS IS THE ACTIVITY WHERE WE SHOW THE ROULETTE AND IT CHOOSES THE NEXT CATEGORY.

public class RouletteActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        /*
            We release the MediaPlayer objects. Because if we do not do this, in a certain moment
            the sound does not work anymore. We think maybe it is because of a buffer.
         */
        GlobalClass.cleanMediaPlayer();

        Animation animation;
        GlobalClass.mp = MediaPlayer.create(this, R.raw.spin);
        GlobalClass.intent=new Intent(this,QuestionActivity.class);
        final ImageView roulette = (ImageView)findViewById(R.id.roulette);

        //When we are in RouletteActivity, it means the game has started. So, it is not new anymore.
        GlobalClass.newGame=false;

        //So, we have to update the PREFERENCES file.
        GlobalClass.saveGame(getApplicationContext());

        //We randomly choose the next category by choosing a random integer.
        GlobalClass.categoryInt=new Random().nextInt(6);

        //WE CHOOSE THE NEXT CATEGORY AND THE ANIMATION OF THE SPIN DEPENDING OF THE INTEGER.
        switch (GlobalClass.categoryInt){
            case 0: //JOKER
                GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.correct);
                GlobalClass.intent=new Intent(this,CategoriesActivity.class);
                animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotatejoker);
                break;
            case 1: //MUSIC
                GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.music);
                animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotatemusic);
                break;
            case 2: //CINEMA
                GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.cinema);
                animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotatecinema);
                break;
            case 3: //SERIES
                GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.series);
                animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateseries);
                break;
            case 4: //VIDEO GAMES
                GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.videogames);
                animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotatevideogames);
                break;
            default: //SPORT
                GlobalClass.mpCategory = MediaPlayer.create(this, R.raw.sport);
                animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotatesports);
                break;
        }

        //The animation starts.
        roulette.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            //When the animation starts.
            public void onAnimationStart(Animation animation) {

                //If the sound is activated, the spin has sound.
                if(GlobalClass.sounds){
                    GlobalClass.mp.start();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }

            //When the animation ends.
            public void onAnimationEnd(Animation animation) {

                //If the sound is activated, the app says the next category.
                if(GlobalClass.sounds){
                    GlobalClass.mp.stop();
                    GlobalClass.mpCategory.start();
                }

                /*
                    For a few seconds the user could see the spin in its original position,
                    that is why we need to make it invisible.
                 */
                roulette.setVisibility(View.INVISIBLE);

                //Go to QuestionActivity.
                startActivity(GlobalClass.intent);
                finish();

            }
        });

    }

}
