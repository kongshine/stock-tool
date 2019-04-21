package stock.tool.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import stock.tool.model.Lixinger;
import stock.tool.model.LxrResult;

import javax.ws.rs.core.MediaType;

public class JerseyClient {

    public static final Client client= new Client();
    static {
        client.setConnectTimeout(3000);
    }

    public static LxrResult post(Object o,String url){
        WebResource wr = client.resource(url);
        return wr.entity(o,MediaType.APPLICATION_JSON_TYPE).post(LxrResult.class);
    }
}
