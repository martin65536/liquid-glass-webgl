package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dx0 extends py0 {
    public float c;

    public dx0(long j, float f) {
        super(j);
        this.c = f;
    }

    @Override // defpackage.py0
    public final void a(py0 py0Var) {
        py0Var.getClass();
        this.c = ((dx0) py0Var).c;
    }

    @Override // defpackage.py0
    public final py0 b(long j) {
        return new dx0(j, this.c);
    }
}
