/*
 * EsperantoKeyboard.java
 *
 * Created on 31. srpen 2007, 13:11
 */

/**
 *
 * @author Stepan
 */
public class EsperantoKeyboard extends Keyboard {
    
    private static final String[] eokeymap=new String[] {
        " 0",
        ".1",
        "abc\u01092",
        "def3",
        "ghi\u011D\u01254",
        "jkl\u01355",
        "mno6",
        "pqrs\u015D7",
        "tuv\u016D8",
        "wxyz9",
        "!,';:[]<>-/\\",
        "#-/\\"};
    
    protected String[] getKeymap() {
        return eokeymap;
    }
    
}