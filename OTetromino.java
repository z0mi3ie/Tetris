/**
 * Kyle Vickers
 * CS415
 * Prog 12
 * 
 * Makes the O tetrominoes
 */

import wheelsunh.users.*;
import java.awt.Color;
import java.util.*;
import java.awt.Point;

public class OTetromino implements Tetromino
{
  /*===================================================================*/
  //  instance variables
  
  // the four tiles for this shape
  protected Vector< Rectangle > tiles; 
  
  // the current board row and column for this shape
  protected int row, col;
  
  // Four configurations for the tile rotations
  protected Vector< Configuration > config;
  
  // the index of the current configuration
  protected int currentConfigIndex;
  
  // the current configuration
  protected Configuration currentConfig ;
  
  /**===================================================================
    * Constructor:
    * Constructs the tetronimo. 
    */
  public OTetromino( int r, int c )
  {
    row = r;
    col = c;
    tiles = new Vector< Rectangle >( );
    
    for( int i = 0; i < 4; i++ )
    {
      tiles.add( new Rectangle( ) );
      tiles.get( i ).setFillColor( Specs.O_COLOR );
      tiles.get( i ).setFrameColor( Color.BLACK );
      tiles.get( i ).setSize( Specs.TILE_SIZE, Specs.TILE_SIZE );
    }
    
    config = new Vector< Configuration >( );
    config.add( new Configuration( 0, 0, 1, 0, 0, 1, 1, 1 ) );
    config.add( new Configuration( 0, 0, 1, 0, 0, 1, 1, 1 ) );
    config.add( new Configuration( 0, 0, 1, 0, 0, 1, 1, 1 ) );
    config.add( new Configuration( 0, 0, 1, 0, 0, 1, 1, 1 ) );
    
    currentConfigIndex = 0;
    currentConfig = config.get( currentConfigIndex );
  }
  
  
  /*===================================================================*/
  //  call drawTile for each tile
  public void draw(  )
  {
    for( int i = 0; i < 4; i++ )
    {
      drawTile( i );
    }
  }
  
  /*===================================================================*/
  // Draw the i'th tile
  public void drawTile( int i )
  {
    Point ccPoint = currentConfig.get( i );
    Point pbCoords = new Point( ccPoint.x + col, ccPoint.y + row );
    Point grCoords = new Point( Specs.BOARD_X + pbCoords.x * Specs.TILE_SIZE,
                               Specs.BOARD_Y + pbCoords.y * Specs.TILE_SIZE );
    
    tiles.get( i ).setLocation( grCoords );
  }
  
  /*===================================================================*/
  /**
   *  set the board row and column
   */ 
  public void setRC( int r, int c )
  {
    row = r;
    col = c;
  }
  
  /*===================================================================*/
  /**
   * Fall: make  the shape fall one row
   */ 
  public void fall( )
  {
    row += 1;
    draw( );
  }
  
  //********************************************************************
  //Moves the tetronimo right
  //********************************************************************
  public void moveRight( )
  {
    col += 1;
    draw( );
  }
  
  //********************************************************************
  // Moves the tetronimo left
  //********************************************************************
  public void moveLeft( )
  {
    col -= 1;
    draw( );
  }
  
  
  /*===================================================================*/
  /**
   *  get the current configuration
   */ 
  public Configuration getConfiguration( )
  {
    return currentConfig;
  }
  
  /*===================================================================*/
  /**
   *  get the current board row
   */ 
  public int getRow( )
  {
    return row;
  }
  
  /*===================================================================*/
  /**
   *  get the max column for this shape
   */ 
  public int getMaxColumn( )
  {
    int max = -1;
    
    for( int i = 0; i < 4; i++ )
    {
      if( currentConfig.get( i ).x > max)
      {
        max = currentConfig.get( i ).x;
      }
    }
    return max;
  }
  
  /*===================================================================*/
  /**
   *   get the current board column 
   */ 
  public int getCol( )
  {
    return col;
  }
  
  
  /*===================================================================*/
  /**
   *   get the maximum row index for this shape
   */ 
  public int getMaxRow( )
  {
    int max = -1;
    for( int i = 0; i < 4; i++ )
    {
      if( currentConfig.get( i ).y > max )
      {
        max = currentConfig.get( i ).y;
      }
    }
    return max;
  }
  
  /*===================================================================*/
  /**
   *   get the minimum row index for this shape
   */ 
  public int getMinColumn( )
  {
    int min = 5;
    for( int i = 0; i < 4; i++ )
    {
      if( currentConfig.get( i ).x < min )
      {
        min = currentConfig.get( i ).x;
      }
    }
    return min;
  }
  
  /*===================================================================*/
  /**
   *  Rotate the shape
   */ 
  public void rotate( )
  {
    if( currentConfigIndex < 3 )
    {
      currentConfigIndex += 1;
      currentConfig = config.get( currentConfigIndex );
      draw( );
    }
    else
    {
      currentConfigIndex = 0;
      currentConfig = config.get( currentConfigIndex );
      draw( );
    }
  }
  
  //********************************************************************
  //derotates the tetronimo
  //********************************************************************
  public void deRotate( )
  {
    if( currentConfigIndex > 0 )
    {
      currentConfigIndex -= 1;
      currentConfig = config.get( currentConfigIndex );
      draw( );
    }
    else
    {
      currentConfigIndex = 3;
      currentConfig = config.get( currentConfigIndex );
      draw( );
    }
  }
  
  /*===================================================================/
   * canRotate( )
   * Returns true if the tetornimo can rotate, false if it can not. 
   */
  public boolean canRotate( )
  {
    int maxC, maxR;
    Configuration nextConfig;
    
    if( currentConfigIndex + 1 <= 3 )
    {
      nextConfig = config.get( currentConfigIndex + 1 );
    }
    else
    {
      nextConfig = config.get( 0 );
    }
    
    maxC = -1;
    for( int i = 0; i < 4; i++ )
    {
      if( nextConfig.get( i ).x + col > maxC )
      {
        maxC = nextConfig.get( i ).x + col;
      }
      if( maxC > Specs.BOARD_COLS - 1 )
        return false;
    }
    
    maxR = -1;
    for( int i = 0; i < 4; i++ )
    {
      if( nextConfig.get( i ).y + row > maxR )
      {
        maxR = nextConfig.get( i ).y + row;
      }
      if( maxR > Specs.BOARD_ROWS - 1)
        return false;
    }
    return true;
  }
  
  public Vector< Point > nextConigTiles( )
  {
    int ghostIndex = currentConfigIndex;
    Configuration nextConfig;
    Vector< Point > nct = new Vector< Point >();
    
    if( currentConfigIndex < 3 )
    {
      ghostIndex = currentConfigIndex += 1;
      nextConfig = config.get( ghostIndex );
    }
    else
    {
      ghostIndex = 0;
      nextConfig = config.get( ghostIndex );
    }
    
    for( int i = 0; i < 3; i++ )
      nct.add( nextConfig.get( i ));
    
    return nct;
  }
  /******************************************************************/
  /**
   *   Get the tiles from this shape
   */ 
  public Vector< Rectangle > getTiles( )
  {
    return tiles;
  }
  
  //---------------------- testing  main ------------------------------
  public static void main( String[] args )
  {     
    new Frame( );
    //new TetrisLab1( );
    
  }
}