package defpackage;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zd {
    public static final zd c = new zd();
    public final HashMap a = new HashMap();
    public final HashMap b = new HashMap();

    public static void b(HashMap hashMap, yd ydVar, z70 z70Var, Class cls) {
        z70 z70Var2 = (z70) hashMap.get(ydVar);
        if (z70Var2 != null && z70Var != z70Var2) {
            throw new IllegalArgumentException("Method " + ydVar.b.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + z70Var2 + ", new value " + z70Var);
        }
        if (z70Var2 == null) {
            hashMap.put(ydVar, z70Var);
        }
    }

    public final xd a(Class cls, Method[] methodArr) {
        int i;
        Class superclass = cls.getSuperclass();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = this.a;
        if (superclass != null) {
            xd xdVar = (xd) hashMap2.get(superclass);
            if (xdVar == null) {
                xdVar = a(superclass, null);
            }
            hashMap.putAll(xdVar.b);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            xd xdVar2 = (xd) hashMap2.get(cls2);
            if (xdVar2 == null) {
                xdVar2 = a(cls2, null);
            }
            for (Map.Entry entry : xdVar2.b.entrySet()) {
                b(hashMap, (yd) entry.getKey(), (z70) entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            try {
                methodArr = cls.getDeclaredMethods();
            } catch (NoClassDefFoundError e) {
                throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
            }
        }
        boolean z = false;
        for (Method method : methodArr) {
            oh0 oh0Var = (oh0) method.getAnnotation(oh0.class);
            if (oh0Var != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length > 0) {
                    if (j80.class.isAssignableFrom(parameterTypes[0])) {
                        i = 1;
                    } else {
                        v7.m("invalid parameter type. Must be one and instanceof LifecycleOwner");
                        return null;
                    }
                } else {
                    i = 0;
                }
                z70 value = oh0Var.value();
                if (parameterTypes.length > 1) {
                    if (z70.class.isAssignableFrom(parameterTypes[1])) {
                        if (value == z70.ON_ANY) {
                            i = 2;
                        } else {
                            v7.m("Second arg is supported only for ON_ANY value");
                            return null;
                        }
                    } else {
                        v7.m("invalid parameter type. second arg must be an event");
                        return null;
                    }
                }
                if (parameterTypes.length <= 2) {
                    b(hashMap, new yd(i, method), value, cls);
                    z = true;
                } else {
                    v7.m("cannot have more than 2 params");
                    return null;
                }
            }
        }
        xd xdVar3 = new xd(hashMap);
        hashMap2.put(cls, xdVar3);
        this.b.put(cls, Boolean.valueOf(z));
        return xdVar3;
    }
}
