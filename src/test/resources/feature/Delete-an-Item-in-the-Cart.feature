Feature:

  Scenario Outline: Delete an item in the cart
    Given Add an item to chart with <productId>, then verify status code 201
    And Delete an item in the cart, then verify status code 204
    Examples:
      | productId |
      | 1225      |
      | 3674      |

