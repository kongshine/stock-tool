package stock.tool.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LxrResult<T>  implements Serializable {
    private int code;

    private String msg;

    List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData(Class<T> clazz) {
        List<T> datas = new ArrayList<>();
        data.forEach(e ->{
            datas.add(JSONObject.toJavaObject((JSON) e,clazz));
        });
        return datas;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
