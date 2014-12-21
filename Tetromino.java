/**
 * Kyle Vickers
 * Tetromino Interface
 * November 22 2010
 * Prog 12
 * 
 * Interface for the tetrominoes
 */

import java.awt.Color;
import java.util.*;
import wheelsunh.users.*;
import java.awt.Point;

//Interface for Tetronimos. 
public interface Tetromino
{
  public int getMaxRow( );
  public int getRow( );
  public int getCol( );
  public void fall( );
  public void moveLeft( );
  public void moveRight( );
  public void rotate( );
  public void setRC( int x, int y );
  public void draw( );
  public int getMaxColumn( );
  public int getMinColumn( );
  public boolean canRotate( );
  public Vector< Rectangle > getTiles( );
  public Vector< Point > nextConigTiles();
  public void deRotate();
}