@products @deleteProduct
Feature: Deleting a product
  As a webshop user
  I want to be able to delete the products that no longer exist
  So that I can keep the store in the correct state

  @deleteProductPositive
  Scenario: Deleting an existing product
    Given a product with following data already exists:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 090007990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to delete the selected product
    Then I should be informed that deleting was successful
    And the selected product shouldn't exist any more

  @deleteProductNegative
  Scenario: Deleting a non existing product
    When I attempt to delete the selected product
    Then I should be informed that selected product was not found

  @deleteProductNegative
  Scenario: Deleting a product with String as an id
    # Should return Bad Request
    When I attempt to delete the product using invalid id
    Then I should be informed that selected product was not found