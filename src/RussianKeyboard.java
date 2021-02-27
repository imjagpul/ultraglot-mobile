public class RussianKeyboard extends Keyboard {
    
    private static final String[] russiankeymap=new String[] {
        " 0",
        " .!,;-:[]<>/\\'1",
        "\u0430\u0431\u0446\u04472",
        "\u0434\u0435\u0451\u044D\u04443",
        "\u0433\u0445\u04384",
        "\u0439\u043A\u043B5",
        "\u043C\u043D\u043E6",
        "\u043F\u0440\u0441\u0448\u04497",
        "\u0442\u0443\u04328",
        "\u0437\u044B\u04369",
        "\u044C\u044A*",
        "\u044F\u044E#"};
    
    protected String[] getKeymap() {
        return russiankeymap;
    }    
}