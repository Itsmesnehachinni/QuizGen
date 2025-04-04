package com.quizgenerator.quiz_service.serviceImpl;

import java.util.List;

@Service
public class PerformQuizService {

    // Sample questions for now, can be fetched from a database
    private List<Question> sampleQuestions = List.of(
            new Question("What is 2 + 2?", List.of("3", "4", "5"), "4"),
            new Question("What is the capital of France?", List.of("Berlin", "Madrid", "Paris"), "Paris")
    );

    public Quiz generateQuiz() {
        Quiz quiz = new Quiz();
        quiz.setQuestions(sampleQuestions); // In reality, this might be fetched based on user's past performance
        return quiz;
    }

    public int evaluateAnswers(List<UserAnswer> userAnswers) {
        int score = 0;

        for (UserAnswer userAnswer : userAnswers) {
            Question question = sampleQuestions.stream()
                    .filter(q -> q.getQuestionText().equals(userAnswer.getQuestionText()))
                    .findFirst()
                    .orElse(null);

            if (question != null && question.getCorrectAnswer().equals(userAnswer.getAnswer())) {
                score++;
            }
        }

        return score;
    }
}
