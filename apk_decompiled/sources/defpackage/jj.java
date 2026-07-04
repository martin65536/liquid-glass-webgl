package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class jj extends s9 {
    public final yj f;
    public transient ij g;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public jj(defpackage.ij r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L7
            yj r0 = r2.r()
            goto L8
        L7:
            r0 = 0
        L8:
            r1.<init>(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.jj.<init>(ij):void");
    }

    @Override // defpackage.s9
    public void l() {
        ij ijVar = this.g;
        if (ijVar != null && ijVar != this) {
            wj j = r().j(x1.A);
            j.getClass();
            in inVar = (in) ijVar;
            inVar.i();
            pc k = inVar.k();
            if (k != null) {
                k.l();
            }
        }
        this.g = pf.f;
    }

    @Override // defpackage.ij
    public yj r() {
        yj yjVar = this.f;
        yjVar.getClass();
        return yjVar;
    }

    public jj(ij ijVar, yj yjVar) {
        super(ijVar);
        this.f = yjVar;
    }
}
