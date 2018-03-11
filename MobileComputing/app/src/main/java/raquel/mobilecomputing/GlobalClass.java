package raquel.mobilecomputing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//IN GlobalClass, WE HAVE ALL THE GLOBAL VARIABLES AND METHODS WHICH WE ARE GOING TO NEED IN THE REST OF THE ACTIVITIES.

public class GlobalClass {

    public static boolean sounds=true; //The user can choose if the sound is activated.
    public static boolean timelimit=true; //The user can choose if there is a time limit for each question.
    public static boolean autosave=true;//The user can choose if we save the score.
    public static boolean newGame=true; //If the user starts a new game OR he/she is already playing a game.

    //When the user chooses the right answer three times in a row, he/she obtains +15 points instead of +5.
    public static int countRightAnswer=0;


    public static int optionsManager; // Next activity when the user press SAVE OPTIONS
    /*
    0 -> MainActivity
    1 -> CategoriesActivity
    2 -> TransitionActivity
    */

    public static int categoryInt; //Category of each question
    /*
    0 -> joker
    1 -> music
    2 -> cinema
    3 -> series
    4 -> video games
    5 -> sports
    */


    /*
        This is in order to start activities inside certain methods...

        Because we need sometimes start another activity inside methods which
        do not permit it with a local variable.
    */
    public static Intent intent;


     /*
        This is in order to make sound the roulette or others sound effects inside certain methods...

        We can not use only one of them for all the sounds, because we need both inside
        a listener when the roulette stops and the app has to say the category.
     */
    public static MediaPlayer mp;
    public static MediaPlayer mpCategory;


    public static int score=20; //The actual score. The user begins with 20 points.
    public static int maxScore=100; //The maximum score. When the user wins. In easy mode, it is 100 points. In hard mode, it is 500 points.

    public static CountDownTimer counter; //The counter time for each question when the time limit is activated.

    //The five ArrayList where we are going to have the questions. One of each category.
    public static List<Question> cinemaQuestions=new ArrayList<>();
    public static List<Question> seriesQuestions=new ArrayList<>();
    public static List<Question> musicQuestions=new ArrayList<>();
    public static List<Question> videoGamesQuestions=new ArrayList<>();
    public static List<Question> sportsQuestions=new ArrayList<>();


    //////////////////////////


    /*
        Release both MediaPlayer. It is necessary do it sometimes, because if we do not do that, the App does not sound anymore
        (maybe it is some kind of problem with a buffer of the memory).
    */
    public static void cleanMediaPlayer(){

        if (GlobalClass.mp != null) {
            GlobalClass.mp.release();
            GlobalClass.mp=null;
        }
        if (GlobalClass.mpCategory != null) {
            GlobalClass.mpCategory.release();
            GlobalClass.mpCategory=null;
        }

    }

    //The method which restarts the variables in order to start a new game.
    public static void newGame(){

        createQuestions();
        newGame=true;
        score=20;
        countRightAnswer=0;
        optionsManager=0;

    }

    /*
        The method which save in a "preferences" file all the options selected by the user
        and even his/her score, but this last thing, only if AUTOSAVE is activated.
     */
    public static void saveGame(Context context){

        //TO GET ACCESS TO A SHARED PREFERENCES FILE OR CREATE A NEW ONE
        SharedPreferences sharedPref = context.getSharedPreferences("PREFERENCES",context.MODE_PRIVATE);

        //WRITE INTO THE NEW SHARED PREFERENCES FILE
        SharedPreferences.Editor editor = sharedPref.edit();

        /*
            We only save the score if the option AUTOSAVE is activated OR when we call newGame().
            This last thing happens when the user starts a new game or when the game has ended.
            We have to save the score then because inside the method newGame() is where
            the score is initialized. So, we have to update it in the PREFERENCES file.
         */
        if(autosave || newGame){
            editor.putInt("score", score);
        }

        //We always save the options the user selected
        editor.putBoolean("newGame",newGame);
        editor.putInt("maxScore",maxScore);
        editor.putInt("optionsManager",optionsManager);
        editor.putInt("categoryInt",categoryInt);
        editor.putBoolean("sounds",sounds);
        editor.putBoolean("autosave",autosave);
        editor.putBoolean("timelimit",timelimit);
        editor.commit();

    }

