
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Canvas;
/*
 * Keyboard.java
 *
 * Created on 29. srpen 2007, 16:26
 */

/**
 *
 * @author Stepan
 */
public class Keyboard {

    /** Special characters (skipped in both MODE_T9_NOSPACE and MODE_T9_NOSPECIAL). */
    private static final String WHITESPACE = " ";
    /** Special characters (skipped in MODE_T9_NOSPECIAL). */
    private static final String SPECIAL = "!,';:[]<>#-/\\";
    protected StringBuffer stringBuffer = new StringBuffer();
    protected int cursor = 0;
    protected boolean cursorActive = false;
    protected final Timer timer = new Timer();
    private TimerTask task = null;
    private static final String[] czkeymap = new String[]{
        " 0",
        ".1",
        "abcáè2",
        "defïìé3",
        "ghií4",
        "jkl5",
        "mnoòó6",
        "pqrsøš7",
        "tuvùú8",
        "wxyzı9",
        "!,';:[]<>",
        "#-/\\"
    };

    protected String[] getKeymap() {
        return czkeymap;
    }

    public String getCurrentString() {
        return stringBuffer.toString();
    }

    public int getCursorPosition() {
        return cursor;
    }

    public void backspace() {
        deactivateAndMoveCursor();

        if (cursor != 0) {
            stringBuffer.deleteCharAt(cursor - 1);
        }

        if (cursor != 0) {
            cursor--;
        }
    }

    public void del() {
        cursorActive = false;

        if (cursor != stringBuffer.length()) {
            stringBuffer.deleteCharAt(cursor);
        }
    }

    public void clear() {
        cursorActive = false;

        stringBuffer.setLength(0);
        cursor = 0;
    }

    public void left() {
        cursorActive = false;

        if (cursor != 0) {
            cursor--;
        }
    }

    public void right() {
        cursorActive = false;

        if (cursor != stringBuffer.length()) {
            cursor++;
        }
    }

    protected int codeToIndex(int keycode) {
        if (keycode == Canvas.KEY_STAR) {
            return 10;
        } else if (keycode == Canvas.KEY_POUND) {
            return 11;
        } else if (keycode >= Canvas.KEY_NUM0 && keycode <= Canvas.KEY_NUM9) {
            return keycode - Canvas.KEY_NUM0;
        } else {
            return -1;
        }
    }

    private void deactivateAndMoveCursor() {
        synchronized (timer) {
            if (cursorActive) {
                cursorActive = false;

                onInsertChar(cursor);

                cursor++;
                Browser.getInstance().getBook().repaint();
            }
        }
    }

    protected void onInsertChar(int cursor) {
    }

    /**
     * Resets timer. The lock on timer must be held when calling this method.
     */
    protected void resetTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        task = new TimerTask() {

            public void run() {
                deactivateAndMoveCursor();
            }
        };
        timer.schedule(task, Settings.getInstance().getCursorDelay());

    }
    //TODO : udelat keymap, Timer na active cursor
    //pak se udela ifactive(), a render box
    public void keyPressed(int keycode) {
        int index = codeToIndex(keycode);

        //unmapped key - ignore
        //TODO deactivate cursor
        if (index == -1) {
            return;
        }

        String map = getKeymap()[index];

        synchronized (timer) {
            if (cursorActive) {
                int current = map.indexOf(stringBuffer.charAt(cursor));
                if (current == -1) { //different key
                    onInsertChar(cursor);

                    cursor++;
                    stringBuffer.insert(cursor, map.charAt(0));
                } else { //same key more times
                    stringBuffer.setCharAt(cursor, map.charAt((current + 1) % map.length()));
                }
            } else {
                stringBuffer.insert(cursor, map.charAt(0));
            }
            cursorActive = true;

            resetTimer();
        }
    }

    /**
     * 
     * @param keycode       the keycode pressed
     * @param answer        the correct answer
     * @return              true if a mistake was made, false otherwise
     */
    public boolean t9KeyPressed(int keycode, String answer) {
        int index = codeToIndex(keycode);

        //unmapped key - ignore
        //TODO deactivate cursor
        if (index == -1) {
            return false; //pressing of unmapped key is not considered a mistake
        }

        String map = getKeymap()[index];


        if (!answer.startsWith(stringBuffer.toString())) {
            //could happen if user input method changes from normal to t9
            //or during test reset
            clear();
        }

        if (stringBuffer.length() == answer.length()) {
            //user attempts to enter longer word then the correct answer is
            return true;
        }

        while (true) {
            char desiredChar = answer.charAt(stringBuffer.length());
            if ((Settings.getInstance().getMode() == Settings.MODE_T9_NOSPECIAL &&
                    (SPECIAL.indexOf(desiredChar) != -1)) || ((Settings.getInstance().getMode() == Settings.MODE_T9_NOSPECIAL ||
                    Settings.getInstance().getMode() == Settings.MODE_T9_NOSPACE) &&
                    (WHITESPACE.indexOf(desiredChar) != -1))) {
                stringBuffer.append(desiredChar);
                continue;
            }

            if (map.indexOf(desiredChar) != -1 ||
                    Test.specialLowerCase(map.toLowerCase()).indexOf(
                    Test.specialLowerCase(Character.toLowerCase(desiredChar) + "")) != -1) {
                //character is mapped on the pressed key - so append it
                stringBuffer.append(desiredChar);
                return false;
            } else {
                //incorrect key pressed - mistake
                return true;
            }
        }
    }
}
