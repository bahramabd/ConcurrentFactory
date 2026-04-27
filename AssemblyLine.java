public class AssemblyLine implements Runnable{
    private final Actions actions;
    private int proGlass;
    private final int reqGlass;
    private int proWheel;
    private int compWheel;
    private final int reqWheel;
    private int proEngine;

    public AssemblyLine (Actions actions, int reqWheel, int reqGlass){
        this.actions=actions;
        this.reqGlass=reqGlass;
        this.reqWheel=reqWheel;
        this.proEngine=0 ;
        this.proGlass=0;
        this.proWheel=0;

    }

    public synchronized void notifyComponentProduced(Component component) {
        // Update component counts
        switch (component) {
            case WHEEL:
                proWheel++;
                compWheel++;
                break;
            case ENGINE:
                proEngine++;
                break;
            case GLASS:
                proGlass++;
                break;
        }

        // Check if all wheels are produced before notifying for glass production
        if (component == Component.WHEEL && compWheel == reqWheel) {
            this.notify();  // Wake up the assembly thread
            compWheel=0;
        }
        // Check if a car can be assembled
        else if (proWheel >= reqWheel && proEngine >= 1 && proGlass >= reqGlass) {
            this.notify();  // Wake up the assembly thread
        }
    }
    public synchronized void notif(){

        //if (proWheel >= reqWheel && proEngine >= 1 && proGlass >= reqGlass) {
            this.notify();  // Wake up the assembly thread
        //}
    }

    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    // Wait until all components are available
                    while (proWheel < reqWheel || proEngine < 1 || proGlass < reqGlass) {
                        this.wait();
                    }
                    proWheel = 0;
                    proEngine = 0;
                    proGlass = 0;
                }

                // Assemble the car
                actions.startAssemblyLine();
                actions.assemble();
                actions.stopAssemblyLine();

                // Notify production lines to produce more components
                synchronized (this) {  // Corrected this line
                    this.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Assembly line interrupted");
        }
    }
}

//public class AssemblyLine implements Runnable {
//    private final Actions actions;
//    private final int requiredWheels;
//    private final int requiredGlasses;
//    private int producedWheels;
//    private int producedEngines;
//    private int producedGlasses;
//
//    public AssemblyLine(Actions actions, int requiredWheels, int requiredGlasses) {
//        this.actions = actions;
//        this.requiredWheels = requiredWheels;
//        this.requiredGlasses = requiredGlasses;
//        this.producedWheels = 0;
//        this.producedEngines = 0;
//        this.producedGlasses = 0;
//    }
//    public synchronized void notifyComponentProduced(Component component) {
//        // Update component counts
//        switch (component) {
//            case WHEEL:
//                producedWheels++;
//                break;
//            case ENGINE:
//                producedEngines++;
//                break;
//            case GLASS:
//                producedGlasses++;
//                break;
//        }
//
//        // Check if a car can be assembled
//        if (producedWheels >= requiredWheels && producedEngines >= 1 && producedGlasses >= requiredGlasses) {
//            this.notify();  // Wake up the assembly thread
//        }
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                synchronized (this) {
//                    // Wait until all components are available
//                    while (producedWheels < requiredWheels || producedEngines < 1 || producedGlasses < requiredGlasses) {
//                        wait();
//                    }
//                    producedWheels = 0;
//                    producedEngines = 0;
//                    producedGlasses = 0;
//                }
//
//                // Assemble the car
//                actions.startAssemblyLine();
//                actions.assemble();
//                actions.stopAssemblyLine();
//
//                // Notify production lines to produce more components
//                synchronized (this) {  // Corrected this line
//                    this.notifyAll();
//                }
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            System.out.println("Assembly line interrupted");
//        }
//    }
//}