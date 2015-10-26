package com.slyvronline.mc.objects;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;

/**
 * This is the most basic entity class.
 * This class represents anything that might be rendered to the screen, from menu items to
 * space ships, to the blocks that make up a space ship.
 * 
 * The posBox determines an entity's position on the screen as well as their rectangular dimensions
 * that are used in rendering.
 * The collisionBox determines the rectangular area that can collide with other Ents.
 * 
 * Color determines the shade that an entity may be rendered with.  Setting the color to white
 * makes it render in its normal colors.
 * 
 * Font and text determine whether this entity contains text data to be displayed on the screen.
 * Text is rendered through specified fonts and does not require a width or height, but should be managed
 * through new line characters (/n) when necessary
 * @author Matt Schrum - slyvr89
 * @date 10/25/2013
 */
public class Ent {

	private String name;
	private int id;
	private String type;
	private Rectangle collisionBox;
	private Rectangle posBox;
	private int centerX;
	private int centerY;
	private Font font;
	private String text;
	private Img img;
	private Color color;
	private float rotation;
	private int scaleX;
	private int scaleY;
	private float animSpeed;
	private long lifetime;
	private boolean display;
	private State state;
	private boolean rotate;
	private enum State {
		HOVER, CLICKED, JUMP, STAND, INJURED, DEAD
	}
	
	public Ent(){
		color = Color.WHITE;
		scaleX=1;
		scaleY=1;
		animSpeed=1;
		this.posBox = new Rectangle();
		this.collisionBox = new Rectangle();
		this.display = true;
	}
	
