public class main {
   public static void main(String argv[]) {
      //System.out.println(System.getProperty("user.dir"));
       //System.loadLibrary("nicslu_util");
       System.load("/home/coglac/Documents/nicslu/NICSLU_UTIL/nicslu_util.so");
       long jarg2,jarg3,jarg4,jarg5,jarg6;
       jarg2=jarg3=jarg4=jarg5=jarg5=jarg6=10;
       int res=10;
       res=nicslu_util.NicsLU_ReadTripletColumnToSparse("/home/coglac/Documents/nicslu/NICSLU_UTIL/ASIC_100k.mtx", jarg2, jarg3, jarg4, jarg5, jarg6);
       System.out.println("It worked!!");
       System.out.println(res);
       System.out.println(jarg2);
     //System.out.println(example.getMy_variable());
     //System.out.println(example.fact(5));
     //System.out.println(example.get_time());
   }
 }
