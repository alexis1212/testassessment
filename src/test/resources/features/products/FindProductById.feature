@products @findProductById
Feature: Find a product by its id
  As a webstore user
  I want to be able to retrieve a certain product by its id
  So that I can see the details of that product

  @findProductByIdPositive @smoke
  Scenario: Find an existing product
    Given a product with following data already exists:
      | name   | type | price | shipping | upc     | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 7990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to retrieve the details of the selected product
    Then I should be presented with the following information:
      | name   | type | price | shipping | upc     | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 7990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |

  @findProductByIdPositive
  Scenario: Retrieve product details correctly
    Given a product with following data already exists:
      | name   | type | price | shipping | upc     | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 7990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to retrieve the details of the selected product
    Then I should get product details containing correct information

  @findProductByIdNegative
  Scenario: Find a non existing product
    When I attempt to retrieve the details of the selected product
    Then I should be informed that selected product was not found

  @findProductByIdPositive
  Scenario: Find a product with String as an id
    # Should return Bad Request
    When I attempt to retrieve the details of the product using invalid id
    Then I should be informed that selected product was not found