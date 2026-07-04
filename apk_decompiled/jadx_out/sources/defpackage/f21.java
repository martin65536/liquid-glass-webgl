package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f21 extends ct0 implements Runnable {
    public final long k;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public f21(long r2, defpackage.g21 r4) {
        /*
            r1 = this;
            yj r0 = r4.f
            r0.getClass()
            r1.<init>(r4, r0)
            r1.k = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.f21.<init>(long, g21):void");
    }

    @Override // defpackage.l30
    public final String X() {
        return super.X() + "(timeMillis=" + this.k + ')';
    }

    @Override // java.lang.Runnable
    public final void run() {
        f31.A(this.i);
        D(new e21("Timed out waiting for " + this.k + " ms", this));
    }
}
