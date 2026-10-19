package service;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServiceTests {
    @Test
    @DisplayName("ApplicationServiceTests")
    public void ApplicationTest() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        ApplicationService applicationService = new ApplicationService(dataAccess);

        applicationService.clear();
    }
}
