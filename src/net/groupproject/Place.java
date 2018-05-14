package net.groupproject;

public class Place {
    private String name;
    private Point point;
    private String type;

    //Constructor------------------------------------------------------------------------------------------------------
    public Place(String name, Point p, String type) {
        this.point = new Point();
        this.name = name;
        this.point.setX(p.getX());
        this.point.setY(p.getY());
        this.type = type;
    }

    public Place(String name, double xCor, double yCor, String type) {
        this.point = new Point();
        this.name = name;
        this.point.setX(xCor);
        this.point.setY(yCor);
        this.type = type;
    }

    public Place() {
        this.name = null;
        this.point = new Point();
    }


    //Getter and Setter------------------------------------------------------------------------------------------------
    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxCoor() {
        return point.getX();
    }

    public void setxCoor(double xCoor) {
        this.point.setX(xCoor);
    }

    public double getyCoor() {
        return point.getY();
    }

    public void setyCoor(double yCoor) {
        this.point.setY(yCoor);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    //Methods----------------------------------------------------------------------------------
    @Override
    public String toString(){
        return name+": x="+getxCoor()+" ; y="+getyCoor()+" ; type: "+type;
    }


}

