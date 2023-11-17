Feature:

  Scenario Outline: Returns the items in a cart.
    Given Add an item to chart with <productId>, then verify status code 201
    And get added cart items ,then verify status code 200
    Examples:
      | productId |
      | 4643      |
      | 4646      |
