import java.lang.reflect.Method;

public class TestClassLoaderNameSpace {
    public static void main(String[] args) throws Exception {
        CustomizedClassLoader loader1 = new CustomizedClassLoader("loader1");
        CustomizedClassLoader loader2 = new CustomizedClassLoader("loader2");
        String path = "";
        System.out.println(path);

        loader1.setPath(path);
        loader2.setPath(path);
        // 第一次调用
        // loadClass: custom > app > ext > boot findBootstrapClassOrNull
        // findClass: ext > app > custom
        Class<?> clazz1 = loader1.loadClass("Person");
        Class<?> clazz2 = loader2.loadClass("Person");
        System.out.println("clazz1的classLoader是" + clazz1.getClassLoader());
        System.out.println("clazz2的classLoader是" + clazz2.getClassLoader());
        System.out.println(clazz1 == clazz2);
        // clazz1的classLoader是sun.misc.Launcher$AppClassLoader@18b4aac2
        // clazz2的classLoader是sun.misc.Launcher$AppClassLoader@18b4aac2
        // 第二次调用：custom > app 缓存直接返回，实际加载器为 app
        // true

        Object object1 = clazz1.newInstance();
        Object object2 = clazz2.newInstance();
        Method method = clazz1.getMethod("setPerson", Object.class);
        method.invoke(object1, object2);
    }
}
