// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 3
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import javax.swing.JColorChooser;
import java.util.Random; 

/** Paramterised Shapes: draw tricolour flags and game boards */
public class ParameterisedShapes{
    
    //Constants for CORE  (three strip flags)
    public static final double FLAG_WIDTH = 200;
    public static final double FLAG_HEIGHT = 133;

    //Constants for COMPLETION
    public static final double BOARD_LEFT = 15;  // Left side of each row
    public static final double BOARD_TOP = 15;   // Top of the first row
    public static final double ROW_SIZE = 40;    // Height of each row.
    public static final double DISH_WIDTH = ROW_SIZE-4;      // Size of the dishes
    public static final double DISH_HEIGHT = DISH_WIDTH-10;  
    public static final double PEBBLE_DIAM = 10; // Size of the pebbles
    
    private static Random rng = new Random(); // Generating random number for Challenge
    
    /**   CORE
     * asks user for a position and three colours, then calls the
     * drawTriColorFlag method, passing the appropriate arguments
     */
    public void doCore(){
        double left = UI.askDouble("Left of flag");
        double top = UI.askDouble("Top of flag");
        boolean horiz = UI.askBoolean("Are the stripes horizontal?");
        UI.println("Now choose the colours");
        Color stripe1 = JColorChooser.showDialog(null, "First Stripe", Color.white);
        Color stripe2 = JColorChooser.showDialog(null, "Second Stripe", Color.white);
        Color stripe3 = JColorChooser.showDialog(null, "Third Stripe", Color.white);
        this.drawThreeStripeFlag(left, top, horiz, stripe1, stripe2, stripe3);
    }

    /**   CORE
     * draws a three colour flag at the given position consisting of
     * three equal size stripes of the given colors
     * The stripes are horizontal or vertical
     * The size of the flag is specified by the constants FLAG_WIDTH and FLAG_HEIGHT
     */
    public void drawThreeStripeFlag(double left, double top, boolean horiz, Color stripe1, Color stripe2, Color stripe3){
        UI.setColor(stripe1); 
        if (horiz == true){
            UI.fillRect(left, top, FLAG_WIDTH, FLAG_HEIGHT/3);
            UI.setColor(stripe2); 
            UI.fillRect(left, top + (FLAG_HEIGHT/3), FLAG_WIDTH, FLAG_HEIGHT/3);
            UI.setColor(stripe3); 
            UI.fillRect(left, top + (FLAG_HEIGHT*2/3), FLAG_WIDTH, FLAG_HEIGHT/3);
        } else{
            UI.fillRect(left, top, FLAG_WIDTH/3, FLAG_HEIGHT);
            UI.setColor(stripe2); 
            UI.fillRect(left + (FLAG_WIDTH/3), top, FLAG_WIDTH/3, FLAG_HEIGHT);
            UI.setColor(stripe3); 
            UI.fillRect(left + (FLAG_WIDTH*2/3), top, FLAG_WIDTH/3, FLAG_HEIGHT);
        }
    }

    /**   COMPLETION
     * Draws a pebble game board with five rows of increasing size
     *   The first row has 6 dishes, the second has 7 dishes, the third has 8, etc.
     *   The positions of the red and blue pebbles are shown in this table:
     *   (where the |'s separate the dishes)
     *     |   | r |   |   |   | b |
     *     |   | b | r |   |   |   |   |
     *     |   |   |   |   |   | r |   | b |
     *     | b |   |   | r |   |   |   |   |   |
     *     |   |   | b |   |   |   |   |   |   | r |
     *
     *  It uses the drawPebbleRow method which draws one row and the two pebbles in it.
     */
    public void doCompletion(){
        UI.clearGraphics();
        this.drawPebbleRow(1,6,1,5, true);
        this.drawPebbleRow(2,7,1,2, false);
        this.drawPebbleRow(3,8,5,7, true);
        this.drawPebbleRow(4,9,0,3, false);
        this.drawPebbleRow(5,10,2,9,false);
    }

