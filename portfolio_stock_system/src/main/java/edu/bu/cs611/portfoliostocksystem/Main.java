/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.bu.cs611.portfoliostocksystem;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import edu.bu.cs611.portfoliostocksystem.controller.ExampleController;
import edu.bu.cs611.portfoliostocksystem.model.ExampleModel;
import edu.bu.cs611.portfoliostocksystem.view.ExampleView;

/**
 *
 * @author Sophie jwz16@bu.edu
 */
public class Main {
  /**
  * @param args the command line arguments
  */
  public static void main(String args[]) {
    FlatMacLightLaf.setup();

    var exampleController = new ExampleController();
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(() -> {
      var exampleView = new ExampleView(exampleController);
      var exampleModel = new ExampleModel();
      exampleController.addView(exampleView);
      exampleController.addModel(exampleModel);

      exampleView.setVisible(true);
    });
  }
}
