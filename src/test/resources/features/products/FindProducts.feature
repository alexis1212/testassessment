@products @findProducts
Feature: Retrieve a list of products
  As a webshop user
  I want to be able to retrieve the list of available products in chunks
  So that I can have a clear overview of products

  @findProductsInChunks
  Scenario Outline: Retrieve list of products in chunks
    When I attempt to list "<limit>" available products starting from "<skip>"
    Then I should get the <count> results starting from <start>
    Examples:
      | limit | skip | count | start |
      |       |      | 10    | 0     |
      | 20    | 10   | 20    | 10    |
      | 30    | 0    | 25    | 0     |
      | 0     | 100  | 0     | 100   |
      |       | 100  | 10    | 100   |
      | 1abc  | 1xyz | 1     | 1     |

  @findProductsEmpty
  Scenario: Retrieve empty list of products
    When I attempt to list "100" available products starting from "100000"
    Then I should get an empty list of products

  @findProductsJson
  Scenario: Retrieve list of products in a correct format
    When I attempt to list "10" available products starting from "1"
    Then I should get a list of products containing correct information

  @findProductsNegative
  Scenario: Retrieve list of products using wrong parameter type
    # Should return Bad Request
    When I attempt to list "qwerty" available products starting from "asdfg"
    Then I should be presented with an error