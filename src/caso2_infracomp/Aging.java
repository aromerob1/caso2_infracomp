package caso2_infracomp;

public class Aging extends Thread {
    private final int FRAME_COUNT;
    private int[] counters;
    private volatile boolean running = true;

    public Aging(int frameCount) {
        this.FRAME_COUNT = frameCount;
        this.counters = new int[FRAME_COUNT];
    }

    public void shutDown() {
        running = false;
    }

    /**
     * Shifts all the counters 1 bit to the right
     */
    private void tick() {
        synchronized (counters) {
            for (int i = 0; i < FRAME_COUNT; i++) {
                counters[i] >>>= 1; // Shift the register to the right
                //System.out.println("Se desplazó el contador de la página " + i + " 1 bit hacia la derecha  |  Nuevo contador: " + counters[i]);
            }
        }
    }

    
    /**
     * References a page, indicating that it has been recently used.
     *
     * @param pageIndex The index of the page being referenced
     */
    public void referencePage(int pageIndex) {
        synchronized (counters) {
            counters[pageIndex] |= (1 << 7);
            //System.out.println("Se referenció la pagina " + pageIndex + " | Nuevo counter: " + counters[pageIndex]);
        }
    }

    
    /**
     * Selects a page to be replaced based on the aging algorithm
     *
     * @return The index of the least referenced page
     */
    public int getPageToChange() {
        synchronized (counters) {
            int oldestPageIndex = 0;
            for (int i = 1; i < FRAME_COUNT; i++) {
                if (counters[i] < counters[oldestPageIndex]) {
                    oldestPageIndex = i;
                }
            }
            return oldestPageIndex;
        }
    }
    
    @Override
    public void run() {
        while (running) {
            tick();
            
            try {
                Thread.sleep(1); // Duerme 1 milisegundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("El algoritmo de envejecimiento dejó de correr");
    	System.out.println("El programa terminó");
    }

}


