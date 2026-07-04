package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g90 extends z30 implements kv {
    public final /* synthetic */ int f = 0;
    public final /* synthetic */ vu g;
    public final /* synthetic */ cd0 h;
    public final /* synthetic */ int i;
    public final /* synthetic */ Object j;
    public final /* synthetic */ Object k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g90(vu vuVar, gv gvVar, m9 m9Var, cd0 cd0Var, int i) {
        super(2);
        this.g = vuVar;
        this.j = gvVar;
        this.k = m9Var;
        this.h = cd0Var;
        this.i = i;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.f;
        x31 x31Var = x31.a;
        int i2 = this.i;
        Object obj3 = this.k;
        Object obj4 = this.j;
        switch (i) {
            case 0:
                ((Number) obj2).intValue();
                int O = d20.O(i2 | 1);
                vu vuVar = this.g;
                cd0 cd0Var = this.h;
                h90.a((qr0) obj4, vuVar, cd0Var, (gg) obj3, (bw) obj, O);
                return x31Var;
            default:
                ((Number) obj2).intValue();
                int O2 = d20.O(i2 | 1);
                vu vuVar2 = this.g;
                o30.a(vuVar2, (gv) obj4, (m9) obj3, this.h, (bw) obj, O2);
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g90(qr0 qr0Var, vu vuVar, cd0 cd0Var, gg ggVar, int i) {
        super(2);
        this.j = qr0Var;
        this.g = vuVar;
        this.h = cd0Var;
        this.k = ggVar;
        this.i = i;
    }
}
