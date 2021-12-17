/**
 *This class represents a strict inflection point of some function
 *@author Asquator
 *@version 0.1
 */
public class inflectionPoint extends Point{
    private boolean _downToUp; // concavity toggle

    /**
     * Constructs an inflection point from it's coordinates and concavity change type
     * @param x the x coordinate
     * @param val the value
     * @param downToUp concavity change type
     */
    public inflectionPoint(double x, double val, boolean downToUp){
        super(x, val);
        _downToUp = downToUp;
    }

    /**
     * Constructs an inflection point from a point and a concavity change type
     * @param point a point to use
     * @param downToUp concavity change type
     */
    public inflectionPoint(Point point, boolean downToUp){
        super(point);
        _downToUp = downToUp;
    }

    protected static boolean isInflection(double x, Interval interval, Polynomial secondDerivative){
        return extremumPoint.isExtremum(x, interval, secondDerivative);
    }

    /*
     * These two functions help determine the concavity change type, assuming the second derivative has constant signs on two sides of the point.
     */

    protected static boolean checkIfUpToDown(double x, Interval interval, Polynomial secondDerivative){
        return extremumPoint.checkIfMin(x, interval, secondDerivative);
    }

    protected static boolean checkIfDownToUp(double x, Interval interval, Polynomial secondDerivative){
        return extremumPoint.checkIfMin(x, interval, secondDerivative);
    }


    /**
     * Get string representation of the point
     * @return String representation of the point
     */
    public String toString(){
        return super.toString() + " point of inflection, concavity changes from " + (_downToUp ? "down to up" : "up to down");
    }
}


