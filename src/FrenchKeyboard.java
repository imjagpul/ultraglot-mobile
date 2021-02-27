public class FrenchKeyboard extends Keyboard {
    
    private static final String[] french=new String[] {
        " 0",
        ".1",    
        "abc\u00E0\u00E2\u00E6\u00E72",
        "def\u00E9\u00E8\u00EA\u00EB3",
        "ghi\u00EE\u00EF4",
        "jkl5",
        "mno\u00F4\u0153\u00F16",
        "pqrs7",
        "tuv\u00FB\u00F9\u00FC8",
        "wxyz\u00FF9",
        "!,';:[]<>-/\\",
        "#-/\\"};
    
    protected String[] getKeymap() {
        return french;
    }    
}
