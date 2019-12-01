package testassessment.requestcontext.products.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testassessment.requestcontext.products.world.AccessSpec;
import testassessment.requestcontext.Uri;

import static io.restassured.RestAssured.given;

/*
All requests related to product endpoints stored in one place and named by action that is performed for better readability
 */
public class ProductRequests {

    /*
    Request to list all products, with or without query parameters depending on provided values
     */
    public Response findProducts(AccessSpec accessSpec, String limit, String skip) {
        RequestSpecification requestSpecification = given().spec(accessSpec.getSpec());
        if (!limit.isEmpty() && !skip.isEmpty())
            requestSpecification
                    .queryParam("$limit", limit)
                    .queryParam("$skip", skip);
        else if (!limit.isEmpty())
            requestSpecification
                    .queryParam("$limit", limit);
        else if (!skip.isEmpty())
            requestSpecification
                    .queryParam("$skip", skip);
        return requestSpecification.when().get(Uri.PRODUCTS.getUri());
    }

    /*
    Request to create a product using any object (Map or Product in our case) for json body
     */
    public Response createProduct(AccessSpec accessSpec, Object product) {
        return given().spec(accessSpec.getSpec())
                .when().body(product).post(Uri.PRODUCTS.getUri());
    }

    /*
    Request to delete a product using different objects (number or String for positive or negative scenarios) for id
     */
    public Response deleteProduct(AccessSpec accessSpec, Object productId) {
        return given().spec(accessSpec.getSpec())
                .when().pathParam("id", productId).delete(Uri.PRODUCT_DETAILS.getUri());
    }

    /*
    Request to retrieve product details using different objects (number or String for positive or negative scenarios) for id
    */
    public Response getProductDetails(AccessSpec accessSpec, Object productId) {
        return given().spec(accessSpec.getSpec())
                .when().pathParam("id", productId).get(Uri.PRODUCT_DETAILS.getUri());
    }

    /*
    Request to update a product
     */
    public Response updateProduct(AccessSpec accessSpec, Object productId, Object product) {
        return given().spec(accessSpec.getSpec())
                .when().pathParam("id", productId).body(product).patch(Uri.PRODUCT_DETAILS.getUri());
    }
}
