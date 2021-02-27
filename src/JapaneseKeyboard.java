
import javax.microedition.lcdui.Canvas;

/*
 * JapaneseKeyboard.java
 *
 * Created on 31. srpen 2007, 13:25
 */

/**
 *
 * @author Stepan
 */
public class JapaneseKeyboard extends Keyboard {
    private static final String[] hiraganaKeymap=new String[] {
        " \u3093\u308F\u3092\u3001\u3002\u30FC\u300C\u300D\u3083\u3085\u3087",
        "\u3042\u3044\u3046\u3048\u304A",
        "\u304B\u304D\u304F\u3051\u3053",
        "\u3055\u3057\u3059\u305B\u305D",
        "\u305F\u3061\u3064\u3066\u3068",
        "\u306A\u306B\u306C\u306D\u306E",
        "\u306F\u3072\u3075\u3078\u307B",
        "\u307E\u307F\u3080\u3081\u3082",
        "\u3084\u3086\u3088",
        "\u3089\u308A\u308B\u308C\u308D"};

    private static final String[] hiraganaNigoriKeymap=new String[] {
        " \u3093\u308F\u3092\u3001\u3002\u30FC\u300C\u300D\u3083\u3085\u3087",
        "\u3042\u3044\u3046\u3048\u304A",
        "\u304C\u304E\u3050\u3052\u3054",
        "\u3056\u3058\u305A\u305C\u305E",
        "\u3060\u3062\u3065\u3067\u3069",
        "\u306A\u306B\u306C\u306D\u306E",
        "\u3070\u3073\u3076\u3079\u307C",
        "\u307E\u307F\u3080\u3081\u3082",
        "\u3083\u3085\u3087",
        "\u3089\u308A\u308B\u308C\u308D"};

    private static final String[] hiraganaMaruKeymap=new String[] {
        " \u3093\u308F\u3092\u3001\u3002\u30FC\u300C\u300D\u3083\u3085\u3087",
        "\u3042\u3044\u3046\u3048\u304A",
        "\u304B\u304D\u304F\u3051\u3053",
        "\u3055\u3057\u3059\u305B\u305D",
        "\u305F\u3061\u3064\u3066\u3068",
        "\u306A\u306B\u306C\u306D\u306E",
        "\u3071\u3074\u3077\u307A\u307D",
        "\u307E\u307F\u3080\u3081\u3082",
        "\u3084\u3086\u3088",
        "\u3089\u308A\u308B\u308C\u308D"};

    private static final String[] katakanaKeymap=new String[] {
        " \u30F3\u30EF\u30F2\u3001\u3002\u300C\u300D\u30A1\u30A3\u30F4\u30A7\u30A9",
        "\u30A2\u30A4\u30A6\u30A8\u30AA",
        "\u30AB\u30AD\u30AF\u30B1\u30B3",
        "\u30B5\u30B7\u30B9\u30BB\u30BD",
        "\u30BF\u30C1\u30C4\u30C6\u30C8",
        "\u30CA\u30CB\u30CC\u30CD\u30CE",
        "\u30CF\u30D2\u30D5\u30D8\u30DB",
        "\u30DE\u30DF\u30E0\u30E1\u30E2",
        "\u30E4\u30E6\u30E8",
        "\u30E9\u30EA\u30EB\u30EC\u30ED"};

    private static final String[] katakanaNigoriKeymap=new String[] {
        " \u30F3\u30EF\u30F2\u3001\u3002\u300C\u300D\u30A1\u30A3\u30F4\u30A7\u30A9",
        "\u30A2\u30A4\u30A6\u30A8\u30AA",
        "\u30AC\u30AE\u30B0\u30B2\u30B4",
        "\u30B6\u30B8\u30BA\u30BC\u30BE",
        "\u30C0\u30C2\u30C5\u30C7\u30C9",
        "\u30CA\u30CB\u30CC\u30CD\u30CE",
        "\u30D0\u30D3\u30D6\u30D9\u30DC",
        "\u30DE\u30DF\u30E0\u30E1\u30E2",
        "\u30E3\u30E5\u30E7",
        "\u30E9\u30EA\u30EB\u30EC\u30ED"};

    private static final String[] katakanaMaruKeymap=new String[] {
        " \u30F3\u30EF\u30F2\u3001\u3002\u300C\u300D\u30A1\u30A3\u30F4\u30A7\u30A9",
        "\u30A2\u30A4\u30A6\u30A8\u30AA",
        "\u30AB\u30AD\u30AF\u30B1\u30B3",
        "\u30B5\u30B7\u30B9\u30BB\u30BD",
        "\u30BF\u30C1\u30C4\u30C6\u30C8",
        "\u30CA\u30CB\u30CC\u30CD\u30CE",
        "\u30D1\u30D4\u30D7\u30DA\u30DD",
        "\u30DE\u30DF\u30E0\u30E1\u30E2",
        "\u30E4\u30E6\u30E8",
        "\u30E9\u30EA\u30EB\u30EC\u30ED"};
    
    // * - switch nigori, maru, nothing
    // # - switch katakana/hiragana
    
    private boolean hiragana=true;
    private int mode=0;
    
    protected String[] getKeymap() {
        switch(mode) {
            case 0: return hiragana ? hiraganaKeymap : katakanaKeymap;
            case 1: return hiragana ? hiraganaNigoriKeymap  : katakanaNigoriKeymap;
            case 2: return hiragana ? hiraganaMaruKeymap : katakanaMaruKeymap;
        }
        throw new IllegalStateException("invalid mode value");
    }

    public void keyPressed(int keycode) {
        if(keycode==Canvas.KEY_POUND) {
            mode++;
            if(mode==3) mode=0;
            
        } else if(keycode==Canvas.KEY_STAR) {
            hiragana=!hiragana;
        } else {
            super.keyPressed(keycode);            
        }        
    }

    protected void onInsertChar(int cursor) {
        //reset mode
        mode=0;        
    }

    
    
    public boolean t9KeyPressed(int keycode, String answer) {
        int index = codeToIndex(keycode);

        //unmapped key - ignore
        //TODO deactivate cursor
        if (index == -1) {
            return false; //pressing of unmapped key is not considered a mistake
        }

        if (!answer.startsWith(stringBuffer.toString())) {
            //could happen if user input method changes from normal to t9
            //or during test reset
            clear();
        }

        if (stringBuffer.length() == answer.length()) {
            //user attempts to enter longer word then the correct answer is
            return true;
        }
        
        char desiredChar = answer.charAt(stringBuffer.length());
        if (hiraganaKeymap[index].indexOf(desiredChar) != -1 ||
                hiraganaMaruKeymap[index].indexOf(desiredChar) != -1 ||
                hiraganaNigoriKeymap[index].indexOf(desiredChar) != -1 ||
                katakanaKeymap[index].indexOf(desiredChar) != -1 ||
                katakanaMaruKeymap[index].indexOf(desiredChar) != -1 ||
                katakanaNigoriKeymap[index].indexOf(desiredChar) != -1) {
            //character is mapped on the pressed key - so append it
            stringBuffer.append(desiredChar);
            return false;
        } else {
            //incorrect key pressed - mistake
            return true;
        }
    }
    
    
    
}
