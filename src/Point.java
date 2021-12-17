/**
 * This function represents a point on graph of a function in 2D euclidean space.
 *  *@author Asquator
 *  *@version 0.1
 */

public class Point {
    protected double _x;
    protected double _val;

    /**Constructs a point from a given x coordinate and a given value
     * @param x the x coordinate
     * @param val the value of a function in the point
     */
    Point(double x, double val){
        _x = x;
        _val = val;
    }

    /**Constructs a point from another point
     * @param other the point to construct from
     */
    Point(Point other){
        _x = other._x;
        _val = other._val;
    }

    /**
     * Get the x coordinate
     * @return the x coordinate
     */
    public double getX() {
        return this._x;
    }

    /**
     * Get the value
     * @return the value of a function at the point
     */
    public double getVal() {
        return this._val;
    }

    /**
     * Set the x coordinate
     * @param x a coordinate to set
     */
    public void setX(double x) {
        this._x = _x;
    }

    /**
     * Set the value
     * @param val a value to set
     */
    public void setY(double val){
        this._val = _val;
    }

    /**
     * Get string representation of the point
     * @return String representation of the point
     */
    public String toString() {
        return "Point: (" + _x + "," + _val + ")";
    }
}
