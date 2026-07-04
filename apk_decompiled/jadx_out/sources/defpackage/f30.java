package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class f30 extends l30 {
    public final boolean i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public f30(d30 d30Var) {
        super(true);
        td tdVar;
        td tdVar2;
        boolean z = true;
        T(d30Var);
        sd P = P();
        if (P instanceof td) {
            tdVar = (td) P;
        } else {
            tdVar = null;
        }
        if (tdVar != null) {
            l30 q = tdVar.q();
            while (!q.M()) {
                sd P2 = q.P();
                if (P2 instanceof td) {
                    tdVar2 = (td) P2;
                } else {
                    tdVar2 = null;
                }
                if (tdVar2 != null) {
                    q = tdVar2.q();
                }
            }
            this.i = z;
        }
        z = false;
        this.i = z;
    }

    @Override // defpackage.l30
    public final boolean M() {
        return this.i;
    }

    @Override // defpackage.l30
    public final boolean N() {
        return true;
    }
}
