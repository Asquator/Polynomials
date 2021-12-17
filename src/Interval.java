
/**
 * This class represents a closed real-valued interval
 * @author Quastor
 * @version 0.1
 */
public class Interval
{
    /**Value to create the default interval [-DEFAULT, DEFAULT]*/
    public static final double DEFAULT = 0;
    private double _a,_b;

    /**
     * Constructs an interval from given a, b endpoints
     * @param a a endpoint
     * @param b b endpoint
     */
    public Interval(double a, double b){
        _a = a;
        _b = b;
    }

    /**
     * Constructs the default interval [-DEFAULT, DEFAULT]
     */
    public Interval(){
        _a = -DEFAULT;
        _b = DEFAULT;
    }

    /**
     * Copy constructor, constructs an interval from another one
     */
    public Interval(Interval other){
        _a = other._a;
        _b = other._b;
    }

    /**
     * Returns the a endpoint
     * @return the a endpoint
     */
    public double getA(){
        return _a;
    }

    /**
     * Returns the b endpoint
     * @return the b endpoint
     */
    public double getB(){
        return _b;
    }

    /**
     * Returns the middle point
     * @return the middle point of the interval
     */
    public double midPoint(){
        return (_a + _b)/2;
    }

    /**
     * Checks if the interval is valid
     * @return true if the interval is valid (a < b)
     */
    public boolean isValid(){
        return _a < _b;
    }

    /**
     * Takes a point x and checks if the interval contains the point x
     * @param x The point to check
     * @return true  if the interval contains the point x
     */
    public boolean containsThePoint(double x){
        return _a <= x && x <= _b;
    }

    /**
     * Takes a point x and checks if x is an inner point of the interval
     * @param x The point to check
     * @return true if x is an inner point of the interval
     */
    public boolean containsInnerPoint(double x){
        return _a < x && x < _b;
    }

    /**
     * Takes a valid interval and an inner point x from the interval, and divides it into 2 intervals [a,x] and [x,b]
     * @param x A point that divides the interval
     * @return An array with two intervals after the division. If the interval is invalid, point is not inner or the point
    doesn't belong to the interval, the output might contain invalid intervals
     */
    public Interval[] divide(double x){
        return new Interval[]{new Interval(_a, x), new Interval(x, _b)};
    }

    /**
     * Takes a valid interval and an inner point x from the interval, and divides it into 2 intervals [a,x - d] and [x + d,b] with deviation d from the point
     * @param x A point that divides the interval
     * @param deviation deviation from the point
     * @return An array with two intervals after the division.  If the interval is invalid, the point is not inner, the point
    doesn't belong to the interval or the deviation is excessive, the output might contain invalid intervals
     */
    public Interval[] divideDeviation(double x, double deviation){
        return new Interval[]{new Interval(_a, x - deviation), new Interval(x + deviation, _b)};
    }
    
}
