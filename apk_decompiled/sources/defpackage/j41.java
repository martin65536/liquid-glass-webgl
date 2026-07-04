package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j41 implements f61 {
    public final String a;
    public final ik0 b;

    public j41(m10 m10Var, String str) {
        this.a = str;
        this.b = n30.B(m10Var);
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

    public final m10 e() {
        return (m10) this.b.getValue();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof j41)) {
            return false;
        }
        return o20.e(e(), ((j41) obj).e());
    }

    public final void f(m10 m10Var) {
        this.b.setValue(m10Var);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a + "(left=" + e().a + ", top=" + e().b + ", right=" + e().c + ", bottom=" + e().d + ')';
    }
}
