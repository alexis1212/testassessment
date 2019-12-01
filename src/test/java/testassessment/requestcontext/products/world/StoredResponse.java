package testassessment.requestcontext.products.world;

import io.restassured.response.Response;

/*
World object to store response so that it can be used and verified in different steps/glue files
 */
public class StoredResponse {

    private Response storedResponse;

    public void setStoredResponse(Response storedResponse) {
        this.storedResponse = storedResponse;
    }

    public Response getStoredResponse() {
        return storedResponse;
    }
}
