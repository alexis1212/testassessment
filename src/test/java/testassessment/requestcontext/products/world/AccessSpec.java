package testassessment.requestcontext.products.world;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import testassessment.PropertiesReader;

import java.util.Properties;

/*
World object to store Request Specification (convenient in case we use different base uri, headers or authentication in different steps)
 */
public class AccessSpec {
    private RequestSpecification spec;
    private Properties properties = new Properties();

    public void setSpec(String baseUri) {
        PropertiesReader.readDataFromProperties(properties);
        spec = new RequestSpecBuilder().setBaseUri(properties.getProperty(baseUri)).
                addHeader("Content-Type", "application/json").
                build();
    }

    public RequestSpecification getSpec() {
        return spec;
    }

}

