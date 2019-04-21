package stock.tool.model;

/**
 * 利润表
 */
public class ProfitStatement {

    //归属于母公司普通股股东的净利润
    private CalculateType npatoshopc;
    //三费占比率
    private CalculateType te_r;

    public CalculateType getNpatoshopc() {
        return npatoshopc;
    }

    public void setNpatoshopc(CalculateType npatoshopc) {
        this.npatoshopc = npatoshopc;
    }

    public CalculateType getTe_r() {
        return te_r;
    }

    public void setTe_r(CalculateType te_r) {
        this.te_r = te_r;
    }
}
