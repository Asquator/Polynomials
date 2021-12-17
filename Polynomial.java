import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * This class represents a polynomial with real coefficients
 * @author Asquator
 * @version 0.1
 */
public class Polynomial {
    private static final double STANDARD_Y_ERROR = 1e-14;
    public static final double STANDARD_X_ERROR = 1e-5;
    private char _variable = 'x'; //_variable in string representation
    private double[] _coefficients;  //Coefficients array

    /**
     * Constructs a polynomial from a list of coefficients
     */
    public Polynomial(double... coef) {
        _coefficients = new double[coef.length];
        for (int i = 0; i < coef.length; i++)
            _coefficients[i] = coef[i];
        reduce();
    }

    public Polynomial(char variable, double... coef) {
        this(coef);
        _variable = variable;
    }

    /**
     * Constructs a polynomial from another one
     *
     * @param other A polynomial to copy
     */
    public Polynomial(Polynomial other) {
        _coefficients = new double[other._coefficients.length];
        for (int i = 0; i < other._coefficients.length; i++)
            _coefficients[i] = other._coefficients[i];
        reduce();
    }

    /*
     * Rounds given value to required decimal places according to the given value
     * Used to round values of derivative or the x coordinates of roots
     */
    private static double roundVal(double val, double error) {
        DecimalFormat df;
        String format = ".";
        for (int dec = 0; Math.pow(10, -dec) > error; dec++)
            format += "#";
        df = new DecimalFormat(format);
        return Double.parseDouble(df.format(val));
    }

    //Reduces the array of coefficients to discard the tail of zeroes
    private void reduce() {
        if (!isConst()) {
            int newDeg = getDegree(); //degree after reduction
            for (int i = _coefficients.length - 1; i >= 0 && _coefficients[i] == 0; i--) {  //Searching the first nonzero coefficient
                newDeg = i - 1;
            }

            if (newDeg < _coefficients.length) {
                double[] newCoef; //new array of coefficients
                newCoef = new double[newDeg + 1]; //initializing the new array of coefficients
                for (int i = 0; i < newDeg + 1; i++) { //filling the array
                    newCoef[i] = _coefficients[i];
                }
                _coefficients = newCoef;

            }
        }
    }


    /**
     * Returns the degree of the polynomial
     *
     * @return the degree of the polynomial
     */
    public int getDegree() {
        if (!isZero())
            return _coefficients.length - 1;
        return -1;
    }

    /**
     * Returns the array of coefficients
     *
     * @return the array of coefficients
     */
    public double[] getCoef() {
        return _coefficients;
    }

    /**
     * Checks if the polynomial equals to the zero polynomial
     *
     * @return true if the polynomial equals to the zero polynomial
     */
    public boolean isZero() {
        return isConst() && _coefficients[0] == 0;
    }

    public boolean isConst() {
        return _coefficients.length == 1;
    }

    public boolean isLinear() {
        return _coefficients.length == 2;
    }


    /**
     * This function calculates the derivative for the current polynomial
     *
     * @return the derivative function (polynomial)
     */
    public Polynomial getDerivative() {
        if (!isConst()) {
            double[] derivativeCoef = new double[_coefficients.length - 1];
            for (int i = 0; i < _coefficients.length - 1; i++)
                derivativeCoef[i] = (i + 1) * _coefficients[i + 1];
            return new Polynomial(derivativeCoef);
        }
        return new Polynomial(0);
    }


    /**
     * Calculate the value of a polynomial in x
     *
     * @param x the value to calculate in
     */
    public double calcAt(double x) {
        double val = 0;
        for (int i = 0; i < _coefficients.length; i++) {
            val += _coefficients[i] * Math.pow(x, i);
        }
        return val;
    }

