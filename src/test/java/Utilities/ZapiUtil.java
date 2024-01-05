package Utilities;

import Managers.FileReaderManager;
import StepDefinitions.WebStepDefinitions.ExtentWebHook;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ZapiUtil {
    public static void updateZephyrResults(String issueKey, String status,boolean attachment ) {
        String baseUrl = "https://prod-api.zephyr4jiracloud.com/connect";
        //fetch issue id from issue key
        RequestSpecification issueIDRequest = RestAssured.given();
        issueIDRequest.header("Content-type", "application/json");
        issueIDRequest.header("Authorization", "");
        String issueEndPoint = baseUrl + "/rest/api/latest/issue" + issueKey;
        String issueID = issueIDRequest.get(issueEndPoint).getBody().jsonPath().get("id").toString();

        //create execution id
        RequestSpecification execIDRequest = RestAssured.given();
        execIDRequest.header("Content-type", "application/json");
        execIDRequest.header("Authorization", "");
        String execEndPoint = baseUrl + "/rest/zapi/latest/execution/";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("issueId", issueID);
        requestBody.put("cycleId", FileReaderManager.getInstance().getConfigFileReader().getZapiCycleID());
        requestBody.put("versionId", FileReaderManager.getInstance().getConfigFileReader().getZapiVersionID());
        requestBody.put("projectId", FileReaderManager.getInstance().getConfigFileReader().getZapiProjectID());
        execIDRequest.body(requestBody);
        String execID = execIDRequest.post(execEndPoint).getBody().asString().split(":")[0].replace("\"", "").replace("{", "");


        //update the status
        RequestSpecification updaterequest = RestAssured.given();
        execIDRequest.header("Content-type", "application/json");
        execIDRequest.header("Authorization", "");
        String updateEndPoint = baseUrl + "/rest/zapi/latest/execution/" + execID + "/execute";
        Map<String, String> upReqBody = new HashMap<>();
        upReqBody.put("status", status);
        updaterequest.body(upReqBody);
        String afterUpdate = updaterequest.put(updateEndPoint).getBody().asString();
        System.out.println(afterUpdate);


        //upload report/log as attachment
        RequestSpecification attachSpecification = RestAssured.given();
        attachSpecification.header("Content-type","multipart/form-data");
        attachSpecification.header("Authorization","");
        String attachEndPoint=baseUrl+"/rest/zapi/latest/attachment/";
        attachSpecification.multiPart("file", new File("src\\LogResults\\testresult-${date:yyyyMMdd}.log"),"text/plain");
        attachSpecification.queryParam("entityId",execID);
        attachSpecification.queryParam("entityType","execution");
        int uploadAttachmentStatus = attachSpecification.post(attachEndPoint).getStatusCode();
        System.out.println("Upload attachement status "+uploadAttachmentStatus);

        if(attachment==true){
            RequestSpecification uploadImage=RestAssured.given();
            uploadImage.header("Content-type","multipart/form-data");
            uploadImage.header("Authorization","");
            String imageEndPoint=baseUrl+"/rest/zapi/latest/attachment/";
            uploadImage.multiPart("file", new File(""));//paste your image path here
            uploadImage.queryParam("entityId",execID);
            uploadImage.queryParam("entityType","execution");
            int uploadImge = uploadImage.post(imageEndPoint).getStatusCode();
            System.out.println("Upload image status : "+uploadImge);
        }


    }
}
