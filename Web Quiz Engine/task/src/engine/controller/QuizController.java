package engine.controller;

import engine.model.*;
import engine.requestmapper.AnswerRequest;
import engine.requestmapper.QuizRequest;
import engine.responsemapper.QuizFeedback;
import engine.responsemapper.QuizResponse;
import engine.service.CompletionService;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final CompletionService completionService;

    @Autowired
    public QuizController(QuizService quizService, CompletionService completionService) {
        this.quizService = quizService;
        this.completionService = completionService;
    }

    @GetMapping
    public ResponseEntity<Page<Quiz>> getQuiz(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ) {
        Page<Quiz> allQuizzes = this.quizService.getAllQuizzes(page, pageSize);
        return ResponseEntity.ok().body(allQuizzes);

    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable("id") int id) {
        Optional<Quiz> byId = this.quizService.getById(id);
        if (byId.isPresent()) {
            Quiz quiz = byId.get();
            QuizResponse response = new QuizResponse();
            List<Options> options = quiz.getOptions();
            String[] op = new String[options.size()];
            for (int i = 0; i < options.size(); i++) {
                op[i] = options.get(i).getOption();
            }
            response.setOptions(op);
            response.setId(quiz.getId());
            response.setText(quiz.getText());
            response.setTitle(quiz.getTitle());
            System.out.println(response);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/completed")
    private ResponseEntity<Page<Completion>> getAllCompleted(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ){
        Page<Completion> allCompleted = this.completionService.getAllCompleted(page, pageSize);
        return ResponseEntity.ok().body(allCompleted);
    }

    @PostMapping
    public Quiz postQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        Quiz quiz = new Quiz();
        List<Options> options = new ArrayList<>();
        String[] options1 = quizRequest.getOptions();
        for (String s : options1) {
            Options o = new Options(s);
            options.add(o);
        }
        List<Answers> answers = new ArrayList<>();
        if (quizRequest.getAnswer() != null) {
            for (Integer i : quizRequest.getAnswer()) {
                Answers a = new Answers(i);
                answers.add(a);
            }
        }
        quiz.setText(quizRequest.getText());
        quiz.setTitle(quizRequest.getTitle());
        quiz.setOptions(options);
        quiz.setAnswers(answers);
        return this.quizService.addQuiz(quiz);
    }


    @PostMapping("/{id}/solve")
    public QuizFeedback checkAnswer(@PathVariable("id") Integer id,
                                    @RequestBody AnswerRequest answer) {
        if (answer.getAnswer() == null) {
            answer.setAnswer(new ArrayList<>());
        }
        return quizService.checkAnswer(id, answer.getAnswer());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz (@PathVariable("id") Integer id){
        Optional<Quiz> quiz = this.quizService.getById(id);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (quiz.isPresent()){
            Quiz q = quiz.get();
            if (q.getUser().getId().equals(user.getId())){
                List<Completion> completedByQuizId = this.completionService.findCompletedByQuizId(q.getId());
                this.completionService.deleteAllCompletion(completedByQuizId);
                this.quizService.deleteQuiz(q);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
