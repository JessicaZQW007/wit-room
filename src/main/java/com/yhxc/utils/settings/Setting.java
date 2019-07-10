package com.yhxc.utils.settings;

import java.io.Serializable;

/**
 * 系统配置类，已经在系统启动时缓存，可以直接使用
 *
 * @文件名称:
 * @since 21/08/2017.
 */
public class Setting implements Serializable {

    private static final long serialVersionUID = -1478999889661796840L;


    public static final String CACHE_NAME = "setting";

    public static final Integer CACHE_KEY = Integer.valueOf(0);

    // 以下是可以对应的属性---------------------
    private String fileRoot;	//文件夹跟目录位置，不可以通过url直接访问，保证安全
    private String webFileRoot; //公开可以通地没有授权的url进行访问的文件夹目录位置
    private String webFileRootUrl; //不需要授权的webfileRooturl访问地址
    

    /**
     *
     * @return 文件夹跟目录位置，不可以通过url直接访问，保证安全
     */
    public String getFileRoot() {
        return fileRoot;
    }

    /**
     *
     * @param fileRoot 文件夹跟目录位置，不可以通过url直接访问，保证安全
     */
    public void setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
    }

    /**
     *
     * @return 公开可以通地没有授权的url进行访问的文件夹目录位置
     */
    public String getWebFileRoot() {
        return webFileRoot;
    }

    /**
     *
     * @param webFileRoot 公开可以通地没有授权的url进行访问的文件夹目录位置
     */
    public void setWebFileRoot(String webFileRoot) {
        this.webFileRoot = webFileRoot;
    }

    /**
     * 
     * @return 不需要授权的webfileRooturl访问地址
     */
	public String getWebFileRootUrl() {
		return webFileRootUrl;
	}

	/**
	 * 
	 * @param webFileRootUrl 不需要授权的webfileRooturl访问地址
	 */
	public void setWebFileRootUrl(String webFileRootUrl) {
		this.webFileRootUrl = webFileRootUrl;
	}
    
    
    
}