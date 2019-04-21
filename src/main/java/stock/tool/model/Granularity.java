package stock.tool.model;

/**
 * 季度: q
 * 半年: h_y
 */
public class Granularity {

    private ProfitStatement profitStatement;

    private BalanceSheet balanceSheet;

    private CashFlow cashFlow;

    private Metrics metrics;

    public ProfitStatement getProfitStatement() {
        return profitStatement;
    }

    public void setProfitStatement(ProfitStatement profitStatement) {
        this.profitStatement = profitStatement;
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public CashFlow getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(CashFlow cashFlow) {
        this.cashFlow = cashFlow;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }
}
