package net.groupproject.quadtree;

import net.groupproject.util.Place;
import net.groupproject.util.Vector2d;

import java.util.ArrayList;
import java.util.List;

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
        this.pos = center;
        this.size = size;
        content = null;
        leftUp = null;
        leftDown = null;
        rightUp = null;
        rightDown = null;
    }

    public QuadTreeQuad(double centerX, double centerY, double size) {
        this(new Vector2d(centerX, centerY), size);
    }

    //calculates the distance between the two Positions p1,p2
    

    public void insert(QuadTreeNode<T> node) {

        if(node==null)
            return;

        //if this is en empty quad just put the node inside, no need to divide it
        if (this.content == null && (leftUp == null && leftDown == null && rightUp == null && rightDown == null)) {
            this.content = node;

            return;
        }

        //= in case something is exactly at the center
        if (node.getPos().getX() >= this.pos.getX()) {
            //right side
            if (node.getPos().getY() >= this.pos.getY()) {
                //up
                if (this.rightUp == null) {
                    this.rightUp = new QuadTreeQuad<T>(this.pos.getX() + this.size / 4, this.pos.getY() + this.size / 4, this.size / 2);
                }
                this.rightUp.insert(node);
            } else {
                //down
                if (this.rightDown == null) {
                    this.rightDown = new QuadTreeQuad<T>(this.pos.getX() + this.size / 4, this.pos.getY() - this.size / 4, this.size / 2);
                }
                this.rightDown.insert(node);
            }
        } else {
            //left side
            if (node.getPos().getY() >= this.pos.getY()) {
                //up
                if (this.leftUp == null) {
                    this.leftUp = new QuadTreeQuad<T>(this.pos.getX() - this.size / 4, this.pos.getY() + this.size / 4, this.size / 2);
                }
                this.leftUp.insert(node);
            } else {
                //down
                if (this.leftDown == null) {
                    this.leftDown = new QuadTreeQuad<T>(this.pos.getX() - this.size / 4, this.pos.getY() - this.size / 4, this.size / 2);
                }
                this.leftDown.insert(node);
            }
        }

        //After splitting this quad into more quads insert the content into the right sub-quad and remove it from this quad.
        QuadTreeNode<T> n= this.content;
        this.content=null;
        this.insert(n);

        return;

    }


    public QuadTreeNode<T> find(Vector2d pos) {

        if (this.content != null && this.content.getPos().equals(pos))
            return this.content;

        //= in case something is exactly at the center
        if (pos.getX() >= this.pos.getX()) {
            //right side
            if (pos.getY() >= this.pos.getY()) {
                //up
                if (this.rightUp != null) {
                    return this.rightUp.find(pos);
                } else {
                    return null;
                }
            } else {
                //down
                if (this.rightDown != null) {
                    return this.rightDown.find(pos);
                } else {
                    return null;
                }
            }
        } else {
            //left side
            if (pos.getY() >= this.pos.getY()) {
                //up
                if (this.leftUp != null) {
                    return this.leftUp.find(pos);
                } else {
                    return null;
                }
            } else {
                //down
                if (this.leftDown != null) {
                    return this.leftDown.find(pos);
                } else {
                    return null;
                }
            }
        }

    }



    //finds every Place within a radius r of Position pos.
    //Returns an int[] Array, where Array[0] is the number of Trainstations and Array[1] is the number of Airports
    public List<QuadTreeNode<T>> getContentInRadius(Vector2d pos, double r) {
        Vector2d boundBoxTl = new Vector2d(pos.getX() - r, pos.getY() + r);
        Vector2d boundBoxBr = new Vector2d(pos.getX() + r, pos.getY() - r);

        //this list will be filled with all content in the specified radius and is then returned to the user.
        List<QuadTreeNode<T>> contents = new ArrayList<QuadTreeNode<T>>();

        if (this.content != null) {
            if (insideBoundBox(this.content.getPos(),boundBoxTl,boundBoxBr)) {
            //if (insideBoundBox(boundBox,this.content.getPos())) {
                if (pos.distance(this.content.getPos()) <= r) {
                    contents.add(this.content);
                }
            }
        } else {
            if (this.leftUp != null && isOverlapping(boundBoxTl,boundBoxBr, this.leftUp.getTopLeftCorner(), this.leftUp.getBottomRightCorner())) {
                contents.addAll(this.leftUp.getContentInRadius(pos, r));
            }
            if (this.rightUp != null && isOverlapping(boundBoxTl,boundBoxBr, this.rightUp.getTopLeftCorner(), this.rightUp.getBottomRightCorner())) {
                contents.addAll(this.rightUp.getContentInRadius(pos, r));

            }
            if (this.leftDown != null && isOverlapping(boundBoxTl,boundBoxBr, this.leftDown.getTopLeftCorner(), this.leftDown.getBottomRightCorner())) {
                contents.addAll(this.leftDown.getContentInRadius(pos, r));
            }
            if (this.rightDown != null && isOverlapping(boundBoxTl,boundBoxBr, this.rightDown.getTopLeftCorner(), this.rightDown.getBottomRightCorner())) {
                contents.addAll(this.rightDown.getContentInRadius(pos, r));
            }
        }


        return contents;
    }



    public boolean isOverlapping(Vector2d Rect1TopLeft, Vector2d Rect1BottomRight,Vector2d Rect2TopLeft, Vector2d Rect2BottomRight) {
        if (Rect1TopLeft.getX() > Rect2BottomRight.getX() // R1 is right to R2
            || Rect1BottomRight.getX() < Rect2TopLeft.getX()// R1 is left to R2
            || Rect1TopLeft.getY() < Rect2BottomRight.getY() // R1 is below R2
            || Rect1BottomRight.getY() > Rect2TopLeft.getY()) { // R1 is above R1
            return false;
        }
        return true;
    }


    //checks, whether a Position is inside a boundingbox defined by the two vertices TopLeft and BottomRight
    private boolean insideBoundBox(Vector2d p, Vector2d bbTl, Vector2d bbBr) {
        return (p.getX() >= bbTl.getX() &&
                p.getX() <= bbBr.getX() &&
                p.getY() <= bbTl.getY() &&
                p.getY() >= bbBr.getY());

    }

    public Vector2d getTopLeftCorner(){
        return new Vector2d(this.pos.getX()-this.size/2, this.pos.getY()+this.size/2);
    }

    public Vector2d getBottomRightCorner(){
        return new Vector2d(this.pos.getX()+this.size/2, this.pos.getY()-this.size/2);
    }


    public Vector2d getPos() {
        return pos;
    }

    public double getSize() {
        return size;
    }




}
