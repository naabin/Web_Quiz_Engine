package engine.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Text is required")
    private String text;

    @NotNull(message = "Options is required")
    @Size(min = 2)
    @OneToMany(mappedBy = "quiz" ,cascade = CascadeType.ALL)
    @JsonIgnoreProperties("quiz")
    private List<Options> options;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("quiz")
    private List<Answers> answers;

    @OneToOne(cascade = CascadeType.ALL)
    private Completion completion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Quiz() {

    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public List<Options> getOptions() {
        return options;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }

    public Completion getCompletion() {
        return completion;
    }

    public void setCompletion(Completion completion) {
        this.completion = completion;
    }

    @Override
    public String toString() {
        return "[Quiz = " +
                "{id = " + getId() +
                " text: " + getText() +
                " title: " + getTitle() +
                " options: " + getOptions() +
                " answer: " + getAnswers() + "}]";
    }
}
