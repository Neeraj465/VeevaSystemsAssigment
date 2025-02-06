Feature: Test Derived Project One
  Background:
    Given User logs in to DP1 homepage

    Scenario: Verify Ticket Menu
      When User counts number of slide present in ticket menu
      Then User validates title of each slide
      And User validates duration of each slide with expected duration