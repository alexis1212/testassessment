package testassessment.glue.products;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.http.HttpStatus;
import testassessment.requestcontext.products.world.AccessSpec;
import testassessment.requestcontext.products.world.StoredResponse;
import testassessment.requestcontext.products.requests.ProductRequests;
import testassessment.requestcontext.products.world.Product;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

public class FindProductById {

    private AccessSpec accessSpec;
    private Product product;
    private StoredResponse storedResponse;

    private ProductRequests productRequests = new ProductRequests();

    /*
    Using DI to store values in variables and reuse them in different steps and classes
    */
    public FindProductById(
            AccessSpec accessSpec,
            Product product,
            StoredResponse storedResponse
    ) {
        this.accessSpec = accessSpec;
        this.product = product;
        this.storedResponse = storedResponse;
    }

    /*
    Sending a request to retrieve details of a product with given id (as stored in the previous step)
     */
    @When("I attempt to retrieve the details of the selected product")
    public void iAttemptToRetrieveTheDetailsOfTheSelectedProduct() {
        int id = ((product.getId() != null) ? product.getId() : 9999999);
        storedResponse.setStoredResponse(
                productRequests.getProductDetails(accessSpec, id)
        );
    }

    /*
    Sending a request to retrieve details of a product using invalid id
    */
    @When("I attempt to retrieve the details of the product using invalid id")
    public void iAttemptToRetrieveTheDetailsOfTheProductUsingInvalidId() {
        storedResponse.setStoredResponse(
                productRequests.getProductDetails(accessSpec, "someString")
        );
    }

    /*
    Asserting that the retrieved details match the previously stored data
     */
    @Then("I should be presented with the following information:")
    public void iShouldBePresentedWithTheFollowingInformation(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);

        storedResponse.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(productMap.get("name")))
                .body("price", equalTo(Float.parseFloat(productMap.get("price"))));
    }

    /*
    Asserting that the retrieved details are in an expected format
    */
    @Then("I should get product details containing correct information")
    public void iShouldGetProductDetailsContainingCorrectInformation() {
        storedResponse.getStoredResponse()
                .then().assertThat()
                .body(matchesJsonSchemaInClasspath("product-schema.json"));
    }

    /*
    Asserting that the product with the provided id cannot be found
    */
    @Then("I should be informed that selected product was not found")
    public void iShouldBeInformedThatSelectedProductWasNotFound() {
        storedResponse.getStoredResponse()
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
