package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ei extends z30 implements kv {
    public final /* synthetic */ int f = 1;
    public final /* synthetic */ kv g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ei(mj0 mj0Var, dt0 dt0Var, kv kvVar, int i) {
        super(2);
        this.h = mj0Var;
        this.i = dt0Var;
        this.g = kvVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        int i = this.f;
        x31 x31Var = x31.a;
        kv kvVar = this.g;
        Object obj3 = this.i;
        Object obj4 = this.h;
        int i2 = 1;
        switch (i) {
            case 0:
                ((Number) obj2).intValue();
                fi.a((mj0) obj4, (dt0) obj3, kvVar, (bw) obj, d20.O(1));
                return x31Var;
            default:
                bw bwVar = (bw) obj;
                int intValue = ((Number) obj2).intValue();
                f81 f81Var = (f81) obj4;
                int i3 = 0;
                if ((intValue & 3) != 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    b4 b4Var = f81Var.e;
                    boolean h = bwVar.h(f81Var);
                    Object L = bwVar.L();
                    ij ijVar = null;
                    dt0 dt0Var = ph.a;
                    if (h || L == dt0Var) {
                        L = new e81(f81Var, ijVar, i3);
                        bwVar.f0(L);
                    }
                    dl.i((kv) L, bwVar, b4Var);
                    boolean h2 = bwVar.h(f81Var);
                    Object L2 = bwVar.L();
                    if (h2 || L2 == dt0Var) {
                        L2 = new e81(f81Var, ijVar, i2);
                        bwVar.f0(L2);
                    }
                    dl.i((kv) L2, bwVar, b4Var);
                    ((nh) obj3).a(b4Var, kvVar, bwVar, 0);
                } else {
                    bwVar.R();
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ei(f81 f81Var, nh nhVar, kv kvVar) {
        super(2);
        this.h = f81Var;
        this.i = nhVar;
        this.g = kvVar;
    }
}
