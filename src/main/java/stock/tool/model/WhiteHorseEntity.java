package stock.tool.model;

import java.util.ArrayList;
import java.util.List;

public class WhiteHorseEntity {

    private String stockCode;

    private String stockName;
    //行业
    private String industry;
    //0:默认，1:银行
    private Integer industryType = 0;

    //白马类型,0:困境型白马，1：景气型白马
    private Integer horseType;
    //描述
    private StringBuilder desc = new StringBuilder();

    //营业收入(同比增长率)(%)Operating revenue (year-on-year growth rate)
    private List<Float> revenueGrowthRate = new ArrayList<>();

    //净利润同比增长率 Annual growth rate of net profit
    private List<Float> netProfitGrowthRate = new ArrayList<>();

    //营业收入
    private List<Double> opRevenue = new ArrayList<>();

    //应收账款Accounts receivable
    private List<Double> acctReceivable = new ArrayList<>();

    //存货
    private List<Double> inventory = new ArrayList<>();

    //流动比率 Liquidity ratio
    private List<Float> lqRatio = new ArrayList<>();

    //扣除非经常性损益后的净利润同比增长率
    private List<Float> kfNetProfitGrowthRate = new ArrayList<>();

    //pe扣非分为点
    private Float pePos10;
    //pb不含商誉分为点
    private Float pbPos10;
    //股价
    private float stockPrice;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getIndustryType() {
        return industryType;
    }

    public void setIndustryType(Integer industryType) {
        this.industryType = industryType;
    }

    public Integer getHorseType() {
        return horseType;
    }

    public void setHorseType(Integer horseType) {
        this.horseType = horseType;
    }

    public List<Float> getRevenueGrowthRate() {
        return revenueGrowthRate;
    }

    public void setRevenueGrowthRate(Float revenueGrowthRate) {
        this.revenueGrowthRate.add(revenueGrowthRate);
    }

    public List<Float> getNetProfitGrowthRate() {
        return netProfitGrowthRate;
    }

    public void setNetProfitGrowthRate(Float netProfitGrowthRate) {
        this.netProfitGrowthRate.add(netProfitGrowthRate);
    }

    public List<Double> getOpRevenue() {
        return opRevenue;
    }

    public void setOpRevenue(Double opRevenue) {
        this.opRevenue.add(opRevenue);
    }

    public List<Double> getAcctReceivable() {
        return acctReceivable;
    }

    public void setAcctReceivable(Double acctReceivable) {
        this.acctReceivable.add(acctReceivable);
    }

    public List<Double> getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory.add(inventory);
    }

    public List<Float> getLqRatio() {
        return lqRatio;
    }

    public void setLqRatio(Float lqRatio) {
        this.lqRatio.add( lqRatio);
    }

    public Float getPePos10() {
        return pePos10;
    }

    public void setPePos10(Float pePos10) {
        this.pePos10 = Math.round(pePos10*100)/100f;
    }

    public Float getPbPos10() {
        return pbPos10;
    }

    public void setPbPos10(Float pbPos10) {
        this.pbPos10 = Math.round(pbPos10*100)/100f;
    }

    public float getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public List<Float> getKfNetProfitGrowthRate() {
        return kfNetProfitGrowthRate;
    }

    public void setKfNetProfitGrowthRate(Float kfNetProfitGrowthRate) {
        this.kfNetProfitGrowthRate.add(kfNetProfitGrowthRate);
    }

    public StringBuilder getDesc() {
        return desc;
    }

    public void setDesc(StringBuilder desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "{" +
                "stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", industry='" + industry + '\'' +
                ", desc='" + desc.toString() + '\'' +
                ", pePos10=" + pePos10 +
                "%, pbPos10=" + pbPos10 +
                "%, stockPrice=" + stockPrice +
                '}';
    }
}
