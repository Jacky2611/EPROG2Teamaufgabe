package net.groupproject;
import net.groupproject.util.Vector2d;

import java.lang.Math;

public class Place {
    private String name;
    private Vector2d pos;
    private String type;


    public Place(String name, double xCoor, double yCoor, String type) {
        this.name = name;
        this.pos=new Vector2d(xCoor,yCoor);
        this.type = type;
    }

    public Place(){
        this.pos=new Vector2d();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxCoor() {
        return this.pos.getX();
    }

    public void setxCoor(double xCoor) {
        this.pos.setX(xCoor);
    }

    public double getyCoor() {
        return this.pos.getY();
    }

    public void setyCoor(double yCoor) {
        this.pos.setY(yCoor);
    }

    public Vector2d getPos(){return this.pos;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
