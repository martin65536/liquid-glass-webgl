package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ce0 extends gh {
    public final ve0 b;
    public final ArrayList c;
    public final we0 d;
    public final ve0 e;
    public final g2 f;

    public ce0() {
        super(2);
        this.b = t20.n();
        this.c = new ArrayList();
        we0 we0Var = at0.a;
        this.d = new we0();
        this.e = new ve0();
        wa waVar = new wa(5, this);
        cx0.e(cx0.a);
        synchronized (cx0.c) {
            cx0.h = me.a0(cx0.h, waVar);
        }
        this.f = new g2(waVar);
    }

    @Override // defpackage.gh
    public final void d(jv0 jv0Var) {
        this.c.add(new ae0(jv0Var));
    }

    @Override // defpackage.gh
    public final void e() {
        synchronized (this.a) {
            try {
                ArrayList arrayList = this.c;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    be0 be0Var = (be0) arrayList.get(i);
                    if (be0Var instanceof zd0) {
                        t20.g(this.b, ((zd0) be0Var).a, ((zd0) be0Var).b);
                    } else if (be0Var instanceof ae0) {
                        t20.J(this.b, ((ae0) be0Var).a);
                    } else {
                        throw new RuntimeException();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.c.clear();
    }

    @Override // defpackage.gh
    public final void g() {
        this.f.a();
        this.c.clear();
        this.e.a();
        synchronized (this.a) {
            this.b.a();
        }
    }

    @Override // defpackage.gh
    public final gv i(jv0 jv0Var) {
        ve0 ve0Var = this.e;
        gv gvVar = (gv) ve0Var.g(jv0Var);
        if (gvVar == null) {
            gvVar = new c(14, this, jv0Var);
            int f = ve0Var.f(jv0Var);
            if (f < 0) {
                f = ~f;
            }
            Object[] objArr = ve0Var.c;
            Object obj = objArr[f];
            ve0Var.b[f] = jv0Var;
            objArr[f] = gvVar;
        }
        return gvVar;
    }

    @Override // defpackage.gh
    public final void j(ed edVar) {
        this.e.k(edVar);
        d(edVar);
        e();
    }
}
