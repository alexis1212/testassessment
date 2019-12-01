package testassessment.smoke;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import testassessment.requestcontext.Uri;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.*;

/*
Smoke tests for positive scenarios, one per each method
*/
public class SmokeTest {

    /*
    FindProducts
     */
    @Test
    public void listAvailableProductsTest() {
        Response response = given().baseUri("http://localhost:3030").header("Content-Type", "application/json")
                .queryParam("$limit", "10")
                .queryParam("$skip", "51955")
                .when().get(Uri.PRODUCTS.getUri())
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body("limit", equalTo(10))
                .body("skip", equalTo(51955))
                .body(matchesJsonSchemaInClasspath("products-schema.json"))
                .extract().response();

        Assert.assertThat(response.jsonPath().getList("data.id").size(), equalTo(10));
    }

    /*
    AddProduct
     */
    @Test
    public void addProductTest() {
        given().baseUri("http://localhost:3030").header("Content-Type", "application/json")
                .when().body("{\n" +
                "  \"name\": \"productName\",\n" +
                "  \"type\": \"productType\",\n" +
                "  \"price\": 6.5,\n" +
                "  \"shipping\": 0.2,\n" +
                "  \"upc\": \"1234567\",\n" +
                "  \"description\": \"productDesc\",\n" +
                "  \"manufacturer\": \"productMan\",\n" +
                "  \"model\": \"productModel\",\n" +
                "  \"url\": \"http://product.url\",\n" +
                "  \"image\": \"http://product.image\"\n" +
                "}")
                .post("/products")
                .then().assertThat().statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo("productName"))
                .body("price", equalTo(Float.parseFloat("6.5")))
                .body("id", notNullValue())
        ;
    }

    /*
    DeleteProduct
     */
    @Test
    public void deleteProductTest() {
        given().baseUri("http://localhost:3030").header("Content-Type", "application/json")
                .when().pathParam("id", "347333").delete("/products/{id}")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(Integer.parseInt("347333")));
    }

    /*
    FindProductById
     */
    @Test
    public void findProductByIdTest() {
        given().baseUri("http://localhost:3030").header("Content-Type", "application/json")
                .when().pathParam("id", "347155").get("/products/{id}")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(Integer.parseInt("347155")))
                .body(matchesJsonSchemaInClasspath("product-schema.json"));
    }

    /*
    UpdateProductById
     */
    @Test
    public void updateProductByIdTest() {
        given().baseUri("http://localhost:3030").header("Content-Type", "application/json")
                .when().pathParam("id", "347155").body("{\n" +
                "  \"name\": \"productName\",\n" +
                "  \"type\": \"productType\",\n" +
                "  \"price\": 6.5,\n" +
                "  \"shipping\": 0.2,\n" +
                "  \"upc\": \"1234567\",\n" +
                "  \"description\": \"productDesc\",\n" +
                "  \"manufacturer\": \"productMan\",\n" +
                "  \"model\": \"productModel\",\n" +
                "  \"url\": \"http://product.url\",\n" +
                "  \"image\": \"http://product.image\"\n" +
                "}").patch("/products/{id}")
                .then().assertThat().statusCode(200)
                .body("name", equalTo("productName"))
                .body("price", equalTo(Float.parseFloat("6.5")))
                .body("id", equalTo(Integer.parseInt("347155")));
    }

}
