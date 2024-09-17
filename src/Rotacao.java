

public class Rotacao {
    private double probabilidade;
    private Fila filaSaida;
    private Fila filaChegada;

    public Rotacao(double probabilidade, Fila saida, Fila chegada){
        this.probabilidade = probabilidade;
        this.filaSaida = saida;
        this.filaChegada = chegada;
    }
    public Rotacao(double probabilidade, Fila saida){
        this.probabilidade = probabilidade;
        this.filaSaida = saida;
        this.filaChegada = null;
    }
    public Fila getFilaSaida(){
        return filaSaida;
    }
    public Fila getFilaChegada(){
        return filaChegada;
    }
    public double getProbabilidade(){
        return probabilidade;
    }
}
