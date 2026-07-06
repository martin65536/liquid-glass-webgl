package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ex0 extends py0 {
    public int c;

    public ex0(int i, long j) {
        super(j);
        this.c = i;
    }

    @Override // defpackage.py0
    public final void a(py0 py0Var) {
        py0Var.getClass();
        this.c = ((ex0) py0Var).c;
    }

    @Override // defpackage.py0
    public final py0 b(long j) {
        return new ex0(this.c, j);
    }
}
