Feature:

  Scenario Outline:
    Given Retrieves the product detail with the given <productId>, then the response status code should be <statusCode>
    Examples:
      | productId | statusCode |
      | 4643      | 200        |
