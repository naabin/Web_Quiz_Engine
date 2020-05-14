package engine.service.impl;

import engine.model.Completion;
import engine.model.User;
import engine.repository.CompletionRepository;
import engine.service.CompletionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletionServiceImpl implements CompletionService {

    private final CompletionRepository completionRepository;

    public CompletionServiceImpl(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    @Override
    public Page<Completion> getAllCompleted(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.completionRepository.getAllByUser(user.getId(), pageable);
    }

    @Override
    public List<Completion> findCompletedByQuizId(Integer quizId) {
        return this.completionRepository.findAllByQuizId(quizId);
    }

    @Override
    public void deleteAllCompletion(List<Completion> completions) {
        this.completionRepository.deleteAll(completions);
    }
}
