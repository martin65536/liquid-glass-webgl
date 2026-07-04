package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class my0 extends py0 {
    public b0 c;
    public int d;
    public int e;

    public my0(long j, b0 b0Var) {
        super(j);
        this.c = b0Var;
    }

    @Override // defpackage.py0
    public final void a(py0 py0Var) {
        synchronized (o4.h) {
            py0Var.getClass();
            this.c = ((my0) py0Var).c;
            this.d = ((my0) py0Var).d;
            this.e = ((my0) py0Var).e;
        }
    }

    @Override // defpackage.py0
    public final py0 b(long j) {
        return new my0(j, this.c);
    }
}
