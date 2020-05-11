package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String option;


    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnoreProperties("options")
    private Quiz quiz;

    public Options() {
    }

    public Options(String option) {
        this.option = option;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    @JsonIgnore
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}