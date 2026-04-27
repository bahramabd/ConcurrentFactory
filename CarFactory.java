public class CarFactory{
    public static void main(String[] args) {


              Actions actions = new Actions();
              AssemblyLine assemblyLine = new AssemblyLine(actions, 4, 13);
                GlassMax glassMax=new GlassMax();
              Thread assemblyLineThread = new Thread(assemblyLine);

              assemblyLineThread.start();
              Thread ent = new Thread(new ProductionLine(Component.ENGINE, actions, assemblyLine,2,glassMax));

              Thread wht = new Thread(new ProductionLine(Component.WHEEL, actions, assemblyLine,2,glassMax));


              ent.start();
              wht.start();
              for (int i=1;i<6;i++){
                  Thread glt = new Thread(new ProductionLine(Component.GLASS, actions, assemblyLine,i,glassMax));
                  glt.start();
              }




    }
}
