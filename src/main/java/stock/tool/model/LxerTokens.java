package stock.tool.model;

public enum LxerTokens {
    WX_CHEN_JIE("0398b575-eebe-4860-a21c-b5b2777542b2"),
    QQ_MINE("e91ac181-ab4a-4a7b-99d3-f3817ff8cab1")


    ;
    private String token;

    private LxerTokens(String token){
        this.token = token;
    }

    public static String token(){
        return WX_CHEN_JIE.token;
    }
}
