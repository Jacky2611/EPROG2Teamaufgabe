package net.groupproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Quadtree {
    private Point topLeftBoundary;
    private Point bottomRightBoundary;
    private Place data;

    private Quadtree topLeftTree;
    private Quadtree topRightTree;
    private Quadtree bottomLeftTree;
    private Quadtree bottomRightTree;

    //Constructor--------------------------------------------------------------------------------------------------------
    public Quadtree() {
        topLeftBoundary = new Point(0.0, 0.0);
        bottomRightBoundary = new Point(0.0, 0.0);
        data = null;
        topLeftTree = null;
        topRightTree = null;
        bottomLeftTree = null;
        bottomRightTree = null;
    }

    public Quadtree(Point topLeftBoundary, Point bottomRightBoundary) {
        this.topLeftBoundary = topLeftBoundary;
        this.bottomRightBoundary = bottomRightBoundary;
        data = null;
        topLeftTree = null;
        topRightTree = null;
        bottomLeftTree = null;
        bottomRightTree = null;
    }

    //Methods----------------------------------------------------------------------------------------------------------

    //Adds Data from .csv to quadtree
    public void addData() {
        Quadtree tree = this;

        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {
            while (s.hasNextLine()) {
                Scanner sc = new Scanner(s.nextLine()).useDelimiter(";");
                sc.useLocale(Locale.ENGLISH);
                Place p = new Place();
                p.setName(sc.next());
                p.setxCoor(sc.nextDouble());
                p.setyCoor(sc.nextDouble());
                p.setType(sc.next());
                tree.add(p);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }
    }


    //checks whether the coordinates of the point are inside the quadtree;
    public boolean inside(Point p) {
        return (p.getX() >= this.topLeftBoundary.getX() &&
                p.getX() <= this.bottomRightBoundary.getX() &&
                p.getY() <= this.topLeftBoundary.getY() &&
                p.getY() >= this.bottomRightBoundary.getY());
    }


    //adds a place to the quadtree
    public void add(Place place) {
        if (place == null) {
            return;
        }
        if (!inside(place.getPoint())) {
            return;
        }
        if (data == null) {
            data = place;
            return;
        }
        if ((topLeftBoundary.getX() + bottomRightBoundary.getX()) / 2 >= place.getxCoor()) {
            //top left tree
            if ((topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2 <= place.getyCoor()) {
                if (topLeftTree == null) {
                    topLeftTree = new Quadtree(new Point(topLeftBoundary.getX(), topLeftBoundary.getY()),
                            new Point((topLeftBoundary.getX() + bottomRightBoundary.getX()) / 2, (topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2));
                } else {
                    topLeftTree.add(place);
                }
            }
            //bot left tree
            else {
                if (bottomLeftTree == null) {
                    bottomLeftTree = new Quadtree(new Point(topLeftBoundary.getX(), (topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2),
                            new Point((topLeftBoundary.getX() + bottomRightBoundary.getX()) / 2, bottomRightBoundary.getY()));
                } else {
                    bottomLeftTree.add(place);
                }
            }
        } else {
            //top right Tree
            if ((topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2 <= place.getyCoor()) {
                if (topRightTree == null) {
                    topRightTree = new Quadtree(new Point((topLeftBoundary.getX() + bottomRightBoundary.getX()) / 2, topLeftBoundary.getY()),
                            new Point(bottomRightBoundary.getX(), (topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2));
                } else {
                    topRightTree.add(place);
                }
            }
            //bot right tree
            else {
                if (bottomRightTree == null) {
                    bottomRightTree = new Quadtree(new Point((topLeftBoundary.getX() + bottomRightBoundary.getX()) / 2, (topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2),
                            new Point(bottomRightBoundary.getX(), bottomRightBoundary.getY()));
                } else {
                    bottomRightTree.add(place);
                }

            }
        }
    }


    //searches the Quadtree for an place with the coordinates of the point p
    public Place search(Point p) {
        if (!inside(p)) {
            return null;
        }
        if (data.getxCoor() == p.getX() && data.getyCoor() == p.getY()) {
            return data;
        }
        if ((topLeftBoundary.getX() + bottomRightBoundary.getX()) / 2 >= p.getX()) {
            //top left tree
            if ((topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2 <= p.getY()) {
                if (topLeftTree == null) {
                    return null;
                }
                return topLeftTree.search(p);
            }
            //bot left tree
            else {
                if (bottomLeftTree == null) {
                    return null;
                }
                return bottomLeftTree.search(p);
            }
        } else {
            //top right Tree
            if ((topLeftBoundary.getY() + bottomRightBoundary.getY()) / 2 <= p.getY()) {
                if (topRightTree == null) {
                    return null;
                }
                return topRightTree.search(p);
            }
            //bot right tree
            else {
                if (bottomRightTree == null) {
                    return null;
                }
                return bottomRightTree.search(p);


            }
        }

    }

    //prints all Places stored in the Quadtree
    public void print() {

        System.out.println(data.toString());
        if (topLeftTree!= null && topLeftTree.data!=null) {
            topLeftTree.print();
        }
        if (topRightTree!= null && topRightTree.data!=null) {
            topRightTree.print();
        }
        if (bottomLeftTree != null && bottomLeftTree.data!=null) {
            bottomLeftTree.print();
        }
        if (bottomRightTree != null && bottomRightTree.data!=null) {
            bottomRightTree.print();
        }

    }
}
