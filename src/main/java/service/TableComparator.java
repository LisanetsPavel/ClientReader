package service;

import java.util.Comparator;

/**
 * Created by pc8 on 31.07.15.
 */
public class TableComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        int first = 0;
        int second = 0;
        try {
            first = Integer.parseInt((String) o1);
        } catch (NumberFormatException e) {
            first = Integer.MAX_VALUE;
        }

        try {

            second = Integer.parseInt((String) o2);
        } catch (NumberFormatException e) {
            second = Integer.MAX_VALUE;
        }
        return first - second;
    }
}
