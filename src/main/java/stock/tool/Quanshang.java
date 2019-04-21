package stock.tool;

import com.alibaba.fastjson.JSONObject;
import stock.tool.model.Lixinger;
import stock.tool.model.LxerReq;
import stock.tool.model.LxerURL;
import stock.tool.model.LxrResult;
import stock.tool.util.JerseyClient;
import stock.tool.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 券商股投资策略
 */
public class Quanshang {

    public static void main(String[] args) throws IOException {
        LxerReq req = new LxerReq();
        req.setDate("2019-04-04");
        req.setStockCodes(Arrays.asList("601211",
                "600030",
                "600837",
                "601688",
                "601211",
                "600999",
                "000776",
                "000166",
                "600958",
                "601788",
                "002736",
                "601066",
                "601881"));
        req.setMetrics(Arrays.asList("pb_pos10","pb_wo_gw_pos10",
                "pe_ttm",
                "dyr",
                "mc",
                "sp"));

        LxrResult<Lixinger> result = JerseyClient.post(JSONObject.toJSON(req), LxerURL.fundamental);
//        LxrResult result = JsonUtil.toEntityFromFile("quanshang-stock/20190308.txt", LxrResult.class);
        if(result.getCode() != 0)
            return;

        List<Lixinger> datas = result.getData(Lixinger.class);

        datas.sort((d1,d2) ->{
            return (int)(d1.getPb_pos10()*100 - d2.getPb_pos10()*100);
        });
        datas.forEach(lxr-> System.out.println(lxr.toString()));
        //过滤掉PB分为点>10%
        List<Lixinger> newDatas = datas.stream().filter(lixinger -> {
            return lixinger.getPb_pos10()<10f;
        }).collect(Collectors.toList());
        System.out.println("--------------------符合条件结果:"+newDatas.size()+"条-----------------------");
        newDatas.forEach(lxr-> System.out.println(lxr.toString()));
    }
}
