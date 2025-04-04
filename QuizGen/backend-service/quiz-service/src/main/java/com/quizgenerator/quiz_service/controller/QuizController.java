package com.quizgenerator.quiz_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private PerformQuizService performQuizService;

    public QuizController(PerformQuizService performQuizService) {
        this.performQuizService = performQuizService;
    }

    // Endpoint to generate a new quiz
    @GetMapping("/generate")
    public Quiz generateQuiz() {
        return performQuizService.generateQuiz();
    }

    // Endpoint to submit answers and get score
    @PostMapping("/submit")
    public int submitQuiz(@RequestBody List<UserAnswer> userAnswers) {
        return performQuizService.evaluateAnswers(userAnswers);
    }
}
