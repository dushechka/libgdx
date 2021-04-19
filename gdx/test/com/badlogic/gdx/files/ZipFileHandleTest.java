package com.badlogic.gdx.files;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ZipFileHandleTest {
	 static final String ARCHIVE_PATH = "res/com/badlogic/gdx/files/test.zip";
	 static final String FILE1_PATH = "files/dir1/file1.txt";
	 static final String FILES_DIR_PATH = "files";
	 static final String DIR1_PATH = "files/dir1";
	 static final String FILE2_NAME = "file2.txt";
	 static final String FILE1_TEXT = "This is the test textfile #1.";

	 File f;
	 ZipFile zf;
	 ZipFileHandle zfh;

	 @Before
	 public void prepare () throws IOException {
	 	 f = new File(ARCHIVE_PATH);
	 	 zf = new ZipFile(f);
	 }

	 @Test
	 public void testExists () {
	 	 zfh = new ZipFileHandle(zf, FILE1_PATH);
	 	 assertTrue(zfh.exists());
	 }

	 @Test
	 public void testChild () {
	 	 zfh = new ZipFileHandle(zf, DIR1_PATH);
	 	 FileHandle fh = zfh.child(FILE2_NAME);
	 	 assertTrue(fh.exists());
	 }

	 @Test
	 public void testSibling () {
	 	 zfh = new ZipFileHandle(zf, FILE1_PATH);
	 	 FileHandle fh = zfh.sibling(FILE2_NAME);
	 	 assertTrue(fh.exists());
	 }

	 @Test
	 public void testParent () {
	 	 zfh = new ZipFileHandle(zf, DIR1_PATH);
	 	 FileHandle fh = zfh.parent();
	 	 assertEquals(FILES_DIR_PATH, fh.path());
	 }

	 @Test
	 public void testRead() {
	 	 zfh = new ZipFileHandle(zf, FILE1_PATH);
	 	 try (InputStream is = zfh.read()) {
			  Scanner s = new Scanner(is);
			  s.useDelimiter("\n");
			  assertEquals(FILE1_TEXT, s.next());
			  s.close();
		 } catch (IOException exc) {
	 	 	 exc.printStackTrace();
		 }
	 }
}
