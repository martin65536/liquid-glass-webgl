package defpackage;

import android.app.Application;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u51 extends ey0 {
    public static u51 i;
    public static final ey0 j = new ey0(11);
    public final Application h;

    public u51(Application application) {
        super(12);
        this.h = application;
    }

    @Override // defpackage.ey0, defpackage.v51
    public final s51 a(Class cls) {
        Application application = this.h;
        if (application != null) {
            return i(cls, application);
        }
        throw new UnsupportedOperationException("AndroidViewModelFactory constructed with empty constructor works only with create(modelClass: Class<T>, extras: CreationExtras).");
    }

    @Override // defpackage.ey0, defpackage.v51
    public final s51 b(Class cls, ee0 ee0Var) {
        if (this.h != null) {
            return a(cls);
        }
        Application application = (Application) ee0Var.a.get(j);
        if (application != null) {
            return i(cls, application);
        }
        if (!s6.class.isAssignableFrom(cls)) {
            return d20.k(cls);
        }
        v7.m("CreationExtras must have an application by `APPLICATION_KEY`");
        return null;
    }

    public final s51 i(Class cls, Application application) {
        if (s6.class.isAssignableFrom(cls)) {
            try {
                s51 s51Var = (s51) cls.getConstructor(Application.class).newInstance(application);
                s51Var.getClass();
                return s51Var;
            } catch (IllegalAccessException e) {
                v7.j("Cannot create an instance of ", cls, e);
                return null;
            } catch (InstantiationException e2) {
                v7.j("Cannot create an instance of ", cls, e2);
                return null;
            } catch (NoSuchMethodException e3) {
                v7.j("Cannot create an instance of ", cls, e3);
                return null;
            } catch (InvocationTargetException e4) {
                v7.j("Cannot create an instance of ", cls, e4);
                return null;
            }
        }
        return d20.k(cls);
    }
}
