package stock.tool.model;

import stock.tool.util.JsonUtil;

import java.io.Serializable;

public class Lixinger implements Serializable {
    //股票代码
    private String stockCode;

    private String date;

    // PE-TTM
    private float pe_ttm;

    //PE-TTM分位点(10年)
    private float pe_ttm_pos10;

    //PE-TTM(扣非)分位点(10年)
    private float d_pe_ttm_pos10;

    //PB分位点(10年),%
    private float pb_pos10;

    //PB(不含商誉)分位点(10年)
    private float pb_wo_gw_pos10;

    //市值
    private long mc;

    //股价
    private float sp;

    //股息率,%
    private float dyr;

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

    public float getPe_ttm() {
        return pe_ttm;
    }

    public void setPe_ttm(float pe_ttm) {
        this.pe_ttm = Math.round(pe_ttm*100)/100f;
    }

    public float getPb_pos10() {
        return pb_pos10;
    }

    public void setPb_pos10(float pb_pos10) {
        this.pb_pos10 = Math.round(pb_pos10*10000)/100f;
    }

    public long getMv() {
        return mc;
    }

    public void setMv(long mv) {
        this.mc = mv;
    }

    public float getSp() {
        return sp;
    }

    public void setSp(float sp) {
        this.sp = sp;
    }

    public float getDyr() {
        return dyr;
    }

    public void setDyr(float dyr) {
        this.dyr = Math.round(dyr*10000)/100f;
    }

    public float getD_pe_ttm_pos10() {
        return d_pe_ttm_pos10;
    }

    public void setD_pe_ttm_pos10(float d_pe_ttm_pos10) {
        this.d_pe_ttm_pos10 =  Math.round(d_pe_ttm_pos10*10000)/100f;
    }

    public float getPb_wo_gw_pos10() {
        return pb_wo_gw_pos10;
    }

    public void setPb_wo_gw_pos10(float pb_wo_gw_pos10) {
        this.pb_wo_gw_pos10 = Math.round(pb_wo_gw_pos10*10000)/100f;
    }

    public float getPe_ttm_pos10() {
        return pe_ttm_pos10;
    }

    public void setPe_ttm_pos10(float pe_ttm_pos10) {
        this.pe_ttm_pos10 = Math.round(pe_ttm_pos10*10000)/100f;
    }

    @Override
    public String toString() {
        return "Lixinger{" +
                "股票代码='" + stockCode + '\'' +
                ", 股票名称='" + JsonUtil.getStockName(stockCode) + '\'' +
                ", date='" + date + '\'' +
                ", PE-TTM=" + pe_ttm +
                ", PE-TTM分位点=" + pe_ttm_pos10 +
                "%, PE-TTM扣非分位点=" + d_pe_ttm_pos10 +
                "%, PB分位点(10年)=" + pb_pos10 +
                "%, PB(不含商誉)分位点(10年)=" + pb_wo_gw_pos10 +
                "%, 市值=" + mc +
                ", 股价=" + sp +
                ", 股息率=" + dyr +'%'+
                '}';
    }
}
