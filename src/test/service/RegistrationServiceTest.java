package service;

import dao.UserDao;
import dao.implementation.UserDaoImpl;
import domain.Registration;
import domain.User;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Test;
import service.implementation.RegistrationService;

import java.io.FileInputStream;

public class RegistrationServiceTest extends DBTestCase {

    private UserDao userDao = UserDaoImpl.getInstance();

    private IDataSet[] dataSets;

    public RegistrationServiceTest(String name) {
        super(name);
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                "com.mysql.jdbc.Driver");
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&autoReconnect=true");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        dataSets = new IDataSet[] { new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/dataset/user_data_set.xml")),
                new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/dataset/reader_data_set.xml"))
        };
        return new CompositeDataSet(dataSets);
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Test
    public void testExecuteIfUserNotExist() {
        Registration registrationData = new Registration();
        registrationData.setLogin("registrationLogin");
        registrationData.setPassword("registrationPassword");
        registrationData.setEmail("registrationEmail@domain.com");

        Service<Registration, Boolean> registrationService = RegistrationService.getInstance();

        Assert.assertTrue(registrationService.execute(registrationData));
    }

    @Test
    public void testExecuteIfUserExist() {
        Registration registrationData = new Registration();
        registrationData.setLogin("login1");
        registrationData.setPassword("password1");
        registrationData.setEmail("email1@domain.com");

        Service<Registration, Boolean> registrationService = RegistrationService.getInstance();

        Assert.assertFalse(registrationService.execute(registrationData));
    }
}
