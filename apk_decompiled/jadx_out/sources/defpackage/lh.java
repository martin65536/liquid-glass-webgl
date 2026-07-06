package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lh extends z30 implements kv {
    public final /* synthetic */ int f = 0;
    public final /* synthetic */ nh g;
    public final /* synthetic */ b4 h;
    public final /* synthetic */ kv i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public lh(b4 b4Var, nh nhVar, kv kvVar) {
        super(2);
        this.h = b4Var;
        this.g = nhVar;
        this.i = kvVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        int i = this.f;
        x31 x31Var = x31.a;
        kv kvVar = this.i;
        b4 b4Var = this.h;
        nh nhVar = this.g;
        switch (i) {
            case 0:
                bw bwVar = (bw) obj;
                int intValue = ((Number) obj2).intValue();
                if ((intValue & 3) != 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    bwVar.V(866651995);
                    fi.a(b4Var, nhVar.k, kvVar, bwVar, 0);
                    bwVar.p(false);
                } else {
                    bwVar.R();
                }
                return x31Var;
            default:
                ((Number) obj2).intValue();
                nhVar.a(b4Var, kvVar, (bw) obj, d20.O(1));
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public lh(nh nhVar, b4 b4Var, kv kvVar, int i) {
        super(2);
        this.g = nhVar;
        this.h = b4Var;
        this.i = kvVar;
    }
}
