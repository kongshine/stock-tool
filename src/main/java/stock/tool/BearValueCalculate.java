package stock.tool;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.commons.codec.binary.StringUtils;
import stock.tool.model.*;
import stock.tool.util.JerseyClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class BearValueCalculate {
    public final static String startYear = "2009";
    public final static String endYear = "2018";
    //净利润
    public static LinkedHashMap<String, List<CalNode>> npMap = new LinkedHashMap<>();
    //资本开支
    public static LinkedHashMap<String, List<CalNode>> cpMap = new LinkedHashMap<>();
    //自由现金流量
    public static LinkedHashMap<String, List<CalNode>> fcfMap = new LinkedHashMap<>();
    //三费占比
    public static LinkedHashMap<String, List<CalNode>> sfMap = new LinkedHashMap<>();

    public static void main(String[] args) {
        LxerRangeReq req = new LxerRangeReq();
        req.setStartDate(startYear+"-03-31");
        req.setEndDate(endYear+"-12-31");
        List<String> codes = Arrays.asList(
                "600220"
        );
        req.setStockCodes(codes);
        req.setMetrics(Arrays.asList(
                "h_y.metrics.fcf.t",//自由现金流
                "h_y.cashFlow.cpfpfiaolta.t",//资本开支
                "h_y.profitStatement.npatoshopc.t",//净利润
                "h_y.profitStatement.te_r.t"//三费占比
        ));

        LxrResult<BearValue> result = JerseyClient.post(JSONObject.toJSON(req), LxerURL.industry);
        if(result.getCode() != 0){
            System.out.println("error:"+result.getMsg());
            return;
        }

        List<BearValue> list = result.getData(BearValue.class);
        list.forEach(bv ->{
            //第四季度
            if("annual_report".equalsIgnoreCase(bv.getReportType())){
                String sc = bv.getStockCode();
                String date = bv.getDate().substring(0,10);
                if(!npMap.containsKey(sc)){
                    npMap.put(sc,new ArrayList<CalNode>());
                }
                Double npValue = bv.getH_y().getProfitStatement().getNpatoshopc().getT();
                npMap.get(sc).add(new CalNode(date,npValue));

                if(!cpMap.containsKey(sc)){
                    cpMap.put(sc,new ArrayList<CalNode>());
                }
                Double cpValue = bv.getH_y().getCashFlow().getCpfpfiaolta().getT();
                cpMap.get(sc).add(new CalNode(date,cpValue));

                if(!fcfMap.containsKey(sc)){
                    fcfMap.put(sc,new ArrayList<CalNode>());
                }
                Double fcfValue = bv.getH_y().getMetrics().getFcf().getT();
                fcfMap.get(sc).add(new CalNode(date,fcfValue));

                if(!sfMap.containsKey(sc)){
                    sfMap.put(sc,new ArrayList<CalNode>());
                }
                Double sfValue = bv.getH_y().getProfitStatement().getTe_r().getT();
                sfMap.get(sc).add(new CalNode(date,sfValue));
            }
        });

        for(String code : codes){
            Double fcf10 = 0d;
            Double zbkz10 = 0d;
           Double netProfiteGrow = 0d;
            Double sfRate = 0d;
            int yearCount = 0;
           for(CalNode cn : fcfMap.get(code)){
               fcf10 += cn.getValue();
           }
           for(CalNode cn : cpMap.get(code)){
               zbkz10 += cn.getValue();
           }
           Double start = 0d,end = 0d;
           for(CalNode cn : npMap.get(code)){
               if(cn.getDate().contains(startYear)){
                   start = cn.getValue();
               }
               if(cn.getDate().contains(endYear)){
                   end = cn.getValue();
               }
           }
           netProfiteGrow = end - start;

            for(CalNode cn : sfMap.get(code)){
                double rate = cn.getValue();
                String date = cn.getDate().substring(0,4);
                System.out.print(date+":"+Math.round(rate*10000)/100d+"%, ");
                sfRate += rate;
                yearCount ++;
            }
           System.out.println();
           System.out.println("股票："+code);
           System.out.println("10年自由现金流总和："+fcf10.longValue()+", 10资本开支总和："+zbkz10.longValue()
           +", 净利润增长值: "+netProfiteGrow.longValue());
           float basicVal = Math.round(fcf10/zbkz10*100);
           float growthVal = Math.round(netProfiteGrow/zbkz10*100);
           double sfAvgRate = Math.round(sfRate/yearCount*10000)/100d;
           System.out.println("小熊基本值："+basicVal+"%, 小熊增长值："+growthVal+"%，三费占比："+
                   sfAvgRate+"%");
           System.out.println("----------------------------------------");
        }

    }
}
