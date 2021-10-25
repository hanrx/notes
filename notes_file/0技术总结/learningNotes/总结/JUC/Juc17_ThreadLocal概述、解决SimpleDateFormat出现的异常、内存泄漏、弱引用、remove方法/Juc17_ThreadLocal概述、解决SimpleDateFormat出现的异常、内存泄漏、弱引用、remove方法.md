
# ①. ThreadLocal简介

## ①. ThreadLocal是什么


## ②. api介绍


## ③. 永远的helloword


## ④. 通过上面代码总结

# ②. 从阿里ThreadLocal规范开始


## ①. 非线程安全的SimpleDateFormat


## ②. 将SimpleDateFormat定义成局部变量(方案一)


## ③. ThreadLocal 解决日期格式乱码问题


## ④. 阿里规范怎么说的？

# ③. ThreadLocal源码分析


## ①. Thread|ThreadLocal|ThreadLocalMap关系


## ②. set方法详解


## ③. get方法详解


## ④. remove方法详解

# ④. ThreadLocal内存泄漏问题


## ①. 为什么源代码用弱引用？


## ②. key为null的entry,原理解析


## ③. set、get方法会去检查所有键为null的Entry对象


## ④. 结论(在finally后面调用remove方法)

# ⑤. ThreadLocal小总结































