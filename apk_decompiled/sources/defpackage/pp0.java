package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pp0 extends r implements bk {
    public final /* synthetic */ wh f;
    public final /* synthetic */ qp0 g;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public pp0(defpackage.wh r2, defpackage.qp0 r3) {
        /*
            r1 = this;
            x1 r0 = defpackage.x1.B
            r1.f = r2
            r1.g = r3
            r1.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pp0.<init>(wh, qp0):void");
    }

    @Override // defpackage.bk
    public final void l(yj yjVar, Throwable th) {
        wh whVar = this.f;
        qp0 qp0Var = this.g;
        k81.O(th, new f9(2, whVar, qp0Var));
        bk bkVar = (bk) qp0Var.e.j(x1.B);
        if (bkVar != null) {
            bkVar.l(yjVar, th);
            return;
        }
        throw th;
    }
}
