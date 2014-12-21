/*
 * Kyle Vickers
 * CS415
 * November 22 2010
 * TetrisStart.java
 * 
 * TetrisStart: 
 * Sets the game and the board up. For blocks are called in order
 * and fall with each second. Pressing down speeds up the drop speed,
 * pressing up rotates the block( checks to see if the block will )
 * go out of bounds before rotating it ), and blocks stop when they
 * hit the bottom of the board. 
 **/

import wheelsunh.users.Frame;
import wheelsunh.users.*;
import java.awt.Color;
import java.util.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.Random;


public class TetrisStart implements Animator, KeyListener
{
  
  private Rectangle[][] board;
  private LTetromino one;
  private AnimationTimer timer;
  private Random gen;
  private static final int LEFT = 37, UP = 38, RIGHT = 39, DOWN = 40;
  private Tetromino curTet, nextTet, preview;
  private int tetNum = 1;
  private TextBox rowBox, scoreBox, levelBox;
  private int cleared, score, level;
  private int delayTimer = Specs.TIMER_DEF; 
  private int combo;
  
  /**==================================================================
    * Constructor:
    * Constructs the board. Initializes the first falling tetronimo.
    */
  public TetrisStart( )
  {
    board = new Rectangle[ Specs.BOARD_ROWS ][ Specs.BOARD_COLS ];
    cleared = 0;
    score = 0;
    level = 1;
    
    scoreBox = new TextBox( 0, 40 );
    scoreBox.setSize( 150, 30 );
    rowBox = new TextBox( 0, 70 );
    rowBox.setSize( 150, 30 );
    levelBox = new TextBox( 0, 100 );
    levelBox.setSize( 150, 30 );
    
    updateDisplay();
    
    for( int r = 0; r < Specs.BOARD_ROWS; r++ )
      for( int c = 0; c < Specs.BOARD_COLS; c++ )
      makeBoardSquare( r, c );
    
    gen = new Random( );
    tetNum = gen.nextInt(7) + 1;
    
    newTetromino();
    
    timer = new AnimationTimer( delayTimer, this);
    timer.start( );
  }
  
  /**===================================================================
    * makeBoardSquare( int r, int c ):
    * Makes the board in correlation to the tile size in Specs. 
    */
  private void makeBoardSquare( int r, int c )
  {
    int x = Specs.BOARD_X + c * Specs.TILE_SIZE;
    int y = Specs.BOARD_Y + r * Specs.TILE_SIZE;
    
    board[r][c] = new Rectangle( );
    board[r][c].setFillColor( Color.BLACK );
    board[r][c].setFrameColor( Color.black ); 
    board[r][c].setLocation( x, y ); 
    board[r][c].setSize( Specs.TILE_SIZE, Specs.TILE_SIZE ); 
  }
  
  /**===================================================================
    * animate( )
    * The animate method which moves the Tetrominoes down the board.
    * This also stops the tetronimoes when they hit the bottom of the
    * board.
    */
  public void animate( )
  {
    int max = curTet.getMaxRow( );
    max += curTet.getRow( );
    
    if( max < Specs.BOARD_ROWS -1 && checkOk( 1, 0 ) ) // still room to fall
    {
      {
        curTet.fall( );  // fall
      }
    }
    else //part of shape hit bottom
    { 
      addTilesToBoard();
      checkRows();
      if( curTet.getRow() == 0 && curTet.getCol() == 4 )
      {
        System.out.println(" YOU LOSE YOU LOSE YOU LOSE ");
        System.out.println(" YOU LOSE YOU LOSE YOU LOSE ");
        System.out.println(" YOU LOSE YOU LOSE YOU LOSE ");
        System.out.println(" YOU LOSE YOU LOSE YOU LOSE ");
        System.out.println(" YOU LOSE YOU LOSE YOU LOSE ");
        System.out.println(" YOU LOSE YOU LOSE YOU LOSE ");
      }
      else
      {
        newTetromino( );
      }
    } 
  }
  
  public void updateDisplay()
  {
    scoreBox.setText( "Score: " + score );
    rowBox.setText( "Rows Cleared: " + cleared );
    levelBox.setText( "Level: " + level );
  }
  
  /**===================================================================
    * keyPressed( KeyEvent e )
    * This gets the key the user presses and performs the correct 
    * option
    */
  public void keyPressed( KeyEvent e ) 
  {
    
    if( e.getKeyCode( ) == 37 )
    {
      
      int min = curTet.getMinColumn( );
      min += curTet.getCol( );
      
      if( min > 0 && checkOk( 0, -1 ) )
        curTet.moveLeft( );
    }
    else if( e.getKeyCode( ) == 38 )
    {
      if( curTet.canRotate( ) == true && checkRotate() == true ) 
        curTet.rotate( );
    }
    else if( e.getKeyCode( ) == 39 )
    {
      int max = curTet.getMaxColumn( );
      max += curTet.getCol( );
      if( max < Specs.BOARD_COLS - 1 && checkOk( 0, 1 ) )
        curTet.moveRight( ); 
    }
    else if( e.getKeyCode( ) == 40 )
    {
      timer.setDelay( Specs.TIMER_DEF - 900 ); 
    }
  }
  
