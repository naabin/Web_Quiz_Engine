package engine.service.impl;

import engine.model.*;
import engine.repository.AnswerRepository;
import engine.repository.CompletionRepository;
import engine.repository.OptionsRepository;
import engine.repository.QuizRepository;
import engine.responsemapper.QuizFeedback;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizzes;
    private final OptionsRepository optionsRepository;
    private final AnswerRepository answerRepository;
    private final CompletionRepository completionRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizzes,
                           OptionsRepository optionsRepository,
                           AnswerRepository answerRepository,
                           CompletionRepository completionRepository) {
        this.quizzes = quizzes;
        this.optionsRepository = optionsRepository;
        this.answerRepository = answerRepository;
        this.completionRepository = completionRepository;
    }

    @Override
    public Optional<Quiz> getById(Integer id) {
        return quizzes.findById(id);
    }

    @Override
    @Transactional
    public Quiz addQuiz(Quiz quiz) {
        List<Answers> answers = quiz.getAnswers();
        for (Answers a : answers) {
            a.setQuiz(quiz);
            answerRepository.save(a);
        }
        List<Options> options = quiz.getOptions();
        for (Options o : options) {
            o.setQuiz(quiz);
            optionsRepository.save(o);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        quiz.setUser(user);
        return quizzes.save(quiz);
    }
    @Override
    public Page<Quiz> getAllQuizzes(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.quizzes.findAll(pageable);
    }


    @Override
    public QuizFeedback checkAnswer(Integer id, List<Integer> givenAnswers) {

        Quiz quiz;
        if (getById(id).isEmpty()) {
            return new QuizFeedback(false, "Wrong answer! Please, try again.");
        }
        quiz = getById(id).get();
        boolean correct;
        List<Answers> storedAnswers = quiz.getAnswers();
        List<Integer> answers = storedAnswers.stream().map(Answers::getAnswer).collect(Collectors.toList());
        if (answers.size() != givenAnswers.size()) {
            return new QuizFeedback(false, "Wrong answer! Please, try again.");
        } else {
            Collections.sort(answers);
            Collections.sort(givenAnswers);
            correct = !answers.retainAll(givenAnswers);
        }
        if (correct) {
            markQuizComplete(id);
            return new QuizFeedback(true, "Congratulations, you're right!");
        } else {
            return new QuizFeedback(false, "Wrong answer! Please, try again.");
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        this.quizzes.delete(quiz);
    }

    @Override
    public void markQuizComplete(Integer id) {
        Optional<Quiz> byId = getById(id);
        if (byId.isPresent()){
            Quiz quiz = byId.get();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Completion completion = new Completion();
            completion.setQuiz(quiz);
            completion.setCompletedAt(LocalDateTime.now());
            completion.setUser(user);
            this.completionRepository.save(completion);
        }
    }
}
