package engine.service;

import engine.model.Quiz;
import engine.responsemapper.QuizFeedback;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface QuizService {


    Optional<Quiz> getById(Integer id);
    Quiz addQuiz(Quiz quiz);
    Page<Quiz> getAllQuizzes(Integer pageNumber, Integer pageSize);
    Page<Quiz> getAllCompleted(Integer pageNumber, Integer pageSize);
    QuizFeedback checkAnswer(Integer id, List<Integer> answers);
    void deleteQuiz(Quiz quiz);
    void markQuizComplete(Integer id);
}
