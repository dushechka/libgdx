package com.badlogic.gdx.tests.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.ZipFileHandleResolver;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.tests.utils.OrthoCamController;

import java.io.IOException;
import java.util.zip.ZipFile;

public class ZipFileHandleResolverTest extends GdxTest {

	 private TiledMap map;
	 private TiledMapRenderer renderer;
	 private OrthographicCamera camera;
	 private OrthoCamController cameraController;
	 private AssetManager assetManager;
	 private BitmapFont font;
	 private SpriteBatch batch;

	 @Override
	 public void create() {
		  float w = Gdx.graphics.getWidth();
		  float h = Gdx.graphics.getHeight();

		  camera = new OrthographicCamera();
		  camera.setToOrtho(false, (w / h) * 10, 10);
		  camera.zoom = 2;
		  camera.update();

		  cameraController = new OrthoCamController(camera);
		  Gdx.input.setInputProcessor(cameraController);

		  font = new BitmapFont();
		  batch = new SpriteBatch();

		  try {
				ZipFile archive = new ZipFile(Gdx.files.internal("tres/test.zip").file());
				ZipFileHandleResolver resolver = new ZipFileHandleResolver(archive);
				assetManager = new AssetManager(resolver);
				assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
				assetManager.load("tmx/level1.tmx", TiledMap.class);
				assetManager.finishLoading();
				map = assetManager.get("tmx/level1.tmx");
				renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	 }

	 @Override
	 public void render() {
		  Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		  Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		  camera.update();
		  renderer.setView(camera);
		  renderer.render();
		  batch.begin();
		  font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		  batch.end();
	 }

//	 @Override
//	 public boolean needsGL20 () {
//		  return true;
//	 }
}
