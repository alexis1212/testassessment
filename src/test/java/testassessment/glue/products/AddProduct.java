package testassessment.glue.products;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.http.HttpStatus;
import testassessment.requestcontext.products.world.AccessSpec;
import testassessment.requestcontext.products.world.StoredResponse;
import testassessment.requestcontext.products.requests.ProductRequests;
import testassessment.requestcontext.products.world.Product;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AddProduct {

    private StoredResponse storedResponse;
    private AccessSpec accessSpec;
    private Product product;

    private ProductRequests productRequests = new ProductRequests();

    /*
    Using DI to store values in variables and reuse them in different steps and classes
    */
    public AddProduct(
            StoredResponse storedResponse,
            AccessSpec accessSpec,
            Product product
    ) {
        this.storedResponse = storedResponse;
        this.accessSpec = accessSpec;
        this.product = product;
    }

    /*
    Precondition that a certain product already exists in the webstore, preserving the id of the created product
     */
    @Given("a product with following data already exists:")
    public void aProductWithFollowingDataAlreadyExists(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);
        product.convertMapToProduct(productMap);

        product.setId(productRequests.createProduct(accessSpec, product).jsonPath().get("id"));
    }

    /*
    Sending a request to create a product with Json generated using Product objects (Strings converted to Floats for prices)
     */
    @When("I attempt to create a following product:")
    public void iAttemptToCreateAFollowingProduct(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);
        product.convertMapToProduct(productMap);

        storedResponse.setStoredResponse(
                productRequests.createProduct(accessSpec, product)
        );
    }

    /*
    Sending a request to create a product with Json generated using Map
    */
    @When("I attempt to create a product with the following data:")
    public void iAttemptToCreateAProductWithTheFollowingData(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);

        storedResponse.setStoredResponse(
                productRequests.createProduct(accessSpec, productMap)
        );
    }

    /*
    Asserting the response code is 2xx and storing product id
     */
    @Then("I should be informed that creating a product was successful")
    public void iShouldBeInformedThatCreatingAProductWasSuccessful() {
        storedResponse.getStoredResponse()
                .then().statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue());

        product.setId(storedResponse.getStoredResponse().jsonPath().getInt("id"));
    }

    /*
    Fetching the product from the store using id and asserting it has the same attributes as sent in request
     */
    @Then("the product with following details should exist:")
    public void theProductWithFollowingDetailsShouldExist(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);

        productRequests.getProductDetails(accessSpec, product.getId())
                .then().assertThat()
                .body("name", equalTo(productMap.get("name")))
                .body("upc", equalTo(productMap.get("upc")))
                .body("model", equalTo(productMap.get("model")));
    }

    /*
    Asserting that we are getting correct 4xx error code
     */
    @Then("I should be informed that the product could not be created")
    public void iShouldBeInformedThatTheProductCouldNotBeCreated() {
        storedResponse.getStoredResponse()
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    /*
    Asserting that we are getting correct error code when trying to create a product with non-unique upc
     */
    @Then("I should be informed that the product with the provided upc already exists")
    public void iShouldBeInformedThatTheProductWithTheProvidedUpcAlreadyExists() {
        storedResponse.getStoredResponse()
                .then().statusCode(HttpStatus.SC_CONFLICT);
    }
}
