import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.html.HTMLDocument.Iterator;

public class ParticleChamberTest {
    //keeps track of time and moves
    int numMoves = 0;
    //stores locations
    List<ArrayList<Integer>> particlePositionIntCache = new ArrayList<ArrayList<Integer>>();
    List<Particle> particles = new ArrayList<Particle>();
    int size;

    static Logger log = Logger.getLogger("dev.blake.portfolio.particlechamber");

    public ParticleChamberTest (int inSize){
        size = inSize;
    }
    void setSize(int inSize){
        size = inSize;
    }

    public String[] animateParticles(int speed, String init){
        //initializes chamber with particles in starting position
        loadChamber(speed, init);   
        log.info(toString());
        while(!isEmpty()){
            moveParticles();
            log.info(toString());   
        }
        //obtain a grid of particle movement
        return getDisplayGridIntArray(particlePositionIntCache, size);
        
    }

    public void loadChamber(int speed, String init){
        char[] initChars = init.toCharArray();
        particlePositionIntCache.clear();
        numMoves=0;
        setSize(initChars.length);
        particlePositionIntCache.add(new ArrayList<Integer>(size));
        for(int loc = 0; loc <  size; loc++){
            if( initChars [loc]=='L' | initChars [loc]=='R'){
                Particle p =  new Particle(initChars[loc], loc, speed); 
                particles.add(p);
                particlePositionIntCache.get(numMoves).add(p.location);
            }
        }
        numMoves++;
    }

    public void moveParticles(){
        particlePositionIntCache.add(new ArrayList<Integer>(size));
        ListIterator<Particle> it = this.particles.listIterator();
        while(it.hasNext()){
            Particle p = it.next();
            p.move();
            if(p.done){
                //remove particle from chamber if done
                it.remove();
            }else{
                //cache particle location if not in cache
                if(!particlePositionIntCache.get(numMoves).contains(p.location))
                    particlePositionIntCache.get(numMoves).add(p.location);
            }
        } 
        numMoves++;
    }

    public boolean isEmpty(){
        return particles.isEmpty();
    }

    class Particle  {
        char direction;
        int location;
        int speed;
        boolean done= false;

        public Particle(char dir, int loc, int sp){
            direction = dir;
            location = loc;
            speed = sp;
        }

        void move(){
            if(direction=='L')
                location = location - speed;
            else if(direction=='R'){
                location = location + speed;
            } 
            if(location < 0){
                done=true;
            }
            if(location>=ParticleChamberTest.this.size){
                done=true;
            }
        } 
        public String toString(){
            return "Particle: " + " location="+location + " direction="+direction + " speed="+ speed + " done="+done;
        }
    }
    // Utility methods
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("chamber size="+ size);
        for(Particle p: particles){
            sb.append(" [  " + p.toString() + " ]");
        }
        return sb.toString();
    }

    //This converts display grid record from int (location) to X,.
    static String[] getDisplayGridIntArray(List<ArrayList<Integer>> particlePosCache, int chamberSize){
        String[] particleLevelsArr = new String[particlePosCache.size()];
        int intCounter=0;
        for(int i=0; i < particleLevelsArr.length; i++){
            StringBuffer sb = new StringBuffer();
            for(int j=0; j < chamberSize; j++){
                if(particlePosCache.get(i).contains(j)){
                    sb.append("X");
                    intCounter++;
                }else{
                    sb.append(".");
                }
            }
            particleLevelsArr[i] = sb.toString();
        }
        System.out.println("cache numInts: "+intCounter);
        return particleLevelsArr;
    }

    static void printDisplayGridArray(String[] arr){
        for(String s: arr){
            System.out.println(s);
        }
        System.out.println("");
    }
    //main
    public static void main(String[] args) {
        log.setLevel(Level.OFF);

        System.out.println("0: ..R......");
        log.info("0-..R......");
        String init="..R......";
        int speed = 2;

        ParticleChamberTest chamber = new ParticleChamberTest(init.length()); 
        String[] sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr);

        System.out.println("1-RR..LL.LRL..");
        log.info("1-RR..LL.LRL");
        init="RR..LL.LRL";
        speed = 6;  
        sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr);

        System.out.println("2-LRLR.LRLR.LRLR.LRLR");
        log.info("2-LRLR.LRLR.LRLR.LRLR");
        init="LRLR.LRLR.LRLR.LRLR";
        speed=2;
        sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr); 

        System.out.println("3-RLRLRLRLRLRRR");
        log.info("3-RLRLRLRLRLRRR");
        speed=10;
        init="RLRLRLRLRLRRR";
        sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr);  

        System.out.println("4-...");
        log.info("4-...");
        init="...";
        speed=1; 
        sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr); 

        System.out.println("5-LRRL.LR.LRR.R.LRRL.LLL");
        log.info("5-LRRL.LR.LRR.R.LRRL.LLL");
        init="LRRL.LR.LRR.R.LRRL.LLL";
        speed=9;  
        sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr);


        System.out.println("6-LRRL.LR.LRR.R.LRRL.LLLRRRRRL...LLLLRRLL....LRR.RRLR.LRRL.LR.LRR.R.LRRL.LLLRRRRRL...LLL");
        log.info("6-LRRL.LR.LRR.R.LRRL.LLLRRRRRL...LLLLRRLL....LRR.RRLR.LRRL.LR.LRR.R.LRRL.LLLRRRRRL...LLL");
        init="LRRLL....LRR.RRLR.LRRL.LR.LRR.R.LRRL.LLLRRRRRL...LLLLRRLL....LRR.RRLR.LRRL.LR.LRR.R.LRRL.LLLRRRRRL...LLL";
        speed=2;  
        sArr = chamber.animateParticles(speed, init);      
        printDisplayGridArray(sArr);
    }
}
