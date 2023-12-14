package edu.bu.cs611.portfoliostocksystem.controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Sophie jwz16@bu.edu
 */
public class ExampleController extends AbstractController {
  
  public Response exampleAction(String param1, String param2) {
    
    var resp = new Response();
    resp.success = param1.equals(param2);
    
    if (resp.success) {
      resp.data.put("message", "Congrats, your action succeeded!");
    } else {
      resp.data.put("message", "Oops, your action failed!");
    }
    
    // Example model property change
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        setModelProperty(
          registeredModels.get(0).hashCode(),
          "text",
          "Hello, after 3 seconds, the example text is changed!"
        );
      }
    }, 3 * 1000);
    
    return resp;
  }
  
}