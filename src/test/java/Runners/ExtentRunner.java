package Runners;

import DataProviders.ConfigFileReader;
import Managers.FileReaderManager;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
//import com.cucumber.listener.Reporter;

import java.io.File;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "StepDefinitions/WebStepDefinitions",
        dryRun = false,
        monochrome = true,
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "json:target/cucumber-report.json"},
        tags = "@Web"

)
@Test
public class ExtentRunner extends AbstractTestNGCucumberTests {

    //    @Override
//    @DataProvider(parallel = true)
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("================ BEFORE WEB TEST SUITE ================");
    }

//    @AfterClass
//    public void writeExtentReport() {
//        Reporter.loadXMLConfig(new File(ConfigFileReader.getReportConfigPath()));
//    }


    @AfterSuite
    public void afterSuite() {
        System.out.println("================ AFTER WEB TEST SUITE ================");
    }
}
