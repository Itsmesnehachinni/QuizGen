package com.quizgenerator.quiz_service.model;

public class UserAnswer {

    private String questionText;
    private String answer;

    // Constructor, Getter, and Setter methods
    public UserAnswer(String questionText, String answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
{
}
