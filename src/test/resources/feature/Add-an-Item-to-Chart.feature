Feature:

  Scenario Outline: Add an item to chart
    Given Add an item to chart with <productId>, then verify status code 201

    Examples:
      | productId |
      | 4643      |
