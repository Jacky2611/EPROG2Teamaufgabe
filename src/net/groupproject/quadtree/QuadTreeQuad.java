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
    

    public boolean insert(QuadTreeNode<T> node) {
        //if this is en empty quad just put the node inside, no need to divide it
        if (this.content == null && (leftUp == null && leftDown == null && rightUp == null && rightDown == null)) {
            this.content = node;

            System.out.println("added " + ((Place) node.getContent()).getName());

            return true;
        }

        //= in case something is exactly at the center
        if (node.getPos().getX() >= this.pos.getX()) {
            //right side
            if (node.getPos().getY() >= this.pos.getY()) {
                //up
                if (this.rightUp == null) {
                    this.rightUp = new QuadTreeQuad<T>(this.pos.getX() + this.size / 4, this.pos.getY() + this.size / 4, this.size / 2);
                }
                return this.rightUp.insert(node);
            } else {
                //down
                if (this.rightDown == null) {
                    this.rightDown = new QuadTreeQuad<T>(this.pos.getX() + this.size / 4, this.pos.getY() - this.size / 4, this.size / 2);
                }
                return this.rightDown.insert(node);
            }
        } else {
            //left side
            if (node.getPos().getY() >= this.pos.getY()) {
                //up
                if (this.leftUp == null) {
                    this.leftUp = new QuadTreeQuad<T>(this.pos.getX() - this.size / 4, this.pos.getY() + this.size / 4, this.size / 2);
                }
                return this.leftUp.insert(node);
            } else {
                //down
                if (this.leftDown == null) {
                    this.leftDown = new QuadTreeQuad<T>(this.pos.getX() - this.size / 4, this.pos.getY() - this.size / 4, this.size / 2);
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

    //prints out the number of Trainstations and Airports
    public void placesNearPoint(Vector2d pos, double r) {

        Vector2d boundBoxTl = new Vector2d(pos.getX() - r, pos.getY() + r);
        Vector2d boundBoxBr = new Vector2d(pos.getX() + r, pos.getY() - r);

        int[] x = placeInRadius(pos, r, boundBoxTl, boundBoxBr);
        System.out.println("There are " + x[0] + " Trainstations and " + x[1] + " Airports");
    }

    //finds every Place within a radius r of Position pos.
    //Returns an int[] Array, where Array[0] is the number of Trainstations and Array[1] is the number of Airports
    private int[] placeInRadius(Vector2d pos, double r, Vector2d boundBoxTl, Vector2d boundBoxBr) {
        int countT, countA;
        countT = countA = 0;
        if (this.content != null) {
            if (insideBoundBox(this.content.getPos(), boundBoxTl, boundBoxBr)) {


                if (pos.distance(this.content.getPos()) <= r) {

                    Place temp = (Place) this.content.getContent();
                    if (temp.getType().equals("TRAINSTATION")) {
                        countT++;
                    } else {
                        countA++;
                    }
                }
            }
            if (this.leftUp != null) {
                int[] arr = this.leftUp.placeInRadius(pos, r, boundBoxTl, boundBoxBr);
                countT += arr[0];
                countA += arr[1];
            }
            if (this.rightUp != null) {
                int[] arr = this.rightUp.placeInRadius(pos, r, boundBoxTl, boundBoxBr);
                countT += arr[0];
                countA += arr[1];
            }
            if (this.leftDown != null) {
                int[] arr = this.leftDown.placeInRadius(pos, r, boundBoxTl, boundBoxBr);
                countT += arr[0];
                countA += arr[1];
            }
            if (this.rightDown != null) {
                int[] arr = this.rightDown.placeInRadius(pos, r, boundBoxTl, boundBoxBr);
                countT += arr[0];
                countA += arr[1];
            }
        }


        return new int[]{countT, countA};
    }

    private int[] placeInRadius(Vector2d pos, double r) {
        Vector2d boundBoxTl = new Vector2d(pos.getX() - r, pos.getY() + r);
        Vector2d boundBoxBr = new Vector2d(pos.getX() + r, pos.getY() - r);

        return placeInRadius(pos, r, boundBoxTl, boundBoxBr);

    }

    //checks, whether a Position is inside a boundingbox defined by the two vertices TopLeft and BottomRight
    private boolean insideBoundBox(Vector2d p, Vector2d bbTl, Vector2d bbBr) {
        return (p.getX() >= bbTl.getX() &&
                p.getX() <= bbBr.getX() &&
                p.getY() <= bbTl.getY() &&
                p.getY() >= bbBr.getY());

    }

    //prints out the number of Airports, which have at least min Trainstations within a radius r
    public void trainstationsByAirport(double r, int min) {

        int count = trainstationsByAirport1(r, min);

        System.out.println("There are " + count + " Airports, which have at least " + min + " Trainstations within a radius of "+r);

    }

    //finds the airports, which have at least min Trainstations within a radius r and returns the number of these Airports
    private int trainstationsByAirport1(double radius, int min) {
        int count = 0;
        int help[];

        if (((Place)this.content.getContent()).getType().equals("AIRPORT")) {
            //TODO: Mistake must be here. Wrong Vector2d? (293 Airports should come out, not 17)
            /*different Vector2d i tried with their return values:
            new Vector2d(this.content.getPos().getX(),this.content.getPos().getY()) - 17
            new Vector2d(this.pos.getX(),this.pos.getY()) - 9
            new Vector2d(this.getPos().getX(),this.getPos().getY()) - 9
            this.pos / this.getPos() - 9
            this.content.getPos() - 17
            this.content.getContent()).getPos() - 17
             */
            help = this.placeInRadius( this.content.getPos(), radius);
            if (help[0] >=min) {
                count++;
            }
        }
        if (this.leftUp != null) {
            count+= this.leftUp.trainstationsByAirport1(radius, min);
        }
        if (this.rightUp != null) {
            count += this.rightUp.trainstationsByAirport1(radius, min);
        }
        if (this.leftDown != null) {
            count += this.leftDown.trainstationsByAirport1(radius, min);
        }
        if (this.rightDown != null) {
            count += this.rightDown.trainstationsByAirport1(radius, min);
        }

        return count;
    }


}
