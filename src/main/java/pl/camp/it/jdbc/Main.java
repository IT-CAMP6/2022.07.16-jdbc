package pl.camp.it.jdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(23);
        list.add(345);
        list.add(3456);
        list.add(32);

        /*for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }*/

        /*for(int element : list) {
            if(element == 345) {
                list.remove(1);
            }
            System.out.println(element);
        }*/

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int element = iterator.next();
            if(element == 23) {
                iterator.remove();
            }
        }

        for(int element : list) {
            System.out.println(element);
        }
    }
}
