package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //UZYJ @Validate w @PostMapping zeby dzialało!
    // not null and not epmpty
    @NotBlank(message = "Quiz powinien zawierać tutuł")
    private String title;
    @NotBlank(message = "Quiz powinien zawierać pytanie")
    private String text;
    @NotNull
    @Size(min = 2, message = "Quiz powinien zawierać conajmniej dwie opcje")
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer = new int[0];
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String postedByUser;
}