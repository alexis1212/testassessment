package testassessment.glue.products;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;
import testassessment.requestcontext.products.world.AccessSpec;
import testassessment.requestcontext.products.world.StoredResponse;
import testassessment.requestcontext.products.requests.ProductRequests;
import testassessment.requestcontext.products.world.Product;

import static org.hamcrest.CoreMatchers.equalTo;

public class DeleteProduct {

    private StoredResponse storedResponse;
    private Product product;
    private AccessSpec accessSpec;
    private ProductRequests productRequests = new ProductRequests();

    /*
    Using DI to store values in variables and reuse them in different steps and classes
    */
    public DeleteProduct(
            StoredResponse storedResponse,
            Product product,
            AccessSpec accessSpec
    ) {
        this.storedResponse = storedResponse;
        this.product = product;
        this.accessSpec = accessSpec;
    }

    /*
    Sending a request to delete a product by id
     */
    @When("I attempt to delete the selected product")
    public void iAttemptToDeleteTheSelectedProduct() {
        int id = ((product.getId() != null) ? product.getId() : 9999999);
        storedResponse.setStoredResponse(
                productRequests.deleteProduct(accessSpec, id)
        );
    }

    /*
    Sending a request to delete a product using invalid id
    */
    @When("I attempt to delete the product using invalid id")
    public void iAttemptToDeleteTheProductUsingInvalidId() {
        storedResponse.setStoredResponse(
                productRequests.deleteProduct(accessSpec, "randomString")
        );
    }

    /*
    Asserting that the request was a success
    */
    @Then("I should be informed that deleting was successful")
    public void iShouldBeInformedThatDeletingWasSuccessful() {
        storedResponse.getStoredResponse()
                .then().statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(product.getId()))
                .body("name", equalTo(product.getName()));
    }

    /*
    Asserting that the deleted product can no longer be found in the webstore
     */
    @Then("the selected product shouldn't exist any more")
    public void theSelectedProductShouldnTExistAnyMore() {
        productRequests.getProductDetails(accessSpec, product.getId()).
                then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
