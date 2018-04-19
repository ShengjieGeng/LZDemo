package com.melon.szlz.lzdemo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.melon.szlz.lzdemo.MyApplication;
import com.melon.szlz.lzdemo.beans.Book;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;

public class FileUtil {
	/**
	 * 从本地读取到应用，集合存储；
	 *
	 * @param bookInfo 书本名称（book1.json）
	 */
	private static String TAG = "LLLL";
	private static String baseBookUri = Environment.getExternalStorageDirectory() + File.separator + "lzjy" + File.separator + "books";

	public static boolean deleteDirOrFile(String Path) {
		return deleteDirOrFile(new File(Path));
	}

	/***
	 * 删除文件方法
	 *
	 * @param dir
	 * @return
	 */
	public static boolean deleteDirOrFile(File dir) {
		if (!dir.exists()) {
			return true;
		}
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirOrFile(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void getBookNerve(final String bookInfo) {
		ThreadPoolManager.getPriorityExecutor().execute(new Runnable() {
			private InputStreamReader isr;
			private BufferedReader br;
			private FileInputStream inputStream;
			@Override
			public void run() {
				String path = baseBookUri + File.separator + bookInfo;
				File file = new File(path);
				long startTime = System.currentTimeMillis();
				if (!file.exists()) {
					Log.d(TAG, "run: 书本不存在 path = " + path);
					return ;
				} else {
					try {
						inputStream = new FileInputStream(file);
						isr = new InputStreamReader(inputStream);
						br = new BufferedReader(isr);
						StringBuilder sb = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null) {
							if (!TextUtils.isEmpty(line)) {
								sb.append(line);
							}
						}
						String bookNerve = sb.toString();
						Book book = new Gson().fromJson(bookNerve, Book.class);
						MyApplication.instance.setBook(book);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}finally {
						try {
							inputStream.close();
							isr.close();
							br.close();
							inputStream = null;
							isr = null;
							br = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				long endTime = System.currentTimeMillis();
				Log.d(TAG, "run: 耗时"+(endTime-startTime)+"ms");
			}
		});

	}

	public static void writeBookNerve(String bookInfo) {

	}

	public static boolean upZipFileDir(File zipFile, String folderPath) {
    	long startTime = System.currentTimeMillis();
		ZipFile zfile= null;
		try {
			//转码为GBK格式，支持中文
			zfile = new ZipFile(zipFile,"GBK");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		Enumeration zList=zfile.getEntries();
		ZipEntry ze=null;
		byte[] buf=new byte[1024];
		while(zList.hasMoreElements()){
			ze=(ZipEntry)zList.nextElement();
			//列举的压缩文件里面的各个文件，判断是否为目录
			if(ze.isDirectory()){
				String dirstr = folderPath + ze.getName();
				dirstr.trim();
				File f=new File(dirstr);
				f.mkdir();
				continue;
			}
			OutputStream os= null;
			FileOutputStream fos = null;
			// ze.getName()会返回 script/start.script这样的，是为了返回实体的File
			File realFile = getRealFileName(folderPath, ze.getName());
			try {
				fos = new FileOutputStream(realFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			os = new BufferedOutputStream(fos);
			InputStream is= null;
			try {
				is = new BufferedInputStream(zfile.getInputStream(ze));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			int readLen=0;
			//进行一些内容复制操作
			try {
				while ((readLen=is.read(buf, 0, 1024))!=-1) {
					os.write(buf, 0, readLen);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		try {
			zfile.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		Log.d(TAG, "upZipFileDir: 耗时"+(System.currentTimeMillis()-startTime));
        zipFile.delete();
		return true;
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * @param baseDir 指定根目录
	 * @param absFileName 相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	public static File getRealFileName(String baseDir, String absFileName){
		String[] dirs=absFileName.split("/");
		File ret = new File(baseDir);
		String substr = null;

		if(dirs.length>1){
			for (int i = 0; i < dirs.length-1;i++) {
				substr = dirs[i];
				ret=new File(ret, substr);
			}

			if(!ret.exists())
				ret.mkdirs();
			substr = dirs[dirs.length-1];
			ret=new File(ret, substr);
			return ret;
		}else{
			ret = new File(ret,absFileName);
		}
		return ret;
	}

	public static boolean isMounted() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
