package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l60 {
    public final Object a;
    public final n60 b;
    public int d;
    public l60 e;
    public boolean f;
    public int c = -1;
    public final ik0 g = n30.B(null);

    public l60(Object obj, n60 n60Var) {
        this.a = obj;
        this.b = n60Var;
    }

    public final l60 a() {
        if (this.f) {
            t00.c("Pin should not be called on an already disposed item ");
        }
        if (this.d == 0) {
            this.b.e.add(this);
            l60 l60Var = (l60) this.g.getValue();
            if (l60Var != null) {
                l60Var.a();
            } else {
                l60Var = null;
            }
            this.e = l60Var;
        }
        this.d++;
        return this;
    }

    public final void b() {
        if (!this.f) {
            if (this.d <= 0) {
                t00.c("Release should only be called once");
            }
            int i = this.d - 1;
            this.d = i;
            if (i == 0) {
                this.b.e.remove(this);
                l60 l60Var = this.e;
                if (l60Var != null) {
                    l60Var.b();
                }
                this.e = null;
            }
        }
    }
}
