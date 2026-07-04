package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pa extends z30 implements mv {
    public final /* synthetic */ q41 f;
    public final /* synthetic */ da g;
    public final /* synthetic */ long h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public pa(q41 q41Var, da daVar, long j) {
        super(4);
        this.f = q41Var;
        this.g = daVar;
        this.h = j;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        int i;
        c40 c40Var = (c40) obj2;
        bw bwVar = (bw) obj3;
        int intValue = ((Number) obj4).intValue();
        ((cb) obj).getClass();
        c40Var.getClass();
        if ((intValue & 48) == 0) {
            if (bwVar.f(c40Var)) {
                i = 32;
            } else {
                i = 16;
            }
            intValue |= i;
        }
        if ((intValue & 145) != 144) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(intValue & 1, z)) {
            ef a = cf.a(new x7(32.0f, true, new v7(0)), x1.r, bwVar, 6);
            long j = bwVar.T;
            int i2 = (int) (j ^ (j >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, zc0.a);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, a);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i2));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            q41 q41Var = this.f;
            da daVar = this.g;
            long j2 = this.h;
            k81.b(jc0.C(-1498348163, new oa(c40Var, q41Var, daVar, j2, 0), bwVar), bwVar, 6);
            k81.b(jc0.C(-1703571788, new oa(c40Var, q41Var, daVar, j2, 1), bwVar), bwVar, 6);
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
