package com.badlogic.gdx.files;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;

import java.util.zip.ZipFile;

public class ZipFileHandleResolver implements FileHandleResolver {
	 private final ZipFile archive;

	 public ZipFileHandleResolver (ZipFile archive) {
		  this.archive = archive;
	 }

	 @Override
	 public FileHandle resolve (String fileName) {
		  return new ZipFileHandle(archive, fileName);
	 }
}
