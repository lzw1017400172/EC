package com.deehow.core;

import com.deehow.core.base.BaseProviderImpl;
import com.deehow.provider.ICmsProvider;

import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass = ICmsProvider.class)
public class CmsProviderImpl extends BaseProviderImpl implements ICmsProvider {
	
}