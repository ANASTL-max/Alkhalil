package com.web.alkhalil.domain;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Instance>{

    public int compare(Instance b, Instance a) {
        return a.nbr<b.nbr ? -1 : a.nbr==b.nbr ? 0 : 1;
    }

}
