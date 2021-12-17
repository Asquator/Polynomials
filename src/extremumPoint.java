
/**
 *This class represents a strict extremum point of some function (assuming it's either minimum or maximum and not both)
 *@author Asquator
 *@version 0.1
 */
public class extremumPoint extends Point
{
    private boolean _isMin; // minimum/maximum toggles

    /**
     * Constructs an extremum point from given coordinates and a type
     * @param x the x coordinate
     * @param val the value
     * @param isMin extremum type (minimum / maximum)
     */
    public extremumPoint(double x, double val, boolean isMin){
        super(x, val);
        _isMin = isMin;
    }

    /**
     * Constructs an extremum point from a given point and a type
     * @param point a point to use
     * @param isMin extremum type
     */
    public extremumPoint(Point point, boolean isMin){
        super(point);
        _isMin = isMin;
    }


    /*
    *Checks if a given point x that belongs to the given interval is an extremum assuming that the function has constant signs on two sides of the point.
    *Uses the derivative of a target function to check whether it changes sign at the point.
    */
    protected static boolean isExtremum(double x, Interval interval, Polynomial derivative){
        double midFirst = (interval.getA() + x) / 2;
        double midSecond = (x + interval.getB()) / 2;
        if(derivative.calcAt(midFirst) * derivative.calcAt(midSecond) < 0) //The derivative changes signs
            return true;
        return false;
    }

    /*
     *Checks if a given point x that belongs to the given interval is a minimum assuming that the function has constant signs on two sides of the point.
     *Uses the first derivative test.
     */
    protected static boolean checkIfMin(double x, Interval interval, Polynomial derivative){
        double midFirst = (interval.getA() + x) / 2;
        double midSecond = (x + interval.getB()) / 2;
        if(derivative.calcAt(midFirst) < 0 && derivative.calcAt(midSecond) > 0)
            return true;
        return false;
    }

    /*
     *Checks if a given point x that belongs to the given interval is a maximum assuming that the function has constant signs on two sides of the point.
     *Uses the first derivative test.
     */
        protected static boolean checkIfMax(double x, Interval interval, Polynomial derivative){
        return !checkIfMin(x, interval, derivative);
    }


    /**
     * Returns if a point is a local minimum
     * @return true if minimum
     */
    public boolean isMin(){
        return _isMin;
    }

    /**
     * Returns if a point is a local maximum
     * @return true if maximum
     */
    public boolean isMax(){
        return !_isMin;
    }

    /**
     * Returns a string representation of the extremum point
     * @return a string representation of the extremum point
     */
    public String toString(){
        return super.toString() + " local " + (_isMin ? "minimum" : "maximum");
    }
}
