package testassessment.requestcontext;

public enum Uri {
    PRODUCTS("/products"),
    PRODUCT_DETAILS("/products/{id}");

    private final String uri;

    Uri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}