  /**===================================================================
    * newTetronimo( )
    * This creates a new tetronimo. In this assignment the tets were
    * not supposed to be randomly generated so i changed it to call
    * the tets one at a time. 
    */
  public void newTetromino( )
  { 
    switch ( tetNum )
    {
      case 1:
        nextTet = new TTetromino( 0, 0 );
        break;
      case 2:
        nextTet = new OTetromino( 0, 0 );
        break;
      case 3:
        nextTet = new ITetromino( 0, 0 );
        break;
      case 4:
        nextTet = new JTetromino( 0, 0 );
        break;
      case 5:
        nextTet = new LTetromino( 0, 0 );
        break;
      case 6:
        nextTet = new STetromino( 0, 0);
        break;
      case 7:
        nextTet = new ZTetromino( 0, 0);
        break;
    }
    
    
    curTet = nextTet;
    curTet.setRC( 0, 4 );
    curTet.draw( );
    
    tetNum = gen.nextInt(7) + 1;
    
    switch ( tetNum )
    {
      case 1:
        makePreviewBox();
        preview = new TTetromino( 0, 0 );
        preview.setRC( 1, 13 );
        preview.draw();
        break;
      case 2:
        makePreviewBox();
        preview = new OTetromino( 0, 0 );
        preview.setRC( 1, 13 );
        preview.draw();
        break;
      case 3:
        makePreviewBox();
        preview = new ITetromino( 0, 0 );
        preview.setRC( 1, 13 );
        preview.draw();
        break;
      case 4:
        makePreviewBox();
        preview = new JTetromino( 0, 0 );
        preview.setRC( 1, 13 );
        preview.draw();
        break;
      case 5:
        makePreviewBox();
        preview = new LTetromino( 0, 0 );
        preview.setRC( 1, 13 );
        preview.draw();
        break;
      case 6:
        makePreviewBox();
        preview = new STetromino( 0, 0);
        preview.setRC( 1, 13 );
        preview.draw();
        break;
      case 7:
        makePreviewBox();
        preview = new ZTetromino( 0, 0);
        preview.setRC( 1, 13 );
        preview.draw();
        break;
    }
  }
  
  //********************************************************************
  //Adds tiles that fall to the baord
  //********************************************************************
  
  public void addTilesToBoard()
  {
    Vector< Rectangle > tiles = new Vector< Rectangle >();
    tiles = curTet.getTiles();
    for( int i = 0; i < tiles.size(); i++ )
    {
      int ctXPos = (tiles.get( i ).getXLocation() - Specs.BOARD_X) / Specs.TILE_SIZE;
      int ctYPos = (tiles.get( i ).getYLocation() - Specs.BOARD_Y) / Specs.TILE_SIZE;
      board[ ctYPos ][ctXPos] = tiles.get(i); 
      board[ ctYPos ][ctXPos].setSize( Specs.TILE_SIZE, Specs.TILE_SIZE );
    }
  }
  
  
  //********************************************************************
  //Checks to see if the tile can fall further
  //********************************************************************
  
  public boolean checkOk( int dy, int dx )
  {    
    Vector< Rectangle > tiles = new Vector< Rectangle >();
    tiles = curTet.getTiles();
    boolean empty = false;
    for( int i = 0; i < tiles.size(); i++ )
    {
      int ctXPos = (tiles.get( i ).getXLocation() - Specs.BOARD_X) / Specs.TILE_SIZE;
      int ctYPos = (tiles.get( i ).getYLocation() - Specs.BOARD_Y) / Specs.TILE_SIZE;
      int ghostY = ( dy + ctYPos );
      int ghostX = ( dx + ctXPos );
      
      if( board[ ghostY ][ ghostX ].getFillColor().equals(Color.BLACK) )
      {
        empty = true;
      }
      else
      {
        return false;
      }
    }
    return empty;
  }
  
  
  //********************************************************************
  //Checks to see if the row is empty
  //********************************************************************
  
  public boolean isEmpty( int r, int c )
  {
    if( board[ r ][ c ].getFillColor().equals( Color.BLACK ) )
    {
      System.out.println("NEXT ROW IS EMPTY");
      return true;
    }
    System.out.println("NEXT ROW IS FILLED");
    return false; 
  }
  
  
  //********************************************************************
  //Checks the rotation of tetronimos
  //********************************************************************
  
