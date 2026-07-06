package defpackage;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i31 extends y20 {
    public static final Class a;
    public static final Constructor b;
    public static final Method c;
    public static final Method d;

    static {
        Class<?> cls;
        Method method;
        Method method2;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(null);
            Class<?> cls2 = Integer.TYPE;
            method2 = cls.getMethod("addFontWeightStyle", ByteBuffer.class, cls2, List.class, cls2, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi24Impl", e.getClass().getName(), e);
            cls = null;
            method = null;
            method2 = null;
        }
        b = constructor;
        a = cls;
        c = method2;
        d = method;
    }

    @Override // defpackage.y20
    public final Typeface f(Context context, qu[] quVarArr) {
        Object obj;
        Typeface typeface;
        boolean z;
        try {
            obj = b.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            obj = null;
        }
        if (obj != null) {
            jw0 jw0Var = new jw0();
            int length = quVarArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    qu quVar = quVarArr[i];
                    Uri uri = quVar.a;
                    Object obj2 = (ByteBuffer) jw0Var.get(uri);
                    if (obj2 == null) {
                        obj2 = g30.z(context, uri);
                        jw0Var.put(uri, obj2);
                    }
                    if (obj2 == null) {
                        break;
                    }
                    try {
                        z = ((Boolean) c.invoke(obj, obj2, Integer.valueOf(quVar.b), null, Integer.valueOf(quVar.c), Boolean.valueOf(quVar.d))).booleanValue();
                    } catch (IllegalAccessException | InvocationTargetException unused2) {
                        z = false;
                    }
                    if (!z) {
                        break;
                    }
                    i++;
                } else {
                    try {
                        Object newInstance = Array.newInstance((Class<?>) a, 1);
                        Array.set(newInstance, 0, obj);
                        typeface = (Typeface) d.invoke(null, newInstance);
                    } catch (IllegalAccessException | InvocationTargetException unused3) {
                        typeface = null;
                    }
                    if (typeface != null) {
                        return Typeface.create(typeface, 0);
                    }
                }
            }
        }
        return null;
    }
}
