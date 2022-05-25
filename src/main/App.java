package main;


import main.lists.CoarseList;
import main.lists.FineList;
import main.lists.LazyList;
import main.lists.OptimisticList;
import main.experiments.CoarseListExperiment;
import main.experiments.FineListExperiment;
import main.experiments.LazyListExperiment;

public class App {
    public static void main(String[] args) throws Exception {
        // runExperiment(args);
        CoarseListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd, probabilidadeContains,
                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
        FineListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd, probabilidadeContains,
                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
        LazyListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd, probabilidadeContains,
                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
        
        // coarseList.add(10);
    }

    // Setup básico. Lista e um conjunto de threads executoras. Cada thread
    // executora fica em loop
    // onde sorteia um tipo de operação (veja interface) e invoca o método
    // correspondente.

    // Parametrização das threads:
    // 1 - número de threads
    static int numeroThreads = 30;
    static boolean running = true;
    static boolean count = false;
    // 2 - acabar threads por tempo (configura) - main para de contabilizar depois
    // de um tempo e interrompe todas as threads.
    // Controle externo (main?) às threads que fazem acesso, que executa o que
    // segue:
    // - lançar threads
    // - passar tempo de aquecimento
    // - inciar contabilizacao
    // - passar tempo de experimento (medicao)
    // - parar threads
    static int tempoExecucao = 60;

    // 3 - distribuição de probabilidade para operações add, delete, contains. ->>
    // prob add > prob delete
    static int probabilidadeAdd = 40, probabilidadeDelete = 30, probabilidadeContains = 30;

    // 4 - o espaço de valores para sortear operações --> limita tamanho máximo da
    // lista (nao duplica valor dentro)
    static int valorMinimo = 1;

    static int valorMaximo = 100000;

    // 5 - medir tamanho médio da lista
    static volatile int countAdd = 0, countContains = 0, countRemove = 0;
    // Parametrização da lista (questões envolvendo a lista)
    static CoarseList<Integer> coarseList = new CoarseList<>();
    static FineList<Integer> fineList = new FineList<>();
    static OptimisticList<Integer> optimisticList = new OptimisticList<>();
    static LazyList<Integer> lazyList = new LazyList<>();

    // 1 - população inicial - gerar uma lista inicialmente populada - para diminuir
    // tempo de warm-up (Ex.: 100k, 200k, 300k elementos)
    static int tamanhoPopulacaoInicial = 100000;
    // 2 - limitar tamanho da lista para nao ter crescimento indefinido, ou entao
    // limitar o espaço de chaves tal que ao inserir uma chave que já está presente
    // nao vai aumentar a lista. O aumento da lista vai gerar tempos crescentes para
    // as operacoes. Precisamos de uma lista de tamanho estacionário.

    // 3 - Tempo de execução e warm-up. Definir um tempo inicial onde medidas não
    // são tomadas pois a lista está ainda variando
    // rapidamente de tamanho e assim de tempo de execucao, influenciando na média.
    // Sugere-se inicialmente 10 s de warmup e 1 min de experimento. Entretanto
    // estes valores devem ser investigados empiricamente.
    // Ou seja, veja se os resultados são sensíveis a estes parâmetros.
    static int warmup = 10000;

    // static Thread thread(int n) {

    // Thread t = new Thread() {
    // @Override
    // public void run() {
    // while (running) {
    // if (Random.getRandomOperation() == 3) {
    // coarseList.add(Random.getRandom());
    // // fineList.add(getRandom());
    // // optimisticList.add(getRandom());
    // // lazyList.add(getRandom());
    // if (count) {
    // countAdd++;}
    // // System.out.println("aad");
    // } else if (Random.getRandomOperation() == 2) {
    // coarseList.contains(Random.getRandom());
    // // fineList.contains(getRandom());
    // // optimisticList.contains(getRandom());
    // // lazyList.contains(getRandom());
    // if (count)
    // countContains++;
    // } else {
    // coarseList.remove(Random.getRandom());
    // // fineList.remove(getRandom());
    // // optimisticList.remove(getRandom());
    // // lazyList.remove(getRandom());
    // if (count)
    // countRemove++;
    // }
    // }
    // }

    // };
    // t.start();
    // return t;
    // }

    // Consolidado
    // static void testThreads(int TH) {
    // Thread[] threads = new Thread[TH];
    // Thread t;
    // for (int i = 0; i < TH; i++) {
    // t = thread(i);
    // threads[i] = t;
    // }

    // try {
    // // WarmUp
    // Thread.sleep(10000);
    // count = true;

    // Thread.sleep(tempoExecucao * 1000);
    // running = false;
    // for (int i = 0; i < TH; i++) {
    // threads[i].join();
    // }
    // } catch (InterruptedException e) {
    // System.err.println(e.toString());

    // } finally {
    // }
    // }

    // public static void runExperiment(String[] args) {
    // // LinkedList<Integer> list = new LinkedList<Integer>();

    // for (int i = 2; i <= numeroThreads; i = i + 2) {
    // coarseList = new CoarseList<>();
    // // fineList = new FineList<>();
    // // optimisticList = new OptimisticList<>();
    // // lazyList = new LazyList<>();
    // countRemove = 0;
    // countContains = 0;
    // countAdd = 0;

    // tamanhoPopulacaoInicial = 100000;
    // for (int k = 0; k < tamanhoPopulacaoInicial; k++) {
    // coarseList.add(k);
    // // fineList.add(k);
    // // optimisticList.add(k);
    // // lazyList.add(k);
    // }
    // running = true;
    // count = false;
    // testThreads(i);
    // System.out.print(i + ",");
    // System.out.print(countAdd / tempoExecucao + ",");
    // System.out.print(countContains / tempoExecucao + ",");
    // System.out.print(countRemove / tempoExecucao + ",");
    // System.out.println(coarseList.count());
    // // System.out.println(fineList.count());
    // // System.out.println(optimisticList.count());
    // // System.out.println(lazyList.count());
    // }
    // // System.out.println(coarseList);

    // // System.out.println(coarseList.add(1));
    // // Timer timer = new Timer();
    // // TimeOutTask timeOutTask = new TimeOutTask(t, timer);
    // // timer.schedule(timeOutTask, 3000);

    // // if (args.length != 3) {
    // // System.out.println("Usage: java <Experiment> numAddThreads
    // numRemoveThreads
    // // numContainsThreads");
    // // } else {
    // // System.out.println("Executando experimentos");
    // // }
    // }

}
