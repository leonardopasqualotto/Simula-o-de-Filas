import java.util.LinkedList;
public class Fila{
	private String nome;
	private int perdas;
	private int servidores, capacidade, clientesAtual;
	private double chegadaMin, chegadaMax, saidaMin, saidaMax; 
	private boolean isInfinito;
	private double [] estados;
	private LinkedList<Rotacao> rotacoes;

	public Fila(String nome, int serv, int capac, double cheMin, double cheMax, double saiMin, double saiMax){	
		this.servidores = serv;this.chegadaMin = cheMin;this.chegadaMax = cheMax;this.saidaMin = saiMin; this.saidaMax = saiMax;
		this.clientesAtual = 0;
		this.nome = nome;
		if(capac < 0){
			capacidade = 1;
			isInfinito = true;
		}else{
			capacidade  = capac;
			isInfinito = false;
		}
		this.estados = new double[capacidade+1];
		rotacoes = new LinkedList<>();	
	}	
	public void addRotacao(Rotacao r){
		rotacoes.add(r);
	}
	public String getNome(){
		return nome;
	}
	public int getPerdas(){
		return perdas;
	}
	public void perdeCliente(){
		perdas++;
	}
	public void setCapacidade(int novaCapacidade){
		capacidade = novaCapacidade;
	}
	public int getCapacidade(){
		return capacidade;
	}
	public int getServidores(){
		return servidores;
	}
	public double getEstados(int i){
		return estados[i];
	}
	public double[] getEstados(){
		return estados;
	}
	public boolean getIsInfinito(){
		return isInfinito;
	}
	public void aumentaCapacidade(){
		
		double [] novaCapacidade = new double[capacidade+2];
		for(int i=0;i<capacidade;i++){
			novaCapacidade[i] = estados[i];
		}
		estados = novaCapacidade;
		capacidade++;
	}
	public int getClientesAtual(){
		return clientesAtual;
	}
	public void adicionaCliente(){
		clientesAtual++;
	}
	public void removeCliente(){
		clientesAtual--;
	}
	public LinkedList <Rotacao> getRotacoes(){
		return rotacoes;
	}
	public double getChegadaMin(){
		return chegadaMin;
	}
	public double getChegadaMax(){
		return chegadaMax;
	}
	public double getSaidaMin(){
		return saidaMin;
	}
	public double getSaidaMax(){
		return saidaMax;
	}
}
