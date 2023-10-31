@Register
Feature:

  Scenario Outline: Register a new API client
    Given Register a new client with the given "<clientName>" and "<clientMail>", then verify the response status code is <statusCode>

    Examples:
      | clientName | clientMail         | statusCode |
      | test       | erkam180@yahoo.com | 201        |
      | test       | test0yahoo.com     | 400        |
      | test       | test0@yahoo.com    | 409        |