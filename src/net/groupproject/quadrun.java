package net.groupproject;



public class quadrun{
    public static void main(String[] args) {


        Point tl = new Point(-100000.0, 100000.0);
        Point br = new Point(100000.0, -100000.0);
        Quadtree Quadtree = new Quadtree(tl, br);

        Quadtree.addData();

        Quadtree.print();
    }

}


