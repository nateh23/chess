package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;

public class ApplicationService extends Service{
    DataAccess dataAccess;

    public ApplicationService(DataAccess dataAccess){
        super(dataAccess);
        this.dataAccess = dataAccess;
    }

    public void clear() throws DataAccessException {
        this.dataAccess.clearAllData();
    }
}
