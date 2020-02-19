#Author: himanshu.dua@optum.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@Adjustement_overpayment_underpayment
Feature: 69/70 overpayment and underpayment adjustment

  @TC_Adjustment_69_70
  Scenario Outline: Validate 30 line warning message on MPC screen
    Given User login with valid inputs "<RegionName>" and "<FilmOfficeNumber>" and "<AdjustingOfficeNumber>" and "<SystemValue>"
    When User navigates to EDS9 screen from login screen "<ICN>"
    And User navigate to AHI screen
    And User navigate to MHI screen from AHI "<ICN>"
    And User update MHI control line to MPC for 69/70 transaction "<ICN>"
    And User performs 69/70 reversal on MPC screen "<Adjustment>"
    And User performs ADJ on claim "<Adjustment>"
    Then User Logoff

    Examples: 
      | RegionName | Region | FilmOfficeNumber | AdjustingOfficeNumber | SystemValue | ICN        | Suffix | Adjustment   |
      #| CICTACX1   | Alpha  |              111 |                   111 | X           | AA00004447 |      1 | Overpayment  |
      | CICTACE1   | Alpha  |              683 |                   683 | E           | 0007436809 |      1 | Overpayment  |
      | CICTACX1   | Alpha  |              111 |                   111 | X           | AA00004060 |      1 | Underpayment |
