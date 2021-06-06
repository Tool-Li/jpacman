@S3 @framework
Feature: Move The Ghost
  As a ghost;
  I get automatically moved around;
  So that I can try to kill the player.

  @S3.1
  Scenario: A ghost moves
    Given the game has started ghost 01
    And  a ghost is next to an empty cell 01
    When  a tick event occurs 01
    Then  the ghost can move to that cell 01

  @S3.2:
  Scenario: The ghost moves over a square with a pellet
    Given the game has started ghost 02
    And  a ghost is next to a cell containing a pellet 02
    When  a tick event occurs 02
    Then  the ghost can move to the cell with the pellet 02
    And  the pellet on that cell is not visible anymore 02

  @S3.3:
  Scenario: The ghost leaves a cell with a pellet
    Given a ghost is on a cell with a pellet (see S3.2) 03
    When  a tick even occurs 03
    Then  the ghost can move away from the cell with the pellet 03
    And  the pellet on that cell is is visible again 03

  @S3.4:
  Scenario: The player dies
    Given the game has started ghost 04
    And  a ghost is next to a cell containing the player 04
    When  a tick event occurs 04
    Then  the ghost can move to the player 04
    And  the game is over ghost 04