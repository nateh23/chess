package handlers;

import dataaccess.DataAccessException;
import io.javalin.http.Context;
import service.ApplicationService;
public class ClearHandler extends Handler{
    ApplicationService service;

    public ClearHandler(ApplicationService service){
        this.service = service;
    }

    public void clear(Context ctx) throws DataAccessException {
        this.service.clear();
    }
}
