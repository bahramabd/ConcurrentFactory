import java.util.concurrent.*;

class ComponentProductionLine implements Runnable {
    private String componentName;
    private Semaphore semaphore;

    public ComponentProductionLine(String componentName, Semaphore semaphore) {
        this.componentName = componentName;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            while (true) {
                startProduction();
                produceComponent();
                stopProduction();
                semaphore.release(); // Notify that a component is ready
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void startProduction() throws InterruptedException {
        System.out.println("Start production of " + componentName);
        // Simulate production time
        Thread.sleep(1000);
    }

    private void produceComponent() throws InterruptedException {
        System.out.println("Producing " + componentName);
        // Simulate production time
        Thread.sleep(2000);
    }

    private void stopProduction() throws InterruptedException {
        System.out.println("Stop production of " + componentName);
        // Simulate production time
        Thread.sleep(1000);
    }
}

class AssemblyLin implements Runnable {
    private Semaphore engineSem, wheelSem, glassSem;

    public AssemblyLin(Semaphore engineSem, Semaphore wheelSem, Semaphore glassSem) {
        this.engineSem = engineSem;
        this.wheelSem = wheelSem;
        this.glassSem = glassSem;
    }

    @Override
    public void run() {
        try {
            while (true) {
                engineSem.acquire();
                wheelSem.acquire(4); // Need 4 wheels
                glassSem.acquire(6); // Need 6 glasses

                startAssembly();
                assembleCar();
                stopAssembly();

                // Release permits to indicate that components are used
                engineSem.release();
                wheelSem.release(4);
                glassSem.release(6);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void startAssembly() throws InterruptedException {
        System.out.println("Start car assembly");
        // Simulate assembly time
        Thread.sleep(2000);
    }

    private void assembleCar() throws InterruptedException {
        System.out.println("Assembling car");
        // Simulate assembly time
        Thread.sleep(3000);
    }

    private void stopAssembly() throws InterruptedException {
        System.out.println("Stop car assembly");
        // Simulate assembly time
        Thread.sleep(2000);
    }
}

public class CarProductionSystem {
    public static void main(String[] args) {
        Semaphore engineSem = new Semaphore(1);
        Semaphore wheelSem = new Semaphore(4);
        Semaphore glassSem = new Semaphore(6);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(new ComponentProductionLine("Engine", engineSem));
        executorService.submit(new ComponentProductionLine("Wheel", wheelSem));
        executorService.submit(new ComponentProductionLine("Glass", glassSem));
        executorService.submit(new AssemblyLin(engineSem, wheelSem, glassSem));

        // Shutdown the executor when done
        executorService.shutdown();
    }
}
