
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/*
 * Test.java
 *
 * Created on 30. srpen 2007, 13:32
 */
/**
 *
 * @author Stepan
 */
public class Test {

    private Vector questions = new Vector();
    private int nextQuestionIndex = 0;
    private String name;
    private static final String HEADER = "TESTv1";
    private int startSize;
    private int stepCount;
    private int wrongCount;
    private static final char[] LOWER = "\u0430\u0431\u0432\u0433\u0434\u0435\u0451\u0436\u0437\u0438\u0439\u043A\u043B\u043C\u043D\u043E\u043F\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044A\u044B\u044C\u044D\u044E\u044F-.,".toCharArray();
    private static final char[] UPPER = "\u0410\u0411\u0412\u0413\u0414\u0415\u0401\u0416\u0417\u0418\u0419\u041A\u041B\u041C\u041D\u041E\u041F\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042A\u042B\u042C\u042D\u042E\u042F\u30FC\u3002\u3001".toCharArray();

    public static String specialLowerCase(String src) {
        for (int i = 0; i < LOWER.length; i++) {
            src = src.replace(UPPER[i], LOWER[i]);
        }
        return src;
    }

    private void readTest(BufferedReader input) throws IOException {
//        if(input==null)
//        throw new IllegalArgumentException(input.toString());

        String header = input.readLine(); //throws null pointer exception for an unknown reason, input is not null

        if (header==null) {
            questions.addElement(new Question("Header non read", "null"));
            return;
        }

        if (!header.startsWith(HEADER)) {
            //incorrect header
            questions.addElement(new Question("Unknown header", "huh"));
            return;
        }

        name = header.substring(HEADER.length());

        Question q;
        do {
            q = Question.read(input);
            if (q != null) {
                if (Settings.getInstance().getReverse()) {
                    q.reverse();
                }
                questions.addElement(q);
            } else {
                break;
            }
        } while (true);

    }

    public Test(BufferedReader input) throws IOException {
        readTest(input);

        //randomize if set
        if (Settings.getInstance().getShuffle()) {
            shuffle(questions);
        }
        startSize = questions.size();
    }

    public Test(Vector includedTests, Browser loader) throws IOException {
        Enumeration e = includedTests.elements();
        while (e.hasMoreElements()) {
            String fileName = (String) e.nextElement();
            BufferedReader reader=new BufferedReader((loader.openFile(fileName)));
            readTest(reader);
            reader.close();
        }        

        //randomize if set
        if (Settings.getInstance().getShuffle()) {
            shuffle(questions);
        }
        startSize = questions.size();
    }

    public String getResult() {
        return "Words count: " + startSize + ", steps count:" + stepCount + " ( " + wrongCount + " wrong)";
    }

    /**
     * This method will schelude the current step after specified number of
     * steps.
     */
    private void askAgain() {

        int desiredIndex = nextQuestionIndex + 6 + Settings.RND.nextInt(3);

        while (desiredIndex > questions.size()) {
            //schelude out of bounds, fill with random steps
            questions.addElement(questions.elementAt(Settings.RND.nextInt(questions.size())));
        }

        questions.insertElementAt(questions.elementAt(nextQuestionIndex - 1), desiredIndex);
    }

    public Question nextQuestion(int status) {
        if (status != QuestionDisplay.GOOD &&
                status != QuestionDisplay.FIRST &&
                (status & QuestionDisplay.SKIPPED) == 0) {
            wrongCount++;
            if(Settings.getInstance().getSmart()) {
                askAgain();
            }
        }

        Question q;
        do {
            if (questions.size() <= nextQuestionIndex) {
                return null;
            }
            q = (Question) questions.elementAt(nextQuestionIndex);
            nextQuestionIndex++;
        } while (q.skipDesired);

        stepCount++;
        return q;
    }

    /**
     * Swaps the elements at the specified positions in the specified list.
     * (If the specified positions are equal, invoking this method leaves
     * the list unchanged.)
     *
     * @param list The list in which to swap elements.
     * @param i the index of one element to be swapped.
     * @param j the index of the other element to be swapped.
     * @throws IndexOutOfBoundsException if either <tt>i</tt> or <tt>j</tt>
     *         is out of range (i &lt; 0 || i &gt;= list.size()
     *         || j &lt; 0 || j &gt;= list.size()).
     */
    private static void swap(Vector l, int i, int j) {
        Object x = l.elementAt(j);
        l.setElementAt(l.elementAt(i), j);
        l.setElementAt(x, i);
    }

    private static void shuffle(Vector list) {
        int size = list.size();

        for (int i = size; i > 1; i--) {
            swap(list, i - 1, Settings.RND.nextInt(i));
        }
    }
}
