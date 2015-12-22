// import Observer & Observable
import java.util.Observable;
import java.util.Observer;

import java.util.ArrayList;


class BuiltInObserverPattern{
  public static void main(String[] args){
    System.out.println("lets start the program");

    WeatherData weatherData = new WeatherData();

    CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay(weatherData);
    weatherData.setMeasurements(80, 65, 40.4f);
    weatherData.setMeasurements(82, 70, 29.2f);
    weatherData.setMeasurements(78, 90, 29.2f);
  }
}


interface Subject {
  void registerObserver(Observer o);
  void removeObserver(Observer o);
  void notifyObservers();
}

// interface Observer {
//   void update(float temp, float humidity, float pressure);
// }

interface DisplayElement {
  void display();
}

class WeatherData extends Observable {
  private ArrayList observers;
  private float temperature;
  private float humidity;
  private float pressure;

  // create a new ArrayList of observers when the WeatherData is instantiated
  public WeatherData(){
    // observers = new ArrayList();
  }

  // Implement Subject interface

  // When an observer is added, add it to th ened of the ArrayList
  public void registerObserver(Observer o){
    observers.add(o);
  }

  // When an observer is removed, take it off ArrayList
  public void removeObserver(Observer o){
    int i = observers.indexOf(o);
    if (i >= 0) {
      observers.remove(i);
    }
  }

  // public void notifyObservers(){
  //   for (int i = 0; i < observers.size(); i++) {
  //     Observer observer = (Observer) observers.get(i);
  //     observer.update(temperature, humidity, pressure);
  //   }
  // }

  public void measuremeantsChanged(){
    setChanged();
    notifyObservers();
  }

  public void setMeasurements(float temperature, float humidity, float pressure){
    this.temperature = temperature;
    this.humidity = humidity;
    this.pressure = pressure;
    measuremeantsChanged();
  }

  public float getTemperature(){
    return this.temperature;
  }

  public float getHumidity(){
    return this.humidity;
  }

  public float getPressure(){
    return this.pressure;
  }
}


class CurrentConditionDisplay implements Observer, DisplayElement {
  Observable observable;
  private float temperature;
  private float humidity;
  private Subject weatherData;

  // constructor to set the weatherData when instantiated and register display as an observer
  public CurrentConditionDisplay(Observable observable){
    this.observable = observable;
    observable.addObserver(this);
  }

  // public void update(float temperature, float humidity, float pressure) {
  //   this.temperature = temperature;
  //   this.humidity = humidity;
  //   display();
  // }

  public void update(Observable obs, Object arg) {
    if (obs instanceof WeatherData) {
      WeatherData weatherData = (WeatherData)obs;
      this.temperature = weatherData.getTemperature();
      this.humidity = weatherData.getHumidity();
      display();
    }
  }

  public void display() {
    System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + " % humidity");
  }
}
