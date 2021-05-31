package news.raf.backend.core;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
@news.raf.backend.core.annotations.NotEmptyBodyFilter
public class NotEmptyBodyFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext){
        List<String> headerArray = requestContext.getHeaders().get("Content-Length");
        int contentLength = Integer.parseInt(headerArray.get(0));
        if (contentLength == 0){
            String message = "Content length is "+contentLength;
            requestContext.abortWith(ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data(message).build());
        }


    }
}
