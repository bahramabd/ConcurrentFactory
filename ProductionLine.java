import javax.swing.plaf.TableHeaderUI;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
class ProductionLine implements Runnable{
    private final Actions actions;
    private final Component component;
    private final AssemblyLine assemblyLine;
    private final int glassLine;
    private int produced;
    private GlassMax glassMax;


    public ProductionLine(Component component, Actions actions, AssemblyLine assemblyLine, int glassLine,GlassMax glassMax){
        this.actions=actions;
        this.component=component;
        this.assemblyLine=assemblyLine;
        this.glassLine=glassLine;
        this.produced=0;
        this.glassMax=glassMax;
    }
    public void run() {
        try {
            while (true) {
                glassMax.setBalance(0);
                // Start production line based on component type
                switch (component) {
                    case ENGINE:
                        actions.startEngineProductionLine();
                        actions.produceEngine();
                        actions.stopEngineProductionLine();
                        assemblyLine.notifyComponentProduced(Component.ENGINE);
                        break;
                    case WHEEL:
                        // Synchronize the production of wheels

                            actions.startWheelProductionLine();
                            for (int i = 0; i < 4; i++) {
                                actions.produceWheel();
                                assemblyLine.notifyComponentProduced(Component.WHEEL);
                            }
                            actions.stopWheelProductionLine();


                        break;
                    case GLASS:
                        actions.startGlassProductionLine(glassLine);
                            Boolean flag=true;
                            while (flag){
                                flag=false;
                            if ((glassMax.getBalance()<13) ){
                                flag=true;
                                int ay=glassMax.deposit();
                                //System.out.println("aaaaaaaaaaaayyyyyyy"+ay+" "+glassMax.getBalance());
                                produced++;
                                actions.produceGlass(glassLine);
                                assemblyLine.notifyComponentProduced(Component.GLASS);
                            }
                            }

                            actions.stopGlassProductionLine(glassLine);
                            break;



                }

                // Wait for the assembly line to signal
                synchronized (assemblyLine) {
                    assemblyLine.wait();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Production line " + component + " interrupted");
        }
    }
}