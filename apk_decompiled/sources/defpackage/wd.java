package defpackage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wd implements vd {
    public static final Map b;
    public final Class a;

    static {
        Map map;
        int i = 0;
        List w = jc0.w(vu.class, gv.class, kv.class, lv.class, mv.class, nv.class, ov.class, pv.class, qv.class, rv.class, wu.class, xu.class, yu.class, zu.class, av.class, bv.class, cv.class, dv.class, ev.class, fv.class, hv.class, iv.class, jv.class);
        ArrayList arrayList = new ArrayList(ne.N(w));
        int i2 = 0;
        for (Object obj : w) {
            int i3 = i2 + 1;
            if (i2 >= 0) {
                arrayList.add(new xj0((Class) obj, Integer.valueOf(i2)));
                i2 = i3;
            } else {
                jc0.H();
                throw null;
            }
        }
        int size = arrayList.size();
        if (size != 0) {
            if (size != 1) {
                int size2 = arrayList.size();
                if (size2 >= 0) {
                    if (size2 < 3) {
                        size2++;
                    } else if (size2 < 1073741824) {
                        size2 = (int) ((size2 / 0.75f) + 1.0f);
                    } else {
                        size2 = Integer.MAX_VALUE;
                    }
                }
                map = new LinkedHashMap(size2);
                int size3 = arrayList.size();
                while (i < size3) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    xj0 xj0Var = (xj0) obj2;
                    map.put(xj0Var.e, xj0Var.f);
                }
            } else {
                xj0 xj0Var2 = (xj0) arrayList.get(0);
                xj0Var2.getClass();
                map = Collections.singletonMap(xj0Var2.e, xj0Var2.f);
                map.getClass();
            }
        } else {
            map = fr.e;
        }
        b = map;
    }

    public wd(Class cls) {
        cls.getClass();
        this.a = cls;
    }

    public final String a() {
        String n;
        Class cls = this.a;
        cls.getClass();
        String str = null;
        if (cls.isAnonymousClass() || cls.isLocalClass()) {
            return null;
        }
        if (cls.isArray()) {
            Class<?> componentType = cls.getComponentType();
            if (componentType.isPrimitive() && (n = dl.n(componentType.getName())) != null) {
                str = n.concat("Array");
            }
            if (str == null) {
                return "kotlin.Array";
            }
            return str;
        }
        String n2 = dl.n(cls.getName());
        if (n2 == null) {
            return cls.getCanonicalName();
        }
        return n2;
    }

    public final String b() {
        String R;
        Class cls = this.a;
        cls.getClass();
        String str = null;
        if (cls.isAnonymousClass()) {
            return null;
        }
        if (cls.isLocalClass()) {
            String simpleName = cls.getSimpleName();
            Method enclosingMethod = cls.getEnclosingMethod();
            if (enclosingMethod != null) {
                return uy0.E(simpleName, enclosingMethod.getName() + '$');
            }
            Constructor<?> enclosingConstructor = cls.getEnclosingConstructor();
            if (enclosingConstructor != null) {
                return uy0.E(simpleName, enclosingConstructor.getName() + '$');
            }
            int A = uy0.A(simpleName, '$', 0, 6);
            if (A == -1) {
                return simpleName;
            }
            return simpleName.substring(A + 1, simpleName.length());
        }
        if (cls.isArray()) {
            Class<?> componentType = cls.getComponentType();
            if (componentType.isPrimitive() && (R = dl.R(componentType.getName())) != null) {
                str = R.concat("Array");
            }
            if (str == null) {
                return "Array";
            }
            return str;
        }
        String R2 = dl.R(cls.getName());
        if (R2 == null) {
            return cls.getSimpleName();
        }
        return R2;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof wd) && o30.q(this).equals(o30.q((wd) obj))) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return o30.q(this).hashCode();
    }

    public final String toString() {
        return this.a.toString() + " (Kotlin reflection is not available)";
    }
}
