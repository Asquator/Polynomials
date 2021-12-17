/**
 * This function represents a point in 2D euclidean space
 *  *@author Asquator
 *  *@version 0.1
 */

public class Point {
    protected double _x;
    protected double _val;

    Point(double x, double val){
        _x = x;
        _val = val;
    }

    Point(Point other){
        _x = other._x;
        _val = other._val;
    }

    public double getX() {
        return this._x;
    }

    public double getVal() {
        return this._val;
    }

    public void setX(double x) {
        this._x = _x;
    }

    public void setY(double val){
        this._val = _val;
    }

    public String toString() {
        return "Point: (" + _x + "," + _val + ")";
    }
}
