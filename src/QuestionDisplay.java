/*
 * QuestionDisplay.java
 *
 * Created on 28. srpen 2007, 14:24
 */

import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Stepan
 */
public class QuestionDisplay extends Canvas {

    private Test activeTest = null;
    private Question currentQuestion;
    private FontUtilWrapping fontUtil;
    private Keyboard keyboard = new Keyboard();
    private int status = GOOD;
    public static final int FIRST = 8;
    public static final int GOOD = 0;
    public static final int HELP_USED = 1;
    public static final int WRONG_ANSWER = 2;
    public static final int SKIPPED = 4;
    private boolean helpDisplayed = false;
    private boolean wrongSecondaryColor = false;

    /** Creates a new instance of QuestionDisplay */
    public QuestionDisplay() {
        try {
            setBitmapFont(FontUtilWrapping.initialize(getClass().getResourceAsStream("ANCZEoRuFrDeKana_16.font")));
//            setBitmapFont(FontUtilWrapping.initialize(getClass().getResourceAsStream("czech.font")));
//            fontUtil = FontUtilWrapping.initialize(getClass().getResourceAsStream("czech.font"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        currentQuestion = new Question("Aha", "!");
        setFullScreenMode(true);
    }
//    private static final int BLEND_SRC = 0xFFFFFF;
    private static final int BLEND_SRC = 0;
    private Image questionImage = null;

    private void clearCache() {
        questionImage = null;
    }

    public void setBitmapFont(FontUtilWrapping fontUtil) {
        this.fontUtil = fontUtil;
    }

    protected void paint(Graphics graphics) {
        int bgcolor = Settings.getInstance().getColor(Settings.COLOR_BG);
        graphics.setColor(bgcolor);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        //if help is displayed paint help only
        if (helpDisplayed) {
            Image img = fontUtil.renderColoredTextWrapping(currentQuestion.getAnswer(), getWidth(), BLEND_SRC, bgcolor, Settings.getInstance().getColor(Settings.COLOR_HELP));
            graphics.drawImage(img, 0, 0, 0);
            return;
        }

        //draw question string
        Image img;
        if (questionImage == null) {
            if (Settings.getInstance().getBlend()) {
                questionImage = fontUtil.renderColoredTextWrapping(currentQuestion.getQuestion(), getWidth(), BLEND_SRC, bgcolor, Settings.getInstance().getColor(Settings.COLOR_FG));
            } else {
                questionImage = fontUtil.renderTextWrapping(currentQuestion.getQuestion(), getWidth());
            }
        }
        img = questionImage;

        int split = Math.max(getHeight() / 2, img.getHeight());

        graphics.drawImage(img, 0, 0, 0);

        //draw user input string
        String s = keyboard.getCurrentString();
        if (s.length() > 0) {
            img = fontUtil.renderColoredTextWrapping(s, getWidth(), BLEND_SRC, bgcolor, Settings.getInstance().getColor(Settings.COLOR_ANSWER));
            graphics.drawImage(img, 0, split, 0);
        }

        //draw status indicator
        if (status != GOOD && status != FIRST) {
            if ((status & WRONG_ANSWER) > 0) {
                if (wrongSecondaryColor) {
                    graphics.setColor(Settings.getInstance().getColor(Settings.COLOR_INDICATOR_WRONG_2));
                } else {
                    graphics.setColor(Settings.getInstance().getColor(Settings.COLOR_INDICATOR_WRONG));
                }
                graphics.drawLine(0, split, getWidth(), split);
            } else if ((status & HELP_USED) > 0) {
                graphics.setColor(Settings.getInstance().getColor(Settings.COLOR_INDICATOR_HELP));
                graphics.drawLine(0, split, getWidth(), split);
            }

        }

        //draw cursor line
        if (Settings.getInstance().getMode() == Settings.MODE_NORMAL) {

            //BUG : blbne kurzor, pri zalamovani (kratke slovo, pak dlouhe slovo
            //      - kurzor je o radek vys uz v tom zalomeni v mezerach
            //     (protoze pri testovani se tam dava pdretezec do mista kurzoru

            int cx = fontUtil.stringWidthWrapped(keyboard.getCurrentString().substring(0, keyboard.getCursorPosition()), getWidth());
            int cy = split + (fontUtil.stringLineCountWrapped(keyboard.getCurrentString().substring(0, keyboard.getCursorPosition()), getWidth())) * fontUtil.getFontHeight();
            graphics.setColor(Settings.getInstance().getColor(Settings.COLOR_CURSOR));
            graphics.drawLine(cx, cy - fontUtil.getFontHeight(), cx, cy);
        }
    }

    public void updateFromMenu() {
        clearCache();
    }

    public boolean isTestLoaded() {
        return activeTest != null;
    }

    public void loadFile(BufferedReader input) throws IOException {
        activeTest = new Test(input);

        clearCache();
        currentQuestion = activeTest.nextQuestion(FIRST);
        status = FIRST;
    }

    public void loadFiles(Vector includedTests, Browser loader) throws IOException {
        activeTest = new Test(includedTests, loader);

        clearCache();
        currentQuestion = activeTest.nextQuestion(FIRST);
        status = FIRST;
    }

    private void markWrongAnswer() {
        status |= WRONG_ANSWER;
        wrongSecondaryColor = !wrongSecondaryColor;
    }

    private void moveNext() {
        keyboard.clear();
        currentQuestion = activeTest.nextQuestion(status);

        clearCache();

        //reset status
        status = GOOD;

        //check if test is over
        if (currentQuestion == null) {
            Alert alert = new Alert("Test done", activeTest.getResult(), null, AlertType.INFO);
            alert.setTimeout(Alert.FOREVER);
            Browser.getInstance().getDisplay().setCurrent(alert, Browser.getInstance().getListFileBrowser());
        }
    }

    private void enter() {
//        if(currentQuestion.getAnswer().equalsIgnoreCase(keyboard.getCurrentString())) {

        //if (Test.specialLowerCase(currentQuestion.getAnswer()).equalsIgnoreCase(Test.specialLowerCase(keyboard.getCurrentString()))) {
        if (currentQuestion.isCorrect(keyboard.getCurrentString())) {
            moveNext();
        } else {
            markWrongAnswer();
        }
    }

    private void help() {
        status |= HELP_USED;
        helpDisplayed = true;
    }

    private void skip() {
        currentQuestion.skipDesired = true;
        status |= SKIPPED;
        moveNext();
    }

    private void menu() {
        Browser.getInstance().getDisplay().setCurrent(Browser.getInstance().getListFileBrowser());
    }

    protected void keyPressed(int keycode) {
        if (helpDisplayed) {
            helpDisplayed = false;

            if (Settings.getInstance().getMode() != Settings.MODE_FLASHCARD ||
                    keycode == Settings.getInstance().getKeyMapping(Settings.HELP)) {
                repaint();
                return;
            }
        }

        if (keycode == Settings.getInstance().getKeyMapping(Settings.MENU)) {
            menu();
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.HELP)) {
            help();
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.SKIP)) {
            skip();
        } else if (Settings.getInstance().getMode() == Settings.MODE_FLASHCARD) {
            if (keycode == Settings.getInstance().getKeyMapping(Settings.ENTER)) {
                //correct answer
                status = status & ~HELP_USED; //remove help used bit when using flashcards
                moveNext();
            } else {
                //incorrect answer
                status |= WRONG_ANSWER;
                moveNext();
            }


            repaint();
            return;
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.ENTER)) {
            enter();
        } else if (Settings.getInstance().isT9()) {
            boolean mistake = keyboard.t9KeyPressed(keycode, currentQuestion.getAnswer());
            if (mistake) {
                markWrongAnswer();
            }
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.CLEAR)) {
            keyboard.clear();
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.BACKSPACE)) {
            keyboard.backspace();
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.DEL)) {
            keyboard.del();
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.LEFT)) {
            keyboard.left();
        } else if (keycode == Settings.getInstance().getKeyMapping(Settings.RIGHT)) {
            keyboard.right();
        } else {
            keyboard.keyPressed(keycode);
        }

        repaint();
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }
}