    //This method read the options and the score from the file "preferences"
    public static void loadGame(Context context){

        cleanMediaPlayer();
        newGame=false;

        //TO GET ACCESS TO A SHARED PREFERENCES FILE OR CREATE A NEW ONE
        SharedPreferences sharedPref = context.getSharedPreferences("PREFERENCES",context.MODE_PRIVATE);

        //READ FROM THE NEW SHARED PREFERENCES FILE
        score=sharedPref.getInt("score",20);
        newGame=sharedPref.getBoolean("newGame",true);
        maxScore=sharedPref.getInt("maxScore",100);
        optionsManager=sharedPref.getInt("optionsManager",0);
        categoryInt=sharedPref.getInt("categoryInt",0);
        sounds=sharedPref.getBoolean("sounds",true);
        autosave=sharedPref.getBoolean("autosave",true);
        timelimit=sharedPref.getBoolean("timelimit",true);

    }

    /*
        This method is checking if we already have created the file "PREFERENCES" or not.
        We use it in order to know if it is the first time the user plays with the application.
     */
    public static boolean checkPreferences(Context context){
        boolean b=false;
        SharedPreferences sharedPref = context.getSharedPreferences("PREFERENCES",context.MODE_PRIVATE);

        if(sharedPref.contains("maxScore"))
        {
            b=true;
        }

        return b;
    }

    //This is the method which creates all the questions.
    public static void createQuestions(){

        createMusicQuestions();
        createCinemaQuestions();
        createSeriesQuestions();
        createVideoGamesQuestions();
        createSportsQuestions();

    }

    //This is the method which creates the music questions.
    public static void createMusicQuestions(){
        String question;
        String correctAnswer;
        List<String> answers;
        Question q;

        musicQuestions=new ArrayList<>();

        question="What was Patsy Cline's real name?";
        correctAnswer="Virginia Patterson Hensley";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Jo Ann DeShannon");
        answers.add("Nancy Wellshire Garnett");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What is the first name of the lead singer of the band 'Daughtry'?";
        correctAnswer="Chris";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Adam");
        answers.add("Rob");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What did Queen want to tie down in their hit from 1977?";
        correctAnswer="Your Mother";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Your Teacher");
        answers.add("Your Father");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="In 2010, who did Pitbull say 'Got Us Fallin' In Love'?";
        correctAnswer="DJ";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Angels");
        answers.add("God");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="On which Avenged Sevenfold album would you find their hit song 'Nightmare'?";
        correctAnswer="Nightmare";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Avenged Sevenfold");
        answers.add("City Of Evil");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What singer was number 1 on Maxim Magazine's 2010 Hot 100 list?";
        correctAnswer="Katy Perry";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Taylor Swift");
        answers.add("Cristina Aguilera");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What 2010 hit by Taylor Swift is on her album of the same title?";
        correctAnswer="Fearless";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("White Horse");
        answers.add("Love Story");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="Who teamed with Eminem on his 2010 song 'Love The Way You Lie'?";
        correctAnswer="Rihanna";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Lady GaGa");
        answers.add("Beyonce");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="Christina Aguilera's 2010 follow-up hit to 'Not Myself Tonight' was 'You Lost _____'";
        correctAnswer="Me";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Her");
        answers.add("Your Mind");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="Who is the featured artist in Timbaland's hit 'If We Ever Meet Again'?";
        correctAnswer="Katy Perry";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Beyonce");
        answers.add("Rihanna");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What other names are mentioned in Lady GaGa's hit song 'Alejandro'?";
        correctAnswer="Fernando and Roberto";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Alfonso and Luis");
        answers.add("Emilio and Gustavo");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="Who has a hit song titled 'All The Right Moves'?";
        correctAnswer="OneRepublic";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Adam Lambert");
        answers.add("John Mayer");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What movie featured Madonna's hit 'Don't Cry For Me Argentina'?";
        correctAnswer="Evita";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Die Another Day");
        answers.add("Relatos Salvajes");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="Which 1990 movie soundtrack included Cher's hit song 'Shoop Shoop (It's In His Kiss)'?";
        correctAnswer="Mermaids";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Home Alone");
        answers.add("Dick Tracy");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What was the last name of Nina, an original MTV video jockey?";
        correctAnswer="Blackwood";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Williams");
        answers.add("Goodman");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        question="What reality show was Dead Or Alive singer Pete Burns on?";
        correctAnswer="Big Brother";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Survivor");
        answers.add("The Amazing Race");
        q=new Question(question,answers,correctAnswer,1);
        musicQuestions.add(q);

        Collections.shuffle(musicQuestions);

    }

