package stock.tool.model;

public class CalculateType {
    //累计
    private Double t;

    //累计同比: t_y2y
    private Double t_y2y;


    public Double getT() {
        return t;
    }

    public void setT(Double t) {
        this.t = t;
    }

    public Double getT_y2y() {
        return t_y2y;
    }

    public void setT_y2y(Double t_y2y) {
        this.t_y2y = t_y2y;
    }
}
