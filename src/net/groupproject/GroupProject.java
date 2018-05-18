package net.groupproject;

import net.groupproject.quadtree.QuadTree;
import net.groupproject.quadtree.QuadTreeNode;
import net.groupproject.util.Place;
import net.groupproject.util.Vector2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Ansraer on 21.04.2018
 * @author Jakob Lang
 * @project TeamaufgabeProjekt
 */
public class GroupProject {

    //TODO: Use proper comments for documentation
    //TODO: Further cleanup of code

    /**
     * Starting point for the entire project.
     */
    public static void main(String[] args) {

        //QT VERSION

        System.out.println("Quadtree:");
        QuadTree<Place> qt = new QuadTree<Place>(new Vector2d(0,0),1000000);
        long time = System.currentTimeMillis();
        qt=addDataToQT(qt);
        time=System.currentTimeMillis()-time;
        System.out.println("Added all the data to the QuadTreen in "+time+"ms.");
        //actually do something with the data:
        placesNearPointQT(qt,new Vector2d(1818.54657,5813.29982),100);
        airportsNearTrainstationsQT(qt, 15.0,20);

        //LIST VERSION

        System.out.println("");//empty line
        System.out.println("List:");
        ListSolution list=new ListSolution();
        time=System.currentTimeMillis();
        list.addData();
        time=System.currentTimeMillis()-time;
        System.out.println("Added all the data to the List in "+time+"ms.");
        //actually do something with the data:
        list.placesNearPlace(new Vector2d(1818.54657,5813.29982),100);
        list.airportNearTrain(15.0,20);


    }

    public static QuadTree<Place> addDataToQT(QuadTree<Place> quadTree) {
        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {

            while (s.hasNextLine()) {


                Scanner sc = new Scanner(s.nextLine()).useDelimiter(";");
                sc.useLocale(Locale.ENGLISH);

                Place p = new Place();

                String name = sc.next();
                //System.out.println("now doing " + name);
                p.setName(name);

                double x = sc.nextDouble();
                //System.out.println("x coord: " + x);
                p.setxCoor(x);


                p.setyCoor(sc.nextDouble());
                p.setType(sc.next());

                quadTree.insert(new QuadTreeNode<Place>(p.getPos(),p));
            }
        } catch (FileNotFoundException e){
            // junctions.csv wurde nicht gefunden
            System.exit(1);
        }
        return quadTree;
    }


    //prints out the number of Trainstations and Airports
    public static void placesNearPointQT(QuadTree<Place> qt, Vector2d pos, double r) {
        long time = System.currentTimeMillis();


        if( pos.getX() > qt.getPos().getX()+qt.getSize()/2 ||
                pos.getX() < qt.getPos().getX()-qt.getSize()/2 ||
                pos.getY() > qt.getPos().getY()+qt.getSize()/2 ||
                pos.getY() < qt.getPos().getY()-qt.getSize()/2){
            System.out.println("Position is not inside the Quadtree");
            return ;
        }
        else if(r<=0){
            System.out.println("The radius must be greater than 0");
            return;
        }

        int airports = 0;
        int stations=0;

        List<QuadTreeNode<Place>> contents = qt.getContentInRadius(pos, r);
        for(QuadTreeNode<Place> n : contents){
            if(((Place)n.getContent()).getType().equals("AIRPORT"))
                airports++;
            else
                stations++;

        }
        System.out.println("There are " + stations + " Trainstations and " + airports + " Airports");
        time = System.currentTimeMillis() - time;

        System.out.println("This took " + time +"ms to calculate");

    }

    public static int airportsNearTrainstationsQT(QuadTree<Place> quadTree, double radius, double number){
        long time = System.currentTimeMillis();

        if(radius<0){
            System.out.println("The number of Trainstations must be positive");
            return -1;
        }
        else if(radius<=0){
            System.out.println("The radius musst be greater than 0");
            return -1;
        }

        List<Place> airports = new ArrayList<Place>();

        //all buildings, necessary to get all airports
        List<QuadTreeNode<Place>> l= quadTree.getContentInRadius( quadTree.getPos(), quadTree.getSize()/2);

        for(QuadTreeNode<Place> node :l){
            if(node.getContent().getType().equals("AIRPORT"))
                airports.add(node.getContent());

        }

        //now for every building check if it has enough trainstations
        int numOfAirportsWithRequiredNumOfTrainstations=0;
        for(Place airport : airports){
            List<QuadTreeNode<Place>> places= quadTree.getContentInRadius( airport.getPos(), radius);

            int trainstations=0;

            for(QuadTreeNode<Place> p : places){
                if(p.getContent().getType().equals("TRAINSTATION"))
                    trainstations++;
            }

            if(trainstations>= number)
                numOfAirportsWithRequiredNumOfTrainstations++;
        }



        System.out.println("There are " + numOfAirportsWithRequiredNumOfTrainstations + " Airports with more than " + number + " Trainstations in a radius of "+radius);
        time = System.currentTimeMillis() - time;

        System.out.println("This took " + time +"ms to calculate");

        return numOfAirportsWithRequiredNumOfTrainstations;
    }



}
