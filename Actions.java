import java.util.concurrent.ThreadLocalRandom;
import java.lang.Thread;

public class Actions{
    private static void action(int min_time,int max_time, String msg){
        assert min_time <= max_time;

        if(max_time>0){
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(min_time, max_time + 1));
            }catch(InterruptedException e){ }
        }

        System.out.println(msg);
    }

    static void startEngineProductionLine(){
        action(100,200,"Started the Engine Production Line");
    }
    static void stopEngineProductionLine(){
        action(100,200,"Stopped the Engine Production Line");
    }
    static void produceEngine(){
        action(300,3000,"Produced an Engine");
    }


    static void startWheelProductionLine(){
        action(100,200,"Started the Wheel Production Line");
    }
    static void stopWheelProductionLine(){
        action(100,200,"Stopped the Wheel Production Line");
    }
    static void produceWheel(){
        action(100,1000,"Produced a Wheel");
    }


    static void startGlassProductionLine(int l){
        action(100,200,"Started the Glass Production Line "+l);
    }
    static void stopGlassProductionLine(int l){
        action(100,200,"Stopped the Glass Production Line "+l);
    }
    static void produceGlass(int l){
        action(250,700,"Produced a Glass on Line "+ l);
    }


    static void startAssemblyLine(){
        action(100,200,"Started the Assembly Line");
    }
    static void stopAssemblyLine(){
        action(100,200,"Stopped the Assembly Line");
    }
    static void assemble(){
        action(2000,2500,"Assembled a Car");
    }


}
