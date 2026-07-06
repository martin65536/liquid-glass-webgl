package defpackage;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class s9 implements ij, jk, Serializable {
    public final ij e;

    public s9(ij ijVar) {
        this.e = ijVar;
    }

    @Override // defpackage.jk
    public jk f() {
        ij ijVar = this.e;
        if (ijVar instanceof jk) {
            return (jk) ijVar;
        }
        return null;
    }

    public ij i(ij ijVar, Object obj) {
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    public StackTraceElement j() {
        int i;
        String str;
        Method method;
        Object invoke;
        Method method2;
        Object invoke2;
        Object obj;
        Integer num;
        int i2;
        cl clVar = (cl) getClass().getAnnotation(cl.class);
        String str2 = null;
        if (clVar == null || clVar.v() < 1) {
            return null;
        }
        int i3 = -1;
        try {
            Field declaredField = getClass().getDeclaredField("label");
            declaredField.setAccessible(true);
            Object obj2 = declaredField.get(this);
            if (obj2 instanceof Integer) {
                num = (Integer) obj2;
            } else {
                num = null;
            }
            if (num != null) {
                i2 = num.intValue();
            } else {
                i2 = 0;
            }
            i = i2 - 1;
        } catch (Exception unused) {
            i = -1;
        }
        if (i >= 0) {
            i3 = clVar.l()[i];
        }
        r7 r7Var = f31.f;
        r7 r7Var2 = f31.g;
        if (r7Var2 == null) {
            try {
                r7 r7Var3 = new r7(Class.class.getDeclaredMethod("getModule", null), getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", null), getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", null), 6);
                f31.g = r7Var3;
                r7Var2 = r7Var3;
            } catch (Exception unused2) {
                f31.g = r7Var;
                r7Var2 = r7Var;
            }
        }
        if (r7Var2 != r7Var && (method = (Method) r7Var2.f) != null && (invoke = method.invoke(getClass(), null)) != null && (method2 = (Method) r7Var2.g) != null && (invoke2 = method2.invoke(invoke, null)) != null) {
            Method method3 = (Method) r7Var2.h;
            if (method3 != null) {
                obj = method3.invoke(invoke2, null);
            } else {
                obj = null;
            }
            if (obj instanceof String) {
                str2 = (String) obj;
            }
        }
        if (str2 == null) {
            str = clVar.c();
        } else {
            str = str2 + '/' + clVar.c();
        }
        return new StackTraceElement(str, clVar.m(), clVar.f(), i3);
    }

    public abstract Object k(Object obj);

    public String toString() {
        StringBuilder sb = new StringBuilder("Continuation at ");
        Object j = j();
        if (j == null) {
            j = getClass().getName();
        }
        sb.append(j);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // defpackage.ij
    public final void u(Object obj) {
        while (true) {
            s9 s9Var = this;
            ij ijVar = s9Var.e;
            ijVar.getClass();
            try {
                obj = s9Var.k(obj);
                if (obj == ik.e) {
                    return;
                }
            } catch (Throwable th) {
                obj = new jq0(th);
            }
            s9Var.l();
            if (ijVar instanceof s9) {
                this = ijVar;
            } else {
                ijVar.u(obj);
                return;
            }
        }
    }

    public void l() {
    }
}
