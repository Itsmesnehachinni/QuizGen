import React, { useState, useEffect } from 'react';
import { Container, Typography, Radio, RadioGroup, FormControl, FormControlLabel, Button, Alert } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const PerformQuiz = () => {
  const [quiz, setQuiz] = useState([]);
  const [userAnswers, setUserAnswers] = useState({});
  const [score, setScore] = useState(null);
  const [quizSubmitted, setQuizSubmitted] = useState(false);
  const navigate = useNavigate(); 


  // Fetch quiz questions (this could come from an API or hardcoded data)
  useEffect(() => {
    fetchQuizQuestions().then(data => setQuiz(data));
  }, []);

  const handleNavigation = () => {
    navigate('/userDashboard');  // Navigates to '/new-page'
  };

  const handleAnswerChange = (questionId, answer) => {
    setUserAnswers(prev => ({
      ...prev,
      [questionId]: answer,
    }));
  };

  const submitQuiz = (e) => {
    e.preventDefault();
    // Calculate score (for simplicity, let's assume a correct answer gives 1 point)
    const totalScore = quiz.reduce((score, question) => {
      return userAnswers[question.id] === question.correctAnswer ? score + 1 : score;
    }, 0);
    setQuizSubmitted(true);
    setScore(totalScore);
  };

  return (
    <Container>
      <div className='performquiz-header-text'>
        <Typography variant="h4" gutterBottom>
          Your Questions
        </Typography>
      </div>
      {quiz.length > 0 ? (
        <form onSubmit={submitQuiz}>
          {quiz.map((question) => (
            <div key={question.id} style={{ marginBottom: '16px' }}>
              <Typography variant="h6">{question.questionText}</Typography>
              <FormControl component="fieldset">
                <RadioGroup
                  name={`question-${question.id}`}
                  onChange={(e) => handleAnswerChange(question.id, e.target.value)}
                >
                  {question.options.map((option) => (
                    <FormControlLabel 
                      key={option} 
                      value={option} 
                      control={<Radio />} 
                      label={option} 
                    />
                  ))}
                </RadioGroup>
              </FormControl>
              {score !== null && userAnswers[question.id] && (
                <Typography variant="body2" style={{ marginTop: '8px' }}>
                  <strong>Your Answer: </strong>{userAnswers[question.id]}
                </Typography>
              )}
              {score !== null && (
                <Typography variant="body2" style={{ marginTop: '8px', color: 'green' }}>
                  <strong>Correct Answer: </strong>{question.correctAnswer}
                </Typography>
              )}
            </div>
          ))}
          {!quizSubmitted ? (<Button type="submit" variant="contained" color="primary">
            Submit Quiz
          </Button>):(<Button type="button" variant="contained" color="primary" onClick={handleNavigation}>
            Go to Dashboard
          </Button>)}
          {score !== null && (
            <Alert severity="success" style={{ marginTop: '16px' }}>
              <Typography variant="h6">Your Score: {score}/{quiz.length}</Typography>
            </Alert>
          )}
        </form>
      ) : (
        <p>Loading quiz...</p>
      )}
    </Container>
  );
};

// Example function to fetch quiz questions (this can be an API call)
const fetchQuizQuestions = async () => {
  return [
    {
      id: 1,
      questionText: 'What is the default value of a local variable in Java?',
      options: ['null', 'No default Value', 'undefined', '0'],
      correctAnswer: 'No default Value',
    },
    {
      id: 2,
      questionText: 'Which of the following is used to find and fix bugs in Java programs?',
      options: ['JVM', 'JDK', 'JRE', 'Debugger'],
      correctAnswer: 'Debugger',
    },
  ];
};

export default PerformQuiz;
