package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tk extends sz0 implements kv {
    public final /* synthetic */ int i = 0;
    public int j;
    public final /* synthetic */ float k;
    public /* synthetic */ Object l;
    public final /* synthetic */ Object m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public tk(c9 c9Var, float f, c7 c7Var, ij ijVar) {
        super(2, ijVar);
        this.l = c9Var;
        this.k = f;
        this.m = c7Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((tk) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((tk) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.m;
        float f = this.k;
        switch (i) {
            case 0:
                tk tkVar = new tk((al) obj2, f, ijVar);
                tkVar.l = obj;
                return tkVar;
            default:
                return new tk((c9) this.l, f, (c7) obj2, ijVar);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        Object obj2 = this.m;
        float f = this.k;
        ik ikVar = ik.e;
        switch (i) {
            case 0:
                hk hkVar = (hk) this.l;
                int i2 = this.j;
                if (i2 != 0) {
                    if (i2 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                al alVar = (al) obj2;
                nf0 nf0Var = alVar.q;
                sk skVar = new sk(alVar, f, hkVar, null);
                this.l = null;
                this.j = 1;
                nf0Var.getClass();
                if (dl.q(new kf0(gf0.e, nf0Var, skVar, null), this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
                int i3 = this.j;
                if (i3 != 0) {
                    if (i3 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                this.j = 1;
                if (y6.c((y6) ((c9) this.l).c, new Float(f), (c7) obj2, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public tk(al alVar, float f, ij ijVar) {
        super(2, ijVar);
        this.m = alVar;
        this.k = f;
    }
}
