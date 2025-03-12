### 1.代码结构

1. com.mcluhan.dcp.util.DcpUtils中的方法为Dcp工具类
2. text下的DcpUtilsTest类为DcpUtils类中方法的测试用例，使用参照DcpUtilsTest中的测试用例即可。

### 2.使用教程

1. 代码复制。
   1. 将pom.xml中的依赖引入到项目中。（需要注意依赖冲突问题）
   2. 然后将com.mcluhan.dcp包中所有代码引入到项目中，解析和签名验证直接调用DcpUtils中的方法即可。