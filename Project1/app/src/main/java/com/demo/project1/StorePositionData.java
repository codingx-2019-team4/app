package com.demo.project1;

public class StorePositionData {
    private int x = 0, y = 0;

    private StorePositionData() {
    }

    public static StorePositionData getInstance() {
        return StorePositionDataHolder.sInstance;
    }

    private static class StorePositionDataHolder {
        private static final StorePositionData sInstance = new StorePositionData();
    }

    public void setPostion(int x, int y) {
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
