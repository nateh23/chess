package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;

public class ApplicationService {
    DataAccess dataAccess;

    public ApplicationService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public void clear() throws DataAccessException {
        this.dataAccess.clearAllData();
    }
}
