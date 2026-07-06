package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hx0 extends py0 {
    public Object c;

    public hx0(long j, Object obj) {
        super(j);
        this.c = obj;
    }

    @Override // defpackage.py0
    public final void a(py0 py0Var) {
        py0Var.getClass();
        this.c = ((hx0) py0Var).c;
    }

    @Override // defpackage.py0
    public final py0 b(long j) {
        return new hx0(cx0.j().g(), this.c);
    }
}
