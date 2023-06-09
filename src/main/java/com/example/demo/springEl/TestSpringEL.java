package com.example.demo.springEl;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@ToString
@Component
public class TestSpringEL {

    /**
     * 注入简单值，输出num为5
     */
    @Value("#{5}")
    private Integer num;

    @Value("#{'rain'.toUpperCase()}")
    private String name;

    //注入bean，访问属性和方法

    /**
     * 注入ID为testConstant的Bean
     */
    @Value("#{testConstant}")
    private TestConstant testConstant;

    /**
     * 注入ID为testConstant的Bean中的STR常量/变量
     */
    @Value("#{testConstant.STR}")
    private String str;

    /**
     * 调用无参方法
     */
    @Value("#{testConstant.showProperty()}")
    private String method1;

    /**
     * 调用有参方法，接收字符串
     */
    @Value("#{testConstant.showProperty('World')}")
    private String method2;

    /**
     * 方法返回的String为大写
     */
    @Value("#{testConstant.showProperty().toUpperCase()}")
    private String method3;

    /**
     * 使用method3这种方式，如果showProperty返回为null，将会抛出NullPointerException，可以使用以下方式避免。
     * 使用?.符号表示如果左边的值为null，将不执行右边方法
     */
    @Value("#{testConstant.showProperty()?.toUpperCase}")
    private String method4;

    //注入JDK中的工具类常量或调用工具类的方法

    /**
     * 获取Math的PI常量
     */
    @Value("#{T(java.lang.Math).PI}")
    private double pi;

    /**
     * 调用random方法获取返回值
     */
    @Value("#{T(java.lang.Math).random()}")
    private double ramdom;

    /**
     * 获取文件路径符号
     */
    @Value("#{T(java.io.File).separator}")
    private String separator;

    //使用SpringEL进行运算及逻辑操作

    /**
     * 拼接字符串
     */
    @Value("#{testConstant.nickname + ' ' + testConstant.name}")
    private String concatString;

    /**
     * 对数字类型进行运算
     */
    @Value("#{ 3 * T(java.lang.Math).PI + testConstant.num}")
    private double operation;

    /**
     * 进行逻辑运算
     */
    @Value("#{testConstant.num > 100 and testConstant.num <= 200}")
    private boolean logicOperation;

    /**
     * 进行或非逻辑操作
     */
    @Value("#{not(testConstant.num == 100 or testConstant.num <= 200)}")
    private Boolean logicOperation2;

    /**
     * 使用三元运算符
     */
    @Value("#{testConstant.num > 100 ? testConstant.num : testConstant.num + 100}")
    private Integer logicOperation3;

    //SpringEL使用正则表达式

    /**
     * 验证是否邮箱地址正则表达式
     */
    @Value("#{testConstant.STR matches '\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+'}")
    private boolean regularExpression;

    //TestConstant类中有名为testList的List变量，和名为testMap的Map变量

    /**
     * 获取下标为0的元素
     */
    @Value("#{testConstant.testList[0]}")
    private String firstStr;

    /**
     * 获取下标为0元素的大写形式
     */
    @Value("#{testConstant.testList[0]?.toUpperCase()}")
    private String upperFirstStr;

    /**
     * 获取map中key为hello的value
     */
    @Value("#{testConstant.testMap['hello']}")
    private String mapValue;

    /**
     * 根据testList下标为0元素作为key获取testMap的value
     */
    @Value("#{testConstant.testMap[testConstant.testList[0]]}")
    private String mapValueByTestList;

    //声明City类，有population（人口）属性。testConstant拥有名为cityList的City类List集合

    /**
     * 过滤testConstant中cityList集合population属性大于1000的全部数据注入到本属性
     */
    @Value("#{testConstant.cityList.?[population > 1000]}")
    private List<City> cityList;

    /**
     * 过滤testConstant中cityList集合population属性等于1000的第一条数据注入到本属性
     */
    @Value("#{testConstant.cityList.^[population == 1000]}")
    private City city;

    /**
     * 过滤testConstant中cityList集合population属性小于1000的最后一条数据注入到本属性
     */
    @Value("#{testConstant.cityList.$[population < 1000]}")
    private City city2;

    /*
     * 首先为city增加name属性，代表城市的名称
     */

    /**
     * 假如我们在过滤城市集合后只想保留城市的名称，可以使用如下方式进行投影
     */
    @Value("#{testConstant.cityList.?[population > 1000].![name]}")
    private List<String> cityName;
}

