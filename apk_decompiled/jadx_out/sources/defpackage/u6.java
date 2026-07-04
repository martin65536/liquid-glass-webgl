package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u6 implements f61 {
    public final int a;
    public final String b;
    public final ik0 c = n30.B(g10.e);
    public final ik0 d = n30.B(Boolean.TRUE);

    public u6(String str, int i) {
        this.a = i;
        this.b = str;
    }

    @Override // defpackage.f61
    public final int a(ob0 ob0Var, m40 m40Var) {
        return e().a;
    }

    @Override // defpackage.f61
    public final int b(ob0 ob0Var) {
        return e().b;
    }

    @Override // defpackage.f61
    public final int c(ob0 ob0Var, m40 m40Var) {
        return e().c;
    }

    @Override // defpackage.f61
    public final int d(ob0 ob0Var) {
        return e().d;
    }

    public final g10 e() {
        return (g10) this.c.getValue();
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof u6) {
                if (this.a == ((u6) obj).a) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final void f(boolean z) {
        this.d.setValue(Boolean.valueOf(z));
    }

    public final void g(k71 k71Var, int i) {
        int i2 = this.a;
        if (i != 0 && (i & i2) == 0) {
            return;
        }
        this.c.setValue(k71Var.a.h(i2));
        f(k71Var.a.s(i2));
    }

    public final int hashCode() {
        return this.a;
    }

    public final String toString() {
        return this.b + '(' + e().a + ", " + e().b + ", " + e().c + ", " + e().d + ')';
    }
}
