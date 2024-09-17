public class PseudoAleatorio{
    private int a, c, semente;
    private double x0, xi, M, u;

    public PseudoAleatorio(int sem){
        this.a = 34;
        this.c = 6873;
        this.M = 1000000000;
        this.semente = sem;
        this.x0 = semente;
    }
    
    public double gera(double A, double B){
        xi = (a*x0 + c) % M;
        x0 = xi;
        u = xi/M;
        return (B-A) * u + A;
    }
}