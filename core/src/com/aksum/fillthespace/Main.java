package com.aksum.fillthespace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	public static final int SCR_HEIGHT = 1000,SCR_WIDTH = 1000;
	SpriteBatch batch;
	OrthographicCamera camera;

	Texture imgBoid;

	Array<Boid> boids = new Array<Boid>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,SCR_WIDTH,SCR_HEIGHT);

		loadResources();

		for(int i = 0;i < 1000;i++){
			boids.add(new Boid(SCR_WIDTH/2,SCR_HEIGHT/2));
//			boids.add(new Boid(MathUtils.random(0,SCR_WIDTH),MathUtils.random(0,SCR_HEIGHT)));
		}

	}

	public void loadResources(){
		imgBoid = new Texture("boid.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		for(int i = 0;i < boids.size;i++){
			boids.get(i).move();
		}

		checkOverlaps();

		for(int i = 0;i < boids.size;i++){
			batch.draw(imgBoid,boids.get(i).x - boids.get(i).radius,boids.get(i).y - boids.get(i).radius,2*boids.get(i).radius,2*boids.get(i).radius);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void checkOverlaps(){
		for(int i = 0;i < boids.size;i++){
			float max = 10000000;
			int index = -1;
			for(int j = 0;j < boids.size;j++){
				if(i == j || !boids.get(i).overlaps(boids.get(j))) continue;
				float len = (boids.get(i).x - boids.get(j).x)*(boids.get(i).x - boids.get(j).x) + (boids.get(i).y - boids.get(j).y)*(boids.get(i).y - boids.get(j).y);
				if(len < max){
					max = len;
					index = j;
				}
			}
			if(index == -1) continue;
			Boid b = boids.get(i);
			float lenX = (boids.get(i).x - boids.get(index).x)*(boids.get(i).x - boids.get(index).x);
			float lenY = (boids.get(i).y - boids.get(index).y)*(boids.get(i).y - boids.get(index).y);
			float a = (float) Math.toDegrees(Math.atan(lenY/lenX));
			if(boids.get(index).x < b.x){
				a = 180 - a;
			}
			if(boids.get(index).y < b.y){
				a = -a;
			}
			a = 180 + a;
			b.a = a;
			boids.set(i,b);
		}
	}
}
