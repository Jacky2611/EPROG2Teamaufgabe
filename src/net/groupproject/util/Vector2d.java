package net.groupproject.util;
import java.lang.Math;
import java.util.Objects;


/**
 * @author Ansraer on 16.05.2018
 * @author Jakob Lang
 * @project TeamaufgabeProjekt
 */
public class Vector2d {

    private double x;
    private double y;

    // Constructor methods ....

    public Vector2d() {
        x = y = 0.0;
    }

    public Vector2d( double dX, double dY ) {
        this.x = dX;
        this.y = dY;
    }

    // Convert vector to a string ...

    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }

    // Compute magnitude of vector ....

    public double length() {
        return Math.sqrt ( x * x + y * y);
    }

    public double distance(Vector2d v1){
        return Math.sqrt(Math.pow(this.x -v1.x,2)+Math.pow(this.y -v1.y,2));
    }

    // Sum of two vectors ....

    public void add( Vector2d v1 ) {
        this.x =this.x + v1.x;
        this.y =this.y + v1.y;
    }

    // Subtract vector v1 from v .....

    public void sub( Vector2d v1 ) {
        this.x =this.x - v1.x;
        this.y =this.y - v1.y;
    }

    // Scale vector by a constant ...

    public void scale( double scaleFactor ) {
        this.x =this.x *scaleFactor;
        this.y =this.y *scaleFactor;
    }

    public double dotProduct ( Vector2d v1 ) {
        return this.x *v1.x + this.y *v1.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return Double.compare(vector2d.x, x) == 0 &&
                Double.compare(vector2d.y, y) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}