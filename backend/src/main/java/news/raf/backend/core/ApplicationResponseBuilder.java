package news.raf.backend.core;

import javax.ws.rs.core.Response;
import java.util.HashMap;

public class ApplicationResponseBuilder {

    private Response.Status status;
    private ResponseBody responseBody;

    public static ApplicationResponseBuilder status(Response.Status status){
            ApplicationResponseBuilder responseBuilder = new ApplicationResponseBuilder();
            responseBuilder.setStatus(status);
            return responseBuilder;
    }
    public ApplicationResponseBuilder data(Object data){
        if (data instanceof String){
            HashMap<String,String> newData = new HashMap<>();
            newData.put("message", (String) data);
            data = newData;
        }
        ResponseBody responseBody = new ResponseBody(this.status.getStatusCode(),data);
        this.setResponseBody(responseBody);
        return this;
    }
    public Response build(){
        return Response.status(this.getStatus()).entity(this.getResponseBody()).build();
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }
}
