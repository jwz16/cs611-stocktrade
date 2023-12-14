package edu.bu.cs611.portfoliostocksystem.view;

import java.beans.PropertyChangeEvent;

/**
 *
 * @author Sophie jwz16@bu.edu
 */
public interface AbstractView {
  
  public void modelPropertyChange(final PropertyChangeEvent evt);
  
}