    /**   COMPLETION
     * Draws a row of a pebble game. Parameters must be sufficient to specify
     * the position and size of the row, and which dishes the red and blue pebbles are in.
     * Hint: use the drawRowOutline, drawDish and drawPebble methods!
     */
    public void drawPebbleRow(int rowNum, int numDishes, int pebbleOne, int pebbleTwo, boolean colour){
        drawRowOutline(rowNum, numDishes); 
        for (int i = 0; i < numDishes; i++){
            drawDish(rowNum, i); 
        }
        //whats the opposite of t
        if (colour == true){
            drawPebble(rowNum, pebbleOne, Color.red);  
            drawPebble(rowNum, pebbleTwo, Color.blue);  
        } else{
            drawPebble(rowNum, pebbleOne, Color.blue); 
            drawPebble(rowNum, pebbleTwo, Color.red); 
        }
    }

    /**
     * Draws the outline of the specified row with the specified number of dishes.
     * (rows numbered from 0)
     */
    public void drawRowOutline(int rowNum, int numDishes){
        UI.setColor(Color.black); 
        UI.drawRect(BOARD_LEFT, BOARD_TOP + (rowNum * ROW_SIZE), ROW_SIZE*numDishes, ROW_SIZE);
    }

    /**
     * Draw the specified dish in the specified row
     * (rows and dishes are numbered from 0)
     */
    public void drawDish(int rowNum, int dishNum){
        double dishLeft = BOARD_LEFT+dishNum*ROW_SIZE + ROW_SIZE/2 - DISH_WIDTH/2 ;
        double dishTop = BOARD_TOP + rowNum * ROW_SIZE + ROW_SIZE/2 - DISH_HEIGHT/2;
        UI.setColor(new Color(230, 230, 230));   // very light grey
        UI.fillOval(dishLeft, dishTop, DISH_WIDTH, DISH_HEIGHT);
        UI.setColor(Color.black);
        UI.drawOval(dishLeft, dishTop, DISH_WIDTH, DISH_HEIGHT);
    }

    /**
     * Draw a pebble in specified dish in the specified row with the specified color.
     * (rows and dishes are numbered from 0)
     */
    public void drawPebble(int rowNum, int dishNum, Color pebbleColor){
        double pebbleTop = BOARD_TOP + rowNum * ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        double pebbleLeft = BOARD_LEFT+dishNum*ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        UI.setColor(pebbleColor);
        UI.fillOval(pebbleLeft, pebbleTop, PEBBLE_DIAM, PEBBLE_DIAM);
    }
    
    public void erasePebble(int rowNum, int dishNum, Color pebbleColor){
        double pebbleTop = BOARD_TOP + rowNum * ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        double pebbleLeft = BOARD_LEFT+dishNum*ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        UI.eraseOval(pebbleLeft, pebbleTop, PEBBLE_DIAM, PEBBLE_DIAM);
        UI.setColor(new Color(230, 230, 230));
        UI.fillOval(pebbleLeft, pebbleTop, PEBBLE_DIAM, PEBBLE_DIAM);
    }
    
