package stock.tool.util;


import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    private static Map<String,String> stockMap = new HashMap<>();
    static {
        stockMap.put("600030","中信证券");
        stockMap.put("600837","海通证券");
        stockMap.put("601688","华泰证券");
        stockMap.put("601211","国泰君安");
        stockMap.put("600999","招商证券");
        stockMap.put("000776","广发证券");
        stockMap.put("000166","申万宏源");
        stockMap.put("600958","东方证券");
        stockMap.put("601788","光大证券");
        stockMap.put("002736","国信证券");
        stockMap.put("601377","兴业证券");
        stockMap.put("601066","中信建投");
        stockMap.put("601881","中国银河");
    }

    public static String getStockName(String  stockCode){
        return stockMap.get(stockCode);
    }

    public static String getStringFromFile(String filepath) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = JsonUtil.class.getClassLoader().getResource(filepath);
        FileReader fileReader = new FileReader(url.getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while((line=bufferedReader.readLine()) != null){
            sb.append(line.trim());
        }
        return sb.toString();
    }

    public static JSONObject toJsonFromFile(String filepath)throws IOException{
        String jsonstr = getStringFromFile(filepath);
        if(filepath == null){
            return new JSONObject();
        }
        return JSONObject.parseObject(jsonstr);
    }

    public static <T> T toEntityFromFile(String filepath, Class<T> clazz)throws IOException{
        String str = getStringFromFile(filepath);
        return JSONObject.parseObject(str,clazz);
    }


}
