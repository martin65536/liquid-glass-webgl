package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tr implements f61 {
    public final f61 a;
    public final f61 b;

    public tr(f61 f61Var, f61 f61Var2) {
        this.a = f61Var;
        this.b = f61Var2;
    }

    @Override // defpackage.f61
    public final int a(ob0 ob0Var, m40 m40Var) {
        int a = this.a.a(ob0Var, m40Var) - this.b.a(ob0Var, m40Var);
        if (a < 0) {
            return 0;
        }
        return a;
    }

    @Override // defpackage.f61
    public final int b(ob0 ob0Var) {
        int b = this.a.b(ob0Var) - this.b.b(ob0Var);
        if (b < 0) {
            return 0;
        }
        return b;
    }

    @Override // defpackage.f61
    public final int c(ob0 ob0Var, m40 m40Var) {
        int c = this.a.c(ob0Var, m40Var) - this.b.c(ob0Var, m40Var);
        if (c < 0) {
            return 0;
        }
        return c;
    }

    @Override // defpackage.f61
    public final int d(ob0 ob0Var) {
        int d = this.a.d(ob0Var) - this.b.d(ob0Var);
        if (d < 0) {
            return 0;
        }
        return d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof tr)) {
            return false;
        }
        tr trVar = (tr) obj;
        if (o20.e(trVar.a, this.a) && o20.e(trVar.b, this.b)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return "(" + this.a + " - " + this.b + ')';
    }
}