    /**
     * Perform addition of polynomials
     *
     * @param other A polynomial to add
     */
    public Polynomial add(Polynomial other) {
        int deg = getDegree(), otherDeg = other.getDegree(); //Degrees of 2 polynomials
        int maxDeg = Math.max(deg, otherDeg); //Maximum degree of the sum

        double[] newCoef = new double[maxDeg];
        for (int i = 0; i <= deg; i++) {
            newCoef[i] = _coefficients[i];
        }

        for (int i = 0; i <= otherDeg; i++) {
            newCoef[i] += other._coefficients[i];
        }
        var newPolynomial = new Polynomial(newCoef);
        newPolynomial.reduce();
        return newPolynomial;
    }


    //Root searching algorithm


    /*
     * Input: interval endpoints and the desired accuracy (epsilon)
     * Returns one interval with opposite signs of polynom in the endpoints, which must be a subset of [min, max]
     */

    private Interval searchIntervalOppSign(double min, double max, double epsilon) {
        double middle;
        Interval firstIntervalResult;
        if (calcAt(min) * calcAt(max) < 0) //An interval was found
            return new Interval(min, max);
        if (Math.abs(max - min) < epsilon) //The lengths are less than EPSILON and no interval was found
            return new Interval();
        //No interval found, the search continues
        middle = (min + max) / 2;
        firstIntervalResult = searchIntervalOppSign(min, middle, epsilon);
        if (firstIntervalResult.isValid()) //Found interval in the first half
            return firstIntervalResult;
        else
            return searchIntervalOppSign(middle, max, epsilon);
    }

    /*
     * Wrapper function for searchIntervalOppSign()
     */
    private Interval searchIntervalOppSign(Interval interval, double epsilon) {
        return searchIntervalOppSign(interval.getA(), interval.getB(), epsilon);
    }

    /*
     * Input: a valid interval with opposite signs of the polynom in endpoints
     * Returns a root approximation in a given interval with given error
     */
    private double approxRootOppSign(double min, double max, double error) {
        double midPoint = (min + max) / 2;
        if (Math.abs(max - min) < error || calcAt(midPoint) == 0) //An approximation was found
            return midPoint;

        else if (calcAt(min) * calcAt(midPoint) < 0) //Opposite signs at min and middle points
            return approxRootOppSign(min, midPoint, error);

        else
            return approxRootOppSign(midPoint, max, error); //Opposite signs at middle and maximum points
    }


    private double[] searchRootsConstSign(double min, double max, double error) {
        if (!isConst()) {
            double[] derivRoots, roots = new double[getDegree()];
            int rootCounter = 0;
            Polynomial pln = this;
            Polynomial derivative;

            while (!pln.isConst()) {
                derivative = pln.getDerivative();
                derivRoots = derivative.getRoots(min, max, error);
                for (int i = 0; i < derivRoots.length; i++) {
                    if (Math.abs(pln.calcAt(derivRoots[i])) < STANDARD_Y_ERROR) { //Checking the value at saddle points of
                        roots[rootCounter++] = derivRoots[i];
                    }
                }
                pln = derivative;

            } //just a constant remained
            if (rootCounter > 0) { //Copying the roots to the final array
                double[] finalRoots = new double[rootCounter];
                for (int i = 0; i < rootCounter; i++) {
                    finalRoots[i] = roots[i];
                }
                return finalRoots;
            }

        } //if constant

        return new double[0];
    }


    /*
     * Wrapper function for searchRootsConstSign()
     */
    private double[] searchRootsConstSign(Interval interval, double error) {
        return searchRootsConstSign(interval.getA(), interval.getB(), error);
    }

    /*
     * Wrapper function for approxRootOppSign()
     */
    private double approxRootOppSign(Interval interval, double error) {
        return approxRootOppSign(interval.getA(), interval.getB(), error);
    }

