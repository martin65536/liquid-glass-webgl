package defpackage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class qc {
    public static final /* synthetic */ Unsafe a = b();

    public static /* synthetic */ Object a(Unsafe unsafe, Object obj, long j, q01 q01Var) {
        while (true) {
            Object objectVolatile = unsafe.getObjectVolatile(obj, j);
            Unsafe unsafe2 = unsafe;
            Object obj2 = obj;
            long j2 = j;
            q01 q01Var2 = q01Var;
            if (unsafe2.compareAndSwapObject(obj2, j2, objectVolatile, q01Var2)) {
                return objectVolatile;
            }
            unsafe = unsafe2;
            obj = obj2;
            j = j2;
            q01Var = q01Var2;
        }
    }

    public static /* synthetic */ Unsafe b() {
        Field field;
        Field field2;
        try {
            field2 = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = Unsafe.class.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    field = declaredFields[i];
                    if (Modifier.isStatic(field.getModifiers()) && Unsafe.class.isAssignableFrom(field.getType())) {
                        break;
                    }
                    i++;
                } else {
                    field = null;
                    break;
                }
            }
            if (field == null) {
                field2 = field;
            } else {
                throw new UnsupportedOperationException("Couldn't find the Unsafe", e);
            }
        }
        field2.setAccessible(true);
        try {
            return (Unsafe) field2.get(null);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
