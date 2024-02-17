package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.websocket.server.PathParam;
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
    @Autowired
    private QuizCompletionService completionService;


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
    @GetMapping
    public Page<Quiz> getPageQuiz(@RequestParam("page") int num ) {
        return quizService.getPage(num);
    }

    @GetMapping("/completed")
    public Page<QuizCompletion> getPageQuizCompletion(Authentication auth, @RequestParam("page") int num ) {
        String username = auth.getName();
        return completionService.getPage(num, username);
    }


    @DeleteMapping("/{id}")
    public void deleteQuiz(@CurrentSecurityContext(expression = "authentication?.name")
                                   String username, @PathVariable int id) {
        Quiz quiz = quizService.findQuizById(id);
        if (username.equals(quiz.getPostedByUser())) {
            quizService.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Użytkownik nie może " +
                    "usunąć nieswojego quizu.");
        }
    }

    @PostMapping("/{id}/solve")//check answer from JSON body! not from @ReqestPar - /{id}/solve?answer=
    public Answer getAnswer(Authentication auth, @PathVariable @Min(0) int id, @RequestBody Quiz quiz) {
       String username = auth.getName();
        return quizService.solveQuiz(id, quiz, username);
    }
}
