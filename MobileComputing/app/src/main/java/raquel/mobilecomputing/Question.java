package raquel.mobilecomputing;

import java.util.ArrayList;
import java.util.List;

/*
This class exists because we need to create the objects Question in order to have all the
information about a question in the same object. We need this when we select the question
and when we show it in QuestionActivity.
 */
public class Question {
    private String question;
    private List<String> answers;
    private String rightAnswer;
    private int category;

    public Question(){
        question="";
        answers = new ArrayList<String>();
        rightAnswer="";
        category=6;
    }

    public Question(String question,List<String> answers,String rightAnswer, int category){
        this.question=question;
        this.answers=answers;
        this.rightAnswer=rightAnswer;
        this.category = category;
    }

    public void setQuestion(String question){
        this.question=question;
    }

    public void setAnswers(List<String> answers){
        this.answers=answers;
    }

    public void setRightAnswer(String rightAnswer){
        this.rightAnswer=rightAnswer;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getQuestion(){
        return this.question;
    }

    public List<String> getAnswers(){
        return this.answers;
    }

    public String getRightAnswer() {
        return this.rightAnswer;
    }

    public int getCategory() {
        return category;
    }

}
