package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.ElementHandler;
import org.aston.learning.stage1.util.console.InputPattern;
import org.aston.learning.stage1.util.console.InputUtils;

public class BusHandler implements ElementHandler<Bus> {
    @Override
    public Bus createElementManually() {
        System.out.println("Создание автобуса:");
        String number = InputUtils.readRegex("Номер*: ", InputPattern.BUS_NUMBER, true);
        String model = InputUtils.readRegex("Модель*: ", InputPattern.BUS_MODEL, true);
        int mileage = InputUtils.readInt("Пробег: ", 0, 1000000);
        return new Bus(number, model, mileage);
    }

    @Override
    public String[] getTableHeaders() {
        return new String[]{"№", "Номер", "Модель", "Пробег"};
    }

    @Override
    public String[] convertToTableRow(Bus bus, int index) {
        return new String[]{
                String.valueOf(index + 1),
                bus.getNumber(),
                bus.getModel(),
                bus.getMileage() + " км"
        };
    }

    @Override
    public String getTypeName() {
        return "Bus";
    }
}