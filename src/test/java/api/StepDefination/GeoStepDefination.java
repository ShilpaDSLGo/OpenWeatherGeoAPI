package api.StepDefination;
import com.aventstack.extentreports.ExtentTest;

import api.utilities.ConfigReader;

import static io.restassured.RestAssured.given;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.cucumber.core.cli.Main;
import org.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.junit.Assert;
import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

public class GeoStepDefination {
	private static final Logger log = LogManager.getLogger(GeoStepDefination.class);
//	private static String baseUrl = "http://api.openweathermap.org/geo/1.0/zip";
	private String location ;
    private Response response;
//    private static final String API_KEY = "f897a99d971b5eef57be6fafa0d83239";
    private static final String COUNTRY_CODE = "US";
    
    public GeoStepDefination() {
        // No-arg constructor helps instantiate the step definition 
    }

    
    
    @Given("I specify the city name as {string}")
    public void i_specify_the_city_name_as(String city) {
    	location = city;
        log.info("City name specified: " + location);
      
    }

    @When("I request geocoding details from the API")
    public void i_request_geocoding_details_from_the_api() throws IOException {
    	log.info("Making request for location: " + location);

    	// API request by city name
        response = RestAssured.given()
                .queryParam("q", location + "," + COUNTRY_CODE)
                .queryParam("appid", ConfigReader.getAPIKey())
                .get("http://api.openweathermap.org/geo/1.0/direct");
                
        log.info("API Response: " + response.asString());
        }

    @Then("the response should contain valid latitude and longitude for {string}")
    public void the_response_should_contain_valid_latitude_and_longitude_for(String expectedLocation) {
    	   Assert.assertEquals(200, response.getStatusCode());

           if (response.jsonPath().getList("$").size() > 0) {
               Assert.assertNotNull(response.jsonPath().getString("[0].lat"));
               Assert.assertNotNull(response.jsonPath().getString("[0].lon"));
           } else {
               Assert.fail("No location found for " + expectedLocation);
           }

           log.info("Received location details for " + expectedLocation + ": Latitude - " +
                     response.jsonPath().getString("[0].lat") +
                     ", Longitude - " +
                     response.jsonPath().getString("[0].lon"));
    }

    
//    Scenario 2
    @Given("I specify the postal code as {string}")
    public void i_specify_the_postal_code_as(String postalCode) {
    	 location = postalCode;
         log.info("Postal code specified: " + location);
    }
    
    @When("I request geocoding details from the API {string}")
    public void i_request_geocoding_details_from_the_api(String postalCode) throws IOException {
    	response = given()
                .queryParam("zip", postalCode + ",US")  // Assuming US locations only
                .queryParam("appid", ConfigReader.getAPIKey() )
            .when()
                .get(ConfigReader.getBaseURL())
            .then()
                .extract().response();
 
 log.info("API response for postal code " + postalCode + ": " + response.asString());
    	
    }

    @Then("the response should contain valid latitude and longitude for postal code {string}")
    public void the_response_should_contain_valid_latitude_and_longitude_for_postal_code(String postalCode) {
    	Assert.assertEquals(200, response.getStatusCode());

    	String latitude = response.jsonPath().getString("lat");
        String longitude = response.jsonPath().getString("lon");

        // Validate that latitude and longitude are present
        if (latitude != null && longitude != null) {
            log.info("Received location details for postal code " + postalCode + ": Latitude - " + latitude + ", Longitude - " + longitude);
        } else {
            Assert.fail("No location found for postal code " + postalCode);
        }
    }
    
//    Scenario 3

    @Given("I specify an invalid city name as {string}")
    public void i_specify_an_invalid_city_name_as(String invalidCity) {
    	location = invalidCity;
        log.info("Invalid city name specified: " + location);
    }

    
    

    @Then("the response should indicate that no location was found for {string}")
    public void the_response_should_indicate_that_no_location_was_found_for(String location) {
    	Assert.assertEquals(404, response.getStatusCode());

        log.info("No location found for: " + location);
    }

 // Scenario 4
    @Given("I specify an invalid postal code as {string}")
    public void i_specify_an_invalid_postal_code_as(String invalidPostalCode) {
    	   location = invalidPostalCode;
           log.info("Invalid postal code specified: " + location);
    }

    @Then("the response should indicate that no location was found for postal code {string}")
    public void the_response_should_indicate_that_no_location_was_found_for_postal_code(String postalCode) {
//    	Ensure that the staus code is 200 
    	Assert.assertEquals(200, response.getStatusCode());
    	 
    	// Check if the API response contains latitude and longitude
        String latitude = response.jsonPath().getString("lat");
        String longitude = response.jsonPath().getString("lon");

        // If lat or lon are null or empty, it means no location was found for the postal code
        if (latitude == null || longitude == null || latitude.isEmpty() || longitude.isEmpty()) {
            log.info("No location found for postal code " + postalCode);
        } else {
            Assert.fail("Location was unexpectedly found for postal code " + postalCode);
        }
    	 
         log.info("No location found for postal code: " + postalCode);
    }
    
    // Scenario 5
    
    @Given("^I specify the city name as \"([^\"]*)\" with special characters$")
    public void iSpecifyTheCityNameAsWithSpecialCharacters(String cityWithSpecialChars) {
        location = cityWithSpecialChars;
        log.info("City name with special characters specified: " + location);
    }

    @Then("the response should return the geocoding details for the first {string} found")
    public void the_response_should_return_the_geocoding_details_for_the_first_found(String cityWithSpecialChars) {
    	Assert.assertEquals(200, response.getStatusCode());

        if (response.jsonPath().getList("$").size() > 0) {
            Assert.assertNotNull(response.jsonPath().getString("lat"));
            Assert.assertNotNull(response.jsonPath().getString("lon"));
        } else {
            Assert.fail("No location found for " + cityWithSpecialChars);
        }

        log.info("Received location details for " + cityWithSpecialChars + ": Latitude - " +
                  response.jsonPath().getString("lat") +
                  ", Longitude - " +
                  response.jsonPath().getString("lon"));
    }

    
    // Scenario 6
    @Given("^I specify the city name as \"([^\"]*)\" with multiple matches$")
    public void iSpecifyTheCityNameAsWithMultipleMatches(String cityWithMultipleMatches) {
        location = cityWithMultipleMatches;
        log.info("City name with multiple matches specified: " + location);
    }
    
    @Then("the response should return the geocoding details for the first found {string}")
    public void theResponseShouldReturnTheGeocodingDetailsForTheFirstFound (String cityWithMultipleMatches) {
        Assert.assertEquals(200, response.getStatusCode());

        if (response.jsonPath().getList("$").size() > 0) {
            String firstResultCity = response.jsonPath().getString("[0].name");
            Assert.assertTrue(firstResultCity.contains(cityWithMultipleMatches));

            log.info("Received location details for the first match of " + cityWithMultipleMatches + ": Latitude - " +
                      response.jsonPath().getString("[0].lat") +
                      ", Longitude - " +
                      response.jsonPath().getString("[0].lon"));
        } else {
            Assert.fail("No location found for " + cityWithMultipleMatches);
        }
    }

}
