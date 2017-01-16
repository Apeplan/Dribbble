package com.simon.agiledevelop.recycler.entity;

import java.util.List;

/**
 * describe: implement the interface if the item is expandable
 * Created by luoxw on 2016/8/8.
 *
 * @author Simon Han
 * @date 2015.09.18
 * @email hanzx1024@gmail.com
 */

public interface IExpandable<T> {
    boolean isExpanded();

    void setExpanded(boolean expanded);

    List<T> getSubItems();

    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    int getLevel();
}
