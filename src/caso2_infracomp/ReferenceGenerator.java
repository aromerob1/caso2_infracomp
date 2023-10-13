package caso2_infracomp;

import java.io.IOException;
import java.io.PrintWriter;

public class ReferenceGenerator {
	
	public ReferenceGenerator(){}
	
	
	/**
     * Generates references and writes them into a file.
     *
     * @param tp       Page size
     * @param nf1      Number of rows in matrix 1
     * @param nc1      Number of columns in matrix 1 and rows in matrix 2
     * @param nc2      Number of columns in matrix 2
     * @param fileName The name of the file where references will be written
     */
	public void generateReferences(int tp, int nf1, int nc1, int nc2, String archivo)
	{
		try (PrintWriter writer = new PrintWriter(archivo + ".txt")) {
			int bytesA = (nf1*nc1)*4;
			int bytesB = (nc1*nc2)*4;
			int bytesC = (nf1*nc2)*4;
			int nReferences = nf1*nc1*nc2+nc1*nc2*nf1+nf1*nc2;
			int nPagesA = (bytesA + tp - 1) / tp;
			int nPagesB = (bytesB + tp - 1) / tp;
			int nPagesC = (bytesC + tp - 1) / tp;
			
		    writer.println("TP=" + Integer.toString(tp));
		    writer.println("NF=" + Integer.toString(nf1));
		    writer.println("NC1=" + Integer.toString(nc1));
		    writer.println("NC2=" + Integer.toString(nc2));
		    writer.println("NR=" + Integer.toString(nReferences));
		    writer.println("NP=" + Integer.toString(nPagesA+nPagesB+nPagesC));
		    
		    for (int i = 0; i < nf1; i++) {
	            for (int j = 0; j < nc2; j++) {
	                for (int k = 0; k < nc1; k++) {
	                    int bytePosA = (i * nc1 + k) * 4;
	                    int pageA = bytePosA / tp;
	                    int offsetA = bytePosA % tp;
	                    writer.println("[A-" + i + "-" + k + "]," + pageA + "," + offsetA);

	                    int bytePosB = (k * nc2 + j) * 4;
	                    int pageB = bytePosB / tp + nPagesA;
	                    int offsetB = bytePosB % tp;
	                    writer.println("[B-" + k + "-" + j + "]," + pageB + "," + offsetB);
	                }
	                int bytePosC = (i * nc2 + j) * 4;
	                int pageC = bytePosC / tp + nPagesA + nPagesB;
	                int offsetC = bytePosC % tp;
	                writer.println("[C-" + i + "-" + j + "]," + pageC + "," + offsetC);
	            }
	        }
		    System.out.println("Archivo de referencias creado exitosamente");
		} catch (IOException e) {
		    System.out.println("Ocurrio un error creando el archivo de referencias");
		    e.printStackTrace();
		}

	}
}
