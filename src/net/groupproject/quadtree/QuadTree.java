package net.groupproject.quadtree;

import net.groupproject.util.Vector2d;

/**
 * @author Ansraer on 16.05.2018
 * @author Jakob Lang
 * @project TeamaufgabeProjekt
 */
public class QuadTree<T> {

    private QuadTreeQuad<T> root;

    public QuadTree(Vector2d center, double size){
        this.root = new QuadTreeQuad<T>(center, size);
    }

    public boolean insert(QuadTreeNode<T> node){

        //only insert an object if it's inside the quadtrees bounds
        if( node.getPos().getX() > this.root.getPos().getX()+this.root.getSize()/2 ||
            node.getPos().getX() < this.root.getPos().getX()-this.root.getSize()/2 ||
            node.getPos().getY() > this.root.getPos().getY()+this.root.getSize()/2 ||
            node.getPos().getY() < this.root.getPos().getY()-this.root.getSize()/2){
            return false;
        }

        return this.root.insert(node);
    }

    public QuadTreeNode<T> find(Vector2d pos){
        //only find an object if it's inside the quadtrees bounds
        if( pos.getX() > this.root.getPos().getX()+this.root.getSize()/2 ||
            pos.getX() < this.root.getPos().getX()-this.root.getSize()/2 ||
            pos.getY() > this.root.getPos().getY()+this.root.getSize()/2 ||
            pos.getY() < this.root.getPos().getY()-this.root.getSize()/2){
            return null;
        }

        return this.root.find(pos);
    }
    public void placesNearPoint(Vector2d pos, double r){
        if( pos.getX() > this.root.getPos().getX()+this.root.getSize()/2 ||
                pos.getX() < this.root.getPos().getX()-this.root.getSize()/2 ||
                pos.getY() > this.root.getPos().getY()+this.root.getSize()/2 ||
                pos.getY() < this.root.getPos().getY()-this.root.getSize()/2){
            System.out.println("Position is not inside the Quadtree");
            return ;
        }
        else if(r<=0){
            System.out.println("The radius must be greater than 0");
            return;
        }

        this.root.placesNearPoint(pos,r);
    }
    public void trainstationsByAirport(double radius, int min){
        if(min<0){
            System.out.println("The number of Trainstations must be positive");
            return;
        }
        else if(radius<=0){
            System.out.println("The radius musst be greater than 0");
            return;
        }
        this.root.trainstationsByAirport(radius,min);
    }
}
