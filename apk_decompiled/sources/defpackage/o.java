package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o extends z30 implements kv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o(int i, int i2, Object obj) {
        super(2);
        this.f = i2;
        this.g = obj;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        int i = this.f;
        int i2 = 7;
        x31 x31Var = x31.a;
        Object obj3 = this.g;
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
                    ((p) obj3).a(bwVar, 0);
                } else {
                    bwVar.R();
                }
                return x31Var;
            case 1:
                up upVar = (up) obj;
                gv gvVar = (gv) obj2;
                upVar.getClass();
                gvVar.getClass();
                gvVar.e(upVar);
                upVar.M((hx) obj3, d20.I(upVar.j()), new q2(0, gvVar));
                return x31Var;
            case 2:
                ((Number) obj2).intValue();
                k81.b((gg) obj3, (bw) obj, d20.O(7));
                return x31Var;
            case 3:
                ((Number) obj2).intValue();
                ((kh) obj3).a((bw) obj, d20.O(1));
                return x31Var;
            case 4:
                cd0 cd0Var = (cd0) obj;
                cd0 cd0Var2 = (ad0) obj2;
                bw bwVar2 = (bw) obj3;
                if (cd0Var2 instanceof oh) {
                    ce ceVar = ((oh) cd0Var2).a;
                    f31.n(3, ceVar);
                    cd0Var2 = dl.A(bwVar2, (cd0) ceVar.c(zc0.a, bwVar2, 0));
                }
                return cd0Var.b(cd0Var2);
            case 5:
                ((Number) obj2).intValue();
                o4.d((gv) obj3, (bw) obj, d20.O(1));
                return x31Var;
            case 6:
                ((Number) obj2).intValue();
                o4.g((String) obj3, (bw) obj, d20.O(7));
                return x31Var;
            default:
                um0 um0Var = (um0) obj;
                long j = ((ch0) obj2).a;
                um0Var.getClass();
                k20 k20Var = (k20) obj3;
                f31.G(k20Var.a, null, new d(k20Var, um0Var, null, i2), 3);
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o(int i, Object obj) {
        super(2);
        this.f = i;
        this.g = obj;
    }
}
