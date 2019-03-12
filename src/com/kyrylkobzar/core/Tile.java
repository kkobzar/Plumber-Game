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
            case TWISTED3: this.setState(PipeState.TWISTED);break;
            case TWISTED: this.setState(PipeState.TWISTED1);break;
            case TWISTED1: this.setState(PipeState.TWISTED2);break;
            case TWISTED2: this.setState(PipeState.TWISTED3);break;
            case THREEPLE3: this.setState(PipeState.THREEPLE);break;
            case THREEPLE: this.setState(PipeState.THREEPLE1);break;
            case THREEPLE1: this.setState(PipeState.THREEPLE2);break;
            case THREEPLE2: this.setState(PipeState.THREEPLE3);break;
            case SIMPLE1: this.setState(PipeState.SIMPLE); break;
            case SIMPLE: this.setState(PipeState.SIMPLE1); break;
            case CROSED: break;
        }
    }
}