    /**
     * Finds all roots of the given polynomial in the given interval. If the polynomial is a constant zero, returns an array with maximal double value.
     * @param min   the a endpoint
     * @param max   the b endpoint
     * @param error the desired accuracy
     * @return Array of roots of the given polynomial. If the polynomial is a constant zero, returns an array with max. double value.
     */
    public double[] getRoots(double min, double max, double error) {
        if (isZero())
            return new double[]{Double.MAX_VALUE};

        Interval targetInterval = new Interval(min, max);
        if (!targetInterval.isValid())
            return new double[0];

        double[] roots = new double[getDegree()]; //Stores roots
        double[] finalRoots;
        int rootCounter = 0; //Counts roots
        int countIntOpp = 0, newCountIntOpp; //Counts intervals with opposite signs

        Interval[] newIntervalsWithRoot, newOppSignIntervals;
        Interval[] intervalsWithRoot = new Interval[0]; //Intervals to check during an iteration
        Interval[] oppSignIntervals = new Interval[0]; //Intervals to approximate roots in
        Interval intervalSearchResult = searchIntervalOppSign(min, max, error); //Stores a temporary result of interval search

        if (intervalSearchResult.isValid()) { //Interval with opposite signs was found
            intervalsWithRoot = new Interval[]{new Interval(min, max)};
            oppSignIntervals = new Interval[]{intervalSearchResult}; //Adding the interval with a root to the search
            countIntOpp = 1;
        } else { //No interval with opposite signs found
            roots = searchRootsConstSign(targetInterval, error);
            rootCounter = roots.length;
        }
        while (countIntOpp > 0) { //If there are intervals with opposite signs
            newCountIntOpp = 0;
            newIntervalsWithRoot = new Interval[this.getDegree()];
            newOppSignIntervals = new Interval[this.getDegree()];

            for (int i = 0; i < countIntOpp; i++) { //Implementing the searching algorithm on an array of intervals
                roots[rootCounter] = approxRootOppSign(oppSignIntervals[i], error); //Approximating and saving the root
                rootCounter++;

                //Dividing the interval into two parts and checking each of them for the presence of roots
                for (Interval interval : intervalsWithRoot[i].divideDeviation(roots[rootCounter - 1], error)) {
                    if (interval.isValid()) {
                        intervalSearchResult = searchIntervalOppSign(interval, error); //Checking if there is an interval with opposite signed endpoints

                        if (intervalSearchResult.isValid()) {   //Such interval was found, adding it to the search
                            newIntervalsWithRoot[newCountIntOpp] = interval;
                            newOppSignIntervals[newCountIntOpp++] = intervalSearchResult;
                        } else { //Such interval wasn't found, applying the constant signed search
                            for (double root : searchRootsConstSign(interval, error))
                                roots[rootCounter++] = root;
                        }
                    }
                }

            }
            //Preparing the variables for the next iteration
            countIntOpp = newCountIntOpp;
            intervalsWithRoot = newIntervalsWithRoot;
            oppSignIntervals = newOppSignIntervals;
        }


        //Rounding according to desired error
        finalRoots = new double[rootCounter];
        for (int i = 0; i < rootCounter; i++)
            finalRoots[i] = roundVal(roots[i], error);
        Arrays.sort(finalRoots);
        return finalRoots;
    }

