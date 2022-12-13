package FiniteDifferenceMethod;

public class FDM {
    //Solving y'' = p(x)y' + q(x)y + r(x)
    private double min;
    private double max;
    private double low_bound;
    private double up_bound;
    private int num;
    private double[] a;
    private double[] b;
    private double[] c;
    private double[] d;

    private double[] l;
    private double[] u;
    private double[] z;
    private double[] w;
    public FDM(double min,double max, double low_bound, double up_bound, int num) {
        this.min = min;
        this.max = max;
        this.low_bound = low_bound;
        this.up_bound = up_bound;
        this.num = num;
        a = new double[num+2];
        b = new double[num+2];
        c = new double[num+2];
        d = new double[num+2];
        l = new double[num+2];
        u = new double[num+2];
        z = new double[num+2];
        w = new double[num+2];

    }

    public static double p(double x) {
        double res = 0;
        //Type in your equation here:
        res = -2/x;
        return res;
    }
    public static double q(double x) {
        double res = 0;
        //Type in your equation here:
        res = 2/x/x;
        return res;
    }
    public static double r(double x) {
        double res = 0;
        //Type in your equation here:
        res = Math.sin(Math.log(x))/x/x;
        return res;
    }
    public void solve() {
        double h = (max-min)/(num+1);
        double h2 = Math.pow(h,2);
        double x = min + h;
        a[1] = 2 + h2*q(x);
        b[1] = -1 + (h/2)*p(x);
        d[1] = -h2*r(x) + (1+(h/2)*p(x))*low_bound;

        for (int i = 2; i <= num - 1; i++) {
            x = min + i*h;
            a[i] = 2 + h2*q(x);
            b[i] = -1 + (h/2)*p(x);
            c[i] = -1 - (h/2)*p(x);
            d[i] = -h2*r(x);
        }

        x = max - h;
        a[num] = 2 + h2*q(x);
        c[num] = -1 - (h/2)*p(x);
        d[num] = -h2*r(x) + (1-(h/2)*p(x))*up_bound;

        l[1] = a[1];
        u[1] = b[1]/a[1];
        z[1] = d[1]/l[1];

        for (int i = 2; i <= num - 1; i++) {
            l[i] = a[i] - c[i]*u[i-1];
            u[i] = b[i]/l[i];
            z[i] = (d[i] - c[i]*z[i-1])/l[i];
        }

        l[num] = a[num]-c[num]*u[num-1];
        z[num] = (d[num]-c[num]*z[num-1])/l[num];
        w[0] = low_bound;
        w[num+1] = up_bound;
        w[num] = z[num];

        for (int i = num - 1; i >= 1; i--) {
            w[i] = z[i] - u[i]*w[i+1];
        }
        for (int i = 0; i <= num + 1; i++) {
            x = min + i*h;
            System.out.printf("x: %3.2f      w: %f\n",x,w[i]);
        }
    }

    public static void main(String[] args) {
        FDM fdm = new FDM(1,2,1,2,9);
        fdm.solve();
    }
}