package testassessment.glue.products;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import testassessment.requestcontext.products.world.AccessSpec;
import testassessment.requestcontext.products.world.StoredResponse;
import testassessment.requestcontext.products.requests.ProductRequests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class FindProducts {

    private AccessSpec accessSpec;
    private StoredResponse storedResponse;

    private ProductRequests productRequests = new ProductRequests();

    /*
    Using DI to store values in variables and reuse them in different steps and classes
     */
    public FindProducts(
            AccessSpec accessSpec,
            StoredResponse storedResponse
    ) {
        this.accessSpec = accessSpec;
        this.storedResponse = storedResponse;
    }

    /*
    Setting the basic specification (headers and base uri) before running the scenarios
     */
    @Before
    public void setBaseUri() {
        accessSpec.setSpec("baseuri");
    }

    /*
    Sending a request and storing a response
     */
    @When("I attempt to list {string} available products starting from {string}")
    public void iAttemptToListAvailableProductsStartingFrom(String limit, String skip) {
        storedResponse.setStoredResponse(
                productRequests.findProducts(accessSpec, limit, skip)
        );
    }

    /*
    Asserting that we retrieved correct number of results
     */
    @Then("I should get the {int} results starting from {int}")
    public void iShouldGetTheCountResultsStartingFromStart(int count, int start) {
        Response response = storedResponse.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body("limit", equalTo(count))
                .body("skip", equalTo(start))
                .extract().response();

        Assert.assertThat(response.jsonPath().getList("data.id").size(), is(count));
    }

    /*
    Asserting that we retrieved correct number of results - empty list in this case
     */
    @Then("I should get an empty list of products")
    public void iShouldGetAnEmptyListOfProducts() {
        Assert.assertThat(
                storedResponse.getStoredResponse()
                        .jsonPath().getList("data.id").isEmpty(), is(true)
        );
    }

    /*
    Asserting that retrieved json is in the correct format
     */
    @Then("I should get a list of products containing correct information")
    public void iShouldGetAListOfProductsContainingCorrectInformation() {
        storedResponse.getStoredResponse()
                .then().assertThat()
                .body(matchesJsonSchemaInClasspath("products-schema.json"));
    }

    /*
    Asserting that we are getting correct error message - in this case API returns 500, should be 400, so this scenario should fail
     */
    @Then("I should be presented with an error")
    public void iShouldBePresentedWithAnError() {
        storedResponse.getStoredResponse()
                .then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
