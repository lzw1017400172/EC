package com.deehow.constant;

import java.io.File;

public interface BusinessConstant {

    /**
     * EDM文件上传根路径
     */
    public final static String ROOT_PATH = File.separator+"home"+File.separator+"data"+File.separator+"file"+File.separator+"edm"+File.separator;


    /**
     * 模型文件上传根路径
     */
    public final static String MODEL_PATH = ROOT_PATH + "MODEL" + File.separator;

    /**
     * 原理图模型文件上传路径
     */
    public final static String MODEL_SCHEMATIC_DIAGRAM_PATH = MODEL_PATH + "MODEL_SCHEMATIC_DIAGRAM" + File.separator;

}
