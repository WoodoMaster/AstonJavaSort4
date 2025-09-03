package org.aston.learning.stage1.menu;

@FunctionalInterface
public interface CollectionAction extends MenuAction {
    void execute(CollectionManager<?> manager);

    // Дефолтная реализация для совместимости с MenuAction
    @Override
    default void execute() {
        throw new UnsupportedOperationException("Use execute(CollectionManager) instead");
    }
}