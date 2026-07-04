package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rd extends h30 {
    public final pc l;

    public rd(pc pcVar) {
        this.l = pcVar;
    }

    @Override // defpackage.h30
    public final boolean r() {
        return true;
    }

    @Override // defpackage.h30
    public final void s(Throwable th) {
        boolean m;
        l30 q = q();
        pc pcVar = this.l;
        Throwable n = pcVar.n(q);
        if (!pcVar.A()) {
            m = false;
        } else {
            m = ((in) pcVar.h).m(n);
        }
        if (!m) {
            pcVar.x(n);
            if (!pcVar.A()) {
                pcVar.l();
            }
        }
    }
}
