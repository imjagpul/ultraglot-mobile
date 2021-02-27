
import javax.microedition.lcdui.Canvas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stepan
 */
public class QwertyKeyboard extends Keyboard {
   private static final String[] qwertykeymap=new String[] {
        " 0",
        ".1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "!,';:[]<>",
       "#-/\\",
       "a\u00E1\u00E4\u00E0\u00E2\u00E6\u0430",
       "b\u0431",
       "c\u010D\u00E7\u0109\u0446\u0447",
       "d\u010F\u0434",
       "e\u00E9\u011B\u00EB\u00E8\u00EA\u00EB\u0435\u0451\u044D",
       "f\u0444",
       "g\u011D\u0433",
       "h\u0125\u0445",
       "i\u00ED\u00EE\u00EF\u0438",
       "j\u0135\u0439",
       "k\u043A",
       "l\u043B",
       "m\u043C",
       "n\u0148\u00F1\u043D",
       "o\u00F3\u00F6\u00F4\u0153\u043E",
       "p\u043F",
       "q",
       "r\u0159\u0440",
       "s\u0161\u00DF\u015D\u0441\u0448\u0449",
       "t\u0165\u0442",
       "u\u00FA\u016F\u00FC\u00FB\u00F9\u016D\u0443",
       "v\u0432",
       "w\u044F\u044E",
       "x\u044C\u044A",
       "y\u00FD\u00FF\u044B",
       "z\u0437\u0436"
    };
    
    protected String[] getKeymap() {
        return qwertykeymap;
    }

    protected int codeToIndex(int keycode) {
        if (keycode == Canvas.KEY_STAR) {
            return 10;
        } else if (keycode == Canvas.KEY_POUND) {
            return 11;
        } else if (keycode >= Canvas.KEY_NUM0 && keycode <= Canvas.KEY_NUM9) {
            return keycode - Canvas.KEY_NUM0;
        } else if (keycode >= (char)'a' && keycode <= 'z') {
            return 12+ keycode - (char)'a';
        } else {
            return -1;
        }
    }
}
