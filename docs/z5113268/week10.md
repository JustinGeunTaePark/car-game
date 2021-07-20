
# Date: 7/5/2018
  ## What I have done today
    -> Refactored/Implementd the Vertical and Horizontal classes so they controlled
      the movement functionality of the vehicles
    -> Refactored some of the GUI code

  ## What I will do next
    -> Continue to refactor the GUI code and separate the Grid element from the main GUI object

  ## Anything in my way ?
    -> The mouse and random component block in the GUIGame class are confusing ...

# Date: 8/5/2018
  ## What I have done today:
    -> Removed the component class block from GUIGame
    -> Separated the grid elements from the GUIGame class

  ## What I will do next:
    -> Implement simpler mouse controls
    -> Write some more comments in the code

   ## Anything in my way ?
    -> The boundaries
    -> Car collisions


# Date: 9/5/2018
   ## What I have done today:
    -> Implementd a basic pick up and drag mouse functionality,
    -> Animation for car 'clicking' to grid tiles
    -> Simplified the GUIgrid class, so it handles translating the GUI coordinates to grid coordinates
    -> The vehicle class is no longer a part of JLabel, the standard grid, state and vehicle classes now should only ever deal with grid coordinates and not the GUI coordinates

  ## What I will do next:
    -> Add Car colours and images
    -> Break apart some of the collision handling
    -> Maintain state consistency
    -> Reset functionality

   ## Anything in my way ?
    -> The state class is proving annoying to deal with


# Date: 11/5/2018
 ## What I have done today:
    -> Reset functioanlity
    -> Added image sprites for the vehicles, stolen from the course webpage
    -> Separated the initial board setup from the GUIGame
    -> Moved the state initialisation from GUIGame to GUIGrid

  ## What I will do next:
    -> Implement Redo
    -> Implement Undo

   ## Anything in my way ?
    -> Group communication
