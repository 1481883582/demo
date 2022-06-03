# Java类加载源码
```java
public abstract class ClassLoader {

    private final ClassLoader parent;

    //双亲委派加载源码
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException
        {
            synchronized (getClassLoadingLock(name)) {
                //First, check if the class has already been loaded
                //首先，检查类是否已经加载
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    try {
                        if (parent != null) {
                            //如果不为null 说明是非BootstrapClassLoader  递归走双亲委派
                            c = parent.loadClass(name, false);
                        } else {
                            //如果父加载器为空，查找 Bootstrap 加载器是不是加载过了
                            c = findBootstrapClassOrNull(name);
                        }
                    } catch (ClassNotFoundException e) {
                        // ClassNotFoundException thrown if class not found
                        // from the non-null parent class loader
                    }

                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        //如果没找到自己加载
                        long t1 = System.nanoTime();
                        c = findClass(name);
                    }
                }
                return c;
            }
        }

    //自己加载
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }
}
```