    //This is the method which creates the cinema questions.
    public static void createCinemaQuestions(){
        String question;
        String correctAnswer;
        List<String> answers;
        Question q;

        cinemaQuestions=new ArrayList<>();

        question="What actor played the drama critic in the Orson Welles classic 'Citizen Kane'?";
        correctAnswer="Joseph Cotten";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Orson Welles");
        answers.add("Jeremy Cotton");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="'After all, tomorrow is another day!' was the last line in which Oscar-winning Best Picture?";
        correctAnswer="Gone With the Wind";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Great Expectations");
        answers.add("The Matrix");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="Who is the only person to win an Oscar for Best Director for the only movie he ever directed?";
        correctAnswer="Jerome Robbins";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Frank Borzage");
        answers.add("Leo McCarey");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="Who is the most nominated actor in Academy history?";
        correctAnswer="Jack Nicholson";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Spencer Tracy");
        answers.add("Paul Newman");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="What was the first Western to win the Academy Award for Best Picture?";
        correctAnswer="Cimarron (1930/31)";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Brokeback Mountain (2005)");
        answers.add("The Last Wagon (1956)");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In which werewolf film were the characters named after famous werewolf horror film movie directors?";
        correctAnswer="The Howling (1981)";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("An American Werewolf in London (1981)");
        answers.add("Wolf (1994)");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In the film Music Box (1989), what was discovered inside the titular object (a Hungarian music box)?";
        correctAnswer="Incriminating photos";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Signed documents");
        answers.add("An human finger");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="What was the name of the strategic military formation used by Greek King Leonidas (Gerard Butler) and his Spartans against the Persians in 300 (2006)?";
        correctAnswer="Phalanx";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Spear Hedge");
        answers.add("Column");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="How many children were there in the Von Trapp family in the musical film The Sound of Music (1965)?";
        correctAnswer="Seven";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Five");
        answers.add("Six");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In Batman Returns (1992), what was Catwoman's original name?";
        correctAnswer="Selina Kyle";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Catherine George");
        answers.add("Catherine Phillips");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="What was the name of the restaurant in Grease (1978) where the Rydell High teens hang out?";
        correctAnswer="Frosty Palace";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Arnold's");
        answers.add("Frenchy's Place");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In the superhero film Iron Man (2008), what was the name of Tony Stark's (Robert Downey, Jr.) new cluster missile weapon, developed by his own Stark Industries?";
        correctAnswer="Jericho";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Babylon");
        answers.add("Skybolt");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In The Dark Knight (2008), what was the name on the Joker's (Heath Ledger) nametag when he impersonated a nurse in Gotham General Hospital before blowing it up?";
        correctAnswer="Matilda";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Melissa");
        answers.add("Madison");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In the original Superman (1978), what was the name of the newspaper that employed Clark Kent (Christopher Reeve) as a reporter?";
        correctAnswer="Daily Planet";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Gotham News");
        answers.add("Gotham Times");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        question="In the suspense thriller Charade (1963), what was the valuable missing object being searched for?";
        correctAnswer="Rare stamps";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Art Painting");
        answers.add("Religious artifact");
        q=new Question(question,answers,correctAnswer,2);
        cinemaQuestions.add(q);

        Collections.shuffle(cinemaQuestions);

    }

    //This is the method which creates the series questions.
    public static void createSeriesQuestions(){
        String question;
        String correctAnswer;
        List<String> answers;
        Question q;

        seriesQuestions=new ArrayList<>();

        question="What does Walt Jr. claim that veggie bacon smells like in the first ever episode of 'Breaking Bad'?";
        correctAnswer="Band-Aids";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Cabbage");
        answers.add("Medicine");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="What was the name of the meth that Jesse Pinkman initially made and distributed in 'Breaking Bad'?";
        correctAnswer="Chili P";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Crazy 8");
        answers.add("Cherry 6");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="Where were Mitch and Stan before their daughter Rosie was killed in 'The Killing'?";
        correctAnswer="Camping";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("In Europe");
        answers.add("Visiting Mitch's parents");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="In the pilot of 'The Killing' it was supposed to be Sarah's last day in Seattle. Where was she moving?";
        correctAnswer="Sonoma";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("San Diego");
        answers.add("Miami");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="Who is one of the main characters in 'The Blacklist'?";
        correctAnswer="Raymond Reddington";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Peter Connor");
        answers.add("Catherine Pierce");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="Which of these TV Series is a spin off of 'Breaking Bad'";
        correctAnswer="Better Call Saul";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Pinkman's Revenge");
        answers.add("The Originals");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="What is the name of the character who disappears in the first episode of 'Stranger Things'?";
        correctAnswer="Billy Byers";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Joyce Byers");
        answers.add("Jonathan Byers");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="What is the name of Malcolm's biggest brother in 'Malcolm In The Middle'?";
        correctAnswer="Francis";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Reese");
        answers.add("Dewey");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="What is Jessica Jones' job in 'Jessica Jones'?";
        correctAnswer="Private Detective";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Waitress");
        answers.add("Lawyer");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        question="What is the name of the actor who plays Hal in 'Malcolm In The Middle' and Walter White in 'Breaking Bad'?";
        correctAnswer="Bryan Cranston";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Aaron Paul");
        answers.add("Frankie Muniz");
        q=new Question(question,answers,correctAnswer,3);
        seriesQuestions.add(q);

        Collections.shuffle(seriesQuestions);

    }