    public void doChallengeSimpler(){
        int numDishes = getDishes(); 
        int redPosition = 0; 
        int bluePosition = numDishes - 1; 
        
        setUpGame(numDishes);
        drawPebble(1, redPosition, Color.red); 
        drawPebble(1, bluePosition, Color.blue); 
        UI.sleep(500);

        for (int i = 1; i < 100000; i++){
            if (i % 2 == 0){ // red turn 
                if (redPosition == numDishes - 2 && bluePosition == numDishes -1){
                    erasePebble(1, redPosition, Color.red); 
                    redPosition = redPosition - 1; 
                    drawPebble(1, redPosition, Color.red);
                    UI.sleep(100);
                    
                } else if (redPosition + 1 == bluePosition){
                    erasePebble(1, redPosition, Color.red); 
                    redPosition = redPosition + 2; 
                    drawPebble(1, redPosition, Color.red);
                    UI.sleep(100); 
                    
                    if (redPosition == numDishes -1){
                        UI.println("Red is the Winner");
                        break; 
                    } else {
                        erasePebble(1, bluePosition, Color.blue); 
                        bluePosition = numDishes - 1; 
                        drawPebble(1, bluePosition, Color.blue);
                        UI.sleep(500);
                    }
                } else{
                    erasePebble(1, redPosition, Color.red); 
                    redPosition = redPosition + 1; 
                    drawPebble(1, redPosition, Color.red);
                    UI.sleep(500);
                    if (redPosition == numDishes -1){
                        UI.println("Red is the Winner");
                        break; 
                    }
                }} else {
                if (bluePosition == 1 && redPosition == 0){
                    erasePebble(1, bluePosition, Color.red); 
                    redPosition = bluePosition + 1; 
                    drawPebble(1, bluePosition, Color.red);
                    UI.sleep(100);
                } else if(bluePosition - 1 == redPosition){
                    erasePebble(1, bluePosition, Color.blue); 
                    bluePosition = bluePosition - 2; 
                    drawPebble(1, bluePosition, Color.blue);
                    UI.sleep(100); 
                    
                    if (bluePosition == 0){
                        UI.println("Blue is the Winner");
                        break; 
                    } else{
                        erasePebble(1, redPosition, Color.red); 
                        redPosition = 0; 
                        drawPebble(1, redPosition, Color.red);
                        UI.sleep(500);
                    }
                } else{
                    erasePebble(1, bluePosition, Color.blue); 
                    bluePosition = bluePosition - 1; 
                    drawPebble(1, bluePosition, Color.blue);
                    UI.sleep(500);
                    if (bluePosition == 0){
                        UI.println("Blue is the Winner");
                        break; 
                        } 
                }
            }
        }
    }
    
