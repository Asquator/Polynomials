
/**
 *This class represents a strict extremum point of some function (assuming it's either minimum or maximum and not both)
 *@author Asquator
 *@version 0.1
 */
public class extremumPoint extends Point
{
    private boolean _isMin; // minimum/maximum toggles

    public extremumPoint(double x, double val, boolean isMin){
        super(x, val);
        _isMin = isMin;
    }

    public extremumPoint(Point point, boolean isMin){
        super(point);
        _isMin = isMin;
    }


    protected static boolean isExtremum(double x, Interval interval, Polynomial derivative){
        double midFirst = (interval.getA() + x) / 2;
        double midSecond = (x + interval.getB()) / 2;
        if(derivative.calcAt(midFirst) * derivative.calcAt(midSecond) < 0)
            return true;
        return false;
    }

    protected static boolean checkIfMin(double x, Interval interval, Polynomial derivative){
        double midFirst = (interval.getA() + x) / 2;
        double midSecond = (x + interval.getB()) / 2;
        if(derivative.calcAt(midFirst) < 0 && derivative.calcAt(midSecond) > 0)
            return true;
        return false;
    }

    public static boolean checkIfMax(double x, Interval interval, Polynomial derivative){
        return !checkIfMin(x, interval, derivative);
    }


    public boolean isMin(){
        return _isMin;
    }

    public boolean isMax(){
        return !_isMin;
    }

    public String toString(){
        return super.toString() + " local " + (_isMin ? "minimum" : "maximum");
    }
}
