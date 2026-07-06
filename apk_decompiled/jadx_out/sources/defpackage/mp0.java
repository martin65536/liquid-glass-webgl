package defpackage;

import android.os.Trace;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mp0 {
    public Set a;
    public wh b;
    public final ef0 c;
    public we0 d;
    public ef0 e;
    public final ef0 f;
    public final ef0 g;
    public we0 h;
    public ve0 i;
    public ArrayList j;
    public we0 k;

    public mp0() {
        ef0 ef0Var = new ef0(new gw[16]);
        this.c = ef0Var;
        we0 we0Var = at0.a;
        this.d = new we0();
        this.e = ef0Var;
        this.f = new ef0(new Object[16]);
        this.g = new ef0(new vu[16]);
    }

    public static final boolean f(gw gwVar, ef0 ef0Var) {
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            np0 np0Var = ((gw) objArr[i2]).a;
            if (np0Var instanceof il0) {
                ef0 ef0Var2 = ((il0) np0Var).f;
                if (ef0Var2.j(gwVar) || f(gwVar, ef0Var2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void a() {
        this.a = null;
        this.b = null;
        ef0 ef0Var = this.c;
        ef0Var.g();
        this.d.b();
        this.e = ef0Var;
        this.f.g();
        this.g.g();
        this.h = null;
        this.i = null;
        this.j = null;
    }

    public final void b() {
        Set set = this.a;
        if (set != null && !set.isEmpty()) {
            Trace.beginSection("Compose:abandons");
            try {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    np0 np0Var = (np0) it.next();
                    it.remove();
                    np0Var.f();
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    public final void c() {
        Set set = this.a;
        if (set != null) {
            this.k = null;
            ef0 ef0Var = this.f;
            int i = 2;
            if (ef0Var.g != 0) {
                Trace.beginSection("Compose:onForgotten");
                try {
                    we0 we0Var = this.h;
                    int i2 = ef0Var.g;
                    while (true) {
                        i2--;
                        if (-1 >= i2) {
                            break;
                        }
                        Object obj = ef0Var.e[i2];
                        try {
                            if (obj instanceof gw) {
                                np0 np0Var = ((gw) obj).a;
                                set.remove(np0Var);
                                np0Var.k();
                            }
                            if (obj instanceof xg) {
                                if (we0Var != null && we0Var.c(obj)) {
                                    ((xg) obj).a();
                                } else {
                                    ((xg) obj).b();
                                }
                            }
                        } catch (Throwable th) {
                            wh whVar = this.b;
                            if (whVar != null) {
                                k81.O(th, new f9(i, whVar, obj));
                            }
                            throw th;
                        }
                    }
                } finally {
                }
            }
            ef0 ef0Var2 = this.c;
            if (ef0Var2.g != 0) {
                Trace.beginSection("Compose:onRemembered");
                try {
                    Set set2 = this.a;
                    if (set2 != null) {
                        Object[] objArr = ef0Var2.e;
                        int i3 = ef0Var2.g;
                        for (int i4 = 0; i4 < i3; i4++) {
                            gw gwVar = (gw) objArr[i4];
                            np0 np0Var2 = gwVar.a;
                            set2.remove(np0Var2);
                            try {
                                np0Var2.d();
                            } catch (Throwable th2) {
                                wh whVar2 = this.b;
                                if (whVar2 != null) {
                                    k81.O(th2, new f9(i, whVar2, gwVar));
                                }
                                throw th2;
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void d() {
        ef0 ef0Var = this.g;
        if (ef0Var.g != 0) {
            Trace.beginSection("Compose:sideeffects");
            try {
                Object[] objArr = ef0Var.e;
                int i = ef0Var.g;
                for (int i2 = 0; i2 < i; i2++) {
                    ((vu) objArr[i2]).a();
                }
                ef0Var.g();
            } finally {
                Trace.endSection();
            }
        }
    }

    public final void e(gw gwVar) {
        if (this.d.c(gwVar)) {
            this.d.l(gwVar);
            if (!this.e.j(gwVar)) {
                ef0 ef0Var = this.c;
                if (!ef0Var.j(gwVar)) {
                    f(gwVar, ef0Var);
                }
            }
            Set set = this.a;
            if (set != null) {
                set.add(gwVar.a);
                return;
            }
            return;
        }
        we0 we0Var = this.k;
        if (we0Var != null && we0Var.c(gwVar)) {
            return;
        }
        this.f.b(gwVar);
    }

    public final void g(Set set, wh whVar) {
        a();
        this.a = set;
        this.b = whVar;
    }
}
