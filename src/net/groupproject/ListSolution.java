package net.groupproject;

import net.groupproject.util.Place;
import net.groupproject.util.Vector2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class ListSolution {

    List<Place> list;


    ListSolution() {
        this.list = new ArrayList<Place>();

    }


    // DataReader for the csv file
    public void addData() {

        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {

            while (s.hasNextLine()) {


                Scanner sc = new Scanner(s.nextLine()).useDelimiter(";");
                sc.useLocale(Locale.ENGLISH);
                Place temp = new Place();
                temp.setName(sc.next());
                temp.setxCoor(sc.nextDouble());
                temp.setyCoor(sc.nextDouble());
                temp.setType(sc.next());

                list.add(temp);


            }


        } catch (FileNotFoundException e)

        {

            System.exit(1);
        }
    }

    public void print() {
        for (Place p : list) {
            p.toString();
        }
    }


    //calls placeInRadius, prints out the values
    public void placesNearPlace(Vector2d p, double radius) {
        long time = System.currentTimeMillis();

        int[] x = placeInRadius(p, radius);
        time = System.currentTimeMillis() - time;
        System.out.println("There are " + x[0] + " Trainstations and " + x[1] + " Airports");
        System.out.println("This took " + time + "ms to calculate");

    }

    //returns an Array with the number of Trainsstations on Index 0 and number of Airports on Index 1
    public int[] placeInRadius(Vector2d p, double radius) {

        int countA = 0;
        int countT = 0;
        for (Place content : list) {

            double x = p.getX() - content.getxCoor();
            double y = p.getY() - content.getyCoor();

            double diff = (Math.sqrt((x * x) + (y * y)));
            if (diff <= radius) {
                if (content.getType().equals("TRAINSTATION")) {
                    countT++;
                } else {
                    countA++;
                }

            }

        }
        int[] x = new int[2];
        x[0] = countT;
        x[1] = countA;
        return x;
    }

    public void airportNearTrain(double radius, int number) {
        long time = System.currentTimeMillis();
        int count = 0;
        int[] arr;
        for (Place content : list) {
            if (content.getType().equals("AIRPORT")) {
                arr = placeInRadius(content.getPos(), radius);
                if (arr[0] >= number) {
                    count++;
                }

            }

        }
        time=System.currentTimeMillis()-time;
        System.out.println("There are " + count + " Airports, that have atleast " + number + " Trainstations with a max distance of " + radius + ".");
        System.out.println("This took " + time + "ms to calculate");

    }


}
