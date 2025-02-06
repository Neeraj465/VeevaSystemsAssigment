Feature: Test Derived Product 2 Homepage
  Background:
    Given User navigates to DP2 Homepage

    Scenario:
      When User stores all hyperlinks in csv file
      Then User verifies and reports if any duplicate hyperlinks are present