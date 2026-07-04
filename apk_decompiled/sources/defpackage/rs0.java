package defpackage;

import android.app.Application;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class rs0 {
    public static final List a = jc0.w(Application.class, is0.class);
    public static final List b = jc0.v(is0.class);

    public static final Constructor a(Class cls, List list) {
        boolean z;
        list.getClass();
        Constructor<?>[] constructors = cls.getConstructors();
        constructors.getClass();
        int i = 0;
        while (true) {
            if (i < constructors.length) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                int i2 = i + 1;
                try {
                    Constructor<?> constructor = constructors[i];
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    parameterTypes.getClass();
                    List V = i8.V(parameterTypes);
                    if (list.equals(V)) {
                        return constructor;
                    }
                    if (list.size() != V.size() || !V.containsAll(list)) {
                        i = i2;
                    } else {
                        throw new UnsupportedOperationException("Class " + cls.getSimpleName() + " must have parameters in the proper order: " + list);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new NoSuchElementException(e.getMessage());
                }
            } else {
                return null;
            }
        }
    }

    public static final s51 b(Class cls, Constructor constructor, Object... objArr) {
        try {
            return (s51) constructor.newInstance(Arrays.copyOf(objArr, objArr.length));
        } catch (IllegalAccessException e) {
            v7.j("Failed to access ", cls, e);
            return null;
        } catch (InstantiationException e2) {
            throw new RuntimeException("A " + cls + " cannot be instantiated.", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("An exception happened in constructor of " + cls, e3.getCause());
        }
    }
}