	public Ent(Map<String,String> entMap){
		this.collisionBox = new Rectangle();
		this.posBox = new Rectangle();
		for(String key : entMap.keySet()){
			if (key.contains("_name")){
				this.name = entMap.get(key);
			}
			if (key.contains("_id")){
				this.id = Integer.parseInt(entMap.get(key));
			}
			if (key.contains("_type")){
				this.type = entMap.get(key);
			}
			if (key.contains("_type")){
				this.type = entMap.get(key);
			}
			if (key.contains("_collisionboxX")){
				this.collisionBox.setX(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_collisionboxY")){
				this.collisionBox.setY(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_collisionboxWidth")){
				this.collisionBox.setWidth(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_collisionboxHeight")){
				this.collisionBox.setHeight(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_posboxX")){
				this.posBox.setX(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_posboxY")){
				this.posBox.setY(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_posboxWidth")){
				this.posBox.setWidth(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_posboxHeight")){
				this.posBox.setHeight(Float.parseFloat(entMap.get(key)));
			}
			if (key.contains("_centerX")){
				this.centerX = Integer.parseInt(entMap.get(key));
			}
			if (key.contains("_centerY")){
				this.centerY = Integer.parseInt(entMap.get(key));
			}
			if (key.contains("_font")){
				this.font = Aosa.getGlobal().getFontByName(entMap.get(key));
			}
			if (key.contains("_text")){
				this.text = entMap.get(key);
			}
			if (key.contains("_img")){
				this.img = Aosa.getGlobal().getImgByName(entMap.get(key));
			}
			if (key.contains("_color")){
				String colorString = entMap.get(key);
				if (colorString.contains(":")){
					String[] colorVals = colorString.split(":");
					float a = Float.parseFloat(colorVals[0]);
					float b = Float.parseFloat(colorVals[1]);
					float g = Float.parseFloat(colorVals[2]);
					float r = Float.parseFloat(colorVals[3]);
					Color color = new Color(r,g,b,a);
					this.color = color;
				}
				else this.color = Color.WHITE;
			}
			if (key.contains("_rotation")){
				this.rotation = Float.parseFloat(entMap.get(key));
			}
			if (key.contains("_scaleX")){
				this.scaleX = Integer.parseInt(entMap.get(key));
			}
			if (key.contains("_scaleY")){
				this.scaleY = Integer.parseInt(entMap.get(key));
			}
			if (key.contains("_animSpeed")){
				this.animSpeed = Float.parseFloat(entMap.get(key));
			}
		}
		this.display = true;
	}
	
	public void render(SpriteBatch batch){
		if (this.display){
			if (this.getColor()!=null){
				batch.setColor(this.getColor());
			}
			else{
				batch.setColor(Color.WHITE);
			}
			if (this.getImg()!=null){
				if (this.getImg().getTexRegs() != null){
					this.getImg().setCurrentFrame(this.getImg().getAnim().getKeyFrame(Aosa.getGlobal().getStateTime(), true));
					batch.draw(this.getImg().getCurrentFrame(),
							this.getPosBox().getX(),
							this.getPosBox().getY(),
							this.getPosBox().getWidth(),
							this.getPosBox().getHeight());
				}
				else if (this.getImg().getTex() != null){
					batch.draw(this.getImg().getTex(),
							this.getPosBox().getX(),
							this.getPosBox().getY(),
							this.getPosBox().getWidth(),
							this.getPosBox().getHeight());
				}
			}
			if (this.getFont()!=null){
				this.getFont().getFont().setColor(this.getColor());
				this.getFont().getFont().draw(batch, this.getText(), this.getPosBox().getX(), this.getPosBox().getY());
			}
		}
	}
	
	public void setX(float x){
		this.posBox.setX(x);
		this.collisionBox.setX(x-1);
	}
	public void setY(float y){
		this.posBox.setY(y);
		this.collisionBox.setY(y-1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Rectangle getCollisionBox() {
		return collisionBox;
	}
	public void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
	public Rectangle getPosBox() {
		return posBox;
	}
	public void setPosBox(Rectangle posBox) {
		this.posBox = posBox;
	}
	public int getCenterX() {
		return centerX;
	}
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Img getImg() {
		return img;
	}
	public void setImg(Img img) {
		this.img = img;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public int getScaleX() {
		return scaleX;
	}
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}
	public int getScaleY() {
		return scaleY;
	}
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}
	public float getAnimSpeed() {
		return animSpeed;
	}
	public void setAnimSpeed(float animSpeed) {
		this.animSpeed = animSpeed;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public long getLifetime() {
		return lifetime;
	}
	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public boolean isRotate() {
		return rotate;
	}
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}

	public Map<String,String> getMapData(String prefix){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put(prefix+"_name", this.getName());
		map.put(prefix+"_id", ""+this.getId());
		map.put(prefix+"_type", this.getType());
		if (this.getCollisionBox()!=null){
			map.put(prefix+"_collisionboxX", ""+this.getCollisionBox().getX());
			map.put(prefix+"_collisionboxY", ""+this.getCollisionBox().getY());
			map.put(prefix+"_collisionboxWidth", ""+this.getCollisionBox().getWidth());
			map.put(prefix+"_collisionboxHeight", ""+this.getCollisionBox().getHeight());
		}
		if(this.getPosBox()!=null){
			map.put(prefix+"_posboxX", ""+this.getPosBox().getX());
			map.put(prefix+"_posboxY", ""+this.getPosBox().getY());
			map.put(prefix+"_posboxWidth", ""+this.getPosBox().getWidth());
			map.put(prefix+"_posboxHeight", ""+this.getPosBox().getHeight());
		}
		map.put(prefix+"_centerX", ""+this.getCenterX());
		map.put(prefix+"_centerY", ""+this.getCenterY());
		if (this.getFont()!=null)
			map.put(prefix+"_font", this.getFont().getName());
		map.put(prefix+"_text",this.getText());
		if (this.getImg()!=null)
			map.put(prefix+"_img", this.getImg().getName());
		if (this.getColor()!=null)
			map.put(prefix+"_color", this.getColor().a+":"+this.getColor().b+":"+this.getColor().g+":"+this.getColor().r);
		map.put(prefix+"_rotation", ""+this.getRotation());
		map.put(prefix+"_scaleX", ""+this.getScaleX());
		map.put(prefix+"_scaleY", ""+this.getScaleY());
		map.put(prefix+"_animspeed", ""+this.getAnimSpeed());
		if (this.getState()!=null)
			map.put(prefix+"_state", this.getState().name());
		
		return map;
	}
	
	public void setValueByKey(String key, String value){
		if (key.contains("_name")){
			this.name = value;
		}
		if (key.contains("_id")){
			this.id = Integer.parseInt(value);
		}
		if (key.contains("_type")){
			this.type = value;
		}
		if (key.contains("_type")){
			this.type = value;
		}
		if (key.contains("_collisionboxX")){
			this.collisionBox.setX(Float.parseFloat(value));
		}
		if (key.contains("_collisionboxY")){
			this.collisionBox.setY(Float.parseFloat(value));
		}
		if (key.contains("_collisionboxWidth")){
			this.collisionBox.setWidth(Float.parseFloat(value));
		}
		if (key.contains("_collisionboxHeight")){
			this.collisionBox.setHeight(Float.parseFloat(value));
		}
		if (key.contains("_posboxX")){
			this.posBox.setX(Float.parseFloat(value));
		}
		if (key.contains("_posboxY")){
			this.posBox.setY(Float.parseFloat(value));
		}
		if (key.contains("_posboxWidth")){
			this.posBox.setWidth(Float.parseFloat(value));
		}
		if (key.contains("_posboxHeight")){
			this.posBox.setHeight(Float.parseFloat(value));
		}
		if (key.contains("_centerX")){
			this.centerX = Integer.parseInt(value);
		}
		if (key.contains("_centerY")){
			this.centerY = Integer.parseInt(value);
		}
		if (key.contains("_font")){
			this.font = Aosa.getGlobal().getFontByName(value);
		}
		if (key.contains("_text")){
			this.text = value;
		}
		if (key.contains("_img")){
			this.img = Aosa.getGlobal().getImgByName(value);
		}
		if (key.contains("_color")){
			String colorString = value;
			if (colorString.contains(":")){
				String[] colorVals = colorString.split(":");
				float a = Float.parseFloat(colorVals[0]);
				float b = Float.parseFloat(colorVals[1]);
				float g = Float.parseFloat(colorVals[2]);
				float r = Float.parseFloat(colorVals[3]);
				Color color = new Color(r,g,b,a);
				this.color = color;
			}
			else this.color = Color.WHITE;
		}
		if (key.contains("_rotation")){
			this.rotation = Float.parseFloat(value);
		}
		if (key.contains("_scaleX")){
			this.scaleX = Integer.parseInt(value);
		}
		if (key.contains("_scaleY")){
			this.scaleY = Integer.parseInt(value);
		}
		if (key.contains("_animSpeed")){
			this.animSpeed = Float.parseFloat(value);
		}
	}
}
