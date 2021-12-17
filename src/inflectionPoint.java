/**
 *This class represents a strict inflection point of some function
 *@author Asquator
 *@version 0.1
 */
public class inflectionPoint extends Point{
    private boolean _downToUp; // concavity toggle

    public inflectionPoint(double x, double val, boolean downToUp){
        super(x, val);
        _downToUp = downToUp;
    }

    public inflectionPoint(Point point, boolean downToUp){
        super(point);
        _downToUp = downToUp;
    }

    protected static boolean isInflection(double x, Interval interval, Polynomial secondDerivative){
        return extremumPoint.isExtremum(x, interval, secondDerivative);
    }

    protected static boolean checkIfUpToDown(double x, Interval interval, Polynomial secondDerivative){
        return extremumPoint.checkIfMin(x, interval, secondDerivative);
    }


    public String toString(){
        return super.toString() + " point of inflection, concavity changes from " + (_downToUp ? "down to up" : "up to down");
    }
}


