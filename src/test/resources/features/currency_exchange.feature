Feature: Currency Exchange Rates

  Scenario: Retrieve exchange rates from NBP API
    Given API NBP is available
    When I request for exchange rates from table A
    Then the response status code should be 200

  Scenario: Display rate for specific currency code
    Given API NBP is available
    When I request for exchange rates from table A
    Then I display the rate for currency with code: USD

  Scenario: Display rate for specific currency name
    Given API NBP is available
    When I request for exchange rates from table A
    Then I display the rate for currency with name: dolar amerykański

  Scenario: Display currencies with rates above a certain value
    Given API NBP is available
    When I request for exchange rates from table A
    Then I display currencies with rates above: 5

  Scenario: Display currencies with rates below a certain value
    Given API NBP is available
    When I request for exchange rates from table A
    Then I display currencies with rates below: 3
