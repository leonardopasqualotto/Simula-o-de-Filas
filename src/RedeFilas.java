import java.util.LinkedList;
import java.util.List;

public class RedeFilas {
    private LinkedList <Fila> filas;
    private double tempo;
    private PseudoAleatorio p;
    private int contAleatorio;
    private LinkedList <Evento> escalonador;
  

    public RedeFilas(){
        tempo = 0;
        filas = new LinkedList<Fila>();
        escalonador = new LinkedList<Evento>();
        p = new PseudoAleatorio(2894);
        contAleatorio = 0;
    }
    public double getTempo(){
        return tempo;
    }
    public LinkedList<Fila> getFilas(){
        return filas;
    }
    public int getContAleatorio(){
        return contAleatorio;
    }
    public LinkedList<Evento> getEscalonador(){
        return escalonador;
    }
    public Fila nomeFila(String fila){
        for(int i=0;i<filas.size();i++){
            if(filas.get(i).getNome().equals(fila)){
                return filas.get(i);
            }
        }
        return filas.get(0);
    }  
    public void addFila(Fila f){
        filas.add(f);
    }
    public void addEvento(Evento e){
        escalonador.add(e);
    }
    public void contabiliza(Evento evento){
        Fila f;
        for(int i=0;i<filas.size();i++){
            f = filas.get(i);
            
            f.getEstados()[f.getClientesAtual()] += evento.getTempo() - tempo;

        }
        tempo = evento.getTempo();
        
	}
    public void chegada(Fila f, Evento atual){
		contabiliza(atual);
		if(f.getClientesAtual() < f.getCapacidade()){
			f.adicionaCliente();
            if(f.getIsInfinito()== true){ 
                f.aumentaCapacidade();
            }
			if(f.getClientesAtual() <=f.getServidores()){
				geraSaidacomRoteamento(f);		
			}
		}else{
            f.perdeCliente();
        }  
        for(int i=0;i<filas.size();i++){
            f = filas.get(i);
            if(f.getChegadaMin() > 0){
                double aleatorio = p.gera(f.getChegadaMin(), f.getChegadaMax());
		        contAleatorio++;
                
		        Evento chegada = new Evento((aleatorio + tempo),f, "Chegada");
		        escalonador.add(chegada);
            }
        }
    }
    public void saida(Fila f, Evento atual){
		contabiliza(atual);
		f.removeCliente();
		if(f.getClientesAtual()>=f.getServidores()){
            geraSaidacomRoteamento(f);
		}
	}
    public void transicao(Fila f1, Fila f2, Evento atual){

        //Contabiliza
        contabiliza(atual);
        // Saida
        f1.removeCliente();
        if(f1.getClientesAtual()>=f1.getServidores()){
            geraSaidacomRoteamento(f1);
		}
        //Chegada
        if(f2.getClientesAtual() < f2.getCapacidade()){
            f2.adicionaCliente();
            if(f2.getIsInfinito()== true){
                
                f2.aumentaCapacidade();
                
            }
            if(f2.getClientesAtual() <= f2.getServidores()){
                geraSaidacomRoteamento(f2);
            }
        }else{
            f2.perdeCliente();
        }
     
    }
    public void geraSaidacomRoteamento(Fila f){
       
        double aleatorio = p.gera(0, 1);
        
        contAleatorio++;
        List<Rotacao> rotacoes = f.getRotacoes();
        int aux = 0;            
        Rotacao r = rotacoes.get(aux); 
        double probAcumulada = r.getProbabilidade();

        while(aleatorio > probAcumulada){
            aux++;
            r = rotacoes.get(aux); 
            probAcumulada += r.getProbabilidade();
        }
        double aleatorio2 = p.gera(f.getSaidaMin(), f.getSaidaMax());
        
        contAleatorio++;
        if(r.getFilaChegada()== null){
            Evento saida = new Evento((aleatorio2 + tempo),f, "Saida");
			escalonador.add(saida);
        }else{
            Evento transicao = new Evento((aleatorio2 + tempo), f, r.getFilaChegada());
            escalonador.add(transicao);
        }
    }   
}
