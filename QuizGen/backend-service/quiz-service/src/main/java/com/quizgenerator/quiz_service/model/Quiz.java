package com.quizgenerator.quiz_service.model;

import java.util.List;

public class Quiz {

    private List<Question> questions;

    // Getter and Setter methods
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
