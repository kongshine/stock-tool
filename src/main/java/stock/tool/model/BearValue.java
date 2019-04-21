package stock.tool.model;

public class BearValue {
    private String stockCode;
    private String date;
    private String reportDate;
    private String reportType;
    private String currency;
    private String standardDate;
    private Granularity q;
    private Granularity h_y;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStandardDate() {
        return standardDate;
    }

    public void setStandardDate(String standardDate) {
        this.standardDate = standardDate;
    }

    public Granularity getQ() {
        return q;
    }

    public void setQ(Granularity q) {
        this.q = q;
    }

    public Granularity getH_y() {
        return h_y;
    }

    public void setH_y(Granularity h_y) {
        this.h_y = h_y;
    }
}
