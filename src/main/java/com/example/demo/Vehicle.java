package com.example.demo;
class Car extends Vehicle{
    String Type="";
    String Engine="Unknown";//Eg sedan ,suv
    Car(String s,int maxx,String type){
        super(s,maxx);
        Type=type;
    }
}

class PetrolCar extends Car{
    PetrolCar(String s,int maxx,String type){
        super(s,maxx,type);
        Engine="Petrol";
    }
}
class EvCar extends Car{
    EvCar(String s,int maxx,String type){
        super(s,maxx,type);
        Engine="EV";
    }
}
class DieselCar extends Car{
    DieselCar(String s,int maxx,String type){
        super(s,maxx,type);
        Engine="Diesel";
    }
}
class Truck extends Vehicle{
    String Engine="Diesel";
    Truck(String s,int maxx,String Eng){
        super(s,maxx);
        Engine=Eng;
    }
}
class Cycle extends Vehicle{
    Cycle(String s,int maxx){
        super(s,maxx);
        Tyre=2;
    }
}
public class Vehicle {
    String Vehiclenumber="";
    int MaxSpeed=0;
    int Tyre=4;
    Vehicle(String s,int maxx){
        Vehiclenumber=s;
        MaxSpeed=maxx;
    }
}
