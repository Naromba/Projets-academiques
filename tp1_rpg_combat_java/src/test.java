
public class test {
	 private static int a = 10;
	 private static int b = 15;
	 private static int c = 20;
	 public static void main(String[] args) {
		 test2();
	    }
	 private static void test1() {
		 Enemy e = new Enemy (a, b, c);
		 System.out.println( e.subirDesDegats(5));
		 System.out.println(a);
		 a++;
		 System.out.println(a);
	 }
	 private static void test2() {
		 double value  = 6.66;
		 int r = (int) Math.ceil(((double)20/ 3));
		 System.out.println(r);
	 }
}
