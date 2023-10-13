package caso2_infracomp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pagination extends Thread {
    private ArrayList<Integer> tp = new ArrayList<Integer>(); //TP
    private int pageFaults;
    private Aging aging;
    private int nReferences;
    private BufferedReader br;
    private int frameCount;
    private int numsTP;
    
    public Pagination(int frameCount, String references)
    {
    	this.frameCount = frameCount;
    	pageFaults = 0;
    	nReferences = 1; //It should be at least 1 reference
    	aging = new Aging(frameCount);
    	aging.start();
    	try {
            this.br = new BufferedReader(new FileReader(references));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    
    /**
     * Reads a reference from the previously created file and handle page fault if necessary.
     */
    public void readReference() {
        try
        {
            String line;
            if ((line = br.readLine()) != null)
            	{
	            	if (line.startsWith("NR")) 
	                {
	                	nReferences = Integer.parseInt(line.split("=")[1]);
	                }
	            	
	            	if (line.startsWith("NP"))
	            	{
	            		int pageCount = Integer.parseInt(line.split("=")[1]);
	            		//No page is in real memory at the start
	            		 for (int i = 0; i < pageCount; i++) {
	            	            tp.add(-1);
	            	        }
	            		 numsTP = 0;
	            	}
	
	                if (line.startsWith("[A-") || line.startsWith("[B-") || line.startsWith("[C-")) { 
	                    String[] parts = line.split(",");
	                    int virtualPage = Integer.parseInt(parts[1]);
	                    
	                    // Search for the real memory location in the TP
	                    // If not there, generate a page fault
	                    if (!isInTP(virtualPage))
	                    {
	                    	pageFaults++;
	                    	//Handle page fault
	                    	handlePageFault(virtualPage);
	                    }
	                    // Notify the aging algorithm that the page was referenced
	                    int realPage = tp.get(virtualPage);
	                    aging.referencePage(realPage);
	                }
            	}
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns true if the virtual page is in the pages table
     * 
     * @param virtualPage  The virtual page to search in the pages table.
     * 
     * @return true if the virtual page is in the pages table or false otherwise.
     */
    public boolean isInTP(int virtualPage)
    {
        if(tp.get(virtualPage) == -1)
        {
        	return false;
        }
        return true;
    }
    
    
    /**
     * Handles a page fault by selecting a page to replace.
     *
     * @param virtualPage The virtual page that caused the fault
     */
    public void handlePageFault(int virtualPage)
    {
    	if (numsTP == frameCount)
    	{
    		int selectedPage = aging.getPageToChange();
        	// Remove the chosen page from real memory (this is only conceptual in this implementation)
        	// Move the required page from SWAP to real memory (this is only conceptual in this implementation)
    		int virtualIndexToRemoveFromTP = tp.indexOf(selectedPage);
    		tp.set(virtualIndexToRemoveFromTP, -1);
        	tp.set(virtualPage, selectedPage);// Update TP
    	}
    	else
    	{
    		int selectedPage = aging.getPageToChange();
        	// Remove the chosen page from real memory (this is only conceptual in this implementation)
        	// Move the required page from SWAP to real memory (this is only conceptual in this implementation)
    		if (tp.contains(selectedPage))
    		{
    			tp.set(tp.indexOf(selectedPage), -1);
    			numsTP--;
    		}
        	tp.set(virtualPage, selectedPage);// Update TP
        	numsTP++;
    	}
    	
    }
    
    @Override
    public void run()
    {
    	for (int n = 0; n < (nReferences+6); n++) 
    	{
    		readReference();
            try 
            {
                Thread.sleep(2); // Sleep 2 ms
            } catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
    	System.out.println("Fallas de pagina que generó el proceso: "+pageFaults);
    	System.out.println("El manejador de paginación dejó de correr");
    	aging.shutDown();
    }
}

