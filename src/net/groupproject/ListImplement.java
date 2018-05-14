package net.groupproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;


public class ListImplement {
    public static void main(String[] args) {
        List places = new List();
        places.addData();
        //places.print();

        places.placesNearPlace(new Place("test",1.0,1.0,"f"),1000.0);
        places.airportNearTrain(100,2);
    }
}




class List {

    private Place place;
    private List next;


    List() {
        this.place = null;
        this.next = null;

    }

    List(Place data) {
        this.place = data;
    }

    public List(List next) {
        this.next = next;
    }


    List getNext() {
        return next;
    }
// DataReader for the csv file
    void addData() {
        List list = this;
        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {

            while (s.hasNextLine()) {


                Scanner sc = new Scanner(s.nextLine()).useDelimiter(";");
                sc.useLocale(Locale.ENGLISH);
                list.next = new List(new Place());
                list.getNext().place.setName(sc.next());
                list.getNext().place.setxCoor(sc.nextDouble());
                list.getNext().place.setyCoor(sc.nextDouble());
                list.getNext().place.setType(sc.next());

                list = list.getNext();


            }


        } catch (FileNotFoundException e)

        {

            System.exit(1);
        }
    }

    void print() {
        List list = this;
        while (list.getNext() != null) {
            if (list.place != null) {
                System.out.println(list.place.getName() + " " + list.place.getxCoor() + " " + list.place.getyCoor() + " " + list.place.getType());

            }
            list = list.getNext();
        }
    }





    //einmal mit place, einmal mit einfachen koordinaten
    public void placesNearPlace(Place p, double radius) {

        int[] x=placeInRadius(p.getxCoor(),p.getyCoor(),radius);
        System.out.println("There are "+x[0]+" Trainstations and "+x[1]+" Airports");

    }
    public void placesNearCoordinates(double xCoor,double yCoor, double radius) {

        int[] x=placeInRadius(xCoor,yCoor,radius);
        System.out.println("There are "+x[0]+" Trainstations and "+x[1]+" Airports near the Coordinates ("+xCoor+", "+yCoor+")");

    }
    //returns an Array with the number of Trainsstations on Index 0 and number of Airports on Index 1
    private int[] placeInRadius(double xCoor, double yCoor, double radius) {

        List list = this.getNext();
        int countA = 0;
        int countT = 0;
        while (list != null) {
            //place mit pythagoras abstand berechnen /(auf polar umrechnen)
            double x = xCoor - list.place.getxCoor();
            double y = yCoor - list.place.getyCoor();

            double diff = (Math.sqrt((x * x) + (y * y)));
            if (diff <= radius) {
                if (list.place.getType().equals("TRAINSTATION")) {
                    countT++;
                }else {countA++;}

            }
            list=list.getNext();
        }
        int[] x=new int[2];
        x[0]=countT;
        x[1]=countA;
        return x;
    }

    public void airportNearTrain(double radius, int number) {
        List temp = this.getNext();
        int count = 0;
        int[] arr;
        while (temp != null) {
            if(temp.place.getType().equals("AIRPORT")) {
                arr = placeInRadius(temp.place.getxCoor(), temp.place.getyCoor(), radius);
                if(arr[0]>=number){
                    count++;
                }

            }
        temp=temp.getNext();
        }

        System.out.println("There are "+count+" Airports, that have atleast "+number+" Trainstations with a max distance of "+radius+".");
    }

}
