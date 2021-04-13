package com.badlogic.gdx.backends.lwjgl;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LwjglZipFileHandle extends FileHandle {
	 final ZipFile archive;
	 final ZipEntry archiveEntry;

	 public LwjglZipFileHandle (ZipFile archive, File file) {
		  super(file, Files.FileType.Classpath);
		  this.archive = archive;
		  archiveEntry = this.archive.getEntry(file.getPath());
	 }

	 public LwjglZipFileHandle (ZipFile archive, String fileName) {
		  super(fileName.replace('\\', '/'), FileType.Classpath);
		  this.archive = archive;
		  this.archiveEntry = archive.getEntry(fileName.replace('\\', '/'));
	 }

	 @Override
	 public FileHandle child (String name) {
		  name = name.replace('\\', '/');
		  if (file.getPath().length() == 0) return new LwjglZipFileHandle(archive, new File(name));
		  return new LwjglZipFileHandle(archive, new File(file, name));
	 }

	 @Override
	 public FileHandle sibling (String name) {
		  name = name.replace('\\', '/');
		  if (file.getPath().length() == 0) throw new GdxRuntimeException("Cannot get the sibling of the root.");
		  return new LwjglZipFileHandle(archive, new File(file.getParent(), name));
	 }

	 @Override
	 public FileHandle parent () {
		  File parent = file.getParentFile();
		  if (parent == null) {
				if (type == FileType.Absolute)
					 parent = new File("/");
				else
					 parent = new File("");
		  }
		  return new LwjglZipFileHandle(archive, parent);
	 }

	 @Override
	 public InputStream read () {
		  try {
				return archive.getInputStream(archiveEntry);
		  } catch (IOException e) {
				throw new GdxRuntimeException("File not found: " + file + " (Archive)");
		  }
	 }

	 @Override
	 public boolean exists() {
		  return archiveEntry != null;
	 }

	 @Override
	 public long length () {
		  return archiveEntry.getSize();
	 }

	 @Override
	 public long lastModified () {
		  return archiveEntry.getTime();
	 }
}
