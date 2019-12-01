package testassessment.glue;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import testassessment.requestcontext.products.world.Product;

import java.util.Locale;
import java.util.Map;

/*
Attempt to register new DataTableType Product to be able to use Product object in Cucumber steps - not used
 */
public class TypeConfigurer implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(
                new DataTableType(
                        Product.class,
                        (Map<String, String> entry) -> {
                            Product product = new Product();
                            product.setName(entry.get("name"));
                            product.setType(entry.get("type"));
                            product.setPrice(Float.parseFloat(entry.get("price")));
                            product.setShipping(Float.parseFloat(entry.get("shipping")));
                            product.setUpc(entry.get("upc"));
                            product.setDescription(entry.get("description"));
                            product.setManufacturer(entry.get("manufacturer"));
                            product.setModel(entry.get("model"));
                            product.setUrl(entry.get("url"));
                            product.setImage(entry.get("image"));
                            return product;
                        }
                )
        );
    }
}