    public void doChallengeHarder(){
        int numDishes = getDishes(); 
        int numRows = getRows(); 
        String playerMove = "L"; 
        int playerRow = 1; 
        int redWin = 0; 
        int blueWin = 0; 
        
        int[] winList = new int[numRows]; // lists that will record the rows that a player has won in so that the pebbles can no longer be moved in that row
        
        // puting values into the winList
        for (int i = 0; i < numRows; i++){
            winList[i] = 99; // number over the max number of rows that will act as a place holder for now
        }
        
        // Draw the board 
        for (int row = 0; row < numRows; row++){
            drawRowOutline(row, numDishes); 
            for (int dish = 0; dish < numDishes; dish++){
                drawDish(row, dish); 
            }
        }
        
        // Set up and draw pebbles 
        int[] redPosition = new int[numRows]; 
        int[] bluePosition = new int[numRows];
        for (int i = 0; i < numRows; i++){
            redPosition[i] = 0; 
            bluePosition[i] = numDishes - 1; 
            
            drawPebble(i, redPosition[i], Color.red); 
            drawPebble(i, bluePosition[i], Color.blue); 
        }
        UI.sleep(500);
        
        // play the game 
        for (int i = 0; i < 100000000; i++){
            if (i % 2 == 0){ // red turn 
                while (true){
                    playerRow = rowInput(numRows) -1; // asks user which row they want to move the pebble in 
                    boolean contains = false; 
                    for (int n: winList){
                        if (n == playerRow){
                            contains = true; 
                        } 
                    }
                    if (contains == true){
                        UI.println("Row " + (playerRow+1) + " has already been won, please choose another row."); 
                    }else {
                        break; 
                    }
                }
                playerMove = userInput(); // ask user if they want to move left or right
                
                if (redPosition[playerRow] == 0 && playerMove.equals("L")){ // first move 
                    UI.println("Sorry, you can only move right right now.");
                    if (bluePosition[playerRow] == 1){ // send blue home 
                        redPosition[playerRow] = redMove(playerRow, 2, redPosition[playerRow]); 
                        bluePosition[playerRow] = blueHome(bluePosition[playerRow], numDishes, playerRow); 
                    }
                    redPosition[playerRow] = redMove(playerRow, 1, redPosition[playerRow]); 
                } else if(redPosition[playerRow] == numDishes - 3 && playerMove.equals("R") && bluePosition[playerRow] == numDishes -2){ // jump over blue but dont return blue home
                    redPosition[playerRow] = redMove(playerRow, 2, redPosition[playerRow]); 
                    UI.println("Red has won in row " + (playerRow+1) + "."); 
                    redWin++;  
                    winList[playerRow] = playerRow; 
                    if (redWin == numRows){
                        UI.println("Red is the winner!");
                        break; 
                    }
    
                }else if(redPosition[playerRow] == bluePosition[playerRow] - 1 && playerMove.equals("R")){ // red jump right & send blue home
                    redPosition[playerRow] = redMove(playerRow, 2, redPosition[playerRow]); 
                    bluePosition[playerRow] = blueHome(bluePosition[playerRow], numDishes, playerRow);
                }else if(redPosition[playerRow] == bluePosition[playerRow] + 1 && playerMove.equals("L")){ // red jump left & sends blue home
                    redPosition[playerRow] = redMove(playerRow, -2, redPosition[playerRow]); 
                    bluePosition[playerRow] = blueHome(bluePosition[playerRow], numDishes, playerRow);
                } else{
                    if (playerMove.equals("R")){ // red move right
                        redPosition[playerRow] = redMove(playerRow, 1, redPosition[playerRow]); 
                        if (redPosition[playerRow] == numDishes - 1){
                            UI.println("Red has won in row " + (playerRow+1) + "."); 
                            redWin++;
                            winList[playerRow] = playerRow; 
                            if (redWin == numRows){
                                UI.println("Red is the winner!");
                                break; 
                            }
                        }
                    } else { // red move left 
                        redPosition[playerRow] = redMove(playerRow, -1, redPosition[playerRow]); 
                    }
                }
            } else { //blue turn 
                int blueRow; 
                
                while (true){
                    blueRow = rng.nextInt(numRows); // picks random row  
                    boolean contains = false; 
                    for (int n: winList){
                        if (n == blueRow){
                            contains = true; 
                        } 
                    }
                    if (contains == true){
                        continue; 
                    }else {
                        break; 
                    }
                }
                
                int blueLeftRight = (int)Math.round(Math.random()); // 0 = right, 1 = left
                
                if (bluePosition[blueRow] == numDishes - 1){ // blue at home position
                    if (redPosition[blueRow] == numDishes -2){ // blue at home and jumps over red  
                        bluePosition[blueRow] = blueMove(blueRow, -2, bluePosition[blueRow]); 
                        redPosition[blueRow] = redHome(redPosition[blueRow], blueRow); 
                    }else{ // moves blue left because that is the only option
                        bluePosition[blueRow] = blueMove(blueRow, -1, bluePosition[blueRow]); 
                    }
                } else if(bluePosition[blueRow] == redPosition[blueRow] + 1 && blueLeftRight == 1){ // blue jump left & send red home
                    bluePosition[blueRow] = blueMove(blueRow, -2, bluePosition[blueRow]);  
                    if (bluePosition[blueRow] == 0){
                        UI.println("Blue has won in row " + (blueRow+1) + "."); 
                        blueWin++;
                        winList[blueRow] = blueRow; 
                        if (blueWin == numRows){
                            UI.println("Blue is the winner!");
                            break; 
                        }
                    }else{
                        redPosition[blueRow] = redHome(redPosition[blueRow], blueRow); 
                    }
                }else if(bluePosition[blueRow] == redPosition[blueRow] - 1 && blueLeftRight == 0){ //blue jump right & send red home
                    bluePosition[blueRow] = blueMove(blueRow, 2, bluePosition[blueRow]); 
                    redPosition[blueRow] = redHome(redPosition[blueRow], blueRow);  
                }else{ 
                    if (blueLeftRight == 1){ // blue move left 
                        bluePosition[blueRow] = blueMove(blueRow, -1, bluePosition[blueRow]);  
                        if (bluePosition[blueRow] == 0){
                            UI.println("Blue has won in row " + (blueRow+1) + "."); 
                            blueWin++;
                            winList[blueRow] = blueRow; 
                            if (blueWin == numRows){
                                UI.println("Blue is the winner!");
                                break; 
                            }
                        }
                    } else{ //blue move right
                        bluePosition[blueRow] = blueMove(blueRow, 1, bluePosition[blueRow]); 
                    }
                }
            }

        }
    }    
    
        
    // Method to move red pebble
    public int redMove(int row, int spaces, int redPosition){
        erasePebble(row, redPosition, Color.red); 
        int newPosition = redPosition + spaces; 
        drawPebble(row, newPosition, Color.red);
        UI.sleep(500);
        
        return newPosition; 
    }
    
