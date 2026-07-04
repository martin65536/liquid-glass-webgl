package defpackage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xd {
    public final HashMap a = new HashMap();
    public final HashMap b;

    public xd(HashMap hashMap) {
        this.b = hashMap;
        for (Map.Entry entry : hashMap.entrySet()) {
            z70 z70Var = (z70) entry.getValue();
            List list = (List) this.a.get(z70Var);
            if (list == null) {
                list = new ArrayList();
                this.a.put(z70Var, list);
            }
            list.add((yd) entry.getKey());
        }
    }

    public static void a(List list, j80 j80Var, z70 z70Var, Object obj) {
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                yd ydVar = (yd) list.get(size);
                Method method = ydVar.b;
                try {
                    int i = ydVar.a;
                    if (i != 0) {
                        if (i != 1) {
                            if (i == 2) {
                                method.invoke(obj, j80Var, z70Var);
                            }
                        } else {
                            method.invoke(obj, j80Var);
                        }
                    } else {
                        method.invoke(obj, null);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException("Failed to call observer method", e2.getCause());
                }
            }
        }
    }
}
