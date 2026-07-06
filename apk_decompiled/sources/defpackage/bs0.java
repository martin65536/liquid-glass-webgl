package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bs0 implements np0 {
    public ss0 e;
    public es0 f;
    public String g;
    public Object h;
    public Object[] i;
    public r7 j;
    public final f6 k = new f6(4, this);

    public bs0(ss0 ss0Var, es0 es0Var, String str, Object obj, Object[] objArr) {
        this.e = ss0Var;
        this.f = es0Var;
        this.g = str;
        this.h = obj;
        this.i = objArr;
    }

    public final void a() {
        String i;
        es0 es0Var = this.f;
        if (this.j == null) {
            if (es0Var != null) {
                f6 f6Var = this.k;
                Object a = f6Var.a();
                if (a != null && !es0Var.c(a)) {
                    if (a instanceof gx0) {
                        gx0 gx0Var = (gx0) a;
                        if (gx0Var.d() != x1.S && gx0Var.d() != dt0.g && gx0Var.d() != x1.V) {
                            i = "If you use a custom SnapshotMutationPolicy for your MutableState you have to write a custom Saver";
                        } else {
                            i = "MutableState containing " + gx0Var.getValue() + " cannot be saved using the current SaveableStateRegistry. The default implementation only supports types which can be stored inside the Bundle. Please consider implementing a custom Saver for this class and pass it as a stateSaver parameter to rememberSaveable().";
                        }
                    } else {
                        i = y20.i(a);
                    }
                    throw new IllegalArgumentException(i);
                }
                this.j = es0Var.a(this.g, f6Var);
                return;
            }
            return;
        }
        v7.i("entry(", this.j, ") is not null");
    }

    @Override // defpackage.np0
    public final void d() {
        a();
    }

    @Override // defpackage.np0
    public final void f() {
        r7 r7Var = this.j;
        if (r7Var != null) {
            r7Var.H();
        }
    }

    @Override // defpackage.np0
    public final void k() {
        r7 r7Var = this.j;
        if (r7Var != null) {
            r7Var.H();
        }
    }
}
