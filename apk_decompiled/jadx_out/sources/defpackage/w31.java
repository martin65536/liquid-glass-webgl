package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w31 implements f61 {
    public final f61 a;
    public final f61 b;

    public w31(f61 f61Var, f61 f61Var2) {
        this.a = f61Var;
        this.b = f61Var2;
    }

    @Override // defpackage.f61
    public final int a(ob0 ob0Var, m40 m40Var) {
        return Math.max(this.a.a(ob0Var, m40Var), this.b.a(ob0Var, m40Var));
    }

    @Override // defpackage.f61
    public final int b(ob0 ob0Var) {
        return Math.max(this.a.b(ob0Var), this.b.b(ob0Var));
    }

    @Override // defpackage.f61
    public final int c(ob0 ob0Var, m40 m40Var) {
        return Math.max(this.a.c(ob0Var, m40Var), this.b.c(ob0Var, m40Var));
    }

    @Override // defpackage.f61
    public final int d(ob0 ob0Var) {
        return Math.max(this.a.d(ob0Var), this.b.d(ob0Var));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof w31)) {
            return false;
        }
        w31 w31Var = (w31) obj;
        if (o20.e(w31Var.a, this.a) && o20.e(w31Var.b, this.b)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (this.b.hashCode() * 31) + this.a.hashCode();
    }

    public final String toString() {
        return "(" + this.a + " ∪ " + this.b + ')';
    }
}
