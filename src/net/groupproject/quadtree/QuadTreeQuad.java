package net.groupproject.quadtree;

import net.groupproject.Place;
import net.groupproject.util.Vector2d;

/**
 * @author Ansraer on 16.05.2018
 * @author Jakob Lang
 * @project TeamaufgabeProjekt
 */
public class QuadTreeQuad<T> {

    private Vector2d pos;
    private double size;

    private QuadTreeNode<T> content;

    private QuadTreeQuad leftUp;
    private QuadTreeQuad leftDown;
    private QuadTreeQuad rightUp;
    private QuadTreeQuad rightDown;


    public QuadTreeQuad(Vector2d center, double size) {
        this.pos=center;
        this.size=size;
        content=null;
        leftUp=null;
        leftDown=null;
        rightUp=null;
        rightDown=null;
    }

    public QuadTreeQuad(double centerX, double centerY, double size){
        this(new Vector2d(centerX,centerY), size);
    }

    public boolean insert(QuadTreeNode<T> node) {
        //if this is en empty quad just put the node inside, no need to divide it
        if(this.content==null && (leftUp==null && leftDown==null && rightUp==null && rightDown==null)){
            this.content=node;

            System.out.println("added " +((Place)node.getContent()).getName());

            return true;
        }

        //= in case something is exactly at the center
        if(node.getPos().getX()>=this.pos.getX()){
            //right side
            if(node.getPos().getY()>=this.pos.getY()){
                //up
                if(this.rightUp==null){
                    this.rightUp = new QuadTreeQuad<T>(this.pos.getX()+this.size/4, this.pos.getY()+this.size/4, this.size/2);
                }
                return this.rightUp.insert(node);
            }else {
                //down
                if(this.rightDown==null){
                    this.rightDown = new QuadTreeQuad<T>(this.pos.getX()+this.size/4, this.pos.getY()-this.size/4, this.size/2);
                }
                return this.rightDown.insert(node);
            }
        } else {
            //left side
            if(node.getPos().getY()>=this.pos.getY()){
                //up
                if(this.leftUp==null){
                    this.leftUp = new QuadTreeQuad<T>(this.pos.getX()-this.size/4, this.pos.getY()+this.size/4, this.size/2);
                }
                return this.leftUp.insert(node);
            }else {
                //down
                if(this.leftDown==null){
                    this.leftDown = new QuadTreeQuad<T>(this.pos.getX()-this.size/4, this.pos.getY()-this.size/4, this.size/2);
                }
                return this.leftDown.insert(node);
            }
        }

    }


    public Vector2d getPos() {
        return pos;
    }

    public double getSize() {
        return size;
    }

    public QuadTreeNode<T> find(Vector2d pos) {

        if(this.content!=null && this.content.getPos().equals(pos))
            return this.content;

        //= in case something is exactly at the center
        if(pos.getX()>=this.pos.getX()){
            //right side
            if(pos.getY()>=this.pos.getY()){
                //up
                if(this.rightUp!=null){
                    return this.rightUp.find(pos);
                }else {
                    return null;
                }
            }else {
                //down
                if(this.rightDown!=null){
                    return this.rightDown.find(pos);
                } else {
                    return null;
                }
            }
        } else {
            //left side
            if(pos.getY()>=this.pos.getY()){
                //up
                if(this.leftUp!=null){
                    return  this.leftUp.find(pos);
                } else {
                    return null;
                }
            }else {
                //down
                if(this.leftDown!=null){
                    return  this.leftDown.find(pos);
                } else {
                    return null;
                }
            }
        }

    }
}