    // Method to send red pebble home 
    public int redHome(int redPosition, int row){
        erasePebble(row, redPosition, Color.red); 
        redPosition = 0; 
        drawPebble(row, redPosition, Color.red);
        UI.sleep(500);
        
        return redPosition; 
    }

    // Method to move blue pebble
    public int blueMove(int row, int spaces, int bluePosition){
        erasePebble(row, bluePosition, Color.blue); 
        bluePosition = bluePosition + spaces; 
        drawPebble(row, bluePosition, Color.blue);
        UI.sleep(500);
        
        return bluePosition; 
    }
    
    // Method to send blue pebble home 
    public int blueHome(int bluePosition, int numDishes, int row){
        erasePebble(row, bluePosition, Color.blue); 
        bluePosition = numDishes - 1; 
        drawPebble(row, bluePosition, Color.blue);
        UI.sleep(500);
        
        return bluePosition; 
    }
    
    public int getDishes(){
        int numDishes = 0; 
        boolean valid = false;
        
        while (valid == false){
            numDishes = UI.askInt("How many dishes?");
            if (numDishes > 3 && numDishes < 11){
                valid = true;  
            } else{
                UI.println("Please enter a number between 3 and 11.");
                continue; 
            }
        }
        return numDishes; 
    }
    
    public int getRows(){
        int numRows = 0; 
        boolean valid = false;
        
        while (valid == false){
            numRows = UI.askInt("How many Rows?");
            if (numRows > 0 && numRows < 11){
                valid = true;  
            } else{
                UI.println("Please enter a number between 0 and 11.");
                continue; 
            }
        }
        return numRows; 
    }
    
    public int rowInput(int numRows){
        boolean valid = false;
        int row = 1; 
        
        while (valid == false){
            row = UI.askInt("Which row do you want to move your pebble in?)");
            if (row > 0 && row < numRows + 1){
                valid = true;  
            } else{
                UI.println("Please enter a number between 0 and " + numRows);
                continue; 
            }
        }
        return row;  
    } 
 
    
    public String userInput(){
        boolean valid = false;
        String player = "L"; 
        
        while (valid == false){
            player = UI.askString("Left or Right? (L or R)");
            if (player.equals("L") || player.equals("R")){
                valid = true;  
            } else{
                UI.println("Please enter either L or R");
                continue; 
            }
        }
        return player;  
    } 

    public void setUpGame(int numDishes){
        drawRowOutline(1, numDishes);
        for (int i = 0; i < numDishes; i++){
            drawDish(1, i); 
        }
    }

    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Core", this::doCore );
        UI.addButton("Completion", this::doCompletion );
        UI.addButton("Challenge Simpler", this::doChallengeSimpler );
        UI.addButton("Challenge Harder", this::doChallengeHarder );
        UI.addButton("Quit", UI::quit );
    }

    public static void main(String[] args){
        ParameterisedShapes ps = new ParameterisedShapes ();
        ps.setupGUI();
    }

}
