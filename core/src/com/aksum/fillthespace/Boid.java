package com.aksum.fillthespace;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class Boid {
    float x,y;
    float radius;
    float ratioRadius;
    float v,a;

    Boid(float x, float y){
        this.x = x;
        this.y = y;

        radius = 3;

        ratioRadius = 45;

        v = MathUtils.random(2,5);
        a = MathUtils.random(0,360);

    }

    void move(){

        if(x <= 0 || x > Main.SCR_WIDTH - radius) a = 180 - a;
        if(y <= 0 || y > Main.SCR_HEIGHT - radius) a = -a;

        x += v*MathUtils.cosDeg(a);
        y += v*MathUtils.sinDeg(a);
    }

    boolean overlaps(Boid o){
        return ((x-o.x)*(x-o.x) + (y-o.y)*(y-o.y) <= ratioRadius*ratioRadius);
    }

}
