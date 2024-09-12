package api.StepDefination;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.cucumber.java.After;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import api.utilities.ConfigReader;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private static ExtentReports extentReports;
    private static ExtentTest test;
    private String location;

    @Before
    public void setUp() throws IOException {
    	RestAssured.baseURI = ConfigReader.getBaseURL();
        // Initialize Log4j Logger
        log.info("ExtentReports and Log4j initialized.");

        
    }

    @After
    public void tearDown() {
        // Flush ExtentReports
//        extentReports.flush();
//        log.info("ExtentReports flushed and logs completed.");
    }

    public static ExtentTest getTest() {
        return test;
    }

    public String getLocation() {
        return location;
    }
}
