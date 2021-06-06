package org.example;

import org.springframework.context.annotation.Import;

@Import(MyImportBeanDefinitionRegistrar.class)
public class BeanConfig {
}
