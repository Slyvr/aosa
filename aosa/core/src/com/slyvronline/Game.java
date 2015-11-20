package com.slyvronline;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.slyvronline.game.TiledObjectUtil;

public class Game extends ApplicationAdapter{
	public static final String TITLE = "Aosa";
	
	private boolean DEBUG = false;
	private final float SCALE = 2.0f;
	public final static float PPM = 30.0f;
	
	private OrthographicCamera camera;
	
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	
	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player;
	
	private SpriteBatch batch;
	private Texture tex;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / SCALE, h / SCALE);
		
		world = new World(new Vector2(0f, -9.8f), false);
		b2dr = new Box2DDebugRenderer();
		
		player = createBox(40,140,32,32,false);
		createBox(40,129,64,32,true);
		
		batch = new SpriteBatch();
		tex = new Texture("data/characters/grunt.png");
		
		map = new TmxMapLoader().load("data/tilemaps/world1.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);
		
		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		
		//Render
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tmr.render();
		
		b2dr.render(world, camera.combined.scl(PPM));
		
		batch.begin();
		batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth() / 2), player.getPosition().y * PPM - (tex.getHeight() / 2));
		batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}
	
	@Override
	public void resize(int w, int h) {
		camera.setToOrtho(false, w / SCALE, h / SCALE);
	}
	
	@Override
	public void dispose(){
		world.dispose();
		b2dr.dispose();
		batch.dispose();
		tex.dispose();
		tmr.dispose();
		map.dispose();
	}
	
	public void update(float delta){
		world.step(1 / 60f,  6,  2);
		
		inputUpdate(delta);
		cameraUpdate(delta);
		tmr.setView(camera);
		batch.setProjectionMatrix(camera.combined);
	}
	
	public void inputUpdate(float delat){
		int horizontalForce = 0;
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce += 1;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			player.applyForceToCenter(0, 300, false);
		}
		
		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
	}
	
	public void cameraUpdate(float delta){
		Vector3 position = camera.position;
		position.x = player.getPosition().x * PPM;
		position.y = player.getPosition().y * PPM;
		camera.position.set(position);
		
		camera.update();
	}
	
	public Body createBox(int x, int y, int width, int height, boolean isStatic){
		Body pBody;
		BodyDef def = new BodyDef();
		
		if (isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;
		
		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
		
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		return pBody;
	}
	
	public void pause() {}
	public void resume() {}
}
