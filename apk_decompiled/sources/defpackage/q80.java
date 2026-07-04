package defpackage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class q80 {
    public static final HashMap a = new HashMap();
    public static final HashMap b = new HashMap();

    public static void a(Constructor constructor, i80 i80Var) {
        try {
            constructor.newInstance(i80Var).getClass();
            throw new ClassCastException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static final String b(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int indexOf = str.indexOf(".", 0);
        if (indexOf >= 0) {
            int length = str.length();
            if (length >= 0) {
                StringBuilder sb2 = new StringBuilder(length);
                do {
                    sb2.append((CharSequence) str, i, indexOf);
                    sb2.append("_");
                    i = indexOf + 1;
                    if (indexOf >= str.length()) {
                        break;
                    }
                    indexOf = str.indexOf(".", i);
                } while (indexOf > 0);
                sb2.append((CharSequence) str, i, str.length());
                str = sb2.toString();
            } else {
                throw new OutOfMemoryError();
            }
        }
        sb.append(str);
        sb.append("_LifecycleAdapter");
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static int c(Class cls) {
        Constructor constructor;
        boolean z;
        boolean z2;
        String str;
        HashMap hashMap = a;
        Integer num = (Integer) hashMap.get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i = 1;
        if (cls.getCanonicalName() != null) {
            ArrayList arrayList = null;
            try {
                Package r4 = cls.getPackage();
                String canonicalName = cls.getCanonicalName();
                if (r4 != null) {
                    str = r4.getName();
                } else {
                    str = "";
                }
                str.getClass();
                if (str.length() != 0) {
                    canonicalName.getClass();
                    canonicalName = canonicalName.substring(str.length() + 1);
                }
                canonicalName.getClass();
                String b2 = b(canonicalName);
                if (str.length() != 0) {
                    b2 = str + '.' + b2;
                }
                constructor = Class.forName(b2).getDeclaredConstructor(cls);
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
            } catch (ClassNotFoundException unused) {
                constructor = null;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            HashMap hashMap2 = b;
            if (constructor != null) {
                hashMap2.put(cls, jc0.v(constructor));
            } else {
                zd zdVar = zd.c;
                HashMap hashMap3 = zdVar.b;
                Boolean bool = (Boolean) hashMap3.get(cls);
                if (bool != null) {
                    z = bool.booleanValue();
                } else {
                    try {
                        Method[] declaredMethods = cls.getDeclaredMethods();
                        int length = declaredMethods.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 < length) {
                                if (((oh0) declaredMethods[i2].getAnnotation(oh0.class)) != null) {
                                    zdVar.a(cls, declaredMethods);
                                    z = true;
                                    break;
                                }
                                i2++;
                            } else {
                                hashMap3.put(cls, Boolean.FALSE);
                                z = false;
                                break;
                            }
                        }
                    } catch (NoClassDefFoundError e2) {
                        throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e2);
                    }
                }
                if (!z) {
                    Class superclass = cls.getSuperclass();
                    if (superclass != null && i80.class.isAssignableFrom(superclass)) {
                        superclass.getClass();
                        if (c(superclass) != 1) {
                            Object obj = hashMap2.get(superclass);
                            obj.getClass();
                            arrayList = new ArrayList((Collection) obj);
                        }
                    }
                    Class<?>[] interfaces = cls.getInterfaces();
                    interfaces.getClass();
                    int i3 = 0;
                    while (true) {
                        if (i3 < interfaces.length) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z2) {
                            int i4 = i3 + 1;
                            try {
                                Class<?> cls2 = interfaces[i3];
                                if (cls2 != null && i80.class.isAssignableFrom(cls2)) {
                                    cls2.getClass();
                                    if (c(cls2) == 1) {
                                        break;
                                    }
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                    }
                                    Object obj2 = hashMap2.get(cls2);
                                    obj2.getClass();
                                    arrayList.addAll((Collection) obj2);
                                }
                                i3 = i4;
                            } catch (ArrayIndexOutOfBoundsException e3) {
                                throw new NoSuchElementException(e3.getMessage());
                            }
                        } else if (arrayList != null) {
                            hashMap2.put(cls, arrayList);
                        }
                    }
                }
            }
            i = 2;
        }
        hashMap.put(cls, Integer.valueOf(i));
        return i;
    }
}
