package com.example.task61d;

import java.util.List;

public class Question {


    private List<QuizBean> quiz;

    public List<QuizBean> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<QuizBean> quiz) {
        this.quiz = quiz;
    }

    public static class QuizBean {
        /**
         * correct_answer : [Correct answer letter]
         * options : ["[First option]","[Second option]","[Third option]","[Fourth option]"]
         * question : [Your question here]?
         */

        private String correct_answer;
        private String question;
        private List<String> options;

        public String getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(String correct_answer) {
            this.correct_answer = correct_answer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }
    }
}
