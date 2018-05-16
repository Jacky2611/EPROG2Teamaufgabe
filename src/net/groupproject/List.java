package net.groupproject;

import net.groupproject.quadtree.QuadTree;
import net.groupproject.quadtree.QuadTreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
//TO
@Deprecated
public class List {
    private Place place;
    private List next;


    public List() {
        this.place = null;
        this.next = null;

    }

    public List(Place data) {
        this.place = data;
    }

    public List(List next) {
        this.next = next;
    }

    public List getNext() {
        return next;
    }

    public void addData() {
        List list = this;
        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {

            while (s.hasNextLine()) {


                Scanner sc = new Scanner(s.nextLine()).useDelimiter(";");
                sc.useLocale(Locale.ENGLISH);


                String name = sc.next();
                //System.out.println("now doing " + name);
                list.place.setName(name);

                double x = sc.nextDouble();
                //System.out.println("x coord: " + x);
                list.place.setxCoor(x);


                list.place.setyCoor(sc.nextDouble());
                list.place.setType(sc.next());


                list.next = new List(new Place());
                list = list.getNext();


            }


        } catch (FileNotFoundException e)

        {
// junctions.csv wurde nicht gefunden
            System.exit(1);
        }
    }

    public void print() {
        List list = this;
        while (list.getNext() != null) {
            if (list.place != null) {
                System.out.println(list.place.getName() + " " + list.place.getxCoor() + " " + list.place.getyCoor() + " " + list.place.getType());

            }
            list = list.getNext();
        }
    }

    public void addToQuadTree(QuadTree<Place> qt) {
        List list = this;
        while (list.getNext() != null) {
            if (list.place != null) {
                qt.insert(new QuadTreeNode<Place>(list.place.getPos(), list.place));

            }
            list = list.getNext();
        }
    }
}
