package raquel.mobilecomputing;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;

//IN THIS ACTIVITY, WE SHOW THE QUESTION.

public class QuestionActivity extends AppCompatActivity {

    /*
        When the user chooses an answer, we have to compare the button id of their answer to the button id
        of the correct answer.
     */
    private int idCorrectAnswer;

    //CREATE THE VIEW FOR ALL QUESTIONS
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Question q;
        List<String> answers;
        LinearLayout layout =(LinearLayout)findViewById(R.id.activity_question);
        TextView questionText=(TextView) findViewById(R.id.question);
        Button answer1=(Button) findViewById(R.id.answer1);
        Button answer2=(Button) findViewById(R.id.answer2);
        Button answer3=(Button) findViewById(R.id.answer3);
        ProgressBar score=(ProgressBar) findViewById(R.id.score);
        score.setMax(GlobalClass.maxScore);
        score.setProgress(GlobalClass.score);

        //The next Activity is always going to be TransitionActivity.
        GlobalClass.intent=new Intent(this,TransitionActivity.class);

        //Set the color of the ProgressBar depending of the score.
        if(GlobalClass.score<15){
            score.setBackgroundColor(Color.RED);
        }else if(GlobalClass.score>(score.getMax()-15)){
            score.setBackgroundColor(Color.BLUE);
        }

        //SELECT THE QUESTION DEPENDING OF THE CATEGORY.
        switch (GlobalClass.categoryInt){
            case 1:
                layout.setBackgroundResource(R.drawable.musicbackground);
                if(GlobalClass.musicQuestions.isEmpty()){
                    GlobalClass.createMusicQuestions();
                }
                q=GlobalClass.musicQuestions.get(0);
                GlobalClass.musicQuestions.remove(0);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.cinemabackground);
                if(GlobalClass.cinemaQuestions.isEmpty()){
                    GlobalClass.createCinemaQuestions();
                }
                q=GlobalClass.cinemaQuestions.get(0);
                GlobalClass.cinemaQuestions.remove(0);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.seriesbackground);
                if(GlobalClass.seriesQuestions.isEmpty()){
                    GlobalClass.createSeriesQuestions();
                }
                q=GlobalClass.seriesQuestions.get(0);
                GlobalClass.seriesQuestions.remove(0);
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.videogamesbackground);
                if(GlobalClass.videoGamesQuestions.isEmpty()){
                    GlobalClass.createVideoGamesQuestions();
                }
                q=GlobalClass.videoGamesQuestions.get(0);
                GlobalClass.videoGamesQuestions.remove(0);
                break;
            default:
                layout.setBackgroundResource(R.drawable.sportsbackground);
                if(GlobalClass.sportsQuestions.isEmpty()){
                    GlobalClass.createSportsQuestions();
                }
                q=GlobalClass.sportsQuestions.get(0);
                GlobalClass.sportsQuestions.remove(0);
                break;
        }

        //Show the question from the Question selected
        questionText.setText(q.getQuestion());

        //Disorder the answers in a random way.
        answers=q.getAnswers();
        Collections.shuffle(answers);

        //If the option Time Limit is activated, we have a counter time in each question.
        if(GlobalClass.timelimit) {
            final TextView counter = (TextView) findViewById(R.id.counter);
            GlobalClass.counter=new CountDownTimer(21000, 1000) {

                public void onTick(long millisUntilFinished) {
                    counter.setText("" + millisUntilFinished / 1000);
                    if(millisUntilFinished/1000<6) {
                        counter.setTextSize(50);
                        counter.setTextColor(Color.RED);
                    }
                }

                public void onFinish() {
                    counter.setText("" + 0);
                    counter.setTextSize(70);
                    wrong();
                    waitAndClose(2500);
                }
            }.start();
        }

        //Figure out the position of the correct answer after disordering the answers.
        if(q.getRightAnswer().equals(answers.get(0))){
            this.idCorrectAnswer=answer1.getId();
        }else if(q.getRightAnswer().equals(answers.get(1))){
            this.idCorrectAnswer=answer2.getId();
        }else{
            this.idCorrectAnswer=answer3.getId();
        }

        //Show the answers.
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));

    }

    //When the user answers the question.
    public void toAnswer(View v){
        Button answer=(Button) findViewById(v.getId());

        //Figure out if it is the right answer.
        if(v.getId()==this.idCorrectAnswer){
            correct();
        }else{
            answer.setBackgroundColor(Color.RED);
            answer.setTextColor(Color.WHITE);
            wrong();
        }

        //Wait 2500 milliseconds in order to show the correct answer to the user.
        waitAndClose(2500);

    }

    //If the answer is correct.
    public void correct(){
        /*
            Show the correct answer. We do not do this inside toAnswer() because we also need
            to call wrong() when the time is out, but we do not call toAnswer() in that case.
            So, we need the code which show the correct answer inside both methods: correct() and wrong().
         */
        Button correctAnswer=(Button) findViewById(this.idCorrectAnswer);
        correctAnswer.setBackgroundColor(Color.BLUE);
        correctAnswer.setTextColor(Color.WHITE);

        //If the sound is activated.
        if(GlobalClass.sounds){
            GlobalClass.mp = MediaPlayer.create(this, R.raw.correct);
            GlobalClass.mp.start();
        }

        /*
           When the user chooses the right answer three times in a row, he/she obtains +15 points instead of +5
           EXCEPT in the case he/she is going to win already with +15 points.
        */
        GlobalClass.countRightAnswer++;

        if(GlobalClass.countRightAnswer==3 && GlobalClass.score<GlobalClass.maxScore-15){
            // +5 points for the user.
            GlobalClass.score=GlobalClass.score+15;
            Toast.makeText(getApplicationContext(),"BONUS +15",Toast.LENGTH_LONG).show();
        }else{
            GlobalClass.score=GlobalClass.score+5;
            Toast.makeText(getApplicationContext(),"+5",Toast.LENGTH_LONG).show();
        }

    }

    //If the answer is wrong or the time is out.
    public void wrong(){
        /*
            Show the correct answer. We do not do this inside toAnswer() because we also need
            to call wrong() when the time is out, but we do not call toAnswer() in that case.
            So, we need the code which show the correct answer inside both methods: correct() and wrong().
         */
        Button correctAnswer=(Button) findViewById(this.idCorrectAnswer);
        correctAnswer.setBackgroundColor(Color.BLUE);
        correctAnswer.setTextColor(Color.WHITE);

        //If the sound is activated.
        if(GlobalClass.sounds){
            GlobalClass.mp = MediaPlayer.create(this, R.raw.wrong);
            GlobalClass.mp.start();
        }

        // -5 points for the user.
        GlobalClass.score=GlobalClass.score-5;
        Toast.makeText(getApplicationContext(),"-5",Toast.LENGTH_LONG).show();

        //We have to put again the counter for the rights answer equal to 0.
        GlobalClass.countRightAnswer=0;

    }


    //Wait 2500 milliseconds in order to show the correct answer to the user.
    public void waitAndClose(int milliseconds) {

        //If the Time Limit is activated, now is the moment to stop the counter.
        if(GlobalClass.timelimit){
            GlobalClass.counter.cancel();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(GlobalClass.intent);
                finish();
            }
        }, milliseconds);

    }

}
