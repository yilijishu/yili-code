package com.yilijishu.code.bean;

import com.yilijishu.utils.FileUtil;
import com.yilijishu.utils.PackageUtils;
import com.yilijishu.utils.YiliCodePathUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;


public class CodeGenerate {

    private ApplicationContext applicationContext;

    private String buildPath;

    private static final String STR_SERVICE = "service";
    private static final String STR_SERVICE_IMPL = "impl";
    private static final String STR_MANAGER = "manager";
    private static final String STR_MAPPER = "mapper";


    public CodeGenerate(ApplicationContext applicationContext, String buildPath) {
        this.applicationContext = applicationContext;
        this.buildPath = buildPath;
        build();
    }

    public void build()  {
        try {
            Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation((Class
                    <? extends Annotation>)Class.forName("com.yilijishu.mybatis.ann.Table"));
            for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
                Class<?> beanClass = entry.getValue().getClass();
                String className = beanClass.getSimpleName();  //DEMO: User
                String classFullName = PackageUtils.getFullyQualifiedName(beanClass); //DEMO: com.yilijishu.user.entity.User
                String basePath = YiliCodePathUtils.rectification(YiliCodePathUtils.convertToPath(classFullName), 2); //DEMO:com/yilijishu/user
                String basePackage = YiliCodePathUtils.convertToPackage(basePath); //DEMO: com.yilijishu.user
                String baseFullPath = YiliCodePathUtils.join(buildPath, basePath); //DEMO: /Users/z/Document/.../main/java/com/yilijishu/user
                String serviceFullPath = YiliCodePathUtils.join(baseFullPath, "service").concat("/").concat(className).concat("Service.java");
                String serviceImplFullPath = YiliCodePathUtils.join(baseFullPath, "service/impl").concat("/").concat(className).concat("ServiceImpl.java");
                String mapperFullPath = YiliCodePathUtils.join(baseFullPath, "mapper").concat("/").concat(className).concat("Mapper.java");
                String managerFullPath = YiliCodePathUtils.join(baseFullPath, "manager").concat("/").concat(className).concat("Manager.java");

                //service
                String packageService = basePackage.concat(".").concat(STR_SERVICE);
                StringBuffer service = buildService(className, packageService);

                FileUtil.writeFileWhenFileNotFound(service.toString(), serviceFullPath);
                //serviceImpl
                String packageServiceImpl = packageService.concat(".").concat(STR_SERVICE_IMPL);
                StringBuffer serviceImpl = buildServiceImpl(className, packageServiceImpl);
                FileUtil.writeFileWhenFileNotFound(serviceImpl.toString(), serviceImplFullPath);
                //mapper
                String packageMapper = basePackage.concat(".").concat(STR_MAPPER);
                String mapperFullClassName = packageMapper.concat(className).concat("Mapper");
                StringBuffer mapper = buildMapper(classFullName, className, packageMapper);
                FileUtil.writeFileWhenFileNotFound(mapper.toString(), mapperFullPath);
                //manager
                String packageManager = basePackage.concat(".").concat(STR_MANAGER);
                StringBuffer manager = buildManager(classFullName, className, packageManager, mapperFullClassName);
                FileUtil.writeFileWhenFileNotFound(manager.toString(), managerFullPath);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("找不到Table类");
        }
    }

    /**
     * 生成manager文件
     * @param entityFullName 实体全名
     * @param entityName 实体名
     * @param packageManager manager包名
     * @param mapperClassFullName mapperclass全名
     * @return code 字符串
     */
    public StringBuffer buildManager(String entityFullName, String entityName, String packageManager, String mapperClassFullName) {
        StringBuffer sbf = new StringBuffer();

        //-- package start
        sbf.append("package ").append(packageManager).append(";").append("\n");
        sbf.append("\n");
        //-- package end

        // import start
        sbf.append("import com.yilijishu.mybatis.manager.BaseManager;\n");
        sbf.append("import ").append(entityFullName).append(";\n");
        sbf.append("import ").append(mapperClassFullName).append(";\n");
        sbf.append("import org.springframework.stereotype.Repository;\n");
        sbf.append("import org.springframework.transaction.annotation.Transactional;\n");
        sbf.append("\n");
        // import end

        //class start
        sbf.append("\n");
        sbf.append("@Transactional\n");
        sbf.append("@Repository\n");
        sbf.append("public class ").append(entityName).append("Manager ").append("extends ").append("BaseMapper<").append(entityName).append(", ").append(entityName).append("Mapper").append("> {\n");

        sbf.append("Service {");
        sbf.append("\n");
        sbf.append("\n");
        sbf.append("}");
        sbf.append("\n");
        //class end
        return sbf;
    }

    /**
     * build mapper code
     * @param classFullName 实体全路径com.yilijishu.entity.EntityName
     * @param className  EntityName
     * @param packageMapper 包名
     * @return 返回代码字符串
     */
    public StringBuffer buildMapper(String classFullName, String className, String packageMapper) {
        StringBuffer sbf = new StringBuffer();

        //-- package start
        sbf.append("package ").append(packageMapper).append(";").append("\n");
        sbf.append("\n");
        //-- package end

        // import start
        sbf.append("import org.apache.ibatis.annotations.*;\n");
        sbf.append("import com.yilijishu.mybatis.mapper.BaseMapper;\n");
        sbf.append("import ").append(classFullName).append(";").append("\n");
        sbf.append("\n");
        // import end

        //class start
        sbf.append("\n");
        sbf.append("@Mapper").append("\n");
        sbf.append("public interface ").append(className).append("Mapper ").append("extends ").append("BaseMapper<").append(className).append("> {\n");

        sbf.append("Service {");
        sbf.append("\n");
        sbf.append("\n");
        sbf.append("}");
        sbf.append("\n");
        //class end
        return sbf;
    }


    /**
     * empty service
     * @param className className
     * @param packageService 包名
     * @return service code
     */
    public StringBuffer buildService(String className, String packageService) {
        StringBuffer sbf = new StringBuffer();

        //-- package start
        sbf.append("package ").append(packageService).append(";").append("\n");
        sbf.append("\n");
        //-- package end

        // import start

        sbf.append("\n");
        // import end

        //class start
        sbf.append("\n");
        sbf.append("public interface ");
        sbf.append(className);
        sbf.append("Service {");
        sbf.append("\n");
        sbf.append("\n");
        sbf.append("}");
        sbf.append("\n");
        //class end
        return sbf;
    }
    /**
     * empty service Impl
     * @param className className
     * @param packageServiceImpl 包名
     * @return 返回文件字符串
     */
    public StringBuffer buildServiceImpl(String className, String packageServiceImpl) {
        StringBuffer sbf = new StringBuffer();

        //-- package start
        sbf.append("package ").append(packageServiceImpl).append(";").append("\n");
        sbf.append("\n");
        //-- package end

        // import start

        sbf.append("import org.springframework.stereotype.Service;");
        sbf.append("\n");
        sbf.append("\n");
        // import end

        //class start
        sbf.append("\n");
        sbf.append("@Service");
        sbf.append("\n");
        sbf.append("public class ");
        sbf.append(className);
        sbf.append("ServiceImpl implements ");
        sbf.append(className);
        sbf.append("Service {");
        sbf.append("\n");
        sbf.append("\n");
        sbf.append("}");
        sbf.append("\n");
        //class end
        return sbf;
    }
}
