package dev.toxi.inventory.pages;

import dev.toxi.inventory.OrcItem;

/**
 * @author Butzlabben
 * @since 21.05.2018
 */
public interface ItemConverter<T> {

    OrcItem convert(T element);

}
