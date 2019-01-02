package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * cms-協同设计模型庫
 * </p>
 *
 * @author liuzw
 * @since 2018-12-26
 */
@TableName("ds_model_library")
@SuppressWarnings("serial")
public class DsModelLibrary extends BaseModel {

    /**
     * 模型名称
     */
	@TableField("name_")
	private String name;
	/**
	 * 模型标识
	 */
	@TableField("identification_")
	private String identification;
    /**
     * 图标
     */
    @TableField("icon_")
    private String icon;
	/**
	 * 1 路由模型，2 启动工具模型
	 */
	@TableField("type_")
	private Integer type;
    /**
     * 路由
     */
	@TableField("route_")
	private String route;
	/**
	 * 命令
	 */
	@TableField("command_")
	private String command;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}