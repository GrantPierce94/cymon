package coms309.roundTrip.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Trivia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String question;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
