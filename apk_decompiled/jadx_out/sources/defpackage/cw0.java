package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cw0 implements un {
    public final ew0 e;
    public final long f;
    public final Object g;
    public final pc h;

    public cw0(ew0 ew0Var, long j, Object obj, pc pcVar) {
        this.e = ew0Var;
        this.f = j;
        this.g = obj;
        this.h = pcVar;
    }

    @Override // defpackage.un
    public final void a() {
        ew0 ew0Var = this.e;
        synchronized (ew0Var) {
            if (this.f < ew0Var.o()) {
                return;
            }
            Object[] objArr = ew0Var.l;
            objArr.getClass();
            long j = this.f;
            if (objArr[((int) j) & (objArr.length - 1)] != this) {
                return;
            }
            jc0.f(objArr, j, jc0.l);
            ew0Var.j();
        }
    }
}
