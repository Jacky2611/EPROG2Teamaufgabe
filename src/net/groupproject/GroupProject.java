package net.groupproject;

import net.groupproject.quadtree.QuadTree;
import net.groupproject.quadtree.QuadTreeNode;
import net.groupproject.util.Vector2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Ansraer on 21.04.2018
 * @author Jakob Lang
 * @project TeamaufgabeProjekt
 */
public class GroupProject {

    /**
     * Starting point for the entire project.
     */
    public static void main(String[] args) {
        QuadTree<Place> qt = new QuadTree<Place>(new Vector2d(0,0),1000000);
        System.out.println("Quadtree:");
        qt=addDataToQT(qt);
        System.out.println("in theory every place should now be in the qt.....");

        //Alle Trainstations und Airports in Radius
        qt.placesNearPoint(new Vector2d(1818.54657,5813.29982),100);


        System.out.println("List:");
        Listsolution list=new Listsolution();
        list.addData();
        list.placesNearPlace(new Vector2d(1818.54657,5813.29982),100);
        list.airportNearTrain(15.0,20);


        //System.out.println("now looking for all trainstations");
        //System.out.println("Found: "+qt.airportByTrainstations(15.0,20));


       // System.out.println("finding place with name 111th Street (Pullman)");

        //System.out.println("found: "+qt.find(new Vector2d(-9752.758450864792,4919.756854900251)).getContent().getName());
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



}
