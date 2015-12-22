import java.util.ArrayList;

class ObserverPattern{
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

interface Observer {
  void update(float temp, float humidity, float pressure);
}

interface DisplayElement {
  void display();
}

class WeatherData implements Subject {
  private ArrayList observers;
  private float temperature;
  private float humidity;
  private float pressure;

  // create a new ArrayList of observers when the WeatherData is instantiated
  public WeatherData(){
    observers = new ArrayList();
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

  public void notifyObservers(){
    for (int i = 0; i < observers.size(); i++) {
      Observer observer = (Observer) observers.get(i);
      observer.update(temperature, humidity, pressure);
    }
  }

  public void measuremeantsChanged(){
    notifyObservers();
  }

  public void setMeasurements(float temperature, float humidity, float pressure){
    this.temperature = temperature;
    this.humidity = humidity;
    this.pressure = pressure;
    measuremeantsChanged();
  }
}


class CurrentConditionDisplay implements Observer, DisplayElement {
  private float temperature;
  private float humidity;
  private Subject weatherData;

  // constructor to set the weatherData when instantiated and register display as an observer
  public CurrentConditionDisplay(Subject weatherData){
    this.weatherData = weatherData;
    weatherData.registerObserver(this);
  }

  public void update(float temperature, float humidity, float pressure) {
    this.temperature = temperature;
    this.humidity = humidity;
    display();
  }

  public void display() {
    System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + " % humidity");
  }
}
