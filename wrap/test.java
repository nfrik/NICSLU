import java.lang.Integer;
import com.nicslu.jni.*;

public class test {
  static {
    System.loadLibrary("nicslu");
  }

  //private static double [ ] Ax = {2., 3., 3., -1., 4., 4., -3., 1., 2., 2., 6., 1.} ;

  public static void main(String[] args) {
    //For test file inputs change n, nnz (look in file) and file name
    int n=99340, nnz=954163;
    String f="ASIC_100k.mtx";

    uintArray ap=new uintArray(n+1);
    uintArray ai=new uintArray(nnz);
    doubleArray ax=new doubleArray(nnz);
    doubleArray b=new doubleArray(n);
    doubleArray x=new doubleArray(n+n);
    uintArray N=new uintArray(1);
    uintArray NNZ=new uintArray(1);
    doubleArray err=new doubleArray(1);

    //For checking from java, arrays are set like this
    //for (int i=0;i<12;i++) ax.setitem(i, Ax[i]);

    N.setitem(0, n);
    NNZ.setitem(0, nnz);

    int ret=nicslu.readFromFile(f, N.cast(), NNZ.cast(), ai.cast(), ap.cast(), ax.cast(), x.cast(), b.cast());
    n=N.getitem(0);
    nnz=NNZ.getitem(0);

    SNicsLU nics =  new SNicsLU();
    ret= nicslu.NicsLU_Initialize(nics);

    ret=nicslu.NicsLU_CreateMatrix(nics, n, nnz, ax.cast(), ai.cast(), ap.cast());
    doubleArray cfgf=doubleArray.frompointer(nics.getCfgf());
    cfgf.setitem(0, 1.0);
    nics.setCfgf(cfgf.cast());

    nicslu.NicsLU_Analyze(nics);
    doubleArray stat=doubleArray.frompointer(nics.getStat());
    System.out.printf("Analysis time: %.8g\n", stat.getitem(0));

    ret=nicslu.NicsLU_CreateScheduler(nics);
    System.out.printf("Time of creating scheduler: %.8g\n", stat.getitem(4));
    System.out.printf("Suggestion: %s.\n", ret==0?"parallel":"sequential");

    uintArray cfgi=uintArray.frompointer(nics.getCfgi());

    nicslu.NicsLU_CreateThreads(nics, Integer.parseInt(args[0]), true);
    System.out.printf("Total cores: %d, threads created: %d\n", (int)stat.getitem(9), (int)cfgi.getitem(5));

    nicslu.NicsLU_BindThreads(nics, false);

    nicslu.NicsLU_Factorize_MT(nics);
    System.out.printf("Factorization time: %.8g\n", stat.getitem(1));

    nicslu.NicsLU_ReFactorize_MT(nics, ax.cast());
    System.out.printf("Re-factorization time: %.8g\n", stat.getitem(2));

    nicslu.NicsLU_Solve(nics, x.cast());
    System.out.printf("Substitution time: %.8g\n", stat.getitem(3));

    ret=nicslu.NicsLU_Residual(n, ax.cast(), ai.cast(), ap.cast(), x.cast(), b.cast(), err.cast(), 1, 0);
    System.out.printf("Ax-b (1-norm): %.8g\n", err.getitem(0));

    nicslu.NicsLU_Residual(n, ax.cast(), ai.cast(), ap.cast(), x.cast(), b.cast(), err.cast(), 2, 0);
    System.out.printf("Ax-b (2-norm): %.8g\n", err.getitem(0));

    nicslu.NicsLU_Residual(n, ax.cast(), ai.cast(), ap.cast(), x.cast(), b.cast(), err.cast(), 0, 0);
  	System.out.printf("Ax-b (infinite-norm): %.8g\n", err.getitem(0));

  	System.out.printf("NNZ(L+U-I): %d\n", (long)nics.getLu_nnz());
    nicslu.NicsLU_Flops(nics, null);
    nicslu.NicsLU_Throughput(nics, null);
    nicslu.NicsLU_ConditionNumber(nics, null);
    System.out.printf("Flops: %.8g\n", stat.getitem(5));
  	System.out.printf("Throughput (bytes): %.8g\n", stat.getitem(12));
  	System.out.printf("Condition number: %.8g\n", stat.getitem(6));
  	nicslu.NicsLU_MemoryUsage(nics, null);
  	System.out.printf("memory (Mbytes): %.8g\n", stat.getitem(21)/1024./1024.);

  }
}
