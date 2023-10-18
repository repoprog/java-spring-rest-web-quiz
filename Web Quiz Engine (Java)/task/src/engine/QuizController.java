package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserRepository userRepository;

    private int counter = 1;

    @PostMapping()   //get the current logged in user object from spring security
    public Quiz createQuiz(@CurrentSecurityContext(expression = "authentication?.name")
                                   String username, @Valid @RequestBody Quiz quiz) {
        quiz.setPostedByUser(username);
        quizService.createQuiz(quiz);
        return quiz;
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return quizService.findQuizById(id);
    }

    @GetMapping()
    public Iterable<Quiz> getAllQuizzes() {
        return quizService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@CurrentSecurityContext(expression = "authentication?.name")
                                   String username, @PathVariable int id) {
        Quiz quiz = quizService.findQuizById(id);
        if (username.equals(quiz.getPostedByUser())) {
            quizService.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Użytkownik niemoże " +
                    "usunąć nieswojego quizu.");
        }
    }

    @PostMapping("/{id}/solve")//check answer from JSON body! not from @ReqestPar - /{id}/solve?answer=
    public Answer getAnswer(@PathVariable @Min(0) int id, @RequestBody Quiz quiz) {

        Quiz quizById = quizService.findQuizById(id);
        if (Arrays.equals(quiz.getAnswer(), quizById.getAnswer())) {
            return new Answer(true, "Congratulations, you're right!");
        } else {
            return new Answer(false, "Wrong answer! Please, try again.");

        }
    }
}