  public boolean checkRotate( )
  {
    curTet.rotate();
    
    Vector< Rectangle > tiles = new Vector< Rectangle >();
    tiles = curTet.getTiles();
    boolean empty = false;
    for( int i = 0; i < tiles.size(); i++ )
    {
      int ctXPos = (tiles.get( i ).getXLocation() - Specs.BOARD_X) / Specs.TILE_SIZE;
      int ctYPos = (tiles.get( i ).getYLocation() - Specs.BOARD_Y) / Specs.TILE_SIZE;
      int ghostY = (  ctYPos );
      int ghostX = (  ctXPos );
      
      if( board[ ghostY ][ ghostX ].getFillColor().equals(Color.BLACK) )
      {
        empty = true;
      }
      else
      {
        curTet.deRotate();
        return false;
      }
    }
    
    curTet.deRotate();
    return empty;
  }
  
  
  //********************************************************************
  //Checks to see if the rows are filled
  //********************************************************************
  
  public void checkRows()
  {
    for( int r = Specs.BOARD_ROWS - 1; r > 0; r-- )
    {
      int filled = 0;
      for( int c = 0; c < board[0].length; c++ )
      {
        if( !board[r][c].getFillColor().equals( Color.BLACK ) )
        {
          filled++;
        }
        if( filled == 10 )
        {
          clearRow( r );
          moveTilesDown( r );
        }
      }
    }
    
    addScore();
    
    combo = 0;
    //System.out.println("Combo at the end of check rows: " + combo );
  }
  
  //********************************************************************
  //Updates the score
  //********************************************************************
  public void addScore()
  {
    switch( combo )
    {
      case 0:
        break;
      case 1:
        System.out.println("SINGLE");
        score += ( 40 * level );
        break;
      case 2:
        System.out.println("DOUBLE");
        score += ( 100 * level );
        break;
      case 3:
        System.out.println("TRIPLE");
        score += ( 300 * level );
        break;
      case 4:
        System.out.println("TETRIS TETRIS TETRIS");
        score += ( 1200 * level );
        break;
    }
    
    updateDisplay();
    
  }
  
  //********************************************************************
  //Clears the row
  //********************************************************************
  
  public void clearRow( int r )
  {
    cleared++;
    
    if( cleared % 10 == 0 )
    {
      level++;
      increaseSpeed();
    }
    
    updateDisplay();
    combo++;
    System.out.println( "Just cleared a row!" );
    
    for( int c = 0; c < board[0].length; c++ )
    {
      board[ r ][ c ].setFillColor( Color.BLACK );
    }
  }
  
  //********************************************************************
  //Moves the next row down
  //********************************************************************
  
  public void nextRowDown( int pr )
  {
    for( int c = 0; c < board[0].length; c++ )
    {
      board[ pr ][ c ].setFillColor( board[pr - 1][ c ].getFillColor () );
    }
  }
  
  //********************************************************************
  //Makes the preview box
  //********************************************************************
  public void makePreviewBox()
  {
    Rectangle clearBox = new Rectangle( 435, 90 );
    clearBox.setFrameColor( Color.BLACK );
    clearBox.setFillColor( Color.WHITE );
    clearBox.setSize( 100, 100 );
  }
  
  //********************************************************************
  //Moves tiles down after a row has been cleared
  //********************************************************************
  
  public void moveTilesDown( int startR )
  {
    
    for( int r = startR ; r > 0; r-- )
    {
      for( int c = 0; c < board[0].length; c++ )
      {
        board[r][c].setFillColor( board[ r - 1 ][ c ].getFillColor() );
        board[r][c].setFrameColor( board[ r - 1][ c ].getFrameColor() );
      }
    }
    checkRows();
  }
  
  
  //********************************************************************
  //Increases the speed of the game as you lvl up
  //********************************************************************
  
  public void increaseSpeed()
  {
    
    if( delayTimer >= 100 )
    {
      delayTimer = 4 * ( delayTimer ) / 5;
      timer.setDelay( delayTimer ); 
    }
    System.out.println( delayTimer );
  }
  
  //*===================================================================/
  /** Handle the key released event */
  public void keyReleased( KeyEvent e ) 
  {
    timer.setDelay( Specs.TIMER_DEF );
  }
  
  //*===================================================================/
  /** Handle the key typed event. */
  public void keyTyped( KeyEvent e ) {}
  
  
  /**===================================================================
    * main( )
    */ 
  public static void main( String args[] )
  {
    Frame frame = new Frame( );
    TetrisStart game = new TetrisStart( );
    frame.addKeyListener( game );
  }
}
