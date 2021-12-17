import java.sql.SQLOutput;
import java.util.Arrays;

/**
 *Tester for polynomial class
 */
public class PolynomialTester {
    public static void main(String[] args) {
        Polynomial pl1 = new Polynomial(3);

        Polynomial pl2 = pl1.getDerivative();
       /* System.out.println(pl2.getDerivative());
        System.out.println(pl1);
        double[] cf = pl1.getCoef();
        System.out.println(pl1);

        */
       // System.out.println(pl1);
       // System.out.println(pl1.getDerivative());

        pl1.displayAnalyticalInfo(-5,5,1e-4);
        /*double[] roots = pl1.getRoots(-4, 2, 10e-5);
        Point[] extrema = pl1.getExtremaPoints(-4, 2, 10e-5);
        Point[] inf = pl1.getInflectionPoints(-4, 2, 10e-5);
        */

        /*System.out.println("roots:");
        for (int i = 0; i < roots.length; i++)
            System.out.println(roots[i]);
        double[] deroots = pl2.getRoots(-4, 2, 10e-5);
        System.out.println("saddles:");
        for (int i = 0; i < deroots.length; i++) {
            System.out.println(deroots[i]);
        }
        System.out.println("Extrema points:");
        for (int i = 0; i < extrema.length; i++)
            System.out.println(extrema[i]);

        System.out.println("Inflection points:");
        for (int i = 0; i < inf.length; i++)
            System.out.println(inf[i]);

         */
    }

}
