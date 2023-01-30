import java.util.*;
import java.io.*;
import java.io.File;
import java.io.PrintWriter;

class Main {
  public static void main(String[] args) throws IOException {
    File f = new File("Guinea-pig-phenotype-Null-Hypothesis.csv");
    Scanner in = new Scanner(f);
    Scanner sc = new Scanner(System.in);
    ArrayList < String > exceptedPoints = new ArrayList < > ();
    ArrayList < String > observePoints = new ArrayList < > ();

    while (in.hasNextLine()) {
      String line = in.nextLine();
      String[] data = line.split(",");
      exceptedPoints.add(data[1]);
      observePoints.add(data[2]);
    }
    //removing title
    exceptedPoints.remove(0);
    observePoints.remove(0);
    // converting ArryList<String> into double[]
    double[] epArr = arrListToarr(exceptedPoints);
    double[] opArr = arrListToarr(observePoints);
    //converting double[] into ArrayList<Double>
    ArrayList < Double > ep = new ArrayList < > ();
    ArrayList < Double > op = new ArrayList < > ();
    ep = arrToArrList(epArr);
    op = arrToArrList(opArr);

    //merged array with excepted data and observe data
    ArrayList < Double > merged = merge(ep, op);
    //Partition my merged ArrayList
    List < List < Double >> partitions = new LinkedList < List < Double >> ();
    partitions = partition(merged);
    //asking user for cut off point
    System.out.print("Enter Cut Off Point ");
    double cutOff = sc.nextDouble();
    System.out.println("Chi Function = " + chiCal(partitions));
    System.out.println("Cut Off Number = " + cutOff);
    if (chiCal(partitions) >= cutOff) {
      System.out.print("The Null Hypothesis Was Disproven");
    } else {
      System.out.print("The Null Hypothesis Was Proven");
    }

  }public static double[] arrListToarr(ArrayList < String > arrList) {
    double[] arr = new double[arrList.size()];
    for (int i = 0; i < arrList.size(); ++i) {
      arr[i] = Double.parseDouble(arrList.get(i));
    }
    return arr;
  }

  public static ArrayList < Double > arrToArrList(double[] arr) {
    ArrayList < Double > array_list = new ArrayList < Double > ();
    for (int i = 0; i < arr.length; i++) {
      array_list.add(arr[i]);
    }
    return array_list;
  }

  public static ArrayList < Double > merge(ArrayList < Double > ep, ArrayList < Double > op) {
    ArrayList < Double > merged = new ArrayList < Double > (ep.size() + op.size());
    int max = Math.max(ep.size(), op.size());
    for (int i = 0; i < max; i++) {
      merged.add(ep.get(i));
      merged.add(op.get(i));
    }
    return merged;
  }
  public static List < List < Double >> partition(ArrayList < Double > dp) {
    int partitionSize = 2;
    List < List < Double >> partitions = new LinkedList < List < Double >> ();
    for (int i = 0; i < dp.size(); i += partitionSize) {
      partitions.add(dp.subList(i, Math.min(i + partitionSize, dp.size())));
    }
    return partitions;
  }
  public static double calP(List < Double > list) { //cal singular ep and op
    return Math.pow((list.get(1) - list.get(0)), 2) / list.get(0);
  }
  public static double chiCal(List < List < Double >> arr) { // intakes the amount of indexs in paratition and calulates the chi of that indiviual ep and op out come. 
    double chiNum = 0;
    for (int i = 0; i < arr.size(); i++) {
      chiNum += calP(arr.get(i)); // adds all outcomes chi functions together
    }
    return chiNum;
  }
}
