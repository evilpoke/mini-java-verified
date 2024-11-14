package testingstuff;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("StatementWithEmptyBody")
public class ObjectOrientated {


    public static void main(String[] args){

        int[][] something= new int[100][100];
        int[][] extract_of_something = new int[100][90];
        for(int i = 0, d = 5; i < something.length && d < something[0].length-5; i++, d++){
            extract_of_something[i][d] = something[i][d];

        }
        System.out.println(Arrays.stream(extract_of_something).collect(Collectors.toList()));



    }
}


