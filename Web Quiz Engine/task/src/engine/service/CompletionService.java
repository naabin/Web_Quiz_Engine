package engine.service;

import engine.model.Completion;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompletionService {
    Page<Completion> getAllCompleted(Integer pageNumber, Integer pageSize);
    List<Completion> findCompletedByQuizId(Integer quizId);
    void deleteAllCompletion(List<Completion> completions);
}
