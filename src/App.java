import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
public class App {
    public static void main(String[] args) throws Exception {

        
        
        RedeFilas rede = leArquivo("src/input.txt"); //Substituir para diretório local
       
        while(rede.getContAleatorio()<100000){
            
            Evento evento = rede.getEscalonador().get(0);

            if(evento.getTipo().equals("Chegada")){rede.chegada(evento.getfilaChegada(), evento);}
            if(evento.getTipo().equals("Saida")){rede.saida(evento.getfilaSaida(), evento);}
            if(evento.getTipo().equals("Transicao")){rede.transicao(evento.getfilaSaida(), evento.getfilaChegada(), evento);}
            
            rede.getEscalonador().remove(0);
            Collections.sort(rede.getEscalonador()); 
        }

        situacaoRede(rede);
    }
    
    public static void situacaoRede(RedeFilas rede){

        LinkedList<Fila> filas = rede.getFilas();LinkedList<Evento> escalonador = rede.getEscalonador();LinkedList<Rotacao> rotacoes;Fila f;Rotacao r;Evento e;
        
        System.out.println("-------------REDE DE FILAS---------------");
        System.out.println("");
        System.out.println("");
        System.out.println("Aleatórios utilizados:  "+rede.getContAleatorio());
        System.out.println("Eventos que sobraram no escalonador");
        System.out.println("");
        System.out.printf("%20s %20s %20s", "TIPO", "TEMPO", "PASSAGEM");
        System.out.println();
        DecimalFormat df = new DecimalFormat("0.0000");
        
        for(int i=0;i<escalonador.size();i++){
            e = escalonador.get(i);
            if(e.getTipo().equals("Transicao")){
                System.out.format("%20s %20s %20s", e.getTipo(), df.format(e.getTempo()), e.getfilaSaida().getNome()+" -> "+e.getfilaChegada().getNome());
            }
            if(e.getTipo().equals("Saida")){
                System.out.format("%20s %20s %20s", e.getTipo(), df.format(e.getTempo()), e.getfilaSaida().getNome()+" -> Rua");
            }
            if(e.getTipo().equals("Chegada")){
                System.out.format("%20s %20s %20s", e.getTipo(), df.format(e.getTempo()), "Rua -> "+ e.getfilaChegada().getNome());
            }
            System.out.println();
        }
        System.out.println();
        for(int i=0;i<filas.size();i++){
            f = filas.get(i);
            System.out.print("Fila "+f.getNome()+": G/G/");
            if(f.getIsInfinito()==true){
                System.out.print(f.getServidores());
            }else{
                System.out.print(f.getServidores()+"/"+f.getCapacidade());
            }
            System.out.println("");
            System.out.println("Chegada: "+f.getChegadaMin()+".."+f.getChegadaMax());
            System.out.println("Saída: "+f.getSaidaMin()+".."+f.getSaidaMax());
            System.out.println("Perdas: "+f.getPerdas());
            System.out.println("Rotaçoes:");
            rotacoes = f.getRotacoes();
            for(int k=0;k<rotacoes.size();k++){
                r = rotacoes.get(k);
                
                if(r.getFilaChegada()==null){
                    System.out.println("Para Rua "+r.getProbabilidade());
                }else{
                    System.out.println("Para "+r.getFilaChegada().getNome()+" "+r.getProbabilidade());
                }
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");
            System.out.printf("%20s %20s %20s", "ESTADO", "TEMPO", "PROBABILIDADE");
            System.out.println();

            for(int j=0;j<=f.getCapacidade();j++){
                if(f.getIsInfinito()==true){

                    if(f.getEstados(j)==0){
                        f.setCapacidade(0);
                    }
                }
                System.out.format("%20s %20s %20s", j, df.format(f.getEstados(j)), df.format(f.getEstados(j)/rede.getTempo()*100)+"%");
                System.out.println();
            }
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }

    }
    public static RedeFilas leArquivo(String path) throws IOException{
        BufferedReader b = new BufferedReader(new FileReader(path));
        String line = b.readLine();
        String [] parametros;
        RedeFilas rede = new RedeFilas();
        Fila fila;
        Rotacao rotacao;
        Evento evento;
        
        while(line.length()>0){

            parametros = line.split(" ");
            fila = new Fila(parametros[0], Integer.parseInt(parametros[1]), Integer.parseInt(parametros[2]), Double.parseDouble(parametros[3]), Double.parseDouble(parametros[4]), Double.parseDouble(parametros[5]), Double.parseDouble(parametros[6]));
            rede.addFila(fila);
            line = b.readLine();

        }

        line = b.readLine();

        while(line.length()>0){

            parametros = line.split(" ");

            if(parametros.length ==3){

                rotacao = new Rotacao(Double.parseDouble(parametros[1]), rede.nomeFila(parametros[0]),rede.nomeFila(parametros[2]) );
                rede.nomeFila(rotacao.getFilaSaida().getNome()).addRotacao(rotacao);

            }if(parametros.length ==2){

                rotacao = new Rotacao(Double.parseDouble(parametros[1]),rede.nomeFila(parametros[0]));
                rede.nomeFila(rotacao.getFilaSaida().getNome()).addRotacao(rotacao);
            }

            line = b.readLine();

        }
    
    line = b.readLine();
    parametros = line.split(" ");
    evento = new Evento(Double.parseDouble(parametros[0]), rede.nomeFila(parametros[1]), parametros[2]);
    rede.addEvento(evento);
    b.close();
    return rede;
    }
}

