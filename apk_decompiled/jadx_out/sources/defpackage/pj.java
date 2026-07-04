package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pj extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ float k;
    public final /* synthetic */ float l;
    public final /* synthetic */ Object m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ pj(Object obj, float f, float f2, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.m = obj;
        this.k = f;
        this.l = f2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((pj) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((pj) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.m;
        switch (i) {
            case 0:
                return new pj((y6) obj2, this.k, this.l, ijVar, 0);
            default:
                return new pj((zt0) obj2, this.k, this.l, ijVar, 1);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        ay0 ay0Var;
        int i = this.i;
        x31 x31Var = x31.a;
        float f = this.l;
        float f2 = this.k;
        Object obj2 = this.m;
        ik ikVar = ik.e;
        switch (i) {
            case 0:
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
                y6 y6Var = (y6) obj2;
                Float f3 = new Float(f2);
                if (f2 > 0.5f) {
                    ay0Var = new ay0(0.5f, 300.0f, new Float(5.0E-4f));
                } else {
                    ay0Var = new ay0(1.0f, 300.0f, new Float(0.01f));
                }
                ay0 ay0Var2 = ay0Var;
                Float f4 = new Float(f / 1000.0f);
                this.j = 1;
                if (y6.c(y6Var, f3, ay0Var2, f4, null, this, 8) == ikVar) {
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
                hu0 hu0Var = ((zt0) obj2).R;
                long floatToRawIntBits = Float.floatToRawIntBits(f2);
                long floatToRawIntBits2 = Float.floatToRawIntBits(f);
                this.j = 1;
                if (n20.h(hu0Var, (floatToRawIntBits << 32) | (4294967295L & floatToRawIntBits2), this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }
}
