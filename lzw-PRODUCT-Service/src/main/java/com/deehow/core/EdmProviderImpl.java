package com.deehow.core;

import com.alibaba.dubbo.config.annotation.Service;
import com.deehow.core.base.BaseProviderImpl;
import com.deehow.provider.IEdmProvider;

@Service(interfaceClass = IEdmProvider.class)
public class EdmProviderImpl extends BaseProviderImpl implements IEdmProvider {
	
}