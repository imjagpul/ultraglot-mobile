
import java.io.IOException;

public class Question {

    /** The question. */
    private String question;
    /** Correct answer. */
    private String answer;
    /** If this question is to be skipped. */
    public boolean skipDesired = false;
    private int trimCache = TRIM_UNKNOWN;
    private static final int TRIM_UNKNOWN = -2;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    private char[] TRIM_AT = ".];?,[!,#%()\\\u3002".toCharArray();

    /**
     * Determines if the specified answer is correct.
     */
    public boolean isCorrect(String answer) {
        String a=Test.specialLowerCase(answer.toLowerCase()).trim();
        String b=Test.specialLowerCase(this.answer.toLowerCase()).trim();
        
        boolean correct=b.equals(a);
        if(correct) return true;
        
        //try triming the answer
        b=Test.specialLowerCase(getShortAnswer().toLowerCase());
        return b.trim().equals(a);
    }
    public String getShortAnswer() {

        if (trimCache == TRIM_UNKNOWN) {

            //find the trim spot

            for (int i = 0; i < TRIM_AT.length; i++) {
                char c = TRIM_AT[i];
                int index = this.answer.indexOf(c);
                if (index != -1) {
                    if (trimCache < 0) {
                        trimCache = index;
                    } else {
                        trimCache = Math.min(trimCache, index);
                    }
                }
            }

        }
        if (trimCache > 1) {
            return this.answer.substring(0,trimCache);
        } else {
            return this.answer;
        }
    }

    public String toString() {
        return question;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void reverse() {
        String temp = answer;
        answer = question;
        question = temp;
    }

    static Question read(BufferedReader reader) throws IOException {

        String question = reader.readLine();
        if (question == null || "".equals(question)) {
            return null;
        }

        String answer;
        answer = reader.readLine();
        if (answer == null || "".equals(question)) {
            return null;
        }

        //skip more answers (mobile version supports only one answer per question)
        String line;
        do {
            line = reader.readLine();
        } while (line != null && !"".equals(line));

        return new Question(question.trim(), answer.trim());
    }

    public Question createReverse() {
        return new Question(answer, question);
    }
}
