package defpackage;

import android.app.Application;
import android.os.Bundle;
import com.kyant.backdrop.catalog.MainActivity;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qs0 implements v51 {
    public final Application e;
    public final u51 f;
    public final Bundle g;
    public final l80 h;
    public final c4 i;

    public qs0(Application application, MainActivity mainActivity, Bundle bundle) {
        u51 u51Var;
        this.i = (c4) mainActivity.h.g;
        this.h = mainActivity.e;
        this.g = bundle;
        this.e = application;
        if (application != null) {
            if (u51.i == null) {
                u51.i = new u51(application);
            }
            u51Var = u51.i;
            u51Var.getClass();
        } else {
            u51Var = new u51(null);
        }
        this.f = u51Var;
    }

    @Override // defpackage.v51
    public final s51 a(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return d(canonicalName, cls);
        }
        v7.m("Local and anonymous classes can not be ViewModels");
        return null;
    }

    @Override // defpackage.v51
    public final s51 b(Class cls, ee0 ee0Var) {
        Constructor a;
        ey0 ey0Var = n20.s;
        LinkedHashMap linkedHashMap = ee0Var.a;
        String str = (String) linkedHashMap.get(ey0Var);
        if (str != null) {
            if (linkedHashMap.get(jc0.i) != null && linkedHashMap.get(jc0.j) != null) {
                Application application = (Application) linkedHashMap.get(u51.j);
                boolean isAssignableFrom = s6.class.isAssignableFrom(cls);
                if (isAssignableFrom && application != null) {
                    a = rs0.a(cls, rs0.a);
                } else {
                    a = rs0.a(cls, rs0.b);
                }
                if (a == null) {
                    return this.f.b(cls, ee0Var);
                }
                if (isAssignableFrom && application != null) {
                    return rs0.b(cls, a, application, jc0.m(ee0Var));
                }
                return rs0.b(cls, a, jc0.m(ee0Var));
            }
            if (this.h != null) {
                return d(str, cls);
            }
            v7.o("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
            return null;
        }
        v7.o("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        return null;
    }

    @Override // defpackage.v51
    public final s51 c(wd wdVar, ee0 ee0Var) {
        Class cls = wdVar.a;
        cls.getClass();
        return b(cls, ee0Var);
    }

    public final s51 d(String str, Class cls) {
        Constructor a;
        s51 b;
        AutoCloseable autoCloseable;
        Application application;
        l80 l80Var = this.h;
        if (l80Var != null) {
            boolean isAssignableFrom = s6.class.isAssignableFrom(cls);
            if (isAssignableFrom && this.e != null) {
                a = rs0.a(cls, rs0.a);
            } else {
                a = rs0.a(cls, rs0.b);
            }
            if (a == null) {
                if (this.e != null) {
                    return this.f.a(cls);
                }
                if (ey0.g == null) {
                    ey0.g = new ey0(12);
                }
                ey0.g.getClass();
                return d20.k(cls);
            }
            c4 c4Var = this.i;
            c4Var.getClass();
            is0 p = n30.p(c4Var.m(str), this.g);
            js0 js0Var = new js0(str, p);
            js0Var.g(c4Var, l80Var);
            a80 a80Var = l80Var.c;
            if (a80Var != a80.f && a80Var.compareTo(a80.h) < 0) {
                l80Var.a(new am(c4Var, l80Var));
            } else {
                c4Var.v();
            }
            if (isAssignableFrom && (application = this.e) != null) {
                b = rs0.b(cls, a, application, p);
            } else {
                b = rs0.b(cls, a, p);
            }
            b.getClass();
            t51 t51Var = b.a;
            if (t51Var != null) {
                if (t51Var.d) {
                    t51.a(js0Var);
                    return b;
                }
                synchronized (t51Var.a) {
                    autoCloseable = (AutoCloseable) t51Var.b.put("androidx.lifecycle.savedstate.vm.tag", js0Var);
                }
                t51.a(autoCloseable);
                return b;
            }
            return b;
        }
        throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
    }
}
