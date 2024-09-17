public class Evento implements Comparable<Evento>{ 
    private double tempo;
    private String tipo;
    private Fila filaSaida;
    private Fila filaChegada;

    public Evento(double tempo, Fila f, String tipo){
        this.tempo = tempo;
        this.tipo = tipo;
        if(tipo.equals("Chegada")){
            filaChegada = f;
        }
        if(tipo.equals("Saida")){
            filaSaida = f;
        }

    }
    public Evento(double tempo, Fila f1, Fila f2){
        this.tipo = "Transicao";
        this.tempo = tempo;
        filaSaida = f1;
        filaChegada = f2;
    }
    public double getTempo(){
        return tempo;
    }
    public String getTipo(){
        return tipo;
    }
    public Fila getfilaSaida(){
        return filaSaida;
    }
    public Fila getfilaChegada(){
        return filaChegada;
    }
    @Override
    public int compareTo(Evento e) {
        if(this.tempo- e.getTempo()>0){
            return 1;
        }else if(this.tempo-e.getTempo()<0){
            return -1;
        }
        return 0;
    }
}
