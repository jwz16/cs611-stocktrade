package edu.bu.cs611.portfoliostocksystem.controller;

import edu.bu.cs611.portfoliostocksystem.model.AbstractModel;
import edu.bu.cs611.portfoliostocksystem.view.AbstractView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Sophie jwz16@bu.edu
 */
public class AbstractController implements PropertyChangeListener {

  /**
   * Response class is used to create response fro views.
   */
  public class Response {
    public boolean success = false;
    public Map<String, Object> data = new HashMap<>();
  }
  
  private static final Logger logger = LogManager.getLogger();

  protected final List<AbstractView> registeredViews;
  protected final List<AbstractModel> registeredModels;

  public AbstractController() {
    registeredViews = new ArrayList<>();
    registeredModels = new ArrayList<>();
  }


  public void addModel(AbstractModel model) {
    registeredModels.add(model);
    model.addPropertyChangeListener(this);
  }

  public void removeModel(AbstractModel model) {
    registeredModels.remove(model);
    model.removePropertyChangeListener(this);
  }

  public void addView(AbstractView view) {
    registeredViews.add(view);
  }

  public void removeView(AbstractView view) {
    registeredViews.remove(view);
  }

  //  Use this to observe property changes from registered models
  //  and propagate them on to all the views.

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    for (var view: registeredViews) {
      view.modelPropertyChange(evt);
    }
  }


  /**
   * This is a convenience method that subclasses can call upon
   * to fire property changes back to the models. This method
   * uses reflection to inspect each of the model classes
   * to determine whether it is the owner of the property
   * in question. If it isn't, a NoSuchMethodException is thrown,
   * which the method ignores.
   *
   * @param propertyName = The name of the property.
   * @param newValue = An object that represents the new value
   * of the property.
   */
  protected void setModelProperty(int modelHashCode, String propertyName, Object newValue) {
    for (AbstractModel model: registeredModels) {
      if (modelHashCode != model.hashCode())
        continue;
      try {
        Method method = model.getClass().getMethod(
          String.format("set%s", StringUtils.capitalize(propertyName)),
          new Class[] { newValue.getClass() }
        );
        method.invoke(model, newValue);
      } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
        // DO NOTHING, this should not happen!
      }
    }
  }

  protected Response fail(String errMsg) {
    var resp = new Response();
    resp.success = false;
    resp.data.put("error", errMsg);
    
    logger.warn(errMsg);

    return resp;
  }
  
}