package org.aston.learning.stage1.sort;

import org.aston.learning.stage1.model.Bus;

import java.util.Comparator;

public class BusModelComparator implements Comparator<Bus> {

    @Override
    public int compare(Bus bus1, Bus bus2) {
        return bus1.getModel().compareToIgnoreCase(bus2.getModel());
    }
}
