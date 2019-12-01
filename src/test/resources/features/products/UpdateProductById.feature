@products @updateProductById
Feature: Updating information about an existing product
  As a webstore user
  I want to be able to update my products information
  So that the correct information is always shown

  @updateProductPositive
  Scenario Outline: Updating an existing product information
    Given a product with following data already exists:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 123457990077 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to update the details of the selected product with the following information:
      | name   | type   | price   | shipping   | upc   | description   | manufacturer   | model   | url   | image   |
      | <name> | <type> | <price> | <shipping> | <upc> | <description> | <manufacturer> | <model> | <url> | <image> |
    Then I should be informed that updating a product was successful
    And the product with following details should exist:
      | name   | type   | price   | shipping   | upc   | description   | manufacturer   | model   | url   | image   |
      | <name> | <type> | <price> | <shipping> | <upc> | <description> | <manufacturer> | <model> | <url> | <image> |
    Examples:
      | name          | type | price | shipping | upc          | description | manufacturer | model | url                | image           |
      | Heroes        | Game | 0.01  | 0.00     | 123467990078 | Stategy     | Ubisoft      | 3     | http://another.com | http://fake.jpg |
      | Heroes        | Game | 9.99  | 12.99    | 123457990077 | Stategy     | Another      | 5     | http://fake.com    |                 |
      | Heroes of M&M | Game | 19.99 | 0.10     | 123457990077 | Description | Ubisoft      | 3     | http://fake.com    | http://fake.jpg |

  @updateProductPartial  @smoke
  Scenario: Updating only product name
    Given a product with following data already exists:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 123457990078 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to update the details of the selected product with the following information:
      | name |
      | Name |
    Then I should be informed that updating a product was successful
    And the product with following details should exist:
      | name | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Name | Game | 9.99  | 0.00     | 123457990078 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |

  @updateProductPartial
  Scenario: Updating only product price
    Given a product with following data already exists:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 123457990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to update the details of the selected product with the following information:
      | price    |
      | 14900.99 |
    Then I should be informed that updating a product was successful
    And the product with following details should exist:
      | name   | type | price    | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 14900.99 | 0.00     | 123457990079 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |

  @updateProductInvalidValues
  Scenario Outline: Updating product with invalid values in fields
    # Should not be allowed
    Given a product with following data already exists:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 123457990070 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    When I attempt to update the details of the selected product with the following information:
      | name   | type   | price   | shipping   | upc   | description   | manufacturer   | model   | url   | image   |
      | <name> | <type> | <price> | <shipping> | <upc> | <description> | <manufacturer> | <model> | <url> | <image> |
    Then I should be informed that updating a product was successful
    And the product with following details should exist:
      | name   | type   | price   | shipping   | upc   | description   | manufacturer   | model   | url   | image   |
      | <name> | <type> | <price> | <shipping> | <upc> | <description> | <manufacturer> | <model> | <url> | <image> |
    Examples:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      |        | Game | 9.99  | 0.00     | 123457990070 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
      | Heroes | Game | 9.99  | 0.00     |              | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |

  @updateProductNegative
  Scenario: Updating a non existing product
    When I attempt to update the details of the selected product with the following information:
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 123457990071 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    Then I should be informed that selected product was not found

  @updateProductNegative
  Scenario: Updating a product using String as an id
    # Should return Bad Request
    When I attempt to update the details of the selected product using invalid id
      | name   | type | price | shipping | upc          | description | manufacturer | model | url             | image           |
      | Heroes | Game | 9.99  | 0.00     | 123457990071 | Stategy     | Ubisoft      | 3     | http://fake.com | http://fake.jpg |
    Then I should be informed that selected product was not found
