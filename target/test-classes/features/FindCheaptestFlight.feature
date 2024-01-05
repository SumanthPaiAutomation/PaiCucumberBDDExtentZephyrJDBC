@Web
  #@ZephyrTestCaseId=UI-001
  Feature: Ticket Booking feature
    Scenario: To Book Cheapest flight from Bangalore to Delhi
      Given I have the url of the booking website
      When I navigate to cleartrip website
      And I click on from location and type from location
      And I click on to destination and type to destination
      And I click on start trip date
      And I click on return trip date
      And I click on search flight button
      Then I verify if I am navigated to price list page
      And I select cheapest from price
      And I select cheapest to price
      And I click on book now Button
      Then I verify if I am navigated to cart page with total price