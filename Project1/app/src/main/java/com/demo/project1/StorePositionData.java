package com.demo.project1;

import java.util.ArrayList;

public class StorePositionData {
    public ArrayList<Position> position = new ArrayList();

    private StorePositionData() {
    }

    public static StorePositionData getInstance() {
        return StorePositionDataHolder.sInstance;
    }

    private static class StorePositionDataHolder {
        private static final StorePositionData sInstance = new StorePositionData();
    }

    public static class Position {
        private int x = 0, y = 0;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
