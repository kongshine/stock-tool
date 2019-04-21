package stock.tool;

import stock.tool.model.*;
import stock.tool.util.ExlsUtil;
import stock.tool.util.JerseyClient;
import stock.tool.util.StockUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WhiteHorseStock {

    //每次执行，修改为最后一个交易日期
    public static final String lastTradeDate="2019-04-01";
    public static final String XLS_PATH="whitehorse\\2019-04-02.xls";

    public static void main(String[] args) throws IOException {
        List<WhiteHorseEntity> wheList = ExlsUtil.read(XLS_PATH);
        final List<WhiteHorseEntity> result = new ArrayList<>();
        for(WhiteHorseEntity entity : wheList){
            if(StockUtil.isWhiteHorseStock(entity)){
                result.add(entity);
            }
        }
        Map<String,WhiteHorseEntity> whsMap = new HashMap<>();
        List<String> stockCodes = new ArrayList<>();
        result.forEach(e->{
            String scode = e.getStockCode().substring(0,6);
            stockCodes.add(scode);
            whsMap.put(scode, e);
            System.out.println(e);
        });

        LxerReq req = new LxerReq();
        req.setToken(LxerTokens.WX_CHEN_JIE.token());
        req.setDate(lastTradeDate);
        req.setStockCodes(stockCodes);
        req.setMetrics(Arrays.asList("d_pe_ttm_pos10","pb_wo_gw_pos10","sp"));

        LxrResult<Lixinger> lxrResult = JerseyClient.post(req, LxerURL.fundamental);
        if(lxrResult.getCode() != 0)
            return;

        lxrResult.getData(Lixinger.class).forEach(r ->{
            float pe_pos =r.getD_pe_ttm_pos10(),pb_pos=r.getPb_wo_gw_pos10();
            if(pe_pos>50 || pb_pos>50){
                WhiteHorseEntity whe = whsMap.remove(r.getStockCode());
                System.out.println("删除："+whe.getStockName()+"，PE分为点:"+pe_pos+",PB分为点:"+pb_pos);
            }else {
                WhiteHorseEntity whe = whsMap.get(r.getStockCode());
                whe.setPePos10(r.getD_pe_ttm_pos10());
                whe.setPbPos10(r.getPb_wo_gw_pos10());
                whe.setStockPrice(r.getSp());
                whe.setHorseType(getHorseType(whe));
            }
        });
        //按PE分为点生序
        WhiteHorseEntity[] finalList = whsMap.values().toArray(new WhiteHorseEntity[0]);
        Arrays.sort(finalList, (a,b)->{
            return (int)(a.getPePos10() - b.getPePos10());
        });

        for(WhiteHorseEntity e:finalList){
            System.out.println(e);
        }

        ExlsUtil.export(finalList);

    }

    private static int getHorseType(WhiteHorseEntity whe){
        List<Float> profitGRate = whe.getKfNetProfitGrowthRate();
        StringBuilder desc = whe.getDesc();
        float first = profitGRate.get(0);
        float latest = first==0 ? profitGRate.get(1):first;
        float prev3y = 0;
        for(int i=2; i<profitGRate.size();i++){
            prev3y += profitGRate.get(i);
        }
        float avgProfit = Math.round((prev3y/3)*100f)/100f;
        desc.append("最近2季度利润增速：")
                .append(latest).append(",过去3年平均净利润增速：")
                .append(avgProfit).append("，增速倍速：").append(Math.round(latest/avgProfit*100)/100f);
        if (avgProfit/2 > latest){//过去3年的平均值的一半大于最近2季度的利润增速大则认为是困境白马
            return  0;//困境白马
        }

        return 1;//景气白马
    }

}
