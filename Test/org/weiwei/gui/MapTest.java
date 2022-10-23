package org.weiwei.gui;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapTest extends TestCase {
    private static Map map = new Map();

    @Test
    public void testUpdate() {
        map.update();
    }

    @Test
    public void testLiveOrDeath() {
        int num = 50;
        for(int i = 0; i < num; i++) {
            for (int j = 0; j < num; j ++) {
                map.setCells(i, j, false);
            }
        }
        map.setCells(0, 0, true);
        map.setCells(0, 1, true);
        map.setCells(0, 2, true);
        map.liveOrDeath();
        assertEquals(true, map.getCells(1, 1));
    }
}