package stock.tool.model;

import java.util.List;

/**
 * 理性仁API的请求数据
 * @author kong
 */
public class LxerReq {
    //我的Token页有用户专属且唯一的Token,必填
    private String token = LxerTokens.token();

    /**
     * string : latest | YYYY-MM-DD (北京时间)
     * 信息结束时间。传入latest会得到最近1.1年内的最新数据，
     * 而传入一个指定的日期会得到指定日期的数据，对于财报数据，这个值是每年的四个季度的最后天的时间点，
     * 比如： 2017-03-31, 2017-06-30, 2017-09-30, 2017-12-31。
     * 需要注意的是，startDate和date至少要传一个。
     */
    private String date;

    /**
     * 	string : YYYY-MM-DD (北京时间)
     * 信息起始时间。传此值可以得到一定时间范围内的数据。
     * 对于财报数据，这个值是每年的四个季度的最后天的时间点，
     * 比如： 2017-03-31, 2017-06-30, 2017-09-30, 2017-12-31。
     * 需要注意的是，startDate和date至少要传一个。
     */
//    private String startDate;

    /**
     *	string : YYYY-MM-DD (北京时间)
     * 信息结束时间。传此值可以得到一定时间范围内的数据。
     * 默认是当前最新时间。对于财报数据，这个值是每年的四个季度的最后天的时间点，
     * 比如： 2017-03-31, 2017-06-30, 2017-09-30, 2017-12-31
     */
//    private String endDate;

    /**
     * 必填
     * 股票代码数组，stockCodes长度>=1且<=100, 格式如下：["600511","600519"]。
     * 请参考股票信息API获取合法的stockCodes。
     * 需要注意的是，当传入startDate时只可以传入一个股票代码。
     */
    private List<String> stockCodes;

    /**
     * 必填
     * 指标数组，指标格式为[granualrity].[tableName].[fieldName].[expressionCaculateType],
     * 例如：['profitStatement.toi.t', 'profitStatement.npatootpc.t_y2y']。
     * 需要注意的是，当stockCodes长度大于1时最多只能选取48个指标。
     */
    private List<String> metrics;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public String getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }
//
//    public String getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(String endDate) {
//        this.endDate = endDate;
//    }

    public List<String> getStockCodes() {
        return stockCodes;
    }

    public void setStockCodes(List<String> stockCodes) {
        this.stockCodes = stockCodes;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }
}
