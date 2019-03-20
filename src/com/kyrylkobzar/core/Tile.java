package com.kyrylkobzar.core;

public class Tile {
    private PipeState state;
    private boolean haveWater = false;

    public PipeState getState() {
        return state;
    }

    public void setState(PipeState state) {
        this.state = state;
    }
    public void setHaveWater(boolean b){this.haveWater = b;}

    public boolean getHaveWater() { return haveWater; }

    public void rotate(){
        switch (state){
            case TWISTED3: this.state = PipeState.TWISTED; break;
            case TWISTED: this.state =PipeState.TWISTED1;break;
            case TWISTED1: this.state = PipeState.TWISTED2;break;
            case TWISTED2: this.state = PipeState.TWISTED3;break;
            case THREEPLE3: this.state = PipeState.THREEPLE;break;
            case THREEPLE: this.state = PipeState.THREEPLE1;break;
            case THREEPLE1: this.state = PipeState.THREEPLE2;break;
            case THREEPLE2: this.state = PipeState.THREEPLE3;break;
            case SIMPLE1: this.state = PipeState.SIMPLE; break;
            case SIMPLE: this.state = PipeState.SIMPLE1; break;
            case CROSED: break;
        }
    }
}
