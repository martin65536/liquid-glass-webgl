package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pr extends qr {
    public final f21 g;

    public pr(long j, f21 f21Var) {
        super(j);
        this.g = f21Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.g.run();
    }

    @Override // defpackage.qr
    public final String toString() {
        return super.toString() + this.g;
    }
}
