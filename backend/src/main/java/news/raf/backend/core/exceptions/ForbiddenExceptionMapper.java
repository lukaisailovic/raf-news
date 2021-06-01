package news.raf.backend.core.exceptions;

import news.raf.backend.core.ApplicationResponseBuilder;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("You are not authorized to access this endpoint").build();
    }
}
