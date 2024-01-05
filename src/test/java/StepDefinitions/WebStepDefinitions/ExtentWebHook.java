package StepDefinitions.WebStepDefinitions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import Managers.AllDriverManager;
import Managers.FileReaderManager;
import Utilities.ZapiUtil;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import com.cucumber.listener.Reporter;
import org.openqa.selenium.WebDriver;

public class ExtentWebHook {

    AllDriverManager allDriverManager;
    static File failedScreenShot = null;
    static String screenShotName="";

    public ExtentWebHook(AllDriverManager allDriverManager) {
        this.allDriverManager = allDriverManager;
    }

    @Before
    public void beforeScenario(Scenario scenario) {

    }

    @After(order = 1)
    public void updateZephyr(Scenario scenario){
        String issueKey="";
        String status="";
        if(scenario.getStatus()== Status.PASSED){
            status="pass";
        }else if(scenario.getStatus()==Status.FAILED){
            status="fail";
        }else if(scenario.getStatus()==Status.SKIPPED){
            status="undefined";
        }else {
            status="";
        }
        if(status.equals("fail")){
            for(String tag : scenario.getSourceTagNames()){
                if(tag.contains("ZephyrTestCaseId")){
                    issueKey=tag.split("=")[1].trim();
                    ZapiUtil.updateZephyrResults(issueKey,"2",true);
                }
            }
        }else if(status.equals("pass")){
            for(String tag : scenario.getSourceTagNames()){
                if(tag.contains("ZephyrTestCaseId")){
                    issueKey=tag.split("=")[1].trim();
                    ZapiUtil.updateZephyrResults(issueKey,"1", false);
                }
            }
        }
    }

    @AfterStep(order = 0)
    public void afterScenarioStep(Scenario scenario) {

        if (scenario.isFailed()) {
            screenShotName = scenario.getName().replaceAll(" ", "_");
            try {
                //This takes a screenshot from the driver at save it to the specified location
                File sourcePath = ((TakesScreenshot) this.allDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
                Files.copy(sourcePath.toPath(), failedScreenShot.toPath());
                //Building up the destination path for the screenshot to save
                //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
                File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenShotName + ".png");

                //Copy taken screenshot from source location to destination location
                Files.copy(sourcePath.toPath(), destinationPath.toPath());

                //This attach the specified screenshot to the test
                //Reporter.addScreenCaptureFromPath(destinationPath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @After(order = 2)
    public void AfterSteps() {
        allDriverManager.closeDriver();
    }

}