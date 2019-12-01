package testassessment.glue.products;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.http.HttpStatus;
import testassessment.requestcontext.products.requests.ProductRequests;
import testassessment.requestcontext.products.world.AccessSpec;
import testassessment.requestcontext.products.world.Product;
import testassessment.requestcontext.products.world.StoredResponse;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateProductById {

    private AccessSpec accessSpec;
    private Product product;
    private StoredResponse storedResponse;

    private ProductRequests productRequests = new ProductRequests();

    /*
    Using DI to store values in variables and reuse them in different steps and classes
    */
    public UpdateProductById(
            AccessSpec accessSpec,
            Product product,
            StoredResponse storedResponse
    ) {
        this.accessSpec = accessSpec;
        this.product = product;
        this.storedResponse = storedResponse;
    }

    /*
    Sending a request to update product information passed from feature file, getting id from previous step and updating product object
     */
    @When("I attempt to update the details of the selected product with the following information:")
    public void iAttemptToUpdateTheDetailsOfTheSelectedProductWithTheFollowingInformation(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);
        product.updateMapToProduct(productMap);
        int id = ((product.getId() != null) ? product.getId() : 9999999);

        storedResponse.setStoredResponse(
                productRequests.updateProduct(accessSpec, id, productMap)
        );
    }

    /*
    Sending a request to update a product using invalid id
    */
    @When("I attempt to update the details of the selected product using invalid id")
    public void iAttemptToUpdateTheDetailsOfTheSelectedProductUsingInvalidId(DataTable dataTable) {
        Map<String, String> productMap = dataTable.transpose().asMap(String.class, String.class);
        storedResponse.setStoredResponse(
                productRequests.updateProduct(accessSpec, "invalidId", productMap)
        );
    }

    /*
    Asserting that we get a success response code and that the information is stored properly
     */
    @Then("I should be informed that updating a product was successful")
    public void iShouldBeInformedThatUpdatingAProductWasSuccessful() {
        storedResponse.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(product.getId()))
                .body("name", equalTo(product.getName()))
                .body("upc", equalTo(product.getUpc()))
                .body("model", equalTo(product.getModel()))
                .body("price", equalTo(product.getPrice()));
    }

    @Then("I should be informed that some of the fields contain invalid values")
    public void iShouldBeInformedThatSomeOfTheFieldsContainInvalidValues() {
        storedResponse.getStoredResponse()
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
