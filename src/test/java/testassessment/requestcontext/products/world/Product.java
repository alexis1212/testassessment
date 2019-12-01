package testassessment.requestcontext.products.world;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

/*
World object to store product information and pass it through different steps/glue files
 */
public class Product {

    private String name;
    private String type;
    private float price;
    private float shipping;
    private String upc;
    private String description;
    private String manufacturer;
    private String model;
    private String url;
    private String image;

    @JsonIgnore
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getShipping() {
        return shipping;
    }

    public void setShipping(float shipping) {
        this.shipping = shipping;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*
    Method to convert a Map containing all the attributes of Product to Product object
     */
    public void convertMapToProduct(Map<String, String> map) {
        this.setName(map.get("name"));
        this.setType(map.get("type"));
        this.setPrice(Float.parseFloat(map.get("price")));
        this.setShipping(Float.parseFloat(map.get("shipping")));
        this.setUpc(map.get("upc"));
        this.setDescription(map.get("description"));
        this.setManufacturer(map.get("manufacturer"));
        this.setModel(map.get("model"));
        this.setUrl(map.get("url"));
        this.setImage(map.get("image"));
    }

    /*
    Method to update only the affected attributes of existing Product object
     */
    public void updateMapToProduct(Map<String, String> map) {
        if (map.keySet().contains("name"))
            this.setName(map.get("name"));
        if (map.keySet().contains("type"))
            this.setType(map.get("type"));
        if (map.keySet().contains("price") && !map.get("price").equals(""))
            this.setPrice(Float.parseFloat(map.get("price")));
        if (map.keySet().contains("shipping") && !map.get("shipping").equals(""))
            this.setShipping(Float.parseFloat(map.get("shipping")));
        if (map.keySet().contains("upc"))
            this.setUpc(map.get("upc"));
        if (map.keySet().contains("description"))
            this.setDescription(map.get("description"));
        if (map.keySet().contains("manufacturer"))
            this.setManufacturer(map.get("manufacturer"));
        if (map.keySet().contains("model"))
            this.setModel(map.get("model"));
        if (map.keySet().contains("url"))
            this.setUrl(map.get("url"));
        if (map.keySet().contains("image"))
            this.setImage(map.get("image"));
    }
}
