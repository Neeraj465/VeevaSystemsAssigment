Feature: Test CoreProduct HomePage
  Background:
    Given User logs in to CP homepage

  Scenario: Store Jacket Details
    When User hovers over Shop Menu
    And User navigates to Men's section
    Then User stores Jacket details

  Scenario: Count Videos Feeds in Homepage
    When User hovers over ... Menu Item
    And User clicks on New & Features
    Then User counts total number of Videos Feeds
    And User counts the videos feeds present in the page from more than "3" days
