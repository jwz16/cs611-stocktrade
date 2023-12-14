package edu.bu.cs611.portfoliostocksystem.model;

/**
 *
 * @author Sophie jwz16@bu.edu
 */
public class ExampleModel extends AbstractModel {
  
  private String text = "";
  
  @Override
  public Integer getId() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void setText(String text) {
    String oldValue = this.text;
    
    this.text = text;
    
    firePropertyChange("text", oldValue, text);
  }
  
  public String getText() {
    return text;
  }
  
}
