/**
 * Kyle Vickers
 * CS415
 * Prog 12
 */

/**
 *  Holds constants for Tetris
 */


import java.awt.Color;

public class Specs
{
  /******************************************************************/
  // Some Tetris Constants
  
  // Board Size
  public static final int BOARD_COLS = 10;   
  public static final int BOARD_ROWS = 20;
  
  //Board Location
  public static final int BOARD_X= 200; 
  public static final int BOARD_Y = 80;
  
  //Tetromino Colors
  public static final Color J_COLOR = Color.BLUE;
  public static final Color I_COLOR = Color.CYAN;
  public static final Color O_COLOR = Color.RED;
  public static final Color T_COLOR = Color.YELLOW;
  public static final Color Z_COLOR = Color.PINK;
  public static final Color S_COLOR = Color.GREEN;
  public static final Color L_COLOR = Color.MAGENTA;
  
  // Tile Size
  public static final int TILE_SIZE = 20;
  
  //Default speed
  public static final int TIMER_DEF = 1000;
}
