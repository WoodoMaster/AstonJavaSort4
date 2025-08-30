package org.aston.learning.stage1.menu;

import java.util.*;

import org.aston.learning.stage1.util.ConsoleColor;
import org.aston.learning.stage1.util.ConsoleUtils;
import org.aston.learning.stage1.util.InputUtils;

public class Menu {
    private final String title;
    private final Map<String, MenuAction> items;
    private final String path;
    private boolean isRunning;

    private static final String EXIT_TEXT = "ВЫХОД";
    private static final String BACK_TEXT = "НАЗАД";

    public Menu(String title) {
        this.title = this.path = title;
        this.items = new LinkedHashMap<>();
        addExitAction();
    }

    public Menu(String title, String parentTitle) {
        this.title = title;
        this.path = parentTitle + " > " + title;
        this.items = new LinkedHashMap<>();
        addBackAction();
    }

    /* ================== GETTERS ================== */

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public Map<String, MenuAction> getItems() {
        return items;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isSubMenu() {
        return !title.equals(path);
    }

    /* ================== BINDINGS ================== */

    public void addAction(String title, MenuAction action) {
        items.put(title, action);
    }

    private void addBackAction() {
        addAction(BACK_TEXT, this::close);
    }

    private void addExitAction() {
        addAction(EXIT_TEXT, ConsoleUtils::exit);
    }

    /* ================== CONTROL ================== */

    public void open() {
        isRunning = true;

        while (isRunning) {
            displayHeader("");
            displayActions();
            int choice = getChoiceIndex();
            executeChoice(choice);
        }
    }

    public void close() {
        isRunning = false;
    }

    private int getChoiceIndex() {
        try {
            return InputUtils.readInt("\nВведите номер действия: ", 0, items.size() - 1);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void executeChoice(int choiceIndex) {
        if (choiceIndex == 0) {
            if (isSubMenu()) {
                items.get(BACK_TEXT).execute();
                return;
            }
            items.get(EXIT_TEXT).execute();
            return;
        }

        int currentItemIndex = 0;
        for (MenuAction item : items.values()) {
            if (currentItemIndex == choiceIndex) {
                item.execute();
                break;
            }
            currentItemIndex++;
        }
    }

    /* ================== DISPLAY ================== */

    public void displayHeader(String suffixPath) {
        String currentCollectionText = "    Текущая коллекция: " + CurrentCollectionManager.getCurrentName();
        String pathText = !suffixPath.isEmpty() ? ("    " + path + " > " + suffixPath) : "    " + path;
        int maxHeaderLength = Math.max(currentCollectionText.length(), pathText.length());

        System.out.println(ConsoleColor.CYAN + "═".repeat(maxHeaderLength + 4));
        System.out.println(currentCollectionText + "\n" + pathText);
        System.out.println("═".repeat(maxHeaderLength + 4) + ConsoleColor.RESET);
    }

    private void displayActions() {
        int i = 1;
        for (String itemName : items.keySet()) {
            if (itemName.equals(BACK_TEXT) || itemName.equals(EXIT_TEXT)) {
                continue;
            }

            System.out.println(" " + i + ". " + itemName);
            i++;
        }

        System.out.println(" 0. " + (isSubMenu() ? BACK_TEXT : EXIT_TEXT));
    }
}