    //This is the method which creates the videogames questions.
    public static void createVideoGamesQuestions(){
        String question;
        String correctAnswer;
        List<String> answers;
        Question q;

        videoGamesQuestions=new ArrayList<>();

        question="What is the name of the main character in 'Monkey Island' saga?";
        correctAnswer="Guybrush Threepwood";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Undead pirate LeChuck");
        answers.add("Elaine Marley");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="What is the name of the main character in 'Deponia' saga?";
        correctAnswer="Rufus";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Bailiff Argus");
        answers.add("Gizmo");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="What is the name of the company which is doing the most recent games about Sherlock Holmes?";
        correctAnswer="Frogwares";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Telltale Games");
        answers.add("Daedalic Entertainment");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="What company designed the game World of Warcraft?";
        correctAnswer="Blizzard Entertainment";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Microsoft");
        answers.add("Daedalic Entertainment");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="What company designed the game League of Legends";
        correctAnswer="Riot Games";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Blizzard Entertainment");
        answers.add("Telltale Games");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="Which character possess the Zeal skill in 'Diablo II'";
        correctAnswer="The Paladin";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("The Necromancer");
        answers.add("The Barbarian");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="In which game Luigi (Mario's brother) appears for the first time?";
        correctAnswer="Mario Bros (1983)";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Super Mario Bros (1985)");
        answers.add("Mario and Luigi (1980)");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="Who is the most famous Pokémon ever?";
        correctAnswer="Pikachu";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Bulbasaur");
        answers.add("Charmander");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="Who is the designer and developer of the famous and old game 'Tetris'?";
        correctAnswer="Alekséi Pázhitnov";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Toru Iwatani");
        answers.add("Toshihiro Nishikado");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        question="What exactly is supposed to be Sonic (Sega main character)?";
        correctAnswer="A hedgehog";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("A squirrel");
        answers.add("A fox");
        q=new Question(question,answers,correctAnswer,4);
        videoGamesQuestions.add(q);

        Collections.shuffle(videoGamesQuestions);

    }

    //This is the method which creates the sport questions.
    public static void createSportsQuestions(){
        String question;
        String correctAnswer;
        List<String> answers;
        Question q;

        sportsQuestions=new ArrayList<>();

        question="Who won the men's 100m breaststroke and set down a new world record time at the Olympic Games of 1996 in Atlanta?";
        correctAnswer="Fred Deburghgraeve";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Robert Van de Valle");
        answers.add("Philippe Lejeune");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="The Swiss daredevil Hermann Schreiber was the only competitor in a unique Olympic discipline in 1936. In which discipline did Schreiber 'participate' at the Berlin Olympics?";
        correctAnswer="Aeronautics";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Croquet");
        answers.add("Moon golf");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="The nickname 'Flying Finn' has been given to several Finnish sportsmen who were internationally successful in their chosen field. What was the sport of the first Flying Finns?";
        correctAnswer="Running";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Ski jumping");
        answers.add("Motorsport");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="What was the nickname of the Portuguese top scorer at the 1966 FIFA World Cup?";
        correctAnswer="The Black Pearl";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("The Rocket");
        answers.add("Mr. 100");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="What boxers' category comes in between 'Super Bantamweight' and 'Junior Lightweight'?";
        correctAnswer="Featherweight";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Bantamweight");
        answers.add("Lightweight");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="Which women's sport was dominated first by Jeannie Longo of France and then by Leontien van Moorsel of Holland during the last few decades of the 20th Century?";
        correctAnswer="Cycle-racing";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Horse Jumping");
        answers.add("Badminton");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="In what sport was the 'Fosbury Flop' a popular innovation?";
        correctAnswer="High Jump";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Handball");
        answers.add("Swimming");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        question="What is Javier Sotomayor's SECOND surname?";
        correctAnswer="Sanabria";
        answers=new ArrayList<>();
        answers.add(correctAnswer);
        answers.add("Rodríguez");
        answers.add("Alonso");
        q=new Question(question,answers,correctAnswer,5);
        sportsQuestions.add(q);

        Collections.shuffle(sportsQuestions);

    }

}
