@products @addProduct
Feature: Adding a new product to the webshop
  As a webshop user
  I want to be able to add new products to webshop
  So that I can offer new products to customers

  @addProductPositive
  Scenario: Create a product with mandatory fields
    When I attempt to create a following product:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 678887990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    Then I should be informed that creating a product was successful
    And the product with following details should exist:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 678887990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |

  @addProductPositive
  Scenario: Create a product without optional fields
    When I attempt to create a product with the following data:
      | name   | type | upc          | description | model |
      | Heroes | Game | 678887990078 | Stategy     | 4     |
    Then I should be informed that creating a product was successful
    And the product with following details should exist:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url | image |
      | Heroes | Game |       |          | 678887990078 | Stategy     |              | 4     |     |       |

  @addProductNegative
  Scenario: Create a product without mandatory fields
    When I attempt to create a product with the following data:
      | type | upc          | description | manufacturer | model | url             | image           |
      | Game | 678887990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    Then I should be informed that the product could not be created

  @addProductNegative
  Scenario Outline: Create a product with invalid values in fields
    When I attempt to create a following product:
      | name   | type   | price   | shipping   | upc   | description   | manufacturer   | model   | url   | image   |
      | <name> | <type> | <price> | <shipping> | <upc> | <description> | <manufacturer> | <model> | <url> | <image> |
    Then I should be informed that the product could not be created
    Examples:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      |        | Game | 9.99  | 0.00     | 678887990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
      | Heroes | Game | 9.99  | 0.00     |              | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
      | Heroes | Game | 9.99  | 0.00     | 678887990079 | Stategy     |              | 3     | http://fake.com | http://fake.jpg |
      | Heroes | Game | 9.99  | 0.001    | 678887990079 | Stategy     |              | 3     | http://fake.com | http://fake.jpg |

  @addProductNegative
  Scenario: Create a product with invalid price values
    When I attempt to create a product with the following data:
      | name   | type | price  | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | String | String   | 678887990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    Then I should be informed that the product could not be created

  @addProductDuplicate
  Scenario: Create a duplicate product with upc that is not unique
    # Should not be allowed
    Given a product with following data already exists:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 978887990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to create a following product:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 978887990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    Then I should be informed that creating a product was successful