    /**
     * Finds and returns an array of strict extremum points in the given interval
     * @param min the a endpoint
     * @param max the b endpoint
     * @param error the desired error
     * @return array of strict extremum points in the given interval.
     */
    public Point[] getExtremaPoints(double min, double max, double error) {
        if(isConst())
            return new Point[0];

        Polynomial derivative = getDerivative();
        double[] saddlePoints = derivative.getRoots(min, max, error); //Finds the saddle points
        int saddleNum = saddlePoints.length;
        if (saddleNum == 0) //There are no saddle points at all
            return new extremumPoint[0];

        Point[] extremaPoints = new Point[getDegree() - 1];
        Arrays.sort(saddlePoints);
        int extremaCounter = 0; //Counts extrema points
        double x; //Contains different saddle points

        Interval[] intervals = new Interval[saddleNum];

        //Setting the first and the last intervals to check change of sign
        if (saddleNum > 1) { //More than one saddle point
            intervals[0] = new Interval(min, saddlePoints[1]);
            intervals[saddleNum - 1] = new Interval(saddlePoints[saddleNum - 1], max);
        } else
            intervals[0] = new Interval(min, max); //Only one saddle point

        for (int i = 1; i < saddleNum - 1; i++) //Setting other intervals to be checked
            intervals[i] = new Interval(saddlePoints[i - 1], saddlePoints[i + 1]);

        for (int i = 0; i < saddleNum; i++) {
            x = saddlePoints[i];
            if (extremumPoint.isExtremum(x, intervals[i], derivative)) //Checking if the saddle point is an extremum
                extremaPoints[extremaCounter++] = new extremumPoint(x, calcAt(x), extremumPoint.checkIfMin(x, intervals[i], derivative) ? true : false); //Classifying and saving the point
        }

        extremumPoint point;
        Point finalExtremaPoints[] = new Point[extremaCounter];
        for (int i = 0; i < extremaCounter; i++) { //Saving and rounding points coordinates
            point = (extremumPoint)extremaPoints[i];
            finalExtremaPoints[i] = new extremumPoint(roundVal(point.getX(), error), roundVal(point.getVal(), STANDARD_Y_ERROR), point.isMin());
        }
        return finalExtremaPoints;
    }

    /**
     * Finds inflection points in the given interval and returns an array of them.
     * @param min the a endpoint
     * @param max the b endpoint
     * @param error the desired error
     * @return an array of inflection points in a given interval with the desired error
     */
    public Point[] getInflectionPoints(double min, double max, double error) {
        Point[] inflectionPoints = getDerivative().getExtremaPoints(min, max, error);
        for (int i = 0; i < inflectionPoints.length; i++)
            inflectionPoints[i] = new inflectionPoint(inflectionPoints[i], ((extremumPoint) inflectionPoints[i]).isMin());

        return inflectionPoints;
    }

    /**
     * Prints analytical information about the polynomial on a given interval
     * @param min the a endpoint
     * @param max the b endpoint
     * @param error the desired accuracy (default: 1E-5)
     */
    public void displayAnalyticalInfo(double min, double max, double error) {
        String info = this + "\n";
        if(isZero()) {
            System.out.println("The zero polynomial was given");
            return;
        }

        info += ("Roots: \n");
        for(double root : getRoots(min, max, error))
            info += "Root: " + root + "\n";
        info += '\n';
        info += ("Extrema Points: \n");
        for(Point point : getExtremaPoints(min, max, error))
            info += point + "\n";
        info += "\n";
        info += ("Inflection Points: \n");
        for (Point point : getInflectionPoints(min, max, STANDARD_X_ERROR)) {
            info += point + "\n";
        }
        System.out.println(info);
    }

    /**
     * Displays analytical info about the polynomial, such as roots, extrema points, inflection points in the given interval
     * @param min the a endpoint
     * @param max the b endpoint
     */
    public void displayAnalyticalInfo(double min, double max){
        displayAnalyticalInfo(min, max, STANDARD_X_ERROR);
    }

    /**
     * Returns string representation of the polynomial
     * @return string representation of the polynomial
     */

    public String toString() {
        if(isZero())
            return Integer.toString(0);
        String polynomialToStr = "";
        boolean firstCoef = true;
        double coef;

        for (int i = 0; i < _coefficients.length; i++) {
            coef = _coefficients[i];
            if (coef != 0) {
                if(coef > 0) {
                    if (!firstCoef)
                        polynomialToStr += " + ";
                }

                else
                    polynomialToStr += " - ";

                if(!(Math.abs(coef) == 1)) {
                    if ((int) coef == coef)
                        polynomialToStr += (int) Math.abs(coef);
                    else
                        polynomialToStr += Math.abs(coef);
                }
                polynomialToStr += Character.toString(_variable) + "^" + i;
                firstCoef = false;
            }
        }

        return polynomialToStr;
    }
}