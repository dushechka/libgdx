package com.badlogic.gdx.files;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertTrue;

public class ZipFileHandleTest {
	 static final String ARCHIVE_PATH = "res/com/badlogic/gdx/files/test.zip";
	 static final String FILE1_PATH = "dir1/file1.txt";
	 static final String FILE2_PATH = "dir2/file2.txt";

	 File f;
	 ZipFile zf;
	 ZipFileHandle zfh;

	 @Before
	 public void prepare () {
	 	 try {
	 	 	 f = new File(ARCHIVE_PATH);
	 	 	 zf = new ZipFile(f);
		 } catch (Exception exc) {
	 	 	 exc.printStackTrace();
		 }
	 }

	 @Test
	 public void testExists () {
	 	 zfh = new ZipFileHandle(zf, FILE1_PATH);
	 	 assertTrue(zfh.exists());
	 	 zfh = new ZipFileHandle(zf, FILE2_PATH);
	 	 assertTrue(zfh.exists());
	 }
}
