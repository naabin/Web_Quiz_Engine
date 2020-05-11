package engine.requestmapper;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QuizRequest {

    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String text;

    @NotNull
    @Size(min = 2)
    private String[] options;

    private Integer[] answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getOptions() {
        return options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public Integer[] getAnswer() {
        return answer;
    }

    public void setAnswer(Integer[] answer) {
        this.answer = answer;
    }
}
