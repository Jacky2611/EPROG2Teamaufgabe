package net.groupproject;

import net.groupproject.quadtree.QuadTree;
import net.groupproject.util.Vector2d;

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
        //TODO: Replace List with something else and use a default ArrayList/directly put the data into the qt
        List places = new List(new Place());
        places.addData();
        places.print();

        QuadTree<Place> qt = new QuadTree<Place>(new Vector2d(0,0),1000000);

        places.addToQuadTree(qt);
        System.out.println("in theory every place should now be in the qt.....");


        System.out.println("finding place with name 111th Street (Pullman)");

        System.out.println("found: "+qt.find(new Vector2d(-9752.758450864792,4919.756854900251)).getContent().getName());
    }



}
