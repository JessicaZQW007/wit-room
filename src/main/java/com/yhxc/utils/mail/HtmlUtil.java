package com.yhxc.utils.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtil {
	
	/**
	 * 获取html中的图片文件
	 * @param path 路径
	 * @param content 
	 * @return 文件列表
	 */
	public static List<File> getImages(String path,String content ) {
		Document document = null;
		List<File> list = new ArrayList<File>();
		try {
			if (StringUtils.isBlank(content)) {
				return list;
			}
			document=Jsoup.parse(content);
			if (document!=null) {
				Elements elements = document.getElementsByTag("img");
				if (elements!=null && elements.size()>0) {
					for (Element element : elements) {
						String fileName = element.attr("src");
						fileName=StringUtils.substringAfter(fileName, "upload1");
						File file = new File(path, fileName);
						list.add(file);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 替换页面的图片
	 * @return
	 */
	public static String replaceContentImages(String content) {
		Document document=null;
		String result=content;
		try {
			document=Jsoup.parse(content);
			Elements elements = document.getElementsByTag("img");
			if (elements!=null && elements.size()>0) {
				int index=0;
				for (Element element : elements) {
					element.attr("src", "cid:"+index);
					index++;
				}
				result=document.